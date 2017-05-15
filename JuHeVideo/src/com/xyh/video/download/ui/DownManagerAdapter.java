package com.xyh.video.download.ui;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.videoplayer.bd.R;
import com.xyh.video.download.CheckedListener;
import com.xyh.video.download.DownloadBean;
import com.xyh.video.download.Downloads;
import com.xyh.video.download.providers.DownloadManager;

public class DownManagerAdapter extends BaseAdapter {

	ArrayList<DownloadBean> mAlldownloadBeans;
	private Context mContext;
	private Resources mResources;
	private DownloadManager mDownloadManager;
	private DownloadListActivity mDownloadListActivity;
	private LayoutInflater mLayoutInflater;
	private CheckedListener mCheckedListener;
	public DownManagerAdapter(ArrayList<DownloadBean> alldownloadBeans, Context context, DownloadManager downloadManager,
			DownloadListActivity downloadListActivity) {
		this.mAlldownloadBeans = alldownloadBeans;
		this.mContext = context;
		this.mDownloadManager = downloadManager;
		this.mDownloadListActivity = downloadListActivity;
		mResources = context.getResources();
		mLayoutInflater = LayoutInflater.from(context);
	}

	public void setList(ArrayList<DownloadBean> mAlldownloadBeans2) {
		this.mAlldownloadBeans = mAlldownloadBeans2;
	}
	

	public void setCheckedListener(CheckedListener mCheckedListener) {
		this.mCheckedListener = mCheckedListener;
	}

	public void setSelect(boolean b) {
		for (int i = 0; i < mAlldownloadBeans.size(); i++) {
			DownloadBean downloadBean = mAlldownloadBeans.get(i);
			downloadBean.selected = b;
			mCheckedListener.onCheckedChange(b, downloadBean.downloadId);
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mAlldownloadBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return mAlldownloadBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final DownloadBean downloadBean = mAlldownloadBeans.get(position);
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.download_list_item, null, false);
			holder = new ViewHolder();
			holder.download_state_icon = (ImageView) convertView.findViewById(R.id.download_state_icon);
			holder.size_current_text = (TextView) convertView.findViewById(R.id.size_current_text);
			holder.size_all_text = (TextView) convertView.findViewById(R.id.size_text);
			holder.progressBar = (ProgressBar) convertView.findViewById(R.id.download_progress);
			holder.iconView = (ImageView) convertView.findViewById(R.id.download_icon);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.download_checkbox);
			holder.download_title = (TextView) convertView.findViewById(R.id.download_title);
			holder.status_text = (TextView) convertView.findViewById(R.id.status_text);
			holder.title_state = (TextView) convertView.findViewById(R.id.title_state);
			holder.zhanwei_view = convertView.findViewById(R.id.zhanwei_view);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final long downloadId = downloadBean.getDownloadId();
		String mediaType = downloadBean.getMediaType();
		final String path = downloadBean.getPath();
		String title = downloadBean.getTitle();
		long totalBytes = downloadBean.getTotalSize();
		long currentBytes = downloadBean.getDownloadSize();
		final int status = downloadBean.getStatus();

		holder.iconView.setVisibility(View.VISIBLE);

		holder.iconView.setImageResource(downloadBean.icon);

		if (title.length() == 0) {
			title = mResources.getString(R.string.missing_title);
		}
		holder.download_title.setText(title);
		int progress = getProgressValue(totalBytes, currentBytes);

