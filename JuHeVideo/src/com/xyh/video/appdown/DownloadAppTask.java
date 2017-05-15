package com.xyh.video.appdown;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import com.shipin.player.VLCApplication;
import com.xyh.video.appdown.DownloadManager.Task;
import com.xyh.video.utils.Constants;
import com.xyh.video.utils.RLog;

public class DownloadAppTask implements Task {
	private int temp=0;
	private String mDownloadUrl;
	private String mFile;
	private String title;
	public DownloadAppTask(String url,String file,String title){
		RLog.i("DownloadAppTask mDownloadUrl", url);
		mDownloadUrl = url;
		mFile = Constants.FILE_PATH+"/"+file;
		this.title = title;
	}
	@Override
	public void run() {
		try {
			File file = new File(mFile);
			HttpURLConnection connection = HttpUtils.getHttpURLConnection(mDownloadUrl);
			//设置连接超时时间
			connection.setConnectTimeout(30 * 1000);
			//设置读取超时时间
			connection.setReadTimeout(60 * 1000);
			connection.setRequestMethod("GET");
			if(file.exists()){
				connection.setRequestProperty("Range","bytes="+file.length()+"-"); 
			}
			connection.setDoInput(true);
			connection.connect();
			
			long length = connection.getContentLength();
		    if(file.exists()){
		    	ApkUtils.installApk(file);
		    }else{
		    	downloadFile(connection,file,length+file.length());
				//MobclickAgent.onEvent(PronClubApplication.mInstance, "apkdownload");
				Log.i("debug", "file--->"+file);
				ApkUtils.installApk(file);
		    }
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		NotificationUtils.getInstance(VLCApplication.getApp()).removeDownloadNotification();
	}
	
	private void downloadFile(HttpURLConnection connection, File file,long length) throws IOException{
		InputStream input = connection.getInputStream();
		FileOutputStream out = new FileOutputStream(file,true);
		byte[] buffer = new byte[1024 * 1024];
		int read = -1;
		long count = 0;
		if(file.exists() ){
			count = file.length();
		}
		while((read = input.read(buffer)) != -1){
			count += read;
			out.write(buffer, 0, read);
			Log.i("debug", "doing");
			if(temp!=(count * 100 /length)){
				Message message=new Message();
				message.arg1=(int) (count * 100 /length);
				message.what=999;
				mHandler.sendMessage(message);
			}
				
			temp=(int) (count * 100 /length);
		}
		input.close();
		out.close();
		Log.i("debug", "success");
	}
	
	Handler mHandler=new Handler(new Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			int what=msg.what;
			int mycount=msg.arg1;
			if(what==999){
				NotificationUtils.getInstance(VLCApplication.getApp()).updateDownloadNotification(title,mycount);
			}
			return false;
		}
	});
	
	
}
