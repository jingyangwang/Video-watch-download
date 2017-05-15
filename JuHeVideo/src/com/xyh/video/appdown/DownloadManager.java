package com.xyh.video.appdown;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.xyh.video.utils.Constants;



public class DownloadManager implements Runnable{
	private static DownloadManager mInstance;
	private LinkedHashMap<String,Task> mQueue;
	private boolean stop;
	private DownloadManager(){
		mQueue = new LinkedHashMap<String,Task>();
		Thread thread =new Thread(this);
		thread.start();
		File path = new File(Constants.FILE_PATH);
		if(!path.exists()){
			path.mkdirs();
		}
	}
	public static synchronized  DownloadManager getInstance(){
		if(mInstance == null){
			 mInstance = new DownloadManager();
		}
		return mInstance;
	}
	
	public void addTask(String url,Task task){
		synchronized(mQueue){
			if(!mQueue.containsKey(url)){
				mQueue.put(url, task);
				mQueue.notify();
			}
		}
	}
	
	
	
	public interface Task{
		public void run();
	}



	@Override
	public void run() {
		while(!stop){
			Task task  = null;
			String key = "";
			synchronized(mQueue){
				Iterator<String> keys = mQueue.keySet().iterator();
				if(!keys.hasNext()){
					try {
						mQueue.wait();
					} catch (InterruptedException e) {
						stop = true;
						e.printStackTrace();
					}
				}else{
					key = keys.next();
					task = mQueue.get(key);
				}
			}
			//开始运行任务
			if(task != null){
				task.run();
				mQueue.remove(key);
			}
		}
	}
}
