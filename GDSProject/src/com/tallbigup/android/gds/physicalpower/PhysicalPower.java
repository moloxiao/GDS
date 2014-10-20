package com.tallbigup.android.gds.physicalpower;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 单例实现
 * 
 * @author qiuzhong
 * 
 */
public class PhysicalPower {
	private final static String KEY_PRE_PHYSICALPOWER = "KEY_PRE_PHYSICALPOWER";
	private final static String KEY_PRE_MAX_NUMBER = "KEY_PRE_MAX_NUMBER";
	private final static String KEY_PRE_CURRENT_NUMBER = "KEY_PRE_CURRENT_NUMBER";
	private final static String KEY_PRE_RESET_TIME = "KEY_PRE_RESET_TIME";
	private boolean startReset = false;

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
	 * 获取当前的体力值
	 * 
	 * @param context
	 * @return
	 */
	public int getCurrentNumber(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		return preferences.getInt(KEY_PRE_CURRENT_NUMBER, 5);
	}

	/**
	 * 设置当前的体力值
	 * 
	 * @param context
	 * @param maxNumber
	 */
	public void setCurrentNumber(Context context, int maxNumber) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(KEY_PRE_CURRENT_NUMBER, maxNumber);
		editor.commit();
	}

	/**
	 * 获取体力刷新间隔
	 * 
	 * @param context
	 * @return
	 */
	public int getResetTime(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		return preferences.getInt(KEY_PRE_RESET_TIME, 1800);
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
	 * 体力消耗
	 */
	public void consumePower(Context context) {
		// currentNumber -= 1;
		// setCurrentNumber(context, currentNumber);
	}

	/**
	 * 
	 * @param context
	 * @param num
	 *            购买的体力值
	 */
	public void plusPower(Context context, int num) {
		// currentNumber += num;
		// setCurrentNumber(context, currentNumber);
	}

}
