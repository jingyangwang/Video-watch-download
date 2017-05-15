package com.xyh.video.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
	public static String MD5Encode(byte[] bytes) {
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			byte[] digest = md.digest();
			String text;
			for (int i = 0; i < digest.length; i++) {
				text = Integer.toHexString(0xFF & digest[i]);
				if (text.length() < 2) {
					text = "0" + text;
				}
				hexString.append(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}

	public static String MD5Encode(String text) {
		return MD5Encode(text.getBytes());
	}

	
	public static String getCurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date today = new Date();
		return df.format(today);
	}
	
	public static String getCurrentDate(long time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date today = new Date(time);
		return df.format(today);
	}
	
	
	public static boolean isSameDay(long time,long time2) {
		Calendar  calendarA = Calendar.getInstance();
		calendarA.setTimeInMillis(time);
		Calendar  calendarB = Calendar.getInstance();
		calendarB.setTimeInMillis(time);
		return calendarA.get(Calendar.YEAR) == calendarB.get(Calendar.YEAR)
		            && calendarA.get(Calendar.MONTH) == calendarB.get(Calendar.MONTH)
		            &&  calendarA.get(Calendar.DAY_OF_MONTH) == calendarB.get(Calendar.DAY_OF_MONTH);
	}
	
	
	public static void writeStringToFile(String fileName,String body){
		FileOutputStream  file = null;
		try {
			file = new FileOutputStream(Constants.IMAGE_PATH+"/"+fileName);
			byte[] bytes = body.getBytes("utf-8");
			file.write(bytes,0,bytes.length);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(file != null){
					file.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static String writeStringFromFile(String fileName){
		StringBuilder builder = new StringBuilder();
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(Constants.IMAGE_PATH+"/"+fileName)));//构造一个BufferedReader类来读取文件
			String s = null;
			while((s = br.readLine()) !=null){//使用readLine方法，一次读一行
				builder.append(s+"\n");
			}
			br.close();  
		}catch(Exception e){
			e.printStackTrace();
		}
		return builder.toString();
	}
}