		boolean indeterminate = status == DownloadManager.STATUS_PENDING;
		holder.progressBar.setIndeterminate(indeterminate);
		if (!indeterminate) {
			holder.progressBar.setProgress(progress);
		}
		if (status == Downloads.STATUS_FILE_ERROR || status == Downloads.STATUS_SUCCESS) {
			holder.size_current_text.setVisibility(View.GONE);
			holder.progressBar.setVisibility(View.GONE);
			holder.zhanwei_view.setVisibility(View.VISIBLE);
		} else {
			holder.size_current_text.setVisibility(View.VISIBLE);
			holder.progressBar.setVisibility(View.VISIBLE);
			holder.zhanwei_view.setVisibility(View.GONE);
		}
		if (downloadBean.getTitleState().equals(mContext.getResources().getString(R.string.completed))) {
			holder.title_state.setText(downloadBean.getTitleState() + " " + mCompleteNum);
		} else {
			holder.title_state.setText(downloadBean.getTitleState() + " " + mDownloadingNum);
		}
		holder.size_current_text.setText(getSizeText(currentBytes) + "/");
		holder.size_all_text.setText(getSizeText(totalBytes));
		holder.status_text.setText(mResources.getString(getStatusStringId(status, holder)));
		holder.checkBox.setChecked(downloadBean.selected);
		holder.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				downloadBean.selected = !downloadBean.selected;
				mCheckedListener.onCheckedChange(downloadBean.selected, downloadBean.downloadId);
			}
		});

		SharedPreferences sp = mContext.getSharedPreferences("downloadmanager", Context.MODE_PRIVATE);
		Boolean isEdit = sp.getBoolean("isEdit", false);
		if (isEdit) {
			holder.checkBox.setVisibility(View.VISIBLE);
			holder.download_state_icon.setVisibility(View.GONE);
		} else {
			holder.checkBox.setVisibility(View.GONE);
			holder.download_state_icon.setVisibility(View.VISIBLE);
			holder.download_state_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (status) {
					case Downloads.STATUS_PENDING:
					case Downloads.STATUS_RUNNING:
						mDownloadManager.pauseDownload(downloadId);
						break;

					case Downloads.STATUS_PAUSED_BY_APP:
					case Downloads.STATUS_WAITING_FOR_NETWORK:
						mDownloadManager.resumeDownload(downloadId);
						break;

					case Downloads.STATUS_SUCCESS:
						mDownloadListActivity.showSuccessDialog(downloadBean);
						break;
					case Downloads.STATUS_WAITING_TO_RETRY:
					case Downloads.STATUS_FILE_ERROR:
						// 重新下载
						mDownloadManager.restartDownload(downloadId);
						break;
					}
				}
			});
		}

		// 第一项和前后不同的项需要显示标题，否则隐藏
		if (position == 0) {
			holder.title_state.setVisibility(View.VISIBLE);
		} else if (position < getCount() && !mAlldownloadBeans.get(position).getTitleState().equals(mAlldownloadBeans.get(position - 1).getTitleState())) {
			holder.title_state.setVisibility(View.VISIBLE);
		} else {
			holder.title_state.setVisibility(View.GONE);
		}
		return convertView;
	}

	private int getStatusStringId(int status, ViewHolder holder) {
		switch (status) {
		case Downloads.STATUS_FILE_ERROR:
			holder.download_state_icon.setImageResource(R.drawable.download_redownload);
			return R.string.download_error;

		case Downloads.STATUS_SUCCESS:
			holder.download_state_icon.setImageResource(R.drawable.download_success);
			return R.string.download_success;

		case Downloads.STATUS_PENDING:
		case Downloads.STATUS_RUNNING:
			holder.download_state_icon.setImageResource(R.drawable.download_running);
			return R.string.download_running;
		case Downloads.STATUS_WAITING_TO_RETRY:
			holder.download_state_icon.setImageResource(R.drawable.download_redownload);
			return R.string.download_retry;
		case Downloads.STATUS_WAITING_FOR_NETWORK:
			holder.download_state_icon.setImageResource(R.drawable.download_redownload);
			return R.string.download_retry;

		case Downloads.STATUS_PAUSED_BY_APP:
			holder.download_state_icon.setImageResource(R.drawable.download_pause);
			return R.string.download_paused;
		}
		holder.download_state_icon.setImageResource(R.drawable.download_redownload);
		return R.string.download_retry;
	}

	private String getSizeText(long totalBytes) {
		String sizeText = "";
		if (totalBytes >= 0) {
			sizeText = Formatter.formatFileSize(mContext, totalBytes);
		}
		return sizeText;
	}

	public int getProgressValue(long totalBytes, long currentBytes) {
		if (totalBytes == -1) {
			return 0;
		}
		return (int) (currentBytes * 100 / totalBytes);
	}

	public class ViewHolder {
		public TextView title_state;
		public TextView status_text;
		public TextView download_title;
		public CheckBox checkBox;
		public ImageView iconView;
		public ProgressBar progressBar;
		public TextView size_all_text;
		public TextView size_current_text;
		public ImageView download_state_icon;
		public TextView title;
		public TextView text;
		public Button bt;
		public View zhanwei_view;
	}

	public ArrayList<DownloadBean> getSelectDownloadBeans() {

		ArrayList<DownloadBean> selectDownloadBeans = new ArrayList<DownloadBean>();
		for (int j = 0; j < mAlldownloadBeans.size(); j++) {
			if (mAlldownloadBeans.get(j).selected) {
				selectDownloadBeans.add(mAlldownloadBeans.get(j));
			}
		}

		return selectDownloadBeans;
	}

	private int mDownloadingNum, mCompleteNum;

	public void setTitleStateNum(int downloadingNum, int completeNum) {
		this.mDownloadingNum = downloadingNum;
		this.mCompleteNum = completeNum;
	}

}
