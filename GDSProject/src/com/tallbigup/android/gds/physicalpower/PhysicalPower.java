package com.tallbigup.android.gds.physicalpower;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;

/**
 * 单例
 * 
 * @author qiuzhong
 * 
 */
public class PhysicalPower {

	private final static String KEY_PRE_PHYSICALPOWER = "KEY_PRE_PHYSICALPOWER";
	private final static String KEY_PRE_MAX_NUMBER = "KEY_PRE_MAX_NUMBER";
	private final static String KEY_PRE_CURRENT_NUMBER = "KEY_PRE_CURRENT_NUMBER";
	private final static String KEY_PRE_RESET_TIME = "KEY_PRE_RESET_TIME";
	private final static String KEY_PRE_LAST_RECOVER_TIME = "KEY_PRE_LAST_RECOVER_TIME";

	public static int TIME = 0;
	public static final int MAX_POWER = 5;
	public static final int RECOVER_TIME = 1800;// 秒
	private Context context;
	private PowerUpdateInterface powerUpdateInterface;

	private PhysicalPower() {
		// 私有构造
	}

	private static PhysicalPower instatnce = null;

	public static PhysicalPower getInstance() {
		if (null != instatnce) {
			return instatnce;
		} else {
			return new PhysicalPower();
		}
	}

	public int getPowerInfo(Context context, PowerUpdateInterface pinterface) {
		this.context = context;
		this.powerUpdateInterface = pinterface;
		long d_value = System.currentTimeMillis() - getLastRecoverTime();
		int number = (int) ((d_value / 1000) / getResetTime());
		if (number > getMaxNumber()) {
			number = getMaxNumber();
		} else {
			saveLastRecoverTime(getLastRecoverTime() + number * getResetTime()
					* 1000);
			calculaTime();
		}
		return number;
	}

	/**
	 * 获取体力上限
	 * 
	 * @return
	 */
	public int getMaxNumber() {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		return preferences.getInt(KEY_PRE_MAX_NUMBER, MAX_POWER);
	}

	/**
	 * 设置体力上限
	 * 
	 * @param maxNumber
	 */
	public void setMaxNumber(int maxNumber) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(KEY_PRE_MAX_NUMBER, maxNumber);
		editor.commit();
	}

	/**
	 * 设置体力刷新间隔
	 * 
	 * @param context
	 * @param resetTime秒
	 */
	public void setResetTime(int resetTime) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(KEY_PRE_RESET_TIME, resetTime);
		editor.commit();
	}

	/**
	 * 获取刷新间隔秒
	 * 
	 * @return
	 */
	public int getResetTime() {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		return preferences.getInt(KEY_PRE_RESET_TIME, RECOVER_TIME);
	}

	/**
	 * 消耗体力
	 */
	public boolean consumePower(int number) {
		int currentPower = getCurrentPower();
		if (currentPower >= getMaxNumber()) {
			saveLastRecoverTime(System.currentTimeMillis());// 体力满值的情况
		}
		currentPower -= number;
		if (currentPower >= 0) {
			saveCurrentPower(currentPower);
			return true;
		}
		return false;
	}

	/**
	 * 添加体力
	 */
	public boolean plusPower(int number) {
		int currentPower = getCurrentPower();
		currentPower += number;
		saveCurrentPower(currentPower);
		return true;
	}

	// 获取当前体力值
	private int getCurrentPower() {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		return preferences.getInt(KEY_PRE_CURRENT_NUMBER, MAX_POWER);
	}

	// 保存当前体力值
	private void saveCurrentPower(int powers) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(KEY_PRE_CURRENT_NUMBER, powers);
		editor.commit();
	}

	// 获取上一次体力回复的时间
	private int getLastRecoverTime() {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		return preferences.getInt(KEY_PRE_LAST_RECOVER_TIME, 0);
	}

	// 保存上一次体力恢复时间
	private void saveLastRecoverTime(long systime) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(KEY_PRE_LAST_RECOVER_TIME, systime);
		editor.commit();
	}

	private void calculaTime() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				do {
					TIME = getResetTime() - 1;
					if (System.currentTimeMillis() - getLastRecoverTime() >= getResetTime() * 1000) {
						saveLastRecoverTime(System.currentTimeMillis());
						powerUpdateInterface.updatePowerRecoverTime();
						TIME = 0;
					}
				} while (getCurrentPower() == getMaxNumber());
			}
		};
		timer.schedule(task, 0, 1000);
	}

	public interface PowerUpdateInterface {
		public void updatePowerRecoverTime();
	}

}
