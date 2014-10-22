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
	private final static String KEY_PRE_QUIT_TIME = "KEY_PRE_QUIT_TIME";

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

	/**
	 * 获取体力上限
	 * 
	 * @param context
	 * @return
	 */
	public int getMaxNumber(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		return preferences.getInt(KEY_PRE_MAX_NUMBER, 5);
	}

	/**
	 * 设置体力上限
	 * 
	 * @param context
	 * @param maxNumber
	 */
	public void setMaxNumber(Context context, int maxNumber) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(KEY_PRE_MAX_NUMBER, maxNumber);
		editor.commit();
	}

	/**
	 * 获取当前的体力值,下一次恢复的时间豪秒
	 * 
	 * @param context
	 * @return
	 */
	public PlayerState getCurrentNumber(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		int temp = preferences.getInt(KEY_PRE_CURRENT_NUMBER, 5);
		long time = System.currentTimeMillis() - getQuitTime(context);
		if (time > 0) {
			if (temp < getMaxNumber(context)) {
				temp += time / (getResetTime(context) * 1000);
				time = time % (getResetTime(context) * 1000);
			}
		}
		PlayerState state = new PlayerState(temp, time);
		return state;
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
	 * 设置离开游戏时的体力值,系统时间毫秒
	 * 
	 * @param context
	 * @param maxNumber
	 */
	public void setCurrentNumber(Context context, PlayerState playerState) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(KEY_PRE_CURRENT_NUMBER, playerState.getCurrentPower());
		editor.putLong(KEY_PRE_QUIT_TIME, playerState.getSecond());
		editor.commit();
	}

	// 获取体力刷新间隔
	public int getResetTime(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		return preferences.getInt(KEY_PRE_RESET_TIME, 1800);
	}

	// 获取退出的系统时间
	private long getQuitTime(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		return preferences.getLong(KEY_PRE_QUIT_TIME, 0);
	}

	/**
	 * 消耗体力值
	 * 
	 * @param context
	 * @return 剩余体力值
	 */
	// public int consumePower(Context context) {
	// SharedPreferences preferences = context.getSharedPreferences(
	// KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
	// int temp = preferences.getInt(KEY_PRE_CURRENT_NUMBER, 5);
	// temp--;
	// SharedPreferences.Editor editor = preferences.edit();
	// editor.putInt(KEY_PRE_CURRENT_NUMBER, temp);
	// editor.commit();
	// return temp;
	// }

	/**
	 * 时间刷新接口
	 * 
	 * @author qiuzhong
	 * 
	 */
	// public interface updateTime {
	// void updateResetTime(int second);
	// }
}
