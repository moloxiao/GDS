package com.tallbigup.android.gds.nativenotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NativeNotifyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Bundle bundle = intent.getExtras();
		
		String title = bundle.getString(NativeNotifyConstants.NOTIFY_INOF_KEY_TITLE);
		String content = bundle.getString(NativeNotifyConstants.NOTIFY_INOF_KEY_CONTENT);
		String packageinfo = bundle.getString(NativeNotifyConstants.NOTIFY_INOF_KEY_PACKAGEINFO);
		String launchinfo = bundle.getString(NativeNotifyConstants.NOTIFY_INOF_KEY_LAUNCHINFO);
		int iconId = bundle.getInt(NativeNotifyConstants.NOTIFY_INOF_KEY_ICONID);
		
		NotifyManager.setMessageSeqId(context, NotifyManager.getMessageSeqId(context));
		
		NotifyManager.showLoginGameNotify(context, 
				title, 
				content, 
				NotifyManager.getMessageSeqId(context), 
				packageinfo, 
				launchinfo, 
				iconId);
	}

}
