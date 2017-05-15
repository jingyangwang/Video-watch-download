package com.xyh.video.download;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

public class DownloadUtils {
	public static String getDownloadPath(Context mContext) {
		String defaultDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
		return sp.getString("download_path", defaultDir);
	}

	public static void setDownloadPath(Context mContext, String path) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
		sp.edit().putString("download_path", path).commit();
	}
}
