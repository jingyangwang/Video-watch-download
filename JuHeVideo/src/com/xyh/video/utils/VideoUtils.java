package com.xyh.video.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class VideoUtils {
	/**
	 * 打开浏览器
	 * @param mContext
	 * @param url
	 */
	public static void openBrowser(Context mContext,String url){
		try {
			Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));   
			it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");   
			mContext.startActivity(it);
		} catch (Exception e) {
			e.printStackTrace();
			Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));   
			try {
				mContext.startActivity(it);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
