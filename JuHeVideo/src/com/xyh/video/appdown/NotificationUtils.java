package com.xyh.video.appdown;

import com.videoplayer.bd.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;



public class NotificationUtils {
	  private static NotificationUtils mInstance;
	  private NotificationManager mManager;
	  private Context mContext;
	  private final int NI_DOWNLOADING = 1001;
	  private  Notification n ;
	  private NotificationUtils(Context ctx){
		  mContext = ctx.getApplicationContext();
		  mManager =(NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	  }
	  
	  public static synchronized  NotificationUtils getInstance(Context ctx){
		 if(mInstance == null){
			 mInstance = new NotificationUtils(ctx);
		 }
		 return mInstance;
	  }
	  
	  public  void updateDownloadNotification(String title ,int progress) {
		  
		  Log.i("debug", "progress--->"+progress);
		  if(n == null){
			  Log.i("debug", "progress-3333333333333333333-->"+progress);
			n = new Notification();
			n.icon = R.drawable.ic_launcher;
		  	n.flags |= Notification.FLAG_NO_CLEAR;
		  	RemoteViews expandedView = new RemoteViews(mContext.getPackageName(), R.layout.notification_bar);
		  	n.contentView = expandedView;
		  }
		  Log.i("debug", "progress-1111-->"+progress);
		  n.contentView.setProgressBar(R.id.noti_progbar, 100, 50, true);
		  n.contentView.setImageViewResource(R.id.noti_icon, R.drawable.logo);
		  n.contentView.setTextViewText(R.id.noti_prog, title);
	      mManager.notify(0, n);
	   }
	  public  void removeDownloadNotification() {
	       mManager.cancel(0);
	  }
	
}
