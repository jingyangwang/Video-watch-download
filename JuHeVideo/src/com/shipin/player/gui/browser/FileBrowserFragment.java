/*
 * *************************************************************************
 *  FileBrowserFragment.java
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

import java.io.File;

import org.videolan.libvlc.util.AndroidUtil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shipin.player.MediaDatabase;
import com.shipin.player.MediaWrapper;
import com.videoplayer.bd.R;
import com.shipin.player.gui.AudioPlayerContainerActivity;
import com.shipin.player.gui.MyPageFragment1;
import com.shipin.player.gui.SharedPreferencesUtils;
import com.shipin.player.util.AndroidDevices;
import com.shipin.player.util.CustomDirectories;
import com.shipin.player.util.Strings;
import com.shipin.player.util.Util;

public class FileBrowserFragment extends BaseBrowserFragment {
	private String dir="";

	private AlertDialog mAlertDialog;
	//    Handler handler=new Handler(){
	//        @Override
	//        public void handleMessage(Message msg){
	//            switch (msg.what){
	//                case 1:
	//                    //TODO
	//                	 Bundle b = msg.getData(); 
	//                     String color = b.getString("color"); 
	//                	 Toast.makeText(getActivity(), ">>>>>>>FileBrowserFragment"+color, Toast.LENGTH_LONG).show();
	//                    break;
	//            }
	//    }};
	//    interface mylistener{
	//    	
	//    	public void setdirectory(String directory);
	//    }
	//mylistener listener;
	@Override
	@Deprecated
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		//listener=(mylistener) activity;
		super.onAttach(activity);
	}

	public FileBrowserFragment() {
		super();
		ROOT = AndroidDevices.EXTERNAL_PUBLIC_DIRECTORY;
	}

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mRoot = mMrl == null;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mEmptyView.setText(getString(R.string.directory_empty));
	}

	@Override
	protected Fragment createFragment() {
		return new FileBrowserFragment();
	}

	public String getTitle(){
		if (mRoot)
			return getCategoryTitle();
		else {
			String title;
			if (mCurrentMedia != null) {
				if (TextUtils.equals(AndroidDevices.EXTERNAL_PUBLIC_DIRECTORY, Strings.removeFileProtocole(mMrl)))
					title = getString(R.string.internal_memory);
				else
					title = mCurrentMedia.getTitle();
			} else
				title = Strings.getName(mMrl);

			return title;
		}
	}



	public String getTitle11(){
		Log.i("debug", "mMrl: " + mMrl);
		if (mRoot)
			return getCategoryTitle();
		else {
			String title = null;
			if (mCurrentMedia != null) {
				if (TextUtils.equals(AndroidDevices.EXTERNAL_PUBLIC_DIRECTORY, Strings.removeFileProtocole(mMrl)))
					title = getString(R.string.internal_memory);
				else
					title = Strings.removeFileProtocole(mMrl);
			} 
			return title;
		}
	}

	@Override
	protected String getCategoryTitle() {
		return getString(R.string.directories);
	}

	@Override
	protected void browseRoot() {
		final Activity context = getActivity();
		mAdapter.updateMediaDirs();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String storages[] = AndroidDevices.getMediaDirectories();
				MediaWrapper directory;
				//                //handle传递
				//                Message msg = new Message();
				//                msg.what = 1;
				//                Bundle b = new Bundle();// 存放数据 

				for (String mediaDirLocation : storages) {
					if (!(new File(mediaDirLocation).exists()))
						continue;
					directory = new MediaWrapper(AndroidUtil.PathToUri(mediaDirLocation));
					directory.setType(MediaWrapper.TYPE_DIR);
					//                    String str=directory.getTitle();
					//                   // Toast.makeText(context, mediaDirLocation+"目录", Toast.LENGTH_LONG).show();
					//                    Log.i(">>>>><<<<<<<<<<", ""+str);
					//handler
					//                    b.putString("color", str); 
					//                    msg.setData(b);
					//                    handler.sendMessage(msg);


					if (TextUtils.equals(AndroidDevices.EXTERNAL_PUBLIC_DIRECTORY, mediaDirLocation))
						directory.setTitle(getString(R.string.internal_memory));
					//directory.setTitle(context.getResources().getString(R.string.app_name));
					//Toast.makeText(getActivity(), mediaDirLocation+"目录", Toast.LENGTH_LONG).show();
					// Log.i("《《《《《《《《《《《《", ""+mediaDirLocation);
					//handler
					//                    b.putString("color", mediaDirLocation); 
					//                    msg.setData(b);
					//                    handler.sendMessage(msg);
					mAdapter.addItem(directory, false, false);
				}
				if (mReadyToDisplay) {
					context.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							updateEmptyView();
							mAdapter.notifyDataSetChanged();
							parseSubDirectories();
						}
					});
				}
				mHandler.sendEmptyMessage(BrowserFragmentHandler.MSG_HIDE_LOADING);
			}
		}).start();
	}

	public void onStart(){
		super.onStart();
		Log.i("debug", "getTitle11(): " + getTitle11());
//		dir = dir.replaceAll("/"+getTitle11()," ");
//		if(dir!=null){
//			dir=dir+"/"+getTitle11();
//			SharedPreferencesUtils.setParam(getActivity(), "key",dir); 
//		}
//		else{
			SharedPreferencesUtils.setParam(getActivity(), "key", getTitle11()+""); 
//		}
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		// SharedPreferencesUtils.setParam(getActivity(), "key", getTitle11()+""); 
		
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mReadyToDisplay)
			update();
		// Toast.makeText(getActivity(), ">>>>>>>FileBrowserFragment"+">>>>>>>>>>>>>>>>>>>>"+getTitle11(), 1).show();
		//        MyPageFragment1 mypro=new MyPageFragment1();
		//        Bundle bundle = new Bundle();  
		//    	bundle.putString("key", getTitle11());  
		//    	mypro.setArguments(bundle); 
		// listener.setdirectory(getTitle());
	}

	@Override
	protected void updateDisplay() {
		super.updateDisplay();
		if (isRootDirectory())
			mAdapter.updateMediaDirs();
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mAlertDialog != null && mAlertDialog.isShowing())
			mAlertDialog.dismiss();
//		if(dir!=null){
			dir = dir.replaceAll("/"+getTitle11()," ");
			SharedPreferencesUtils.setParam(getActivity(), "key",dir); 
//		}
//		else{
//			SharedPreferencesUtils.setParam(getActivity(), "key", getTitle11()+""); 
//		}
	}

	public void showAddDirectoryDialog() {
		final Context context = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		final AppCompatEditText input = new AppCompatEditText(context);
		if (!AndroidUtil.isHoneycombOrLater()) {
			input.setTextColor(getResources().getColor(R.color.grey50));
		}
		input.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		builder.setTitle(R.string.add_custom_path);
		builder.setMessage(R.string.add_custom_path_description);
		builder.setView(input);
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int which) {
				return;
			}
		});
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String path = input.getText().toString().trim();
				File f = new File(path);
				if (!f.exists() || !f.isDirectory()) {
					Util.snacker(getView(), getString(R.string.directorynotfound, path));
					return;
				}

				CustomDirectories.addCustomDirectory(f.getAbsolutePath());
				refresh();
				((AudioPlayerContainerActivity)getActivity()).updateLib();
			}
		});
		mAlertDialog = builder.show();
	}

	@Override
	protected boolean handleContextItemSelected(MenuItem item, int position) {
		if (mRoot) {
			if (item.getItemId() == R.id.directory_remove_custom_path){
				BaseBrowserAdapter.Storage storage = (BaseBrowserAdapter.Storage) mAdapter.getItem(position);
				MediaDatabase.getInstance().recursiveRemoveDir(storage.getUri().getPath());
				CustomDirectories.removeCustomDirectory(storage.getUri().getPath());
				mAdapter.updateMediaDirs();
				mAdapter.removeItem(position, true);
				((AudioPlayerContainerActivity)getActivity()).updateLib();
				return true;
			} else
				return false;
		} else
			return super.handleContextItemSelected(item, position);
	}
}
