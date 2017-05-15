package com.xyh.video.download.ui;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.videoplayer.bd.R;
import com.xyh.video.base.BaseFragmentActivity;
import com.xyh.video.download.DownloadUtils;
import com.xyh.video.utils.StorageUtils;

public class FileManagerActivity extends BaseFragmentActivity implements Runnable, OnItemClickListener, OnClickListener {
	private Context mContext;

	private ListView listView;
	private String currentPath = "";
	private String jumpPath = "";
	private String rootPath = null;
	private ArrayList<FileItem> fileItems;
	private FileAdapter adapter;
	private TextView filepath_textview;
	private Button btn_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_manager);
		mContext = FileManagerActivity.this;
		initViewsAndEvents();
		jumpPath = getIntent().getStringExtra("path");
		rootPath = getIntent().getStringExtra("path");
		new Thread(this).start();
	}

	private void initViewsAndEvents() {
		listView = (ListView) findViewById(R.id.file_list);
		TextView file_list_empty = (TextView) findViewById(R.id.file_list_empty);
		listView.setEmptyView(file_list_empty);
		listView.setOnItemClickListener(this);
		filepath_textview = (TextView) findViewById(R.id.file_path_textview);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		findViewById(R.id.title_back).setOnClickListener(this);
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 0:
				adapter = new FileAdapter(mContext, fileItems);
				listView.setAdapter(adapter);
				filepath_textview.setText(getString(R.string.folder_path, currentPath));
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void run() {
		fileItems = getFileList(jumpPath);
		mHandler.sendEmptyMessage(0);
	}

	public ArrayList<FileItem> getFileList(String jumpPath) {
		currentPath = jumpPath;
		ArrayList<FileItem> fileItems = new ArrayList<FileItem>();
		File rootFile = new File(jumpPath);
		File[] files = StorageUtils.listFiles(rootFile);
		if (files != null) {
			for (File file : files) {
				if(file.isDirectory()){
					FileItem fileItem = new FileItem();
					fileItem.fileName = file.getName();
					fileItem.filePath = file.getAbsolutePath();
					fileItem.isDirectory = file.isDirectory();
					fileItems.add(fileItem);
				}
			}
		}
		return fileItems;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		FileItem fileItem = (FileItem) parent.getItemAtPosition(position);
		if (fileItem.isDirectory) {
			jumpPath = fileItem.filePath;
			new Thread(FileManagerActivity.this).start();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			DownloadUtils.setDownloadPath(mContext, currentPath);
			finish();
			break;
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}
	}

	class FileAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<FileItem> list;

		public FileAdapter(Context context, ArrayList<FileItem> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			View view = convertView;

			if (view == null) {
				view = LayoutInflater.from(context).inflate(R.layout.listview_item_file, parent, false);
				holder = new Holder();
				holder.name = (TextView) view.findViewById(R.id.filename_textview);
				view.setTag(holder);
			} else {
				holder = (Holder) view.getTag();
			}

			final FileItem item = list.get(position);
			holder.name.setText(item.fileName);
			return view;
		}

		private class Holder {
			TextView name;
		}
	}

	class FileItem {
		public boolean isDirectory;
		public String filePath;
		public String fileName;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!jumpPath.equals(rootPath)) {
				jumpPath = new File(jumpPath).getParent();
				new Thread(this).start();
				return true;
			} else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
