package com.shipin.player.gui.video;

/*****************************************************************************
 * VideoListActivity.java
 *****************************************************************************
 * Copyright © 2011-2012 VLC authors and VideoLAN
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/


import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.videolan.libvlc.Media;
import org.videolan.libvlc.util.AndroidUtil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.shipin.player.Config;
import com.shipin.player.MediaDatabase;
import com.shipin.player.MediaGroup;
import com.shipin.player.MediaLibrary;
import com.shipin.player.MediaWrapper;
import com.videoplayer.bd.R;
import com.shipin.player.Thumbnailer;
import com.shipin.player.VLCApplication;
import com.shipin.player.gui.FileItem;
import com.shipin.player.gui.MyFragment;
import com.shipin.player.gui.SecondaryActivity;
import com.shipin.player.gui.browser.MediaBrowserFragment;
import com.shipin.player.interfaces.ISortable;
import com.shipin.player.interfaces.IVideoBrowser;
import com.shipin.player.util.AndroidDevices;
import com.shipin.player.util.Util;
import com.shipin.player.util.VLCInstance;
import com.shipin.player.util.WeakHandler;
import com.shipin.player.widget.SwipeRefreshLayout;

public class VideoGridFragment extends MediaBrowserFragment implements ISortable, IVideoBrowser, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    public final static String TAG = "VLC/VideoListFragment";

    public final static String KEY_GROUP = "key_group";
 
    private static final int DELETE_MEDIA = 0;
    private static final int DELETE_DURATION = 3000;
    private static final int HIDDEN_MEDIA = 1;
    protected LinearLayout mLayoutFlipperLoading;
    protected GridView mGridView;
    protected TextView mTextViewNomedia;
    protected View mViewNomedia;
    protected MediaWrapper mItemToUpdate;
    protected String mGroup;
    protected final CyclicBarrier mBarrier = new CyclicBarrier(2);

    private VideoListAdapter mVideoAdapter;
    private Thumbnailer mThumbnailer;
    private VideoGridAnimator mAnimator;

    private MyFragment mMainActivity;

    //Gridview position saved in onPause()
    private int mGVFirstVisiblePos;
    
    private final String shar_hidden_video_set = "hidden_video_set";

    /* All subclasses of Fragment must include a public empty constructor. */
    public VideoGridFragment() { }
    
    /*
     * 添加文件模式选择操作，添加变量
     */
    private LinearLayout ly,ly1,ly2,ly3;
    private View lyInclude;
    
    //jj 编辑功能shared保存
  	SharedPreferences editFunction;
  	private static int ly1_count=1;
  	
  	//-----------1116 xj 建链表list存取position-----------
  	ArrayList<Integer> position_list=new ArrayList<Integer>();
  	ArrayList<String> position_list1=new ArrayList<String>();
  	//-----------1116 xj 建链表list存取position-----------
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        editFunction = getActivity().getSharedPreferences("jmydata", getActivity().MODE_WORLD_WRITEABLE);

        mVideoAdapter = new VideoListAdapter(this);
        if (savedInstanceState != null)
            setGroup(savedInstanceState.getString(KEY_GROUP));
        /* Load the thumbnailer */
        FragmentActivity activity = getActivity();
        if (activity != null)
            mThumbnailer = new Thumbnailer(activity, activity.getWindowManager().getDefaultDisplay());
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.video_grid, container, false);

        // init the information for the scan (1/2)
        mLayoutFlipperLoading = (LinearLayout) v.findViewById(R.id.layout_flipper_loading);
        mTextViewNomedia = (TextView) v.findViewById(R.id.textview_nomedia);
        mViewNomedia = v.findViewById(android.R.id.empty);
        mGridView = (GridView) v.findViewById(android.R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeLayout);
        lyInclude = v.findViewById(R.id.edit_linear_ly);
        ly1 = (LinearLayout) v.findViewById(R.id.edit_linear_ly1);
        ly2 = (LinearLayout) v.findViewById(R.id.edit_linear_ly2);
        ly3 = (LinearLayout) v.findViewById(R.id.edit_linear_ly3);
		//------------------------1116 xj 添加文件批处理的按键监听-----------------------------
		ly1.setOnClickListener(listener);
		ly2.setOnClickListener(listener);
		ly3.setOnClickListener(listener);
		//------------------------1116 xj 添加文件批处理的按键监听-----------------------------
        //编辑模式判断 jj
        String videoShow = editFunction.getString("video", "0");
        if(videoShow.equals("0")){
        	lyInclude.setVisibility(View.GONE);
        }else{
        	lyInclude.setVisibility(View.VISIBLE);
        }
        
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange700);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        
        //更新数据 yukunjin
        updateList();

        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0);
            }
        });
        mGridView.setAdapter(mVideoAdapter);
        mGridView.setOnItemClickListener(this);
        return v;
    }
	//------------------------1116 xj 添加文件批处理的按键监听-----------------------------
	OnClickListener listener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.edit_linear_ly1:
				if(ly1_count%2!=0){
					for(int i = 0;i < mVideoAdapter.getCount();i++){
						MediaWrapper item = mVideoAdapter.getItem(i);
						item.setSelState(true);
//						mGridView.getChildAt(i).setBackgroundColor(Color.parseColor("#3bac25"));
//						View mitem = (View) mGridView.getItemAtPosition(i);
//						mitem.setBackgroundColor(Color.parseColor("#3bac25"));
//						position_list.add(i);
						position_list1.add(" "+i);
						Log.i("debug", "添加：：：："+i);
					}
				}
				else{
					for(int i = 0;i < mVideoAdapter.getCount();i++){
						MediaWrapper item = mVideoAdapter.getItem(i);
						item.setSelState(false);
//						mGridView.getChildAt(i).setBackgroundColor(Color.WHITE);
//						View mitem = (View) mGridView.getItemAtPosition(i);
//						mitem.setBackgroundColor(Color.parseColor("#3bac25"));
						for (int j = 0; j < position_list1.size(); j++) {
							position_list1.remove(j);
							Log.i("debug", "移除：：：："+i);
						}
					}
				}
				mVideoAdapter.notifyDataSetInvalidated();
				ly1_count++;
				break;
			case R.id.edit_linear_ly2:
				Snackbar.make(getView(), getString(R.string.msg_file_deleting), Snackbar.LENGTH_LONG)
				.show();
				int keycout=0;
				int position1=0;
