/*****************************************************************************
 * MainActivity.java
 *****************************************************************************
 * Copyright © 2011-2014 VLC authors and VideoLAN
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

package com.shipin.player.gui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Matrix;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.videolan.libvlc.util.AndroidUtil;

import com.shipin.player.Config;
import com.shipin.player.MediaDatabase;
import com.shipin.player.MediaLibrary;
import com.shipin.player.PlaybackService;
import com.videoplayer.bd.MRL_Activity;
import com.videoplayer.bd.R;
import com.shipin.player.VLCApplication;
import com.shipin.player.gui.SidebarAdapter.SidebarEntry;
import com.shipin.player.gui.audio.AudioBrowserFragment;
import com.shipin.player.gui.browser.BaseBrowserFragment;
import com.shipin.player.gui.browser.FileBrowserFragment;
import com.shipin.player.gui.browser.FilePickerFragment;
import com.shipin.player.gui.browser.MediaBrowserFragment;
import com.shipin.player.gui.browser.NetworkBrowserFragment;
import com.shipin.player.gui.browser.StorageBrowserFragment;
import com.shipin.player.gui.network.MRLPanelFragment;
import com.shipin.player.gui.video.VideoGridFragment;
import com.shipin.player.gui.video.VideoListAdapter;
import com.shipin.player.gui.video.VideoPlayerActivity;
import com.shipin.player.interfaces.IRefreshable;
import com.shipin.player.interfaces.ISortable;
import com.shipin.player.util.SharePreUtil;
import com.shipin.player.util.Util;
import com.shipin.player.util.VLCInstance;
import com.shipin.player.util.WeakHandler;
import com.shipin.player.widget.HackyDrawerLayout;
import com.umeng.analytics.MobclickAgent;


public class MyFragment extends AudioPlayerContainerActivity implements OnItemClickListener, SearchSuggestionsAdapter.SuggestionDisplay, FilterQueryProvider {//,OnButtonClickListener
	public final static String TAG = "video/MainActivity";
	private static final String PREF_FIRST_RUN = "first_run";
	private static final int ACTIVITY_RESULT_PREFERENCES = 1;
	private static final int ACTIVITY_SHOW_INFOLAYOUT = 2;
	private static final int ACTIVITY_SHOW_PROGRESSBAR = 3;
	private static final int ACTIVITY_HIDE_PROGRESSBAR = 4;
	private static final int ACTIVITY_SHOW_TEXTINFO = 5;
	
	MediaLibrary mMediaLibrary;

	private SidebarAdapter mSidebarAdapter;
	private HackyDrawerLayout mDrawerLayout;
	//    private ListView mListView;
	private ActionBarDrawerToggle mDrawerToggle;

	private View mInfoLayout;
	private ProgressBar mInfoProgress;
	private TextView mInfoText;
	private String mCurrentFragment;

	private Button btn;
	private int mVersionNumber = -1;
	private boolean mFirstRun = false;
	private boolean mScanNeeded = false;

	private Handler mHandler = new MainActivityHandler(this);
	private int mFocusedPrior = 0;
	private int mActionBarIconId = -1;
	Menu mMenu;
	private SearchView mSearchView;

	private Context mContext=MyFragment.this;
	private ViewPager pager;
	private static final int PAGER_NUM = 2;//2个页面  
	private int mCurrentViewID = 0;         //当前页面  
	private int mMyDuration = 100; 
	private List<String>titleList;
	//private PagerTabStrip tab;
	private List<Fragment>fragList;
	Fragment fragment1;
	Fragment fragment2;
	protected static final String ACTION_SHOW_PROGRESSBAR = "org.videolan.vlc.gui.ShowProgressBar";
	protected static final String ACTION_HIDE_PROGRESSBAR = "org.videolan.vlc.gui.HideProgressBar";
	protected static final String ACTION_SHOW_TEXTINFO = "org.videolan.vlc.gui.ShowTextInfo";
	public static final String ACTION_SHOW_PLAYER = "org.videolan.vlc.gui.ShowPlayer";
	private ImageView cursor;
	int offset,one,two;
	private TextView libray,file1;
	private ImageView image1;
	private LinearLayout linearlay;
	HorizontalListView hListView;
	
	//xj  frgment对象全局定义  1104
	FragmentManager fm = this.getSupportFragmentManager();
	FragmentTransaction trans = fm.beginTransaction();
	//jj 编辑功能shared保存
	SharedPreferences editFunction;
	//显示隐藏item调用判断
	private boolean edit;
	//fragment适配器
	private MyFragmentPagerAdapter adapter2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		editFunction = getSharedPreferences("jmydata", MODE_WORLD_WRITEABLE);
		editFunction.edit().putBoolean("fileState", false).commit();
		editFunction.edit().putString("show", "0").commit();
		editFunction.edit().putString("video", "0").commit();
		
		if (!VLCInstance.testCompatibleCPU(this)) {
			Log.i("debug", "1111111111111");
			finish();
			return;
		}
		/* Enable the indeterminate progress feature */
		supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		/* Get the current version from package */
		mVersionNumber = Config.VERSION_CODE;

		/* Check if it's the first run */
		mFirstRun = mSettings.getInt(PREF_FIRST_RUN, -1) != mVersionNumber;
		if (mFirstRun) {
			Editor editor = mSettings.edit();
			editor.putInt(PREF_FIRST_RUN, mVersionNumber);
			Util.commitPreferences(editor);
		}

		mMediaLibrary = MediaLibrary.getInstance();
		if (mMediaLibrary.getMediaItems().isEmpty()) {
			if (mSettings.getBoolean(PreferencesActivity.AUTO_RESCAN, true))
				mMediaLibrary.scanMediaItems();
			else
				mMediaLibrary.loadMedaItems();
		}

		/*** Start initializing the UI ***/

		setContentView(R.layout.my_fragment);
		//		image1=(ImageView) findViewById(R.id.image);
		//		AlphaAnimation animation = new AlphaAnimation(1.0,0.);   
		//		animation.setDuration(5000); //设置持续时间5秒  
		//		image1.startAnimation(animation);

		MobclickAgent.updateOnlineConfig(this);

		mDrawerLayout = (HackyDrawerLayout) findViewById(R.id.root_container);
		//        mListView = (ListView)findViewById(R.id.sidelist);
		//        mListView.setFooterDividersEnabled(true);
		//        mSidebarAdapter = new SidebarAdapter(this);
		//        mListView.setAdapter(mSidebarAdapter);

		initAudioPlayerContainerActivity();
		//mSlidingPane.setActivated(false);


		if (savedInstanceState != null){
			mCurrentFragment = savedInstanceState.getString("current");
			//	if (mCurrentFragment != null)
			//mSidebarAdapter.setCurrentFragment(mCurrentFragment);
		}

		/* Initialize UI variables */
		mInfoLayout = findViewById(R.id.info_layout);
		mInfoProgress = (ProgressBar) findViewById(R.id.info_progress);
		mInfoText = (TextView) findViewById(R.id.info_text);
		hListView = (HorizontalListView) findViewById(R.id.horizon_listview);
		/* Set up the action bar */
		prepareActionBar();

		/* Set up the sidebar click listener
		 * no need to invalidate menu for now */
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close){
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder) instanceof MediaBrowserFragment)
					((MediaBrowserFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder)).setReadyToDisplay(true);
			}
		};

		// Set the drawer toggle as the DrawerListener
		//	mDrawerLayout.setDrawerListener(mDrawerToggle);
		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		/* Reload the latest preferences */
		reloadPreferences();


		/*--------------------------------------------- */
		titleList=new ArrayList<String>();
		titleList.add("Libray");
		titleList.add("File");

		initIndView();
		//进行整改 文件进行批处理  xj 1102
