package com.xyh.video.utils;

import android.os.Environment;

public class Constants
{
	
	public static final String IMAGE_PATH = Environment.getExternalStorageDirectory() + "/Video_XYH/app";
	public static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/Video_XYH/app";
	public static final String KEY_PATH = Environment.getExternalStorageDirectory() + "/Video_XYH/key";
	
    public static final String PREFENCE = "Video";
    //public static final String HOST= "http://52.76.39.120:8082/hots/api/";
    public static final String host = "http://api.hdyfhpoi.com/hots/api/";
    public static final String BANNER_URL   = host + "2001";
    public static final String VIDEOLIST_URL  = host + "2002";
    public static final String LIKE_RUL  = host + "2003";
    public static final String CLASSIY_URL   = host + "2004";
    public static final String PROBABILITY_URL   = host + "2005";
    public static final String WALL_URL   = host + "5";
    
    
    public static final String UNKNOWN   = "unknown";
    public static final  String MALFORMED = "malformed";
    public static final  String SMALL     = "small";
    public static final  String NORMAL    = "normal";
    public static final  String LONG      = "long";
    public static final  String LARGE     = "large";
    public static final  String XLARGE    = "xlarge";
    public static final  String LOW       = "low";
    public static final String MEDIUM    = "medium";
    public static final  String HIGH      = "high";
    public static final  String REFERRER  = "referrer";

    public static final   String ENCODING = "UTF-8";
    public static final  String MD5      = "MD5";
    public static final   String SHA1     = "SHA-1";
}
