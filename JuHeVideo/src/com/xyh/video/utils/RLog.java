package com.xyh.video.utils;

import android.util.Log;

/**
 * Log工具类
 * 
 * @author 王雷
 * 
 */
public final class RLog {
	private static boolean EnableLog = true;
//	private static boolean EnableLog = false;

	public static void d(Object obj) {
		if (EnableLog) {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
			StringBuffer toStringBuffer = new StringBuffer("----------").append(traceElement.getFileName().substring(0, traceElement.getFileName().lastIndexOf(".")) + "__")
					.append(traceElement.getMethodName()).append("()|").append(traceElement.getLineNumber()).append("----------");
			String title = toStringBuffer.toString();
			if (obj != null) {
				Log.d("video", title + "\n" + obj);
			} else {
				Log.d("video", title + "\n" + "I am Here");
			}
		}
	}

	public static void d(final String key, final Object value) {
		if (EnableLog)
			Log.d("video", key + ":  " + value);
	}

    public static void v(final String key, final Object value) {
		if (EnableLog)
			Log.v("video", key + ":  " + value);
	}

	public static void i(final Object key, final Object value) {
		if (EnableLog)
			Log.i("video", key + ":  " + value);
	}

	public static void w(final String key, final Object value) {
		if (EnableLog)
			Log.w("video", key + ":  " + value);
	}

	public static void e(final String key, final Object value) {
        if (EnableLog)
		    Log.e("video", key + ":  " + value);
	}

}