//				for (int i = 0; i < position_list1.size(); i++) {
//					int kk = Integer.parseInt(position_list1.get(i).trim());
//					position1=kk-keycout;
//					Message msg = delete.obtainMessage(DELETE_MEDIA, position1, 0);
//					delete.sendMessageDelayed(msg, DELETE_DURATION);
//					keycout++;
//				}
//				for (int j = 0; j < position_list1.size(); j++) {
//					position_list1.remove(j);
//				}
//				position_list1.clear();
				
				// yukunjin  delete
				for(int i=0;i<mVideoAdapter.getCount();i++){
					if(mVideoAdapter.getItem(i).isSelState()){
//						int mposition = mVideoAdapter.getPosition(mVideoAdapter.getItem(i));
						position1 = i-keycout;
						Log.i("debug", "可操作的：：：："+position1);
						Message msg = delete.obtainMessage(DELETE_MEDIA, position1, 0);
						delete.sendMessageDelayed(msg, DELETE_DURATION);
						keycout++;
					}
				}
				//更新数据 yukunjin
//		        updateList();
				mGridView.setAdapter(mVideoAdapter);
				break;
			case R.id.edit_linear_ly3:
				
				boolean enableHiddenVideo = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(Config.SHARED_PREF_ENABLE_HIDDEN, false);
				if(!enableHiddenVideo){
					Snackbar.make(getView(), getString(R.string.msg_file_hidden_disable), Snackbar.LENGTH_LONG)
					.show();
				}
				else{
					Snackbar.make(getView(), getString(R.string.file_hidden), Snackbar.LENGTH_LONG)
					.setAction(android.R.string.cancel, mCancelHiddenMediaListener)
					.show();
					int keycout_hide=0;
					int position1_hide=0;
//					for (int i = 0; i < position_list1.size(); i++) {
//						int k = Integer.parseInt(position_list1.get(i).trim());
//						position1=k-keycout_hide;
//						Message msg = mHiddenHandler.obtainMessage(HIDDEN_MEDIA, position1, 0);
//						mHiddenHandler.sendMessageDelayed(msg, HIDDEN_MEDIA);
//						keycout_hide++;
//					}
//					for (int j = 0; j < position_list1.size(); j++) {
//						position_list1.remove(j);
//					}
					// yukunjin  delete
					for(int i=0;i<mVideoAdapter.getCount();i++){
						if(mVideoAdapter.getItem(i).isSelState()){
							position1_hide = i- keycout_hide;
							Log.i("debug", "可操作的：：：："+position1_hide);
							Message msg = mHiddenHandler.obtainMessage(HIDDEN_MEDIA, position1_hide, 0);
							mHiddenHandler.sendMessageDelayed(msg, HIDDEN_MEDIA);
							keycout_hide++;
						}
					}
				}
				//更新数据 yukunjin
