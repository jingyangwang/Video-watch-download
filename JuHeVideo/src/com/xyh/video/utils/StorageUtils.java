package com.xyh.video.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

public class StorageUtils {
	/**
	 * 获取某个目录的可用存储空间大小
	 * 
	 * @param path
	 * @return
	 */
	public static long getFileAvailableSize(String path) {
		try {
			StatFs stat = new StatFs(path);
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取某个目录的最大存储空间
	 * 
	 * @param path
	 * @return
	 */
	public static long getFileTotalSize(String path) {
		try {
			StatFs stat = new StatFs(path);
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String formatBytes(long size) {
		DecimalFormat formatter = new DecimalFormat("#0.0");
		if (size > 1024 * 1024 * 1024) {
			return formatter.format(size / (float) (1024 * 1024 * 1024)) + "GB";
		} else if (size > 1024 * 1024) {
			return formatter.format(size / (float) (1024 * 1024)) + "MB";
		} else if (size > 1024) {
			return formatter.format(size / 1024) + "KB";
		} else {
			return formatter.format(size) + "B";
		}
	}

	public static String formatBytes2(long size) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		if (size > 1024 * 1024 * 1024) {
			return formatter.format(size / (float) (1024 * 1024 * 1024)) + "GB";
		} else if (size > 1024 * 1024) {
			return formatter.format(size / (float) (1024 * 1024)) + "MB";
		} else if (size > 1024) {
			return formatter.format(size / 1024) + "KB";
		} else {
			return formatter.format(size) + "B";
		}
	}
	
	
	/**
	 * 遍历sd卡的文件
	 * @param folderFile
	 * @return
	 */
	public static File[] listFiles(File folderFile) {
		if (folderFile == null || !folderFile.exists()) {
			return null;
		}
		Process process = null;
		DataInputStream dataInputStream = null;
		ArrayList<String> strings = new ArrayList<String>();
		File[] files = null;
		try {
			process = Runtime.getRuntime().exec("ls -a " + folderFile.getAbsolutePath());
			dataInputStream = new DataInputStream(process.getInputStream());
			InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strings.add(line);
			}
			bufferedReader.close();
			inputStreamReader.close();
			process.waitFor();
			files = new File[strings.size()];
			for (int i = 0; i < strings.size(); i++) {
				files[i] = new File(folderFile, strings.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (dataInputStream != null) {
					dataInputStream.close();
				}
				process.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (files == null || files.length == 0) {
			return folderFile.listFiles();
		}
		return files;
	}

	/**
	 * 获取sdcard0的目录
	 * 
	 * @param mContext
	 * @return
	 */
	public static String getSdcard0(Context mContext) {
		if (Environment.MEDIA_MOUNTED.equals( Environment.getExternalStorageState())) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}

	/**
	 * 获取sdcard1的目录
	 * 
	 * @param mContext
	 * @return
	 */
	public static String getSdcard1(Context mContext) {
		StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Activity.STORAGE_SERVICE);
		Method mMethodGetPaths = null;
		String sdcard1 = null;
		String[] paths = null;
		try {
			mMethodGetPaths = mStorageManager.getClass().getMethod("getVolumePaths");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (paths != null && paths.length > 1) {
			sdcard1 = paths[1];
		}
		return sdcard1;
	}

}
