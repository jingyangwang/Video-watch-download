package com.xyh.video.download.ui;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.videoplayer.bd.R;
import com.xyh.video.base.BaseFragmentActivity;
import com.xyh.video.download.DownloadUtils;
import com.xyh.video.utils.StorageUtils;

public class DownloadPathSettingActivity extends BaseFragmentActivity implements OnClickListener {
	private TextView storage_sdcard0_size_textview;
	private TextView storage_sdcard1_size_textview;
	private CustomProgressbar storage_sdcard0_memery_progressbar, storage_sdcard1_memery_progressbar;
	private TextView download_path_textview;
	private Context mContext;
	private String sdcard0, sdcard1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_downloadpath_setting);
		mContext = DownloadPathSettingActivity.this;
		initViewAndEvent();
	}

	private void initViewAndEvent() {

		sdcard0 = StorageUtils.getSdcard0(mContext);
		sdcard1 = StorageUtils.getSdcard1(mContext);
		findViewById(R.id.title_back).setOnClickListener(this);
		LinearLayout storage_sdcard0_layout = (LinearLayout) findViewById(R.id.storage_sdcard0_layout);
		storage_sdcard0_size_textview = (TextView) findViewById(R.id.storage_sdcard0_size_textview);
		storage_sdcard0_memery_progressbar = (CustomProgressbar) findViewById(R.id.storage_sdcard0_memery_progressbar);
		storage_sdcard0_layout.setOnClickListener(this);
		if (sdcard0 != null) {
			long availMemory = StorageUtils.getFileAvailableSize(sdcard0);
			long totalMemory = StorageUtils.getFileTotalSize(sdcard0);

			String availSize = StorageUtils.formatBytes(availMemory);
			String totalSize = StorageUtils.formatBytes(totalMemory);
			storage_sdcard0_size_textview.setText(getString(R.string.memory_detail, availSize, totalSize));
			storage_sdcard0_memery_progressbar.setProgress(100 - (int) (availMemory * 100 / totalMemory));
		}

		LinearLayout storage_sdcard1_layout = (LinearLayout) findViewById(R.id.storage_sdcard1_layout);
		storage_sdcard1_size_textview = (TextView) findViewById(R.id.storage_sdcard1_size_textview);
		storage_sdcard1_memery_progressbar = (CustomProgressbar) findViewById(R.id.storage_sdcard1_memery_progressbar);
		storage_sdcard1_layout.setOnClickListener(this);

		if (sdcard1 != null && new File(sdcard1).exists()) {
			storage_sdcard1_layout.setVisibility(View.VISIBLE);
			long availMemory = StorageUtils.getFileAvailableSize(sdcard1);
			long totalMemory = StorageUtils.getFileTotalSize(sdcard1);
			if (totalMemory != 0) {
				String availSize = StorageUtils.formatBytes(availMemory);
				String totalSize = StorageUtils.formatBytes(totalMemory);
				storage_sdcard1_size_textview.setText(getString(R.string.memory_detail, availSize, totalSize));
				storage_sdcard1_memery_progressbar.setProgress(100 - (int) (availMemory * 100 / totalMemory));
			} else {
				storage_sdcard1_layout.setVisibility(View.GONE);
			}

		} else {
			storage_sdcard1_layout.setVisibility(View.GONE);
		}

		download_path_textview = (TextView) findViewById(R.id.download_path_textview);
	}

	@Override
	protected void onResume() {
		super.onResume();
		download_path_textview.setText(getResources().getString(R.string.download_default_path, DownloadUtils.getDownloadPath(mContext)));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.storage_sdcard0_layout:
			Intent intent = new Intent(mContext, FileManagerActivity.class);
			intent.putExtra("path", sdcard0);
			startActivity(intent);
			break;
		case R.id.storage_sdcard1_layout:
			Intent intent1 = new Intent(mContext, FileManagerActivity.class);
			intent1.putExtra("path", sdcard1);
			startActivity(intent1);
			break;
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}
	}

}
