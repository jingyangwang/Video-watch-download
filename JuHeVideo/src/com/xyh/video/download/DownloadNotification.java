/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xyh.video.download;

import java.util.Collection;
import java.util.HashMap;

import com.videoplayer.bd.R;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;


/**
 * This class handles the updating of the Notification Manager for the cases
 * where there is an ongoing download. Once the download is complete (be it
 * successful or unsuccessful) it is no longer the responsibility of this
 * component to show the download in the notification manager.
 *
 */
class DownloadNotification {

	Context mContext;
	HashMap<Long, NotificationItem> mNotifications;
	private SystemFacade mSystemFacade;

	static final String LOGTAG = "DownloadNotification";
	static final String WHERE_RUNNING = "(" + Downloads.COLUMN_STATUS + " >= '100') AND (" + Downloads.COLUMN_STATUS + " <= '199') AND ("
			+ Downloads.COLUMN_VISIBILITY + " IS NULL OR " + Downloads.COLUMN_VISIBILITY + " == '" + Downloads.VISIBILITY_VISIBLE + "' OR "
			+ Downloads.COLUMN_VISIBILITY + " == '" + Downloads.VISIBILITY_VISIBLE_NOTIFY_COMPLETED + "')";
	static final String WHERE_COMPLETED = Downloads.COLUMN_STATUS + " >= '200' AND " + Downloads.COLUMN_VISIBILITY + " == '"
			+ Downloads.VISIBILITY_VISIBLE_NOTIFY_COMPLETED + "'";

	/**
	 * This inner class is used to collate downloads that are owned by the same
	 * application. This is so that only one notification line item is used for
	 * all downloads of a given application.
	 *
	 */
	static class NotificationItem {
		int mId; // This first db _id for the download for the app
		long mTotalCurrent = 0;
		long mTotalTotal = 0;
		String mTitle;
		Builder mBuilder;

		void addItem(String title, long currentBytes, long totalBytes) {
			mTotalCurrent += currentBytes;
			if (totalBytes <= 0 || mTotalTotal == -1) {
				mTotalTotal = -1;
			} else {
				mTotalTotal += totalBytes;
			}
		}

		void update(String title, long currentBytes, long totalBytes) {
			mTotalCurrent = currentBytes;
			mTotalTotal = totalBytes;
			mTitle = title;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param ctx
	 *            The context to use to obtain access to the Notification
	 *            Service
	 */
	DownloadNotification(Context ctx, SystemFacade systemFacade) {
		mContext = ctx;
		mSystemFacade = systemFacade;
		mNotifications = new HashMap<Long, NotificationItem>();
	}

	/*
	 * Update the notification ui.
	 */
	public void updateNotification(Collection<DownloadInfo> downloads) {
		updateActiveNotification(downloads);
		updateCompletedNotification(downloads);
	}

	private void updateActiveNotification(Collection<DownloadInfo> downloads) {
		for (DownloadInfo download : downloads) {
			if (!isActiveAndVisible(download)) {
				continue;
			}
			long max = download.mTotalBytes;
			long progress = download.mCurrentBytes;
			long id = download.mId;
			String title = download.mTitle;

			if (title == null || title.length() == 0) {
				continue;
			}
			if (download.mStatus == Downloads.STATUS_PAUSED_BY_APP) {
				mNotifications.remove(id);
				continue;
			}
			NotificationItem item;
			if (mNotifications.containsKey(id)) {
				item = mNotifications.get(id);
				item.update(title, progress, max);
			} else {
				item = new NotificationItem();
				item.mId = (int) id;
				item.update(title, progress, max);
			}
			mNotifications.put(id, item);
		}

		for (NotificationItem item : mNotifications.values()) {
			Notification n = null;
			Builder builder = item.mBuilder;
			if (builder == null) {
				builder = new Builder(mContext);
				builder.setSmallIcon(android.R.drawable.stat_sys_download_done);
				builder.setContentTitle(item.mTitle);
				builder.setWhen(System.currentTimeMillis());
				int progress = 0;
				if (item.mTotalTotal > 0) {
					progress = (int) (item.mTotalCurrent * 100 / item.mTotalTotal);
				}
				builder.setContentText(progress + "%");
				builder.setProgress(100, progress, false);

				n = builder.build();
				n.flags |= Notification.FLAG_ONGOING_EVENT;
				n.icon = android.R.drawable.stat_sys_download;
				n.when = System.currentTimeMillis();
				n = builder.build();
				item.mBuilder = builder;
			} else {
				int progress = (int) (item.mTotalCurrent * 100 / item.mTotalTotal);
				builder.setContentText(progress + "%");
				builder.setProgress(100, progress, false);
				n = builder.build();
			}
			Intent intent = new Intent(Constants.ACTION_LIST);
			intent.setClassName(mContext.getPackageName(), DownloadReceiver.class.getName());
			intent.setData(ContentUris.withAppendedId(Downloads.ALL_DOWNLOADS_CONTENT_URI, item.mId));
			n.contentIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
			mSystemFacade.postNotification(item.mId, n);
		}
	}

	private void updateCompletedNotification(Collection<DownloadInfo> downloads) {
		for (DownloadInfo download : downloads) {
			if (!isCompleteAndVisible(download)) {
				continue;
			}
			long id = download.mId;
			NotificationItem notificationItem = mNotifications.get(id);
			if (notificationItem != null) {
				mNotifications.remove(id);
				mSystemFacade.cancelNotification(id);
				Builder builder = new Builder(mContext);
				String title = download.mTitle;
				Uri contentUri = ContentUris.withAppendedId(Downloads.ALL_DOWNLOADS_CONTENT_URI, id);
				String caption;
				Intent intent;
				if (Downloads.isStatusError(download.mStatus)) {
					caption = mContext.getResources().getString(R.string.notification_download_failed);
					intent = new Intent(Constants.ACTION_LIST);
				} else {
					caption = mContext.getResources().getString(R.string.notification_download_complete);
					if (download.mDestination == Downloads.DESTINATION_EXTERNAL) {
						intent = new Intent(Constants.ACTION_OPEN);
					} else {
						intent = new Intent(Constants.ACTION_LIST);
					}
				}
				intent.setClassName(mContext.getPackageName(), DownloadReceiver.class.getName());
				intent.setData(contentUri);

				builder.setSmallIcon(android.R.drawable.stat_sys_download_done);
				builder.setContentTitle(title);
				builder.setAutoCancel(true);
				builder.setContentText(caption);
				Notification n = builder.build();
				n.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT;
				n.icon = android.R.drawable.stat_sys_download;
				n.when = System.currentTimeMillis();
				n = builder.build();
				n.contentIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

				mSystemFacade.postNotification(id, n);
			}

		}
	}

	private boolean isActiveAndVisible(DownloadInfo download) {
		return 100 <= download.mStatus && download.mStatus < 200 && download.mVisibility != Downloads.VISIBILITY_HIDDEN;
	}

	private boolean isCompleteAndVisible(DownloadInfo download) {
		return download.mStatus >= 200;
	}
}
