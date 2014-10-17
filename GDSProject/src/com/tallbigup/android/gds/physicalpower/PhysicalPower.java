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
	private final static String KEY_PRE_RESET_TIME = "KEY_PRE_RESET_TIME";
	private final static int maxNumber = 5;
	private int currentNumber;
	private int resetTime;
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

	private int getMaxNumber(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		currentNumber = preferences.getInt(KEY_PRE_MAX_NUMBER, 1800);
		return currentNumber;
	}

	private void setMaxNumber(Context context, int maxNumber) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(KEY_PRE_MAX_NUMBER, maxNumber);
		editor.commit();
	}

	private int getResetTime(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				KEY_PRE_PHYSICALPOWER, Context.MODE_PRIVATE);
		resetTime = preferences.getInt(KEY_PRE_RESET_TIME, 1800);
		return resetTime;
	}

	private void setResetTime(Context context, int resetTime) {
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
		currentNumber -= 1;
		setMaxNumber(context, currentNumber);
	}

	/**
	 * 
	 * @param context
	 * @param num
	 *            购买的体力值
	 */
	public void plusPower(Context context, int num) {
		currentNumber += num;
		setMaxNumber(context, currentNumber);
	}

}
