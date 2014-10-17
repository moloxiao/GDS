package com.tallbigup.android.gds.nativenotify;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class NotifyManager {

	/**
	 * 根据传入Notification的Id值，清除对应ID的通知栏显示。
	 * @param context android上下文
	 * @param id 需要清除的notification ID
	 */
	public static void cleanNofitifcation(Context context, int id){
		NotificationManager manager =
			    (NotificationManager) (context.getSystemService(Context.NOTIFICATION_SERVICE));
		manager.cancel(id);
	}
	
	/**
	 * 根据传入intent值计算出Notification的Id值，清除对应ID的通知栏显示。
	 * @param context
	 * @param intent
	 */
	public static void cleanNofitifcation(Context context, Intent intent){
		if(intent == null) {
			return;
		}
		if(intent.getExtras() == null) {
			return;
		}
		
		NotificationManager manager =
			    (NotificationManager) (context.getSystemService(Context.NOTIFICATION_SERVICE));
		int id = intent.getExtras().getInt(NativeNotifyConstants.START_BY_NOTIFY_KEY_SEQ);
		manager.cancel(id);
	}
	
	public static boolean isStartByNotifaction(Intent intent) {
		if(intent == null) {
			return false;
		}
		
		if(intent.getExtras() == null) {
			return false;
		}
		
		if(intent.getExtras().getInt(NativeNotifyConstants.START_BY_NOTIFY_KEY_STARTTYPE) ==
				NativeNotifyConstants.START_BY_PUSH) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 显示提醒用户进入游戏的notification。
	 * @param context
	 */
	public static void showLoginGameNotify (
			Context context, 
			String title, 
			String content, 
			int seq, 
			String packinfo, 
			String launchInfo,  
			int iconId) {
		
		Intent intent = new Intent();
		
		if(isPackageInstall(context, packinfo)) {
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packinfo, launchInfo);
            intent.putExtra(
            			NativeNotifyConstants.START_BY_NOTIFY_KEY_STARTTYPE, 
            			NativeNotifyConstants.START_BY_PUSH);
            intent.putExtra(
            		NativeNotifyConstants.START_BY_NOTIFY_KEY_SEQ, 
            		seq);
            intent.setComponent(cn);
		}else {
            return ;
		}		
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, seq, intent, 0);
		Notification notifycation = new Notification();
		notifycation.icon = iconId;
		notifycation.defaults = Notification.DEFAULT_ALL;
		notifycation.flags |= Notification.FLAG_AUTO_CANCEL;
		notifycation.setLatestEventInfo(context, title, content, contentIntent);
		
		NotificationManager manager =
			    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(seq, notifycation);
	}
	
	/**
	 * 设置下次检查服务器消息的时间。
	 * @param context
	 * @param time 单位毫秒。下次启动请求的时间。
	 */
	public static void setNextNotification(Context context, 
			long time, 
			String title, 
			String content) {

		ComponentName cn = new ComponentName(context, 
				"com.tallbigup.android.cloud.extend.nativenotify.NativeNotifyReceiver");
	    ActivityInfo info;
	    
	    try {
			info = context.getPackageManager().getReceiverInfo(cn, PackageManager.GET_META_DATA);
			
			Bundle bundle1 = info.metaData;
			String packageinfo = bundle1.getString(NativeNotifyConstants.METADATA_PACKAGEINFO);
			String launchInfo = bundle1.getString(NativeNotifyConstants.METADATA_LAUNCHINFO);
			int iconId = bundle1.getInt(NativeNotifyConstants.METADATA_PUSHICON);
			
			AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        Intent alarmIntent = new Intent(context, NativeNotifyReceiver.class);
	        alarmIntent.putExtra(NativeNotifyConstants.NOTIFY_INOF_KEY_TITLE, title);
	        alarmIntent.putExtra(NativeNotifyConstants.NOTIFY_INOF_KEY_CONTENT, content);
	        alarmIntent.putExtra(NativeNotifyConstants.NOTIFY_INOF_KEY_PACKAGEINFO, packageinfo);
	        alarmIntent.putExtra(NativeNotifyConstants.NOTIFY_INOF_KEY_LAUNCHINFO, launchInfo);
	        alarmIntent.putExtra(NativeNotifyConstants.NOTIFY_INOF_KEY_ICONID, iconId);
	        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
	        
	        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pIntent);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void cleanGameNotification(Context context) {
		Intent alarmIntent = new Intent(context, NativeNotifyReceiver.class);
		PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pIntent);
	}
	
	/**
	 * 判断某个应用是否已经安装
	 * @param context
	 * @param packinfo
	 * @return
	 */
	public static boolean isPackageInstall(Context context, String packinfo) {
		PackageInfo packageInfo;

		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					packinfo, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		if (packageInfo == null) {
			return false;
		} else {
			return true;
		}
	}
	
	private static final String SHAREDPREFERENCES_DB_NAME_PUSH = 
			"SHAREDPREFERENCES_DB_NAME_PUSH";
	private static final String SHAREDPREFERENCES_KEY_MESSAGE_SEQ = 
			"SHAREDPREFERENCES_KEY_MESSAGE_SEQ";
	
	public static int getMessageSeqId(Context context){
		if(context == null){
			return 0;
		}
		SharedPreferences dbPush = context.getSharedPreferences(
				SHAREDPREFERENCES_DB_NAME_PUSH, 0);
		int id = dbPush.getInt(SHAREDPREFERENCES_KEY_MESSAGE_SEQ, 0);
		return id;
	}
	
	public static boolean setMessageSeqId(Context context, int id){
		if(context == null){
			return false;
		}
		SharedPreferences dbPush = context.getSharedPreferences(
				SHAREDPREFERENCES_DB_NAME_PUSH, 0);
		SharedPreferences.Editor editor = dbPush.edit();
		editor.putInt(SHAREDPREFERENCES_KEY_MESSAGE_SEQ, id);
		return editor.commit();
	}
	
}