//		fragment1=new VideoGridFragment();
		//fragment1=new MyFragment_test();
		//fragment1=new Filefragment();
		//fragment1=new StorageBrowserFragment();
		//fragment1=new MyPageFragment1();
		fragment1=new MyFragment_frg1();
		//进行整改 文件进行批处理  xj 1102
		//		Fragment fragment2 = getSupportFragmentManager()
		//                .findFragmentById(R.id.fragment_placeholder);

			//	fragment2=new FileBrowserFragment();
		fragment2=new MyPageFragment1();
		//btn=(Button) findViewById(R.id.back_btn);
		//linearlay=(LinearLayout) findViewById(R.id.linear_back);
		pager=(ViewPager) findViewById(R.id.pager);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				Matrix matrix = new Matrix();
				switch (arg0) {
				case 0:
					matrix.postTranslate(offset + (one - offset) * arg1, 0);
					break;
				case 1:
					matrix.postTranslate(one + (two - one) * arg1, 0);
					break;
				default:
					break;
				}
				cursor.setImageMatrix(matrix);
			}
			@Override
			public void onPageSelected(int currentID) {
				if(currentID == 0){
					libray.setTextColor(getApplication().getResources().getColor(R.color.txt_green));
					file1.setTextColor(Color.BLACK);
				}else{
					libray.setTextColor(Color.BLACK);
					file1.setTextColor(getApplication().getResources().getColor(R.color.txt_green));
				}
				mCurrentViewID = currentID;  
			}
		});
		fragList = new ArrayList<Fragment>();

		fragList.add(fragment1);
		fragList.add(fragment2);
		//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		////	ft.replace(R.id.fragment_placeholder, new FileBrowserFragment(), mCurrentFragment);
		//		ft.replace(R.id.fragment_placeholder, new FileBrowserFragment());
		//		ft.commit();
		libray=(TextView) findViewById(R.id.libray);
		file1=(TextView) findViewById(R.id.file1);
		file1.setOnClickListener(listener);
		libray.setOnClickListener(listener);
		
		adapter2 = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragList, titleList);

		pager.setAdapter(adapter2);
		/*--------------------------------------------- */

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {

			case R.id.libray:
				if(mCurrentViewID != 0){  
					mCurrentViewID--;  
					pager.setCurrentItem(mCurrentViewID, true);  
				}  
				break;
			case R.id.file1:
				if(mCurrentViewID != PAGER_NUM-1){  
					mCurrentViewID++;  
					pager.setCurrentItem(mCurrentViewID, true);
				}  

				break;
			}
		}
	};


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void prepareActionBar() {
		//        mActionBar.setDisplayHomeAsUpEnabled(true);
		//        mActionBar.setHomeButtonEnabled(true);
		// 给左上角图标的左边加上一个返回的图标
		mActionBar.setDisplayHomeAsUpEnabled(false);
		// 左上角的图标是否可以点击
		mActionBar.setHomeButtonEnabled(false);
		// 使左上角图标是否显示
		mActionBar.setDisplayShowHomeEnabled(false); 


	}

	@Override
	protected void onResume() {
		MobclickAgent.onResume(this);
		super.onResume();


		//			/* Load media items from database and storage */
		if (mScanNeeded)
			mMediaLibrary.scanMediaItems();
		//			if (mSlidingPane.getState() == mSlidingPane.STATE_CLOSED)
		//				mActionBar.hide();
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		//btn.setVisibility(View.INVISIBLE);

		// Figure out if currently-loaded fragment is a top-level fragment.
		//        Fragment current = getSupportFragmentManager()
		//                .findFragmentById(R.id.fragment_placeholder);
		//        boolean found = (current == null) || SidebarAdapter.sidebarFragments.contains(current.getTag());

		/**
		 * Restore the last view.
		 *
		 * Replace:
		 * - null fragments (freshly opened Activity)
		 * - Wrong fragment open AND currently displayed fragment is a top-level fragment
		 *
		 * Do not replace:
		 * - Non-sidebar fragments.
		 * It will try to remove() the currently displayed fragment
		 * (i.e. tracks) and replace it with a blank screen. (stuck menu bug)
		 */
	}
	

	/**
	 * Stop audio player and save opened tab
	 */
	@Override
	protected void onPause() {
		MobclickAgent.onPause(this);
		super.onPause();
		/* Check for an ongoing scan that needs to be resumed during onResume */
		mScanNeeded = mMediaLibrary.isWorking();
		/* Stop scanning for files */
		mMediaLibrary.stop();
		/* Save the tab status in pref */
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putString("fragment", mCurrentFragment);
		Util.commitPreferences(editor);

		mFocusedPrior = 0;
		//暂停退出编辑模式
//		Filefragment_test f1 = (Filefragment_test) fm.findFragmentByTag("file");
//		LinearLayout ly = f1.getLy();
//		ly.setVisibility(View.GONE);
//		editFunction.edit().putString("show", "0").commit();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("current", mCurrentFragment);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		/* Reload the latest preferences */
		reloadPreferences();
	}

	@Override
	public void onBackPressed() {
		/* Close the menu first */
		//        if(mDrawerLayout.isDrawerOpen(mListView)) {
		//            if (mFocusedPrior != 0)
		//                requestFocusOnSearch();
		//            mDrawerLayout.closeDrawer(mListView);
		//            return;
		//        }

		// Slide down the audio player if it is shown entirely.
		if (slideDownAudioPlayer())
			return;

		if (mCurrentFragment!= null) {
			// If it's the directory view, a "backpressed" action shows a parent.
			if (mCurrentFragment.equals(SidebarEntry.ID_NETWORK) || mCurrentFragment.equals(SidebarEntry.ID_DIRECTORIES)){
				BaseBrowserFragment browserFragment = (BaseBrowserFragment) getSupportFragmentManager()
						.findFragmentById(R.id.fragment_placeholder);
				if (browserFragment != null) {
					browserFragment.goBack();
					return;
				}
			}
		}
		Log.i("debug", "2222222222222");
		finish();
	}

	private Fragment getFragment(String id)
	{
		Fragment frag = getSupportFragmentManager().findFragmentByTag(id);
		if (frag != null)
			return frag;
		return mSidebarAdapter.fetchFragment(id);
	}

	private static void ShowFragment(FragmentActivity activity, String tag, Fragment fragment, String previous) {
		if (fragment == null) {
			Log.e(TAG, "Cannot show a null fragment, ShowFragment("+tag+") aborted.");
			return;
		}

		FragmentManager fm = activity.getSupportFragmentManager();

		//abort if fragment is already the current one
		Fragment current = fm.findFragmentById(R.id.fragment_placeholder);
		if(current != null && current.getTag().equals(tag))
			return;

		//try to pop back if the fragment is already on the backstack
		if (fm.popBackStackImmediate(tag, 0))
			return;

		//fragment is not there yet, spawn a new one
		FragmentTransaction ft = fm.beginTransaction();
		ft.setCustomAnimations(R.anim.anim_enter_right, R.anim.anim_leave_left, R.anim.anim_enter_left, R.anim.anim_leave_right);
		ft.replace(R.id.fragment_placeholder, fragment, tag);
		ft.addToBackStack(previous);
		ft.commit();
	}

	/**
	 * Show a secondary fragment.
	 */
	public void showSecondaryFragment(String fragmentTag) {
		showSecondaryFragment(fragmentTag, null);
	}

	public void showSecondaryFragment(String fragmentTag, String param) {
		Intent i = new Intent(this, SecondaryActivity.class);
		i.putExtra("fragment", fragmentTag);
		if (param != null)
			i.putExtra("param", param);
		startActivity(i);
		// Slide down the audio player if needed.
		slideDownAudioPlayer();
	}


	/** Create menu from XML
	 */
	@TargetApi(Build.VERSION_CODES.FROYO)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		mMenu = menu;
		/* Note: on Android 3.0+ with an action bar this method
		 * is called while the view is created. This can happen
		 * any time after onCreate.
		 */
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.media_library_2, menu);

		if (AndroidUtil.isFroyoOrLater()) {
			SearchManager searchManager =
					(SearchManager) VLCApplication.getAppContext().getSystemService(Context.SEARCH_SERVICE);
			mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.ml_menu_search));
			mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			mSearchView.setQueryHint(getString(R.string.search_hint));
			SearchSuggestionsAdapter searchSuggestionsAdapter = new SearchSuggestionsAdapter(this, null);
			searchSuggestionsAdapter.setFilterQueryProvider(this);
			//  mSearchView.setIconifiedByDefault(false);//(设置搜索图标在编辑框外，ture时在框内)。
			//mSearchView.setSearchViewBackground(mSearchView);
			//setSearchViewBackground(mSearchView);
			mSearchView.setSuggestionsAdapter(searchSuggestionsAdapter);
		} else
			menu.findItem(R.id.ml_menu_search).setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (menu == null)
			return false;
		Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder);
		MenuItem item;
		// Disable the sort option if we can't use it on the current fragment.
		if (current == null || !(current instanceof ISortable)) {
			item = menu.findItem(R.id.ml_menu_sortby);
			if (item == null)
				return false;
			item.setEnabled(false);
			item.setVisible(false);
		} else {
			ISortable sortable = (ISortable) current;
			item = menu.findItem(R.id.ml_menu_sortby);
			if (item == null)
				return false;
			item.setEnabled(true);
			item.setVisible(true);
			item = menu.findItem(R.id.ml_menu_sortby_name);
			if (sortable.sortDirection(VideoListAdapter.SORT_BY_TITLE) == 1)
				item.setTitle(R.string.sortby_name_desc);
			//item.setTitle(R.string.sortby_name_desc);
			else
				item.setTitle(R.string.sortby_name);
			item = menu.findItem(R.id.ml_menu_sortby_length);
			if (sortable.sortDirection(VideoListAdapter.SORT_BY_LENGTH) == 1)
				item.setTitle(R.string.sortby_length_desc);
			else
				item.setTitle(R.string.sortby_length);
			item = menu.findItem(R.id.ml_menu_sortby_date);
			if (sortable.sortDirection(VideoListAdapter.SORT_BY_DATE) == 1)
				item.setTitle(R.string.sortby_date_desc);
			else
				item.setTitle(R.string.sortby_date);
		}

		boolean networkSave = current instanceof NetworkBrowserFragment && !((NetworkBrowserFragment)current).isRootDirectory();
		if (networkSave) {
			item = menu.findItem(R.id.ml_menu_save);
			item.setVisible(true);
			String mrl = ((BaseBrowserFragment)current).mMrl;
			item.setIcon(MediaDatabase.getInstance().networkFavExists(Uri.parse(mrl)) ?
					R.drawable.ic_menu_bookmark_w :
						R.drawable.ic_menu_bookmark_outline_w);
		} else
			menu.findItem(R.id.ml_menu_save).setVisible(false);
		if (current instanceof MRLPanelFragment)
			menu.findItem(R.id.ml_menu_clean).setVisible(!((MRLPanelFragment) current).isEmpty());
		boolean showLast = current instanceof AudioBrowserFragment || (current instanceof VideoGridFragment && mSettings.getString(PreferencesActivity.VIDEO_LAST, null) != null);
		menu.findItem(R.id.ml_menu_last_playlist).setVisible(showLast);
		return true;
	}

	/**
	 * Handle onClick form menu buttons
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//xj  frgment对象全局定义  1104
		
		
		trans = getSupportFragmentManager().beginTransaction();

		// Current fragment loaded
		Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder);

		// Handle item selection
		switch (item.getItemId()) {
		case R.id.ml_menu_sortby_name:
		case R.id.ml_menu_sortby_length:
		case R.id.ml_menu_sortby_date:
			if (current == null)
				break;
			if (current instanceof ISortable) {
				int sortBy = VideoListAdapter.SORT_BY_TITLE;
				if (item.getItemId() == R.id.ml_menu_sortby_length)
					sortBy = VideoListAdapter.SORT_BY_LENGTH;
				else if(item.getItemId() == R.id.ml_menu_sortby_date)
					sortBy = VideoListAdapter.SORT_BY_DATE;
				((ISortable) current).sortBy(sortBy);
				supportInvalidateOptionsMenu();
			}
			break;
		case R.id.ml_menu_equalizer://均衡器
			showSecondaryFragment(SecondaryActivity.EQUALIZER);
			break;
		case R.id.ml_menu_preferences://设置
			//            	showSecondaryFragment(PreferencesActivity.EQUALIZER);
			startActivity(new Intent(this, PreferencesActivity.class));

			break;
			// Refresh
			//xj 1104 添加网络流
		case R.id.ml_menu_flow:
//			int num = MyFragment.this.getSupportFragmentManager().getBackStackEntryCount();
//			for (int i = 0; i < num; i++) {
//			    BackStackEntry backstatck = MyFragment.this.getSupportFragmentManager().getBackStackEntryAt(i);
//			    //TALogger.d("Fragment", backstatck.getName());
//			   // MyFragment.this.getSupportFragmentManager().popBackStackImmediate(backstatck.class.getName(), 0);
//			}
			
//			int num=MyFragment.this.getSupportFragmentManager().getBackStackEntryCount();
//			if(num>1){
//				BackStackEntry backstatck;
//				backstatck = MyFragment.this.getSupportFragmentManager().getBackStackEntryAt(num-1);
//				MyFragment.this.getSupportFragmentManager().popBackStack(backstatck.getId(), 0);
//			}
			
//			int num=MyFragment.this.getSupportFragmentManager().getBackStackEntryCount();
//			BackStackEntry backstatck;
//			backstatck = MyFragment.this.getSupportFragmentManager().getBackStackEntryAt(0);
//			if(backstatck.getClass().toString()=="Filefragment_test"){
//				MyFragment.this.getSupportFragmentManager().popBackStack(backstatck.getId(), 0);
//			}
		//	trans.hide(fragment1);
//			trans.hide(new Filefragment_test());
			trans.replace(R.id.fragment_video, new MRLPanelFragment(),"network");
			trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			trans.addToBackStack(null);
			trans.commit();
//			Intent intent=new Intent(this,MRL_Activity.class);
//			startActivity(intent);
			
			break;
			//xj 1104 添加网络流
			
		//文件夹视图切换
		case R.id.ml_menu_folder:
			break;
		case R.id.ml_menu_sortby_file:
			if(mCurrentViewID==0){
				boolean b = editFunction.getBoolean("fileState", false);
				if(!b){
					editFunction.edit().putBoolean("fileState", true).commit();
//					trans.add(R.id.fragment_video, new VideoGridFragment());
					//trans.add(R.id.fragment_video, new Filefragment_test(),"file");
					trans.replace(R.id.fragment_video,new Filefragment_test(),"file");
					trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					trans.addToBackStack(null);
					trans.commit();
				}
			}
			break;
		case R.id.ml_menu_sortby_video:
			if(mCurrentViewID==0){
				if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
					//btn=(Button) findViewById(R.id.back_btn);
					//btn.setVisibility(View.VISIBLE);
					//linearlay.setVisibility(View.VISIBLE);
					editFunction.edit().putBoolean("fileState", false).commit();
					super.onBackPressed();
					return false;
				}
				//pager.setAdapter(adapter2);
			}
			break;
			//文件夹视图切换结束
			
		// 编辑功能jin
		case R.id.ml_menu_edit:
			//VideoPlayerActivity.start(this, Uri.parse("https://fpdl.vimeocdn.com/vimeo-prod-skyfire-std-us/01/4148/5/145743834/440564733.mp4?token=564ee47d_0x775fffe1ef042de0b9c61d4e3a914eaa51ca5c5c"));
			if(mCurrentViewID==0){
				boolean b = editFunction.getBoolean("fileState", false);
				if(b){
					Filefragment_test f1 =  (Filefragment_test) fm.findFragmentByTag("file");
					if(f1!=null){
						String bs = editFunction.getString("show", "3");
						LinearLayout ly = f1.getLy();
						ListView lv = f1.getLv();
						ArrayList<FileItem> al = f1.getFileItems();
						Frgadpter fadapter = new Frgadpter(mContext, al);
						if(bs.equals("0")){
							ly.setVisibility(View.VISIBLE);
							edit = true;
							SharePreUtil.setedit(mContext, edit);
							lv.setAdapter(fadapter);
							editFunction.edit().putString("show", "1").commit();
							editFunction.edit().putString("video", "1").commit();
						}else if(bs.equals("1")){
							ly.setVisibility(View.GONE);
							edit = false;
							SharePreUtil.setedit(mContext, edit);
							lv.setAdapter(fadapter);
							editFunction.edit().putString("show", "0").commit();
							editFunction.edit().putString("video", "0").commit();
						}
					}
				}else{
					String videoShow = editFunction.getString("video", "0");
					VideoGridFragment vgf = (VideoGridFragment) fm.findFragmentByTag("video");
					if(videoShow.equals("0")){
						editFunction.edit().putString("video", "1").commit();
						editFunction.edit().putString("show", "1").commit();
			        }else{
			        	editFunction.edit().putString("video", "0").commit();
			        	editFunction.edit().putString("show", "0").commit();
			        }
					vgf.getmVideoAdapter().clear();
					pager.setAdapter(adapter2);
//		        	adapter2.notifyDataSetChanged();
				}
			}
			break;
			//编辑功能结束jin
	
		case R.id.ml_menu_refresh:
			if (!mMediaLibrary.isWorking()) {
				if(current != null && current instanceof IRefreshable)
					((IRefreshable) current).refresh();
				else
					mMediaLibrary.scanMediaItems(true);
			}
			break;
			// Restore last playlist
		case R.id.ml_menu_last_playlist:
			if (current instanceof AudioBrowserFragment) {
				Intent i = new Intent(PlaybackService.ACTION_REMOTE_LAST_PLAYLIST);
				sendBroadcast(i);
			} else if (current instanceof VideoGridFragment) {
				final Uri uri = Uri.parse(mSettings.getString(PreferencesActivity.VIDEO_LAST, null));
				if (uri != null)
					VideoPlayerActivity.start(this, uri);
			}
			break;
		case android.R.id.home:
			// Slide down the audio player.
			if (slideDownAudioPlayer())
				break;
			//                /* Toggle the sidebar */
			//                if (mDrawerToggle.onOptionsItemSelected(item)) {
			//                    return true;
			//                }
			break;
		case R.id.ml_menu_clean:
			if (getFragment(mCurrentFragment) instanceof MRLPanelFragment)
				((MRLPanelFragment)getFragment(mCurrentFragment)).clearHistory();
			break;
		case R.id.ml_menu_save:
			if (current == null)
				break;
			((NetworkBrowserFragment)current).toggleFavorite();
			item.setIcon(R.drawable.ic_menu_bookmark_w);
			break;
		}
		//        mDrawerLayout.closeDrawer(mListView);
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ACTIVITY_RESULT_PREFERENCES) {
			if (resultCode == PreferencesActivity.RESULT_RESCAN)
				mMediaLibrary.scanMediaItems(true);
			else if (resultCode == PreferencesActivity.RESULT_RESTART) {
				Intent intent = getIntent();
				Log.i("debug", "333333333333333");
				finish();
				startActivity(intent);
			}
		}
	}

	public void setMenuFocusDown(boolean idIsEmpty, int id) {
		if (mMenu == null)
			return;
		//Save menu items ids for focus control
		final int[] menu_controls = new int[mMenu.size()+1];
		for (int i = 0 ; i < mMenu.size() ; i++){
			menu_controls[i] = mMenu.getItem(i).getItemId();
		}
		menu_controls[mMenu.size()] = mActionBarIconId;
		/*menu_controls = new int[]{R.id.ml_menu_search,
            R.id.ml_menu_open_mrl, R.id.ml_menu_sortby,
            R.id.ml_menu_last_playlist, R.id.ml_menu_refresh,
            mActionBarIconId};*/
		int pane = mSlidingPane.getState();
		for(int r : menu_controls) {
			View v = findViewById(r);
			if (v != null) {
				if (!idIsEmpty)
					v.setNextFocusDownId(id);
				else {
					if (pane ==  mSlidingPane.STATE_CLOSED) {
						v.setNextFocusDownId(R.id.play_pause);
					} else if (pane == mSlidingPane.STATE_OPENED) {
						v.setNextFocusDownId(R.id.header_play_pause);
					} else if (pane ==
							mSlidingPane.STATE_OPENED_ENTIRELY) {
						v.setNextFocusDownId(r);
					}
				}
			}
		}
	}

	public void setSearchAsFocusDown(boolean idIsEmpty, View parentView, int id) {
		View playPause = findViewById(R.id.header_play_pause);

		if (!idIsEmpty) {
			View list;
			int pane = mSlidingPane.getState();

			if (parentView == null)
				list = findViewById(id);
			else
				list = parentView.findViewById(id);

			if (list != null) {
				if (pane == mSlidingPane.STATE_OPENED_ENTIRELY) {
					list.setNextFocusDownId(id);
				} else if (pane == mSlidingPane.STATE_OPENED) {
					list.setNextFocusDownId(R.id.header_play_pause);
					playPause.setNextFocusUpId(id);
				}
			}
		} else {
			playPause.setNextFocusUpId(R.id.ml_menu_search);
		}
	}

	// Note. onKeyDown will not occur while moving within a list


	private boolean key_back() {
		// TODO Auto-generated method stub
		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//			linearlay= (LinearLayout) findViewById(R.id.linear_back);
//			linearlay.setVisibility(View.VISIBLE);
			hListView.setVisibility(View.VISIBLE);
			super.onBackPressed();
			//return false;
		} else {
			if(getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder) != null){
				//((FilePickerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder)).browseUp();
				//linearlay.setVisibility(View.GONE);
				hListView.setVisibility(View.GONE);
				Log.i("debug", "4444444444444444");
				finish();
				//return false;
			}
		}
		return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//Filter for LG devices, see https://code.google.com/p/android/issues/detail?id=78154
		//linearlay= (LinearLayout) findViewById(R.id.linear_back);
		
		//key_back();
		
		//编辑与文件模式的返回功能
