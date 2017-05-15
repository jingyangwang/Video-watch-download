/*
 * *************************************************************************
 *  StorageBrowserFragment.java
 * **************************************************************************
 *  Copyright © 2015 VLC authors and VideoLAN
 *  Author: Geoffrey Métais
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *  ***************************************************************************
 */

package com.shipin.player.gui.browser;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.videolan.libvlc.Media;

import com.shipin.player.MediaWrapper;
import com.videoplayer.bd.R;
import com.shipin.player.gui.SharedPreferencesUtils;
import com.shipin.player.util.AndroidDevices;
import com.shipin.player.util.CustomDirectories;
import com.shipin.player.util.SharePreUtil;

import java.io.File;

public class StorageBrowserFragment extends FileBrowserFragment implements View.OnClickListener {

	public static final String KEY_IN_MEDIALIB = "key_in_medialib";

	boolean mScannedDirectory = false;
	FloatingActionButton mAddDirectoryFAB;

	public StorageBrowserFragment(){
		mHandler = new BrowserFragmentHandler(this);
		ROOT = AndroidDevices.EXTERNAL_PUBLIC_DIRECTORY;
	}

	@Override
	protected Fragment createFragment() {
		return new StorageBrowserFragment();
	}

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mAdapter = new StorageBrowserAdapter(this);
		if (bundle == null)
			bundle = getArguments();
		if (bundle != null){
			mScannedDirectory = bundle.getBoolean(KEY_IN_MEDIALIB);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		        if (mRoot) {
			        mAddDirectoryFAB = (FloatingActionButton) v.findViewById(R.id.fab_add_custom_dir);
		            mAddDirectoryFAB.setVisibility(View.VISIBLE);
		            mAddDirectoryFAB.setOnClickListener(this);
		        }
		//修改布局文件，添加批处理按钮     xj   1102
//		LinearLayout linear=(LinearLayout) v.findViewById(R.id.linear_ly);
//		linear.setVisibility(View.VISIBLE);
//		LinearLayout linear1=(LinearLayout) v.findViewById(R.id.linear_ly1);
//		LinearLayout linear2=(LinearLayout) v.findViewById(R.id.linear_ly2);
//		LinearLayout linear3=(LinearLayout) v.findViewById(R.id.linear_ly3);
//		linear1.setOnClickListener(listener);
//		linear1.setOnClickListener(listener);
//		linear1.setOnClickListener(listener);
		//修改布局文件，添加批处理按钮     xj   1102
		return v;
	}
	//修改监听事件   xj 1102
//	OnClickListener listener=new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			switch (v.getId()) {
//			case R.id.linear_ly1:
//SharePreUtil.setcheck(getActivity(), true);
//				break;
//			case R.id.linear_ly2:
//
//				break;
//			case R.id.linear_ly3:
//
//				break;
//
//			default:
//				break;
//			}
//		}
//	};
	//修改监听事件   xj 1102


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(KEY_IN_MEDIALIB, mScannedDirectory);
	}

	@Override
	protected void browseRoot() {
		String storages[] = AndroidDevices.getMediaDirectories();
		BaseBrowserAdapter.Storage storage;
		for (String mediaDirLocation : storages) {
			storage = new BaseBrowserAdapter.Storage(Uri.fromFile(new File(mediaDirLocation)));
			if (TextUtils.equals(AndroidDevices.EXTERNAL_PUBLIC_DIRECTORY, mediaDirLocation))
				storage.setName(getString(R.string.internal_memory));
			mAdapter.addItem(storage, false, false);
		}
		mHandler.sendEmptyMessage(BrowserFragmentHandler.MSG_HIDE_LOADING);
		if (mReadyToDisplay) {
			updateEmptyView();
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void update() {
		mAdapter.updateMediaDirs();
		super.update();
	}

	@Override
	public void onMediaAdded(int index, Media media) {
		if (media.getType() != Media.Type.Directory)
			return;
		super.onMediaAdded(index, media);
	}

	protected void updateDisplay() {
		if (!mAdapter.isEmpty()) {
			if (mSavedPosition > 0) {
				mLayoutManager.scrollToPositionWithOffset(mSavedPosition, 0);
				mSavedPosition = 0;
			}
		}
		mAdapter.notifyDataSetChanged();
	}

	public void browse (MediaWrapper media, int position, boolean scanned){
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		Fragment next = createFragment();
		Bundle args = new Bundle();
		args.putParcelable(KEY_MEDIA, media);
		args.putBoolean(KEY_IN_MEDIALIB, mScannedDirectory || scanned);
		next.setArguments(args);
		ft.replace(R.id.fragment_placeholder, next, media.getLocation());
	    //ft.addToBackStack(mMrl);
		ft.addToBackStack(null);
		ft.commit();
	}

	protected void setContextMenu(MenuInflater inflater, Menu menu, int position) {
		if (mRoot) {
			BaseBrowserAdapter.Storage storage = (BaseBrowserAdapter.Storage) mAdapter.getItem(position);
			boolean isCustom = CustomDirectories.contains(storage.getUri().getPath());
			if (isCustom)
				inflater.inflate(R.menu.directory_custom_dir, menu);
		} else
			super.setContextMenu(inflater, menu, position);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.fab_add_custom_dir){
			showAddDirectoryDialog();
		}
	}

	@Override
	protected String getCategoryTitle() {
		return getString(R.string.directories_summary);
	}
}
