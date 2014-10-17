package com.tallbigup.android.gds.sign;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 基本签到模式实现。
 * @author molo
 *
 */
public class SignManager {
	
	private static final String STORE_NAME = "SignManager";
	private static final String STORE_KEY_NOTIFYPLAYER = "STORE_KEY_NOTIFYPLAYER";
	private static final String STORE_KEY_SIGNDAY = "STORE_KEY_SIGNDAY";
	/**
	 * 连续签到天数
	 */
	private static final String STORE_KEY_SIGNDAYS = "STORE_KEY_SIGNDAYS";

	/**
	 * 获得已经连续签到几天的信息（今天不算的连续签到信息）。
	 */
	public static int getCurrentSignDays(Context context) {
		if(SignUtil.getBetweenDay(
				SignUtil.getDateByString(SignManager.getLastSignDay(context)), new Date()) <= 1) {
		}else {
			SignManager.saveSignDays(context, 0);
			return 0;
		}
		return SignManager.getSignDays(context);
	}
	
	/**
	 * 将连续签到天数设置为0
	 * @param context
	 */
	public static void cleanSignDays(Context context) {
		SignManager.saveSignDays(context, 0);
	}
	
	/**
	 * 存储连续签到天数。未检测参数，不要传入小于0的数字。
	 * @param context
	 * @param oldSignDay
	 */
	private static void saveSignDays(Context context, int oldSignDay) {
		SharedPreferences sharePreference = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharePreference.edit();
		editor.putInt(STORE_KEY_SIGNDAYS, oldSignDay);
		editor.commit();
	}
	
	private static int getSignDays(Context context) {
		SharedPreferences sharePreference = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
		return sharePreference.getInt(STORE_KEY_SIGNDAYS, 0);
	}
	
	/**
	 * 今日是否签到过
	 * @param context
	 * @return true,今天签到过。false，今天未签到过。
	 */
	public static boolean isSignToday(Context context) {
		if(SignUtil.getBetweenDay(
				new Date(), SignUtil.getDateByString(SignManager.getLastSignDay(context)) ) == 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 签到
	 * @param context
	 */
	public static void sign(Context context) {
		if(!isSignToday(context)) {	// 如果今天没有签到过
			SignManager.saveLastSignDay(context, SignUtil.getStringByDate(new Date()));
			SignManager.saveSignDays(context, SignManager.getCurrentSignDays(context)+1);
			if(getNeedNotifyPlayer(context)) {
				// TODO : 设置本地提醒任务
			}
		}
	}
	
	/**
	 * 获得上次签到的时间
	 * @param context
	 * @return
	 */
	private static String getLastSignDay(Context context) {
		SharedPreferences sharePreference = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
		return sharePreference.getString(STORE_KEY_SIGNDAY, "");
	}
	
	/**
	 * 存储最近签到日期
	 * @param context
	 * @param dstr
	 * @return
	 */
	private static String saveLastSignDay(Context context, String dstr) {
		SharedPreferences sharePreference = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharePreference.edit();
		editor.putString(STORE_KEY_SIGNDAY, dstr);
		editor.commit();
		return sharePreference.getString(STORE_KEY_SIGNDAY, "");
	}
	
	
	/**
	 * 是否需要本地提醒。
	 * @param context
	 * @param need
	 * @return
	 */
	public static boolean setNeedNotifyPlayer(Context context, boolean need) {
		SharedPreferences sharePreference = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharePreference.edit();
		editor.putBoolean(STORE_KEY_NOTIFYPLAYER, need);
		editor.commit();
		return getNeedNotifyPlayer(context);
	}
	
	/**
	 * TODO : 是否需要本地提醒(此功能暂未实现)。
	 * @return
	 */
	private static boolean getNeedNotifyPlayer(Context context) {
		SharedPreferences sharePreference = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
		return sharePreference.getBoolean(STORE_KEY_NOTIFYPLAYER, false);
	}
}