//		String videoShow = editFunction.getString("video", "3");
//		if (keyCode == KeyEvent.KEYCODE_BACK && videoShow.equals("1")) {//编辑功能返回效果
//			VideoGridFragment vgf = (VideoGridFragment) fm.findFragmentByTag("video");
//			if(vgf!=null){
//				vgf.getLyInclude().setVisibility(View.GONE);
//				editFunction.edit().putString("video", "0").commit();
//				//super.onBackPressed();
//				trans.replace(R.id.fragment_video, new VideoGridFragment(),"video");
//				trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//				trans.addToBackStack(null);
//				trans.commit();
//				return true;
//			}
//		}
		if(mCurrentViewID==0){
			boolean b = editFunction.getBoolean("fileState", false);
			if(b){
				String bshow = editFunction.getString("show", "3");
				if (keyCode == KeyEvent.KEYCODE_BACK && bshow.equals("1")) {//编辑功能返回效果
					Filefragment_test f1 = (Filefragment_test) fm.findFragmentByTag("file");
					LinearLayout ly = f1.getLy();
					ListView lv = f1.getLv();
					Frgadpter fadapter = new Frgadpter(mContext, f1.getFileItems());
					ly.setVisibility(View.GONE);
					edit = false;
					SharePreUtil.setedit(mContext, edit);
					lv.setAdapter(fadapter);
					editFunction.edit().putString("show", "0").commit();
					return true;
				}else if(keyCode == KeyEvent.KEYCODE_BACK && bshow.equals("0")){//视频目录展示返回
					Filefragment_test f1 = (Filefragment_test) fm.findFragmentByTag("file");
					if(f1!=null){
						ArrayList<FileItem> list = f1.getSavefileItems();
						if(list!=null && list!=f1.getFileItems()){
							Frgadpter fadapter = new Frgadpter(mContext, list);
							f1.getLv().setAdapter(fadapter);
							f1.setSavefileItems(null);
							return true;
						}else{
							editFunction.edit().putBoolean("fileState", false).commit();
							super.onBackPressed();
							return false;
						}
					}
				}
			}
			String videoShow = editFunction.getString("video", "0");
			if(videoShow.equals("1")){
				Log.i("debug", "!!!!!!!!!!!!!!!!");
				editFunction.edit().putString("video", "0").commit();
				VideoGridFragment vgf = (VideoGridFragment) fm.findFragmentByTag("video");
				vgf.getmVideoAdapter().clear();
//				vgf.updateList();
				pager.setAdapter(adapter2);
			}else{
				if(getSupportFragmentManager().findFragmentByTag("network") == null && getSupportFragmentManager().findFragmentByTag("video") != null && keyCode == KeyEvent.KEYCODE_BACK){
					Log.i("debug", "5555555555555");
					finish();
					return false;
				}
			}
		}
		
		if (getSupportFragmentManager().getBackStackEntryCount() > 2) {
			//btn=(Button) findViewById(R.id.back_btn);
			//btn.setVisibility(View.VISIBLE);
			//linearlay.setVisibility(View.VISIBLE);
			super.onBackPressed();
			return false;
		} else {
			if(getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder) != null && keyCode == KeyEvent.KEYCODE_BACK){
				//((FilePickerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder)).browseUp();
				//btn.setVisibility(View.GONE);
				//linearlay.setVisibility(View.GONE);
				Log.i("debug", "66666666666666");
				finish();
				return true;
			}
		}
		
		if ((keyCode == KeyEvent.KEYCODE_MENU) &&
				(Build.VERSION.SDK_INT <= 16) &&
				(Build.MANUFACTURER.compareTo("LGE") == 0)) {
			return true;
		}
		if (mFocusedPrior == 0)
			setMenuFocusDown(true, 0);
		if (getCurrentFocus() != null)
			mFocusedPrior = getCurrentFocus().getId();
		return super.onKeyDown(keyCode, event);
	}

	// Note. onKeyDown will not occur while moving within a list
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//Filter for LG devices, see https://code.google.com/p/android/issues/detail?id=78154
		if ((keyCode == KeyEvent.KEYCODE_MENU) &&
				(Build.VERSION.SDK_INT <= 16) &&
				(Build.MANUFACTURER.compareTo("LGE") == 0)) {
			openOptionsMenu();
			return true;
		}
		View v = getCurrentFocus();
		if (v == null)
			return super.onKeyUp(keyCode, event);
		if ((mActionBarIconId == -1) &&
				(v.getId() == -1)  &&
				(v.getNextFocusDownId() == -1) &&
				(v.getNextFocusUpId() == -1) &&
				(v.getNextFocusLeftId() == -1) &&
				(v.getNextFocusRightId() == -1)) {
			mActionBarIconId = Util.generateViewId();
			v.setId(mActionBarIconId);
			v.setNextFocusUpId(mActionBarIconId);
			v.setNextFocusDownId(mActionBarIconId);
			v.setNextFocusLeftId(mActionBarIconId);
			v.setNextFocusRightId(R.id.ml_menu_search);
			if (AndroidUtil.isHoneycombOrLater())
				v.setNextFocusForwardId(mActionBarIconId);
			if (findViewById(R.id.ml_menu_search) != null)
				findViewById(R.id.ml_menu_search).setNextFocusLeftId(mActionBarIconId);
		}
		return super.onKeyUp(keyCode, event);
	}

	private void reloadPreferences() {
		mCurrentFragment = mSettings.getString("fragment", "video");
	}

	@Override
	public Cursor runQuery(CharSequence constraint) {
		return MediaDatabase.getInstance().queryMedia(constraint.toString());
	}

	private static class MainActivityHandler extends WeakHandler<MyFragment> {
		public MainActivityHandler(MyFragment owner) {
			super(owner);
		}

		@Override
		public void handleMessage(Message msg) {
			MyFragment ma = getOwner();
			if(ma == null) return;

			switch (msg.what) {
			case ACTIVITY_SHOW_INFOLAYOUT:
				ma.mInfoLayout.setVisibility(View.VISIBLE);
				break;
			case ACTIVITY_SHOW_PROGRESSBAR:
				ma.setSupportProgressBarIndeterminateVisibility(true);
				ma.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				break;
			case ACTIVITY_HIDE_PROGRESSBAR:
				ma.setSupportProgressBarIndeterminateVisibility(false);
				ma.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				break;
			case ACTIVITY_SHOW_TEXTINFO:
				String info = (String) msg.obj;
				int max = msg.arg1;
				int progress = msg.arg2;
				ma.mInfoText.setText(info);
				ma.mInfoProgress.setMax(max);
				ma.mInfoProgress.setProgress(progress);

				if (info == null) {
					/* Cancel any upcoming visibility change */
					removeMessages(ACTIVITY_SHOW_INFOLAYOUT);
					ma.mInfoLayout.setVisibility(View.GONE);
				}
				else {
					/* Slightly delay the appearance of the progress bar to avoid unnecessary flickering */
					if (!hasMessages(ACTIVITY_SHOW_INFOLAYOUT)) {
						Message m = new Message();
						m.what = ACTIVITY_SHOW_INFOLAYOUT;
						sendMessageDelayed(m, 300);
					}
				}
				break;
			}
		}
	}

	public void hideKeyboard(){
		((InputMethodManager) VLCApplication.getAppContext().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				getWindow().getDecorView().getRootView().getWindowToken(), 0);
	}

	public void showProgressBar() {
		mHandler.obtainMessage(ACTIVITY_SHOW_PROGRESSBAR).sendToTarget();
	}

	public void hideProgressBar() {
		mHandler.obtainMessage(ACTIVITY_HIDE_PROGRESSBAR).sendToTarget();
	}

	public void sendTextInfo(String info, int progress, int max) {
		mHandler.obtainMessage(ACTIVITY_SHOW_TEXTINFO, max, progress, info).sendToTarget();
	}

	public void clearTextInfo() {
		mHandler.obtainMessage(ACTIVITY_SHOW_TEXTINFO, 0, 100, null).sendToTarget();
	}

	protected void onPanelClosedUiSet() {
		mDrawerLayout.setDrawerLockMode(HackyDrawerLayout.LOCK_MODE_LOCKED_CLOSED);
	}

	protected void onPanelOpenedEntirelyUiSet() {
		mDrawerLayout.setDrawerLockMode(HackyDrawerLayout.LOCK_MODE_UNLOCKED);
	}

	protected void onPanelOpenedUiSet() {
		mDrawerLayout.setDrawerLockMode(HackyDrawerLayout.LOCK_MODE_UNLOCKED);
		removeTipViewIfDisplayed();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//        SidebarAdapter.SidebarEntry entry = (SidebarEntry) mListView.getItemAtPosition(position);
		//        Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder);
		//
		//        if(current == null || (entry != null && current.getTag().equals(entry.id))) { /* Already selected */
		//            if (mFocusedPrior != 0)
		//                requestFocusOnSearch();
		//            mDrawerLayout.closeDrawer(mListView);
		//            return;
		//        }
		//
		//        // This should not happen
		//        if(entry == null || entry.id == null)
		//            return;
		//
		//        if (entry.type == SidebarEntry.TYPE_FRAGMENT) {
		//
		//                /* Slide down the audio player */
		//            slideDownAudioPlayer();
		//
		//                /* Switch the fragment */
		//            Fragment fragment = getFragment(entry.id);
		//            if (fragment instanceof MediaBrowserFragment)
		//                ((MediaBrowserFragment)fragment).setReadyToDisplay(false);
		//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		//            ft.replace(R.id.fragment_placeholder, fragment, entry.id);
		//            ft.addToBackStack(mCurrentFragment);
		//            ft.commit();
		//            mCurrentFragment = entry.id;
		//            mSidebarAdapter.setCurrentFragment(mCurrentFragment);
		//
		//            if (mFocusedPrior != 0)
		//                requestFocusOnSearch();
		//        } else if (entry.type == SidebarEntry.TYPE_SECONDARY_FRAGMENT)
		//            showSecondaryFragment(SecondaryActivity.ABOUT);
		//        else if (entry.attributeID == R.attr.ic_menu_preferences)
		//            startActivityForResult(new Intent(this, PreferencesActivity.class), ACTIVITY_RESULT_PREFERENCES);
		//        mDrawerLayout.closeDrawer(mListView);
	}

	private void requestFocusOnSearch() {
		View search = findViewById(R.id.ml_menu_search);
		if (search != null)
			search.requestFocus();
	}


	/**
	 * 初始化动画
	 */
	private void initIndView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		int bmpW = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.indicator_blue_small1).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
		one = offset + screenW / 2;
		two = offset + (screenW / 2) * 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

//	@Override
//	public void onBackClick(View view) {
//		//btn=(Button) findViewById(R.id.back_btn);
//		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//			//btn.setVisibility(View.VISIBLE);
//			super.onBackPressed();
//
//
//		} else {
//			//btn.setVisibility(View.GONE);
//			//				if(getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder) != null){
//			finish();
//			//					btn=(Button) findViewById(R.id.back_btn);
//			//					btn.setVisibility(View.GONE);
//			//				}
//		}
//
//	}
	
	
	
}
