package com.xyh.video.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

public class CustomEventCommit {
	public static final String download = "download";
	public static final String install = "install";
	/**
	 * @param mContext
	 * @param event
	 *            事件
	 * @param appName
	 *            用于提交名称
	 */
	public static void commit(Context mContext, String event, String appName, boolean immediate) {

		HashMap<String, String> hashMap = new HashMap<String, String>();
		if (appName == null) {
			appName = "null";
		}
		hashMap.put("appname", appName);
		MobclickAgent.onEvent(mContext, event, hashMap);
		if (immediate) {
			MobclickAgent.flush(mContext);
		}

		RLog.d("CustomEventCommit", "event:" + event + "::appName:" + appName);
	}

	public static void commit(Context mContext, String event, String appName) {
		commit(mContext, event, appName, true);
	}
}
