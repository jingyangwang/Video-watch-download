package com.xyh.video.download.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.videoplayer.bd.R;
import com.xyh.video.base.BaseFragmentActivity;
import com.xyh.video.download.CheckedListener;
import com.xyh.video.download.DownloadBean;
import com.xyh.video.download.Downloads;
import com.xyh.video.download.FileTypeUtils;
import com.xyh.video.download.providers.DownloadManager;
 
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 下载管理页面
 * 
 * @author 庄宏岩
 *
 */
public class DownloadListActivity extends BaseFragmentActivity implements OnClickListener, OnScrollListener, OnItemClickListener, CheckedListener {
	private TitledListView mSizeOrderedListView;
	private View mEmptyView;

	private DownloadManager mDownloadManager;
	private MyContentObserver mContentObserver = new MyContentObserver();
	public static final int MSG_UPDATE_LIST = 1000;
	private TextView title_select;
	private TextView title_done;
	private TextView title_edit;

	private class MyContentObserver extends ContentObserver {
		public MyContentObserver() {
			super(new Handler());
		}

		@Override
		public void onChange(boolean selfChange) {
			new GetDownloadList().start();
		}
	}

	private Context mContext;
	private RelativeLayout layout_top,layout_top2,layout_bottom, layout_bottom2;

	private SharedPreferences sp;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		this.mContext = DownloadListActivity.this;
		mDownloadManager = new DownloadManager(getContentResolver(), getPackageName());
		mDownloadManager.setAccessAllDownloads(true);
		setupViews();

