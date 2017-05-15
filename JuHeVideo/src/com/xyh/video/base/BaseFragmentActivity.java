package com.xyh.video.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.videoplayer.bd.R;

/**
 * Created by Tommy on 15/3/12.
 */
public class BaseFragmentActivity extends FragmentActivity {

	protected Context mContext;
	protected Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mActivity = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题栏
//		PushAgent.getInstance(getApplicationContext()).onAppStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, R.anim.activity_out);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.activity_in, 0);
	}

	@SuppressLint("NewApi")
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
			// WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//			View v = findViewById(R.id.root_layout);
//			if (v != null) {
//				v.setFitsSystemWindows(true);
//			}
//		}
		
		if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) { 
            Window window = getWindow();  
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);  
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);  
            window.setStatusBarColor(Color.TRANSPARENT);  
        }  
	}

	@Override
	protected void onStart() {
		super.onStart();
		//GoogleAnalytics.getInstance(this).reportActivityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		//GoogleAnalytics.getInstance(this).reportActivityStop(this);
	}
}
