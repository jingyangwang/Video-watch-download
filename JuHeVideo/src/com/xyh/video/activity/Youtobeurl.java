///**
// * 
// */
//package com.xyh.video.activity;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.xyh.video.activity.GwebActivity.ParseYoutubeVideo;
//import com.xyh.video.utils.RLog;
//
///**
// * @author wjy
// *
// */
//public class Youtobeurl {
//
//	/**
//	 * @param args
//	 */
////	public static void main(String[] args) {
////		// TODO Auto-generated method stub
////
////	}
//	public Youtobeurl() {
//		// TODO Auto-generated constructor stub
//	
////		String data="https://www.youtube.com";
//		String data="https://www.youtube.com/watch?v=zv26xcdp_4s";
////		title = getIntent().getStringExtra("title");
//		String title="aaaaa";
//		Pattern places = Pattern.compile("src=\"(.*?)\"");
//		Matcher matcher = places.matcher(data);
//		String url = null;
//		if (matcher.find()) {
//			url = matcher.group(1);
//		} else {
//			url = data;
//		}
//		url = url.replace("nsfwyoutube", "youtube");
//		RLog.i("ffa", "url: " + url);
//	
//	
//		
//		
//		
//		
//		String id = getYoutubeVideoIdFrom("https://www.youtube.com/watch?v=zv26xcdp_4s");
////		String id = getYoutubeVideoIdFrom("https://www.youtube.com/watch?v=7gcCRAl58u4");
////		7gcCRAl58u4
//		Log.i("ffa", "webView"+webView.getUrl());
//		
//		Log.i("ffa", "id"+id);
//		if (!TextUtils.isEmpty(id)) {
//			Log.i("ffa", ""+id);
//			new ParseYoutubeVideo(GwebActivity.this, id).execute();
////			Toast.makeText(GwebActivity.this, "下载启动中", Toast.LENGTH_LONG).show();
//		}
//	
//	
//	
//	}
//	
//	
//	
//
//}
