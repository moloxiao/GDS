package com.tallbigup.android.gds.nativenotify;

public class NativeNotifyConstants {

	/**
	 * 用于区分是不是从本地提醒的进入
	 */
	public static final String START_BY_NOTIFY_KEY_STARTTYPE = 
			"com.tallbigup.android.cloud.extend.nativenotify.starttype";
	public static final int START_BY_PUSH = 1;
	
	/**
	 * 信息key-notification标题
	 */
	public static final String NOTIFY_INOF_KEY_TITLE = 
			"com.tallbigup.android.cloud.extend.nativenotify.infokey.title";
	
	/**
	 * 信息key-notification内容
	 */
	public static final String NOTIFY_INOF_KEY_CONTENT = 
			"com.tallbigup.android.cloud.extend.nativenotify.infokey.content";
	
	/**
	 * 信息key-notification内容
	 */
	public static final String NOTIFY_INOF_KEY_PACKAGEINFO = 
			"com.tallbigup.android.cloud.extend.nativenotify.infokey.packageinfo";
	
	/**
	 * 信息key-notification内容
	 */
	public static final String NOTIFY_INOF_KEY_LAUNCHINFO = 
			"com.tallbigup.android.cloud.extend.nativenotify.infokey.launchinfo";
	
	/**
	 * 信息key-notification内容
	 */
	public static final String NOTIFY_INOF_KEY_ICONID = 
			"com.tallbigup.android.cloud.extend.nativenotify.infokey.iconid";
	
	/**
	 * 用于获取本地游戏提醒的NotificationID，用于删除Notification提醒
	 */
	public static final String START_BY_NOTIFY_KEY_SEQ = 
			"com.tallbigup.android.cloud.extend.nativenotify.seq";
	
	public static final String METADATA_PACKAGEINFO = "package_info";
	
	public static final String METADATA_LAUNCHINFO = "launch_info";
	
	public static final String METADATA_PUSHICON = "push_icon";
}
