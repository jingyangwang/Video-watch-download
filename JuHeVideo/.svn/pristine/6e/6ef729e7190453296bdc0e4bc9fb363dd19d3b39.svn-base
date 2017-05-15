package com.example.polymerization.app;

import com.example.polymerization.db.SQLHelper;

import android.app.Application;

public class AppApplication extends Application {
	private static AppApplication mAppApplication;
	private SQLHelper sqlHelper;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mAppApplication = this;
	}

	/** ��ȡApplication */
	public static AppApplication getApp() {
		return mAppApplication;
	}
	
	/** ��ȡ��ݿ�Helper */
	public SQLHelper getSQLHelper() {
		if (sqlHelper == null)
			sqlHelper = new SQLHelper(mAppApplication);
		return sqlHelper;
	}
	
	/** �ݻ�Ӧ�ý��ʱ����� */
	public void onTerminate() {
		if (sqlHelper != null)
			sqlHelper.close();
		super.onTerminate();
	}

	public void clearAppCache() {
	}
}
