package com.shipin.player.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.shipin.player.gui.FileItem;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

/**
 * uri content://media/external/images/media
 * 讀取SDcard上的資源
 * @author Administrator
 *
 */
public class ReadSDCardResource {
 
	public static final String TAG = "ReadSDCardResource";

	public void readSDCardImageResourceByContentProvider(Context context) {
		//必须在查找前进行全盘的扫描，否则新加入的图片是无法得到显示的(加入对sd卡操作的权限)TODO 多试几次
//		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
//				.parse("file://" + Environment.getExternalStorageDirectory())));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(new File("file://" + Environment.getExternalStorageDirectory())); //out is your output file
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        } else {
        	context.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://"
                            + Environment.getExternalStorageDirectory())));
        }

		ContentResolver mContentResolver = context.getContentResolver();
 

		// 视频 MediaStore.Video.Media.EXTERNAL_CONTENT_URI
		// 图片MediaStore.Images.Media.EXTERNAL_CONTENT_URI
		Uri image_Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
 
		// 要读的字段名,TITLE是标题 DATA是路径
		// String[] columns = new String[] {MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.DATA};
		String str[] = { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.DATA };

		// 跟查询SQL一样了,除了第一个参数不同外.后面根据时长过滤小于10秒的文件
		// Cursor cursor = mContentResolver.query(AUDIO_URI, columns,
		// MediaStore.Audio.Media.DURATION+">?", new String[]{"10000"}, null);
		Cursor cursor = mContentResolver.query(image_Uri, null,
				MediaStore.Images.Media.MIME_TYPE + "=? or "
						+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);
 
		// Log.i(TAG, cursor.moveToNext()+"");
		while (cursor.moveToNext()) {
			// 循环读取第一列,即文件路径,0列是标题
			Log.i(TAG, "----");
			Log.i(TAG, cursor.getString(0));
			Log.i(TAG, cursor.getString(1));
			Log.i(TAG, cursor.getString(2));
		}
		cursor.close();
	}

	public ArrayList<FileItem> readSDCardMediaResourceByContentProvider(Context context) {
		ArrayList<FileItem> list = new ArrayList<FileItem>();
		ContentResolver mContentResolver = context.getContentResolver();
		// 获取音频文件的URI
		Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		// 要读的列名,TITLE是标题 DATA是路径
		String[] projection = new String[] {MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.DATA};
		Cursor cursor = mContentResolver.query(AUDIO_URI, projection, null, null, null);
		while(cursor.moveToNext()){
			FileItem fitem = new FileItem();
			fitem.setId(Integer.parseInt(cursor.getString(0)));
			fitem.setFileName(cursor.getString(1));
			fitem.setFilePath(cursor.getString(2));
			list.add(fitem);
		}
		Log.i("dove", list.toString());
		return list;
	}

}
