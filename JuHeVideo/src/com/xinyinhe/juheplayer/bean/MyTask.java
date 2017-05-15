package com.xinyinhe.juheplayer.bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MyTask {
	private String mUrl;
	private List<Data> list;
	private Handler referenceHandler;
	private Context mContext;
	
	public MyTask(String mUrl, Handler referenceHandler , Context mContext) {
		super();
		this.mContext = mContext;
		this.mUrl = mUrl;
		this.referenceHandler = referenceHandler;
		new VideoTask().execute(mUrl);
	}

	/*
	 * 网络异步请求  yukunjin
	 */
	class VideoTask extends AsyncTask<String, Integer, List<Data>>{
		@Override
		protected List<Data> doInBackground(String... params) {
			HttpClient hc = new DefaultHttpClient();
			HttpGet get = new HttpGet(params[0]);
//			VideoConfig video = null;
			MyDatas md = null;
			try {
				HttpResponse hr = hc.execute(get);
				if(hr.getStatusLine().getStatusCode()==200){
					Log.i("dove", "联网成功！！！");
//					String str = EntityUtils.toString(hr.getEntity(), "utf-8");
					String str = getFromAssets("juhes.json");
					Gson gson = new Gson();
					list = new ArrayList<Data>();
					md = gson.fromJson(str, MyDatas.class);
					list.addAll(md.getDatas());
//					Log.i("dove", "json:::::::"+list.toString());
				}else{
					Log.i("dove", "联网失败！！！");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
	
		@Override
		protected void onPostExecute(List<Data> result) {
			if(result!=null){
				Message message = Message.obtain();  
                message.obj = result;  
                message.what = 1;  
				referenceHandler.sendMessage(message);
			}
		}
	}	
	
	public String getFromAssets(String fileName){
	    String result = "";
	    try {
			InputStream in = mContext.getResources().getAssets().open(fileName);
			//获取文件的字节数
			int lenght = in.available();
			//创建byte数组
			byte[]  buffer = new byte[lenght];
			//将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "UTF-8");//你的文件的编码
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

