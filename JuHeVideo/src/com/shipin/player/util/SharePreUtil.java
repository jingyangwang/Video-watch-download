package com.shipin.player.util;



import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class SharePreUtil {
	
	
	public static void setcheck(Context context,boolean checked){
	SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
	sp.edit().putBoolean("checked", checked).commit();
	}
	
	
	public static boolean getcheck(Context context){
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sp.getBoolean("checked", false);
	}
	
	
	public static void setedit(Context context,boolean edit){
		SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(context);

		sp.edit().putBoolean("edit", edit).commit();
		
	
	}
		
		
		public static boolean getedit(Context context){
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(context);
			return sp.getBoolean("edit", false);
		}
		
	
	
	
	
	
	
	
	
	
	
	
	

}