		new GetDownloadList().start();
	}
      
	private void setupViews() {     
		setContentView(R.layout.download_list);
		setTitle(getText(R.string.download_title));
		layout_top = (RelativeLayout) findViewById(R.id.layout_top);
		layout_top2 = (RelativeLayout) findViewById(R.id.layout_top2);
		layout_bottom = (RelativeLayout) findViewById(R.id.layout_bottom);
		layout_bottom2 = (RelativeLayout) findViewById(R.id.layout_bottom2);
		sp = mContext.getSharedPreferences("downloadmanager", Context.MODE_PRIVATE);
		sp.edit().putBoolean("isEdit", false).commit();
		layout_bottom2.setVisibility(View.GONE);
		layout_bottom.setVisibility(View.VISIBLE);
		layout_top2.setVisibility(View.GONE);
		layout_top.setVisibility(View.VISIBLE);

		mSizeOrderedListView = (TitledListView) findViewById(R.id.size_ordered_list);
		mSizeOrderedListView.setOnItemClickListener(this);
		mEmptyView = findViewById(R.id.empty);
		if (downManagerAdapter == null) {
			downManagerAdapter = new DownManagerAdapter(new ArrayList<DownloadBean>(), mContext, mDownloadManager, this);
			downManagerAdapter.setCheckedListener(DownloadListActivity.this);
		}
		mSizeOrderedListView.setAdapter(downManagerAdapter);
		mSizeOrderedListView.setOnScrollListener(this);

		findViewById(R.id.layout_bottom).setOnClickListener(this);
		findViewById(R.id.layout_bottom2).setOnClickListener(this);
		findViewById(R.id.title_back).setOnClickListener(this);
		title_select = (TextView) findViewById(R.id.title_select);
		title_done = (TextView) findViewById(R.id.title_done);
		title_edit = (TextView) findViewById(R.id.title_edit);
		title_select.setOnClickListener(this);
		title_done.setOnClickListener(this);
		title_edit.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		mContext.getContentResolver().registerContentObserver(Downloads.ALL_DOWNLOADS_CONTENT_URI, true, mContentObserver);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mContext.getContentResolver().unregisterContentObserver(mContentObserver);
	}

	class GetDownloadList extends Thread {

		@Override
		public void run() {
			super.run();
			ArrayList<DownloadBean> mAlldownloadBeans = new ArrayList<DownloadBean>();
			Cursor cursor = mContext.getContentResolver().query(Downloads.ALL_DOWNLOADS_CONTENT_URI, null, null, null, Downloads._DATA + " DESC");
			int mIdColumnId = cursor.getColumnIndexOrThrow(Downloads._ID);
			int mTitleColumnId = cursor.getColumnIndexOrThrow(Downloads.COLUMN_TITLE);
			int mStatusColumnId = cursor.getColumnIndexOrThrow(Downloads.COLUMN_STATUS);
			int mTotalBytesColumnId = cursor.getColumnIndexOrThrow(Downloads.COLUMN_TOTAL_BYTES);
			int mCurrentBytesColumnId = cursor.getColumnIndexOrThrow(Downloads.COLUMN_CURRENT_BYTES);
			int mMediaTypeColumnId = cursor.getColumnIndexOrThrow(Downloads.COLUMN_MIME_TYPE);
			int mDateColumnId = cursor.getColumnIndexOrThrow(Downloads.COLUMN_LAST_MODIFICATION);
			int mDataColumnId = cursor.getColumnIndexOrThrow(Downloads._DATA);

			while (cursor.moveToNext()) {
				DownloadBean downloadBean = new DownloadBean();
				int downloadId = cursor.getInt(mIdColumnId);
				String title = cursor.getString(mTitleColumnId);
				long totalSize = cursor.getLong(mTotalBytesColumnId);
				long downloadSize = cursor.getLong(mCurrentBytesColumnId);
				String mediaType = cursor.getString(mMediaTypeColumnId);
				int status = cursor.getInt(mStatusColumnId);
				long date = cursor.getLong(mDateColumnId);
				String path = cursor.getString(mDataColumnId);

				downloadBean.setDownloadId(downloadId);
				downloadBean.setTitle(title);
				downloadBean.setTotalSize(totalSize);
				downloadBean.setDownloadSize(downloadSize);
				downloadBean.setMediaType(mediaType);
				downloadBean.setStatus(status);
				downloadBean.setPath(path);
				downloadBean.setDate(date);
				downloadBean.selected = mSelectedIds.contains(downloadId);
				downloadBean.setIcon(FileTypeUtils.getFileTypeIconByName(path));
				if (status == Downloads.STATUS_SUCCESS) {
					downloadBean.setTitleState(getResources().getString(R.string.download_success));
				} else {
					downloadBean.setTitleState(getResources().getString(R.string.download_running));
				}
				mAlldownloadBeans.add(downloadBean);
			}
			Message message = new Message();
			message.what = MSG_UPDATE_LIST;
			message.obj = mAlldownloadBeans;
			handler.sendMessage(message);

		}
	};

	private ArrayList<DownloadBean> mAlldownloadBeans;
	private ArrayList<DownloadBean> mDownloadingBeans;
	private ArrayList<DownloadBean> mCompleteBeans;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_UPDATE_LIST:
				mAlldownloadBeans = (ArrayList<DownloadBean>) msg.obj;
				mCompleteBeans = new ArrayList<DownloadBean>();
				mDownloadingBeans = new ArrayList<DownloadBean>();
				for (DownloadBean downloadBean : mAlldownloadBeans) {
					if (downloadBean.getStatus() == Downloads.STATUS_SUCCESS) {
						mCompleteBeans.add(downloadBean);
					} else {
						mDownloadingBeans.add(downloadBean);
					}
				}
				mAlldownloadBeans = new ArrayList<DownloadBean>();
				mAlldownloadBeans.addAll(mDownloadingBeans);
				mAlldownloadBeans.addAll(mCompleteBeans);
				downManagerAdapter.setList(mAlldownloadBeans);
				downManagerAdapter.setTitleStateNum(mDownloadingBeans.size(), mCompleteBeans.size());
				downManagerAdapter.notifyDataSetChanged();
				if (mAlldownloadBeans == null || mAlldownloadBeans.size() == 0) {
					mEmptyView.setVisibility(View.VISIBLE);
				} else {
					mEmptyView.setVisibility(View.GONE);
					mSizeOrderedListView.setVisibility(View.VISIBLE);
				}
				break;

			default:
				break;
			}
		}

	};
	private DownManagerAdapter downManagerAdapter;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_bottom:
			Intent intent = new Intent(mContext, DownloadPathSettingActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_bottom2:
			if (mSelectedIds.size() > 0) {
				selectDownloadBeans = downManagerAdapter.getSelectDownloadBeans();
				delete_contentview = View.inflate(mContext, R.layout.download_delete_message, null);
				new AlertDialog.Builder(mContext).setTitle(getResources().getString(R.string.delete_local_title, mSelectedIds.size()))
						.setView(delete_contentview).setNegativeButton(R.string.delete_local_delete, getDeleteClickHandler1(selectDownloadBeans))
						.setPositiveButton(R.string.delete_local_cancle, null).show();
			}
			break;
		case R.id.title_back:
			finish();
			break;
		case R.id.title_done:
			stopEditMode();
			break;
		case R.id.title_edit:
			editMode();
			break;
		case R.id.title_select:
			if (title_select.getText().toString().trim().equals(mContext.getResources().getString(R.string.deselect))) {
				// 取消
				title_select.setText(R.string.select_all);
				downManagerAdapter.setSelect(false);
			} else {
				// 全选择
				title_select.setText(R.string.deselect);
				downManagerAdapter.setSelect(true);
			}
			break;
		}
	}

	private ArrayList<DownloadBean> selectDownloadBeans;
	private Set<Integer> mSelectedIds = new HashSet<Integer>();
	private View delete_contentview;

	private void clearSelection() {
		mSelectedIds.clear();
	}

	private void deleteDownload(DownloadBean downloadBean, boolean deleteFile) {
		int status = downloadBean.getStatus();
		boolean isComplete = status == Downloads.STATUS_SUCCESS || status == Downloads.STATUS_FILE_ERROR;
		String localUri = downloadBean.getPath();
		if (isComplete && localUri != null && deleteFile) {
			String path = Uri.parse(localUri).getPath();
			if (path.startsWith(Environment.getExternalStorageDirectory().getPath())) {
				mDownloadManager.markRowDeleted(downloadBean.getDownloadId());
				return;
			}
		}
		mDownloadManager.remove(downloadBean.getDownloadId());
	}

	private AlertDialog dialog;

	public void showSuccessDialog(final DownloadBean downloadBean) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		View contentView = getLayoutInflater().inflate(R.layout.download_dialog_success, null);

		TextView delete_local = (TextView) contentView.findViewById(R.id.delete_local);
		TextView show_download_local = (TextView) contentView.findViewById(R.id.show_download_local);

		show_download_local.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(mContext).setTitle(R.string.show_download_local_title).setMessage(downloadBean.getPath())
						.setNegativeButton(R.string.show_download_return, null).show();
				closeDialog();
			}
		});
		delete_local.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				delete_contentview = View.inflate(mContext, R.layout.download_delete_message, null);
				new AlertDialog.Builder(mContext).setTitle(getResources().getString(R.string.delete_local_title, 1)).setView(delete_contentview)
						.setNegativeButton(R.string.delete_local_delete, getDeleteClickHandler1(downloadBean))
						.setPositiveButton(R.string.delete_local_cancle, null).show();
				closeDialog();
			}
		});

		dialog = adb.create();
		dialog.setView(contentView, 0, 0, 0, 0);

		dialog.show();
	}

	private DialogInterface.OnClickListener getDeleteClickHandler1(final ArrayList<DownloadBean> selectDownloadBeans) {
		return new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CheckBox check_box = (CheckBox) delete_contentview.findViewById(R.id.check_box);
				for (DownloadBean downloadBean : selectDownloadBeans) {
					deleteDownload(downloadBean, check_box.isChecked());
				}
				clearSelection();
				stopEditMode();
			}
		};
	}

	private DialogInterface.OnClickListener getDeleteClickHandler1(final DownloadBean downloadBean) {
		return new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CheckBox check_box = (CheckBox) delete_contentview.findViewById(R.id.check_box);
				deleteDownload(downloadBean, check_box.isChecked());
			}
		};
	}

	public void closeDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		/*
		 * if (mAlldownloadBeans != null && mAlldownloadBeans.size() >= 2) { //
		 * 第一项与第二项标题不同，说明标题需要移动 if
		 * (!mAlldownloadBeans.get(firstVisibleItem).getTitleState().equals(
		 * mAlldownloadBeans.get(firstVisibleItem + 1).getTitleState())) {
		 * ((TitledListView) view).moveTitle(); } else { ((TitledListView)
		 * view).updateTitle(mAlldownloadBeans.get(firstVisibleItem).
		 * getTitleState()); } if (firstVisibleItem >= mDownloadingBeans.size())
		 * { ((TitledListView)
		 * view).updateTitleState(getResources().getString(R.string.completed) +
		 * mCompleteBeans.size()); } else { ((TitledListView)
		 * view).updateTitleState(getResources().getString(R.string.downloading)
		 * + mDownloadingBeans.size()); } }
		 */
		if (mAlldownloadBeans == null || mAlldownloadBeans.size() == 0) {
			((TitledListView) view).GoneTitleState();
		} else {
			if (mAlldownloadBeans.size() >= 2) {
				// 第一项与第二项标题不同，说明标题需要移动
				if (!mAlldownloadBeans.get(firstVisibleItem).getTitleState().equals(mAlldownloadBeans.get(firstVisibleItem + 1).getTitleState())) {
					((TitledListView) view).moveTitle();
				} else {
					((TitledListView) view).updateTitle(mAlldownloadBeans.get(firstVisibleItem).getTitleState());
				}
			}
			if (firstVisibleItem >= mDownloadingBeans.size()) {
				((TitledListView) view).updateTitleState(getResources().getString(R.string.completed) + mCompleteBeans.size());
			} else {
				((TitledListView) view).updateTitleState(getResources().getString(R.string.downloading) + mDownloadingBeans.size());
			}
			((TitledListView) view).VisibleTitleState();
		}
	}

	private void editMode() {
		layout_bottom2.setVisibility(View.VISIBLE);
		layout_bottom.setVisibility(View.GONE);
		layout_top2.setVisibility(View.VISIBLE);
		layout_top.setVisibility(View.GONE);
		sp.edit().putBoolean("isEdit", true).commit();
		downManagerAdapter.notifyDataSetChanged();
	}

	private void stopEditMode() {
		layout_bottom2.setVisibility(View.GONE);
		layout_bottom.setVisibility(View.VISIBLE);
		layout_top2.setVisibility(View.GONE);
		layout_top.setVisibility(View.VISIBLE);
		sp.edit().putBoolean("isEdit", false).commit();
		title_select.setText(R.string.select_all);
		// 清除所有数据的选中状态
		downManagerAdapter.setSelect(false);
		downManagerAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		DownloadBean downloadBean = (DownloadBean) parent.getItemAtPosition(position);
		if (Downloads.isStatusCompleted(downloadBean.status) && !sp.getBoolean("isEdit", false)) {
			if (downloadBean.path != null && new File(downloadBean.path).exists()) {
				Intent activityIntent = new Intent(Intent.ACTION_VIEW);
				activityIntent.setDataAndType(Uri.fromFile(new File(downloadBean.path)), downloadBean.mediaType);
				activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				try {
					startActivity(activityIntent);
				} catch (ActivityNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onCheckedChange(boolean checked, int id) {
		if (mSelectedIds == null) {
			mSelectedIds = new HashSet<Integer>();
		}
		if (checked) {
			mSelectedIds.add(id);
		} else {
			mSelectedIds.remove(id);
		}
	}

}