//		        updateList();
				mGridView.setAdapter(mVideoAdapter);
				break;

			default:
				break;
			}

		}
	};
	Handler delete = new DeleteHandler(this);
	private static class DeleteHandler extends WeakHandler<VideoGridFragment>{
		public DeleteHandler(VideoGridFragment owner) {
			super(owner);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
			case DELETE_MEDIA:
				getOwner().deleteMedia(msg.arg1);
			}
		}
	}
	//------------------------1116 xj 添加文件批处理的按键监听-----------------------------

	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerForContextMenu(mGridView);

        // init the information for the scan (2/2)
        IntentFilter filter = new IntentFilter();
        filter.addAction(Util.ACTION_SCAN_START);
        filter.addAction(Util.ACTION_SCAN_STOP);
        getActivity().registerReceiver(messageReceiverVideoListFragment, filter);
        if (mMediaLibrary.isWorking()) {
            Util.actionScanStart();
        }
        
        mAnimator = new VideoGridAnimator(mGridView);
    }

    @Override
    public void onPause() {
        super.onPause();
        
        mGVFirstVisiblePos = mGridView.getFirstVisiblePosition();
        mMediaLibrary.setBrowser(null);
        mMediaLibrary.removeUpdateHandler(mHandler);

        /* Stop the thumbnailer */
        if (mThumbnailer != null)
            mThumbnailer.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((getActivity() instanceof MyFragment))
            mMainActivity = (MyFragment) getActivity();
        mMediaLibrary.setBrowser(this);
        mMediaLibrary.addUpdateHandler(mHandler);
        final boolean refresh = mVideoAdapter.isEmpty();
        //updateList();
//        if (refresh)
//        	updateList();
//        else {
//            mViewNomedia.setVisibility(View.GONE);
//            focusHelper(false);
//        }
        
        if(!mVideoAdapter.isEmpty()){
            mViewNomedia.setVisibility(View.GONE);
            focusHelper(false);
        }
        
        //Get & set times
        ArrayMap<String, Long> times = MediaDatabase.getInstance().getVideoTimes();
        mVideoAdapter.setTimes(times);
        mGridView.setSelection(mGVFirstVisiblePos);
        updateViewMode();
        if (mGroup == null && refresh)
            mAnimator.animate();

        /* Start the thumbnailer */
        if (mThumbnailer != null)
            mThumbnailer.start(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_GROUP, mGroup);
    }
    
    @Override
    public void onDestroyView() {
    	if(position_list1.size()>0){
	    	for (int j = 0; j < position_list1.size(); j++) {
				position_list1.remove(j);
			}
    	}
        getActivity().unregisterReceiver(messageReceiverVideoListFragment);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mThumbnailer != null)
            mThumbnailer.clearJobs();
        mBarrier.reset();
        mVideoAdapter.clear();
    }

    protected String getTitle(){
        if (mGroup == null)
            return getString(R.string.video);
        else
            return mGroup;
    }

    private void updateViewMode() {
        if (getView() == null || getActivity() == null) {
            Log.w(TAG, "Unable to setup the view");
            return;
        }
        Resources res = getResources();
        boolean listMode = res.getBoolean(R.bool.list_mode);
        listMode |= res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &&
                PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("force_list_portrait", false);
        // Compute the left/right padding dynamically
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int sidePadding;

        // Select between grid or list
        if (!listMode) {
            sidePadding = (int) ((float)outMetrics.widthPixels / 100f * (float)Math.pow(outMetrics.density, 3) / 2f);
            mGridView.setNumColumns(GridView.AUTO_FIT);
            mGridView.setColumnWidth(res.getDimensionPixelSize(R.dimen.grid_card_width));
            mGridView.setVerticalSpacing(res.getDimensionPixelSize(R.dimen.grid_card_vertical_spacing));
            mVideoAdapter.setListMode(false);
        } else {
            sidePadding = res.getDimensionPixelSize(R.dimen.listview_side_padding);
            mGridView.setNumColumns(1);
            mGridView.setVerticalSpacing(0);
            mGridView.setHorizontalSpacing(0);
            mVideoAdapter.setListMode(true);
        }
        sidePadding = Math.max(0, Math.min(100, sidePadding));
        mGridView.setPadding(sidePadding, mGridView.getPaddingTop(),
                sidePadding, mGridView.getPaddingBottom());
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	MediaWrapper media = mVideoAdapter.getItem(position);
    	String videoShow = editFunction.getString("video", "0");
        if(videoShow.equals("0")){
        	if (media == null)
                return ;
            if (media instanceof MediaGroup) {
            	MyFragment activity = (MyFragment)getActivity();
                activity.showSecondaryFragment(SecondaryActivity.VIDEO_GROUP_LIST, media.getTitle());
            }
            else
                playVideo(media, false);
            
        }else{
        	if(media.isSelState()){
        		Log.i("debug", "移除：：：："+position);
//        		view.setBackgroundColor(Color.WHITE);
        		mVideoAdapter.getItem(position).setSelState(false);
//        		media.setSelState(false);
//        		position_list.remove(position);
        		position_list1.remove(" "+position);
            }else{
            	Log.i("debug", "添加：：：："+position);
//            	view.setBackgroundColor(Color.parseColor("#3bac25"));
            	mVideoAdapter.getItem(position).setSelState(true);
//            	media.setSelState(true);
				//1116  xj list存入位置
//				position_list.add(position);
            	position_list1.add(" "+position);
				//1116  xj list存入位置
            }
        	mVideoAdapter.notifyDataSetInvalidated();
        }
        
    }

    protected void playVideo(MediaWrapper media, boolean fromStart) {
        media.removeFlags(MediaWrapper.MEDIA_FORCE_AUDIO);
        VideoPlayerActivity.start(getActivity(), media.getUri(), fromStart);
    }

    protected void playAudio(MediaWrapper media) {
        if (mService != null) {
            media.addFlags(MediaWrapper.MEDIA_FORCE_AUDIO);
            mService.load(media);
        }
    }

    private boolean handleContextItemSelected(MenuItem menu, int position) {
        if (position >= mVideoAdapter.getCount())
            return false;
        MediaWrapper media = mVideoAdapter.getItem(position);
        if (media == null)
            return false;
        switch (menu.getItemId()){
            case R.id.video_list_play_from_start:
                playVideo(media, true);
                return true;
            case R.id.video_list_play_audio:
                playAudio(media);
                return true;
            case R.id.video_list_info:
                Activity activity = getActivity();
                if (activity instanceof MyFragment)
                    ((MyFragment)activity).showSecondaryFragment(SecondaryActivity.MEDIA_INFO, media.getLocation());
                else {
                    Intent i = new Intent(activity, SecondaryActivity.class);
                    i.putExtra("fragment", "mediaInfo");
                    i.putExtra("param", media.getLocation());
                    startActivity(i);
                }
                return true;
            case R.id.video_list_delete:
                Snackbar.make(getView(), getString(R.string.file_deleted), Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.cancel, mCancelDeleteMediaListener)
                    .show();
                Message msg = mDeleteHandler.obtainMessage(DELETE_MEDIA, position, 0);
                mDeleteHandler.sendMessageDelayed(msg, DELETE_DURATION);
                return true;
            case R.id.video_list_hidden:
            	// 判断隐藏模式是否打开
            	boolean enableHiddenVideo = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(Config.SHARED_PREF_ENABLE_HIDDEN, false);
            	if(!enableHiddenVideo){
            		Snackbar.make(getView(), getString(R.string.msg_file_hidden_disable), Snackbar.LENGTH_LONG)
            		.show();
            		return true;
            		
            	}
            	Snackbar.make(getView(), getString(R.string.file_hidden), Snackbar.LENGTH_LONG)
            	.setAction(android.R.string.cancel, mCancelHiddenMediaListener)
            	.show();
            	Message hiddenMsg = mDeleteHandler.obtainMessage(HIDDEN_MEDIA, position, 0);
            	mHiddenHandler.sendMessageDelayed(hiddenMsg, DELETE_DURATION);
            	return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // Do not show the menu of media group.
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
        MediaWrapper media = mVideoAdapter.getItem(info.position);
        if (media == null || media instanceof MediaGroup)
            return;
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.video_list, menu);
        setContextMenuItems(menu, media);
    }

    private void setContextMenuItems(Menu menu, MediaWrapper mediaWrapper) {
        long lastTime = mediaWrapper.getTime();
        if (lastTime > 0)
            menu.findItem(R.id.video_list_play_from_start).setVisible(true);

        boolean hasInfo = false;
        final Media media = new Media(VLCInstance.get(), mediaWrapper.getUri());
        media.parse();
        if (media.getMeta(Media.Meta.Title) != null)
            hasInfo = true;
        media.release();
        menu.findItem(R.id.video_list_info).setVisible(hasInfo);
        menu.findItem(R.id.video_list_delete).setVisible(!AndroidUtil.isLolliPopOrLater() ||
                mediaWrapper.getLocation().startsWith("file://" + AndroidDevices.EXTERNAL_PUBLIC_DIRECTORY));
    }

    @Override
    public boolean onContextItemSelected(MenuItem menu) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menu.getMenuInfo();
        if (info != null && handleContextItemSelected(menu, info.position))
            return true;
        return super.onContextItemSelected(menu);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onContextPopupMenu(View anchor, final int position) {
        if (!AndroidUtil.isHoneycombOrLater()) {
            // Call the "classic" context menu
            anchor.performLongClick();
            return;
        }

        PopupMenu popupMenu = new PopupMenu(getActivity(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.video_list, popupMenu.getMenu());
        MediaWrapper media = mVideoAdapter.getItem(position);
        if (media == null)
            return;
        setContextMenuItems(popupMenu.getMenu(), media);
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return handleContextItemSelected(item, position);
            }
        });
        popupMenu.show();
    }

    /**
     * Handle changes on the list
     */
    private Handler mHandler = new VideoListHandler(this);

    public void updateItem() {
        mVideoAdapter.update(mItemToUpdate);
        try {
            mBarrier.await();
        } catch (InterruptedException e) {
        } catch (BrokenBarrierException e) {
        }
    }
    private void focusHelper(boolean idIsEmpty) {
        View parent = getView();
        if (getActivity() == null || !(getActivity() instanceof MyFragment))
            return;
        MyFragment activity = (MyFragment)getActivity();
        activity.setMenuFocusDown(idIsEmpty, android.R.id.list);
        activity.setSearchAsFocusDown(idIsEmpty, parent,
                android.R.id.list);
        }

    /**
     * liukedong 20151026 xiugai隐藏视频
     */
    public void updateList() {
    	
        if (!mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(true);
        final List<MediaWrapper> itemList = mMediaLibrary.getVideoItems();
//      Log.i("dove", itemList.toString());
//      将mVideoAdapter里面的视频列表中   已经设置为隐藏的视频移除
//      for(int position=0; position<mVideoAdapter.getCount(); position++){
//      	MediaWrapper media = mVideoAdapter.getItem(position);
//      	String path = media.getUri().getPath();
//      	// 如果path在隐藏列表中，则移除
//      	Set<String> hiddenVideos = PreferenceManager.getDefaultSharedPreferences(getActivity()).getStringSet(shar_hidden_video_set, null);
//      	if(hiddenVideos != null && hiddenVideos.size() > 0){
//      		for(String videoPath : hiddenVideos){
//      			Log.e("liukedong", videoPath);
//          		if(path.equals(videoPath)){
//          			mVideoAdapter.remove(media);
//          		}
//          	}
//      	}else{
//      		Log.e("liukedong", "mei you yao yincang de video");
//      	}
//      }
        
       boolean enableHiddenVideo = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(Config.SHARED_PREF_ENABLE_HIDDEN, false);
        
       if(enableHiddenVideo){
    	   for(int position=0; position<itemList.size(); position++){
    		   MediaWrapper media = itemList.get(position);
    		   String path = media.getUri().getPath();
    		   Log.e("liukedong", "是否需要隐藏： " + path);
    		   // 如果path在隐藏列表中，则移除
    		   Set<String> hiddenVideos = PreferenceManager.getDefaultSharedPreferences(getActivity()).getStringSet(shar_hidden_video_set, null);
    		   if(hiddenVideos != null && hiddenVideos.size() > 0){
    			   for(String videoPath : hiddenVideos){
    				   Log.e("liukedong", "添加隐藏的视屏文件名： " + videoPath);
    				   if(path.equals(videoPath)){
    					   itemList.remove(media);
    				   }
    			   }
    		   }else{
    			   Log.e("liukedong", "mei you yao yincang de video");
    		   }
    	   }
       }

        if (mThumbnailer != null)
            mThumbnailer.clearJobs();
        else
            Log.w(TAG, "Can't generate thumbnails, the thumbnailer is missing");

        mVideoAdapter.setNotifyOnChange(true);
        mVideoAdapter.clear();

        if (itemList.size() > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mGroup != null || itemList.size() <= 10) {
                        for (MediaWrapper item : itemList) {
                            if (mGroup == null || item.getTitle().startsWith(mGroup)) {
                                mVideoAdapter.setNotifyOnChange(false);
                                mVideoAdapter.add(item);
                                if (mThumbnailer != null)
                                    mThumbnailer.addJob(item);
                            }
                        }
                    }
                    else {
                        List<MediaGroup> groups = MediaGroup.group(itemList);
                        for (MediaGroup item : groups) {
                            mVideoAdapter.setNotifyOnChange(false);
                            mVideoAdapter.add(item.getMedia());
                            if (mThumbnailer != null)
                                mThumbnailer.addJob(item);
                        }
                    }
                    if (mReadyToDisplay)
                        display();
                }
            }).start();
        } else
            focusHelper(true);
        stopRefresh();
    }

    @Override
    public void showProgressBar() {
        if (mMainActivity != null)
            mMainActivity.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        if (mMainActivity != null)
            mMainActivity.hideProgressBar();
    }

    @Override
    public void clearTextInfo() {
        if (mMainActivity != null)
            mMainActivity.clearTextInfo();
    }

    @Override
    public void sendTextInfo(String info, int progress, int max) {
        if (mMainActivity != null)
            mMainActivity.sendTextInfo(info, progress, max);
    }

    @Override
    public void sortBy(int sortby) {
        mVideoAdapter.sortBy(sortby);
    }

    @Override
    public int sortDirection(int sortby) {
        return mVideoAdapter.sortDirection(sortby);
    }

    public void setItemToUpdate(MediaWrapper item) {
        mItemToUpdate = item;
        mHandler.sendEmptyMessage(VideoListHandler.UPDATE_ITEM);
    }

    public void setGroup(String prefix) {
        mGroup = prefix;
    }

    public void await() throws InterruptedException, BrokenBarrierException {
        mBarrier.await();
    }

    public void resetBarrier() {
        mBarrier.reset();
    }

    private final BroadcastReceiver messageReceiverVideoListFragment = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equalsIgnoreCase(Util.ACTION_SCAN_START)) {
                mLayoutFlipperLoading.setVisibility(View.VISIBLE);
                mTextViewNomedia.setVisibility(View.INVISIBLE);
            } else if (action.equalsIgnoreCase(Util.ACTION_SCAN_STOP)) {
                mLayoutFlipperLoading.setVisibility(View.INVISIBLE);
                mTextViewNomedia.setVisibility(View.VISIBLE);
            }
        }
    };

    public void stopRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (getActivity()!=null && !MediaLibrary.getInstance().isWorking()){
        	mVideoAdapter.clear();
            MediaLibrary.getInstance().scanMediaItems(true);
        }
    }

    @Override
    public void display() {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mViewNomedia.setVisibility(mVideoAdapter.getCount() > 0 ? View.GONE : View.VISIBLE);
                    mReadyToDisplay = true;
                    mVideoAdapter.setNotifyOnChange(true);
                    mVideoAdapter.sort();
                    mGVFirstVisiblePos = mGridView.getFirstVisiblePosition();
                    mGridView.setSelection(mGVFirstVisiblePos);
                    mGridView.requestFocus();
                    focusHelper(false);
                }
            });
    }

    public void clear(){
        mVideoAdapter.clear();
    }

    /**
     * 删除视频
     * @param position
     */
    public void deleteMedia(int position){
        final MediaWrapper media = mVideoAdapter.getItem(position);
        final String path = media.getUri().getPath();
        new Thread(new Runnable() {
            public void run() {
                Util.recursiveDelete(VLCApplication.getAppContext(), new File(path));
            }
        }).start();
        mMediaLibrary.getMediaItems().remove(media);
        mVideoAdapter.remove(media);
        if (mService != null) {
            final List<String> list = mService.getMediaLocations();
            if (list != null && list.contains(media.getLocation())) {
                mService.removeLocation(media.getLocation());
            }
        }
    }
    
    /**
     * 隐藏视频
     * @param position
     */
    public void hiddenMedia(int position){
        final MediaWrapper media = mVideoAdapter.getItem(position);
        final String path = media.getUri().getPath();
        
//        隐藏存储中的视频文件
        SharedPreferences sharedPred = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> hiddenVideos = sharedPred.getStringSet(shar_hidden_video_set, null);
        if(hiddenVideos == null){
        	hiddenVideos = new HashSet<String>();
        }
        Log.i("liukedong", "zhui bei yin cang : " + path);
        hiddenVideos.add(path);
        sharedPred.edit().putStringSet(shar_hidden_video_set, hiddenVideos).commit();
        
//        移除mMediaLibrary对象中的媒体对象
        mMediaLibrary.getMediaItems().remove(media);
//        移除mVideoAdapter对象中的媒体对象
        mVideoAdapter.remove(media);
        
        if (mService != null) {
            final List<String> list = mService.getMediaLocations();
            if (list != null && list.contains(media.getLocation())) {
                mService.removeLocation(media.getLocation());
            }
        }
    }


    View.OnClickListener mCancelDeleteMediaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDeleteHandler.removeMessages(DELETE_MEDIA);
        }
    };

    Handler mDeleteHandler = new VideoDeleteHandler(this);

    private static class VideoDeleteHandler extends WeakHandler<VideoGridFragment>{

        public VideoDeleteHandler(VideoGridFragment owner) {
            super(owner);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DELETE_MEDIA:
                    getOwner().deleteMedia(msg.arg1);
            }
        }
    }
//    隐藏视频的handler
    
    View.OnClickListener mCancelHiddenMediaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        	mHiddenHandler.removeMessages(HIDDEN_MEDIA);
        }
    };
    
    Handler mHiddenHandler = new VideoHiddenHandler(this);
    
    private static class VideoHiddenHandler extends WeakHandler<VideoGridFragment>{
    	
    	public VideoHiddenHandler(VideoGridFragment owner) {
    		super(owner);
    	}
    	@Override
    	public void handleMessage(Message msg) {
    		super.handleMessage(msg);
    		switch (msg.what){
    		case HIDDEN_MEDIA:
    			getOwner().hiddenMedia(msg.arg1);
    		}
    	}
    }

    
    //添加编辑布局组件的get,set
	public View getLyInclude() {
		return lyInclude;
	}

	public void setLyInclude(View lyInclude) {
		this.lyInclude = lyInclude;
	}

	public VideoListAdapter getmVideoAdapter() {
		return mVideoAdapter;
	}

	public void setmVideoAdapter(VideoListAdapter mVideoAdapter) {
		this.mVideoAdapter = mVideoAdapter;
	}
    
}
