package com.xyh.video.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class ParamsUtils {

	public static Map<String, String> getPublicParams(Context context){
		Map<String, String>  params=new HashMap<String, String>();
		params.put("country", DeviceInfoUtils.getCountry(context) == null?"":DeviceInfoUtils.getCountry(context));
		params.put("imsi", DeviceInfoUtils.getIMSI(context));
		params.put("mac", DeviceInfoUtils.getMacAddress(context));
		params.put("imei", DeviceInfoUtils.getIMEI(context));
		params.put("pkg", context.getPackageName());
		params.put("network", ""+DeviceInfoUtils.getNETType(context));
		params.put("versioncode", DeviceInfoUtils.getAndroidVersion());
		params.put("androidapi", ""+DeviceInfoUtils.getAndroidSDK());
		params.put("androidid", DeviceInfoUtils.getLocaldeviceId(context));
		params.put("language",  DeviceInfoUtils.getLanguage(context));
		params.put("clientid", "6");
		params.put("channelname", "channel");
		return params;
	}
	
}
