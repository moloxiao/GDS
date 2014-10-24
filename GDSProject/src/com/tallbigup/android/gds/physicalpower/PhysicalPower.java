package com.tallbigup.android.gds.physicalpower;

import android.content.Context;
import android.content.SharedPreferences;

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

	public void init(Context context) {
		this.context = context;
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
	 * @param resetTime
	 */
	public void setResetTime(Context context, int resetTime) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(KEY_PRE_RESET_TIME, resetTime);
		editor.commit();
	}

	/**
	 * 消耗体力
	 */
	public void consumePower(int number) {
		int currentPower = getCurrentPower();
		currentPower -= number;
		saveCurrentPower(currentPower);
	}

	/**
	 * 添加体力
	 */
	public void plusPower(int number) {
		int currentPower = getCurrentPower();
		currentPower += number;
		saveCurrentPower(currentPower);
	}

	/**
	 * 游戏开始时调用
	 */
	public void getStartPower() {
		// TODO
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

	public interface OmgInterface {
		// TODO
	}

}
