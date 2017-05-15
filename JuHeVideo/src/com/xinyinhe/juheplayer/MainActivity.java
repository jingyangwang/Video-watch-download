package com.xinyinhe.juheplayer;


import java.util.ArrayList;
import java.util.List;

import com.example.polymerization.adapter.DragAdapter;
import com.example.polymerization.adapter.OtherAdapter;
import com.example.polymerization.adapter.TabPageIndicatorAdapter1;
import com.example.polymerization.bean.ChannelItem;
import com.example.polymerization.bean.ChannelManage;
import com.example.polymerization.frgment.Animals;
import com.example.polymerization.frgment.Cook;
import com.example.polymerization.frgment.Education;
import com.example.polymerization.frgment.Entertainment;
import com.example.polymerization.frgment.Excitingmoments;
import com.example.polymerization.frgment.Fun;
import com.example.polymerization.frgment.Gag;
import com.example.polymerization.frgment.Games;
import com.example.polymerization.frgment.Lifestyle;
import com.example.polymerization.frgment.Movies;
import com.example.polymerization.frgment.Music;
import com.example.polymerization.frgment.News;
import com.example.polymerization.frgment.Pets;
import com.example.polymerization.frgment.Popualr;
import com.example.polymerization.frgment.Sports;
import com.example.polymerization.frgment.Technolgy;
import com.example.polymerization.frgment.Trailers;
import com.example.polymerization.frgment.Travel;
import com.example.polymerization.view.DragGrid;
import com.example.polymerization.view.OtherGridView;
import com.shipin.player.VLCApplication;
import com.videoplayer.bd.R;
import com.videoplayer.bd.Youtube_url;
import com.viewpagerindicator.TabPageIndicator;
import com.xinyinhe.juheplayer.mensetting.MenSettingAbout;
import com.xinyinhe.juheplayer.mensetting.MenSettingMyAccount;
import com.xinyinhe.juheplayer.mensetting.MenSettingStarts;
import com.xinyinhe.juheplayer.mensetting.MenSettingUpdate;
import com.xyh.video.activity.GwebActivity;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.ViewGroup.LayoutParams;  
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

@SuppressLint("NewApi") //public class MainActivity extends FragmentActivity  {//implements OnPageChangeListener
public class MainActivity extends ActionBarActivity  {
//	//控制横向popwind
//	/** 用户栏目的GRIDVIEW */
//	private DragGrid userGridView;
//	/** 其它栏目的GRIDVIEW */
//	private OtherGridView otherGridView;
//	/** 用户栏目对应的适配器，可以拖动 */
//	DragAdapter userAdapter;
//	/** 其它栏目对应的适配器 */
//	OtherAdapter otherAdapter;
//	/** 其它栏目列表 */
//	ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
//	/** 用户栏目列表 */
	ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
//	/** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */	
	boolean isMove = false;
	//控制横向popwind
	private ViewPager pager;
	private List<String>titleList;
	private PagerTabStrip tab;
	private PagerTitleStrip TitleStrip;
	private List<Fragment>fragList;
	PopupWindow popupWindow ;
	List<String> POP_list;
	private ImageView image1;
	Context  context=null;
	SelectPicPopupWindow menuWindow;
	
	TabPageIndicator indicator;
	TabPageIndicatorAdapter adapter1;
	TabPageIndicatorAdapter1 adapter2;
	
	//	//启动线程更新适配器1126 xj
	//	Handler hanler=new Handler(){
	//		public void handleMessage(android.os.Message msg) {
	//			switch (msg.what) {
	//			case 1:
	//				FragmentPagerAdapter adapter1 = new TabPageIndicatorAdapter(
	//						getSupportFragmentManager());
	//				break;
	//
	//			default:
	//				break;
	//			}
	//
	//
	//		};
	//	};
	//启动线程更新适配器1126
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("dove", "onCreate：：：：：");
	//	requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);  
		 //设置ActionBar 背景色 透明
//		 MainActivity.this.getActionBar().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
//		 //设置半透明的底色
//		 MainActivity.this.getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));
		 
		setContentView(R.layout.activity_main);
//		1130 actionbar xj
//		ActionBar actionBar = getActionBar();
//		Resources r = getResources();
//		Drawable myDrawable = r.getDrawable(R.drawable.back_002);
//		actionBar.setBackgroundDrawable(myDrawable);
//		1130 actionbar xj
		context=MainActivity.this;
		//xj
		//ActionBar actionBar = getActionBar();  
		// actionBar.hide();  
		// actionBar.show();  
		//xj
		pager=(ViewPager) findViewById(R.id.pager);
		pager.setOffscreenPageLimit(0);
//		Log.i("dada", "pager对象"+pager);
		//FrameLayout frame=(FrameLayout) findViewById(R.id.fra);
		//frame.setVisibility(View.VISIBLE);
		userChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(VLCApplication.getApp().getSQLHelper()).getUserChannel());
		titleList=new ArrayList<String>();
		fragList=new ArrayList<Fragment>();
		
		
		for (int i = 0; i < userChannelList.size(); i++) {
			titleList.add(userChannelList.get(i).getName());
			String name=userChannelList.get(i).getName();
			if(name.equalsIgnoreCase("popualr")){
				fragList.add(new Popualr());
				continue;
			}
			else if(name.equalsIgnoreCase("music")){
				fragList.add(new Music());
				continue;
			}
			else if(name.equalsIgnoreCase("movies")){
				fragList.add(new Movies());
				continue;
			}
			if(name.equalsIgnoreCase("games")){
				fragList.add(new Games());
				continue;
			}
			else if(name.equalsIgnoreCase("Entertainment")){
				fragList.add(new Entertainment());
				continue;
			}
			else if(name.equalsIgnoreCase("animals")){
				fragList.add(new Animals());
				continue;
			}
			else if(name.equalsIgnoreCase("sports")){
				fragList.add(new Sports());
				continue;
			}
			
			if(name.equalsIgnoreCase("news")){
				fragList.add(new News());
				continue;
			}
			else if(name.equalsIgnoreCase("education")){
				fragList.add(new Education());
				continue;
			}
			else if(name.equalsIgnoreCase("technolgy")){
				fragList.add(new Technolgy());
				continue;
			}
			else if(name.equalsIgnoreCase("gag")){
				fragList.add(new Gag());
				continue;
			}
			if(name.equalsIgnoreCase("lifestyle")){
				fragList.add(new Lifestyle());
				continue;
			}
			else if(name.equalsIgnoreCase("pets")){
				fragList.add(new Pets());
				continue;
			}
			else if(name.equalsIgnoreCase("cook")){
				fragList.add(new Cook());
				continue;
			}
			else if(name.equalsIgnoreCase("trailers")){
				fragList.add(new Trailers());
				continue;
			}
			else if(name.equalsIgnoreCase("fun")){
				fragList.add(new Fun());
				continue;
			}
			else if(name.equalsIgnoreCase("Excitingmoments")){
				fragList.add(new Excitingmoments());
				continue;
			}
			else if(name.equalsIgnoreCase("Travel")){
				fragList.add(new Travel());
				continue;
			}
			
			Log.i("dada","viewpager size = "+fragList.size());
			//fragList.add(new Fragment1());
		}
		//线程更新适配器
		//		hanler.sendEmptyMessage(1);
		//线程更新适配器
		//		titleList.add("Video Sites");
		//		titleList.add("Popular");
		//		titleList.add("Music");
		//		titleList.add("Top");
		//		titleList.add("TOP1");
		//		titleList.add("Top2");
		//		fragList=new ArrayList<Fragment>();
		//		fragList.add(new Fragment1());
		//		fragList.add(new Fragment2());
		//		fragList.add(new Fragment3());
		//		fragList.add(new Fragment4());
		//		fragList.add(new Fragment5());
		//		fragList.add(new Fragment6());
		// ViewPager的adapter
//		adapter1 = new TabPageIndicatorAdapter(
//				getSupportFragmentManager(),titleList);
//		adapter1 = new TabPageIndicatorAdapter(
//				getSupportFragmentManager());
		
		adapter2=new TabPageIndicatorAdapter1(getSupportFragmentManager(),titleList,fragList);
		
//		Log.i("dada", "适配器对象"+adapter1);
	//	final ViewPager pager = (ViewPager) findViewById(R.id.pager);
		image1=(ImageView) findViewById(R.id.image1);
		image1.setOnClickListener(listener);
		//pager.setAdapter(adapter1);
		pager.setAdapter(adapter2);
		//pager.setOffscreenPageLimit(0);
		//	pager.setCurrentItem(7);
		// 实例化TabPageIndicator然后设置ViewPager与之关联
		
		 indicator = (TabPageIndicator) findViewById(R.id.indicator);
//		 Log.i("dada", "indicator对象"+indicator);
		indicator.setViewPager(pager, 0);
		 
		 //indicator.setViewPager(pager);
		//Log.i("debug", "setCurrentItem(index)--->"+index);
		//indicator.setCurrentItem(0);//设置选择索引
		// 如果我们要对ViewPager设置监听，用indicator设置就行了
		indicator.setOnPageChangeListener(changelistener);
//		indicator.setOnPageChangeListener(new OnPageChangeListener() {
//			@Override
//			public void onPageSelected(int index) {
//				pager.setCurrentItem(index);
//			}
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//			}
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//
//			}
//		});
	}
	OnPageChangeListener changelistener=new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int index) {
			// TODO Auto-generated method stub
			pager.setCurrentItem(index);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	OnClickListener listener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.image1:
				//				Toast.makeText(MainActivity.this, "一点击", 1).show();
				//				showPopupWindow(v,context);
				Intent intent=new Intent(MainActivity.this,ChannelActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
	//	private void showPopupWindow(View view,Context context) {
	//		// 一个自定义的布局，作为显示的内容
	//		//		View contentView = LayoutInflater.from(context).inflate(
	//		//				R.layout.alert_dialog, null);
	//		//popwindow 控制监听的横条列表 xj 1126
	//		View contentView = LayoutInflater.from(context).inflate(
	//				R.layout.subscribe_activity, null);
	//		userGridView = (DragGrid) contentView.findViewById(R.id.userGridView);
	//		otherGridView = (OtherGridView)contentView. findViewById(R.id.otherGridView);
	//		// userChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).getUserChannel());
	//		otherChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).getOtherChannel());
	//		userAdapter = new DragAdapter(MainActivity.this, userChannelList);
	//		userGridView.setAdapter(userAdapter);
	//		otherAdapter = new OtherAdapter(MainActivity.this, otherChannelList);
	//		otherGridView.setAdapter(this.otherAdapter);
	//		//设置GRIDVIEW的ITEM的点击监听
	//		otherGridView.setOnItemClickListener(itemlistener1);
	//		userGridView.setOnItemClickListener(itemlistener1);
	//		//popwindow 控制监听的横条列表 xj 1126
	//		//		GridView pop_windows=(GridView)contentView.findViewById(R.id.gridview_popwindow);
	//		//		POP_list=new ArrayList<String>();
	//		//		for (int i = 0; i < 10; i++) {
	//		//			POP_list.add("a"+i);
	//		//		}
	//		//		Secher_adpter adpter=new Secher_adpter(context, POP_list);
	//		//		pop_windows.setAdapter(adpter);
	//		final PopupWindow popupWindow = new PopupWindow(contentView,
	//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
	//		popupWindow.setTouchable(true);
	//		popupWindow.setTouchInterceptor(new OnTouchListener() {
	//			@Override
	//			public boolean onTouch(View v, MotionEvent event) {
	//				Log.i("mengdd", "onTouch : ");
	//				return false;
	//				// 这里如果返回true的话，touch事件将被拦截
	//				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
	//			}
	//		});
	//		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
	//		// 我觉得这里是API的一个bug
	//		popupWindow.setBackgroundDrawable(getResources().getDrawable(
	//				R.drawable.picture));
	//		// 设置好参数之后再show
	//		popupWindow.showAsDropDown(view);
	//	}
	//	//点击监听 1126 xj popwindow
	//	OnItemClickListener itemlistener1=new OnItemClickListener() {
	//
	//		@Override
	//		public void onItemClick(AdapterView<?> parent, final View view, final int position,
	//				long id) {
	//			// TODO Auto-generated method stub
	//			switch (parent.getId()) {
	//			case R.id.userGridView:
	//				//position为 0，1 的不可以进行任何操作
	//				if (position != 0) {// && position != 1
	//					final ImageView moveImageView = getView(view);
	//					if (moveImageView != null) {
	//						TextView newTextView = (TextView) view.findViewById(R.id.text_item);
	//						final int[] startLocation = new int[2];
	//						newTextView.getLocationInWindow(startLocation);
	//						final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
	//						otherAdapter.setVisible(false);
	//						//添加到最后一个
	//						otherAdapter.addItem(channel);
	//						new Handler().postDelayed(new Runnable() {
	//							public void run() {
	//								try {
	//									int[] endLocation = new int[2];
	//									//获取终点的坐标
	//									otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
	//									MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
	//									userAdapter.setRemove(position);
	//								} catch (Exception localException) {
	//								}
	//							}
	//						}, 50L);
	//						//更新list横向链表 xj 1126
	//						userChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).getUserChannel());
	//						titleList.removeAll(titleList);
	//						fragList.removeAll(fragList);
	//						for (int i = 1; i < userChannelList.size(); i++) {
	//							titleList.add(userChannelList.get(i).getName());
	//							fragList.add(new Fragment1());
	//						}
	////						FragmentPagerAdapter adapter2 = new TabPageIndicatorAdapter(
	////								getSupportFragmentManager());
	////						pager.setAdapter(adapter2);
	//						//更新list横向链表 更新list横向链表 xj 1126
	//					}
	//				}
	//				break;
	//			case R.id.otherGridView:
	//				final ImageView moveImageView = getView(view);
	//				if (moveImageView != null){
	//					TextView newTextView = (TextView) view.findViewById(R.id.text_item);
	//					final int[] startLocation = new int[2];
	//					newTextView.getLocationInWindow(startLocation);
	//					final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
	//					userAdapter.setVisible(false);
	//					//添加到最后一个
	//					userAdapter.addItem(channel);
	//					new Handler().postDelayed(new Runnable() {
	//						public void run() {
	//							try {
	//								int[] endLocation = new int[2];
	//								//获取终点的坐标
	//								userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
	//								MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);
	//								otherAdapter.setRemove(position);
	//							} catch (Exception localException) {
	//							}
	//						}
	//					}, 50L);
	//				}
	//				break;
	//			default:
	//				break;
	//			}
	//		}
	//	};
	//	/**
	//	 * 点击ITEM移动动画
	//	 * @param moveView
	//	 * @param startLocation
	//	 * @param endLocation
	//	 * @param moveChannel
	//	 * @param clickGridView
	//	 */
	//	private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final ChannelItem moveChannel,
	//			final GridView clickGridView) {
	//		int[] initLocation = new int[2];
	//		//获取传递过来的VIEW的坐标
	//		moveView.getLocationInWindow(initLocation);
	//		//得到要移动的VIEW,并放入对应的容器中
	//		final ViewGroup moveViewGroup = getMoveViewGroup();
	//		final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
	//		//创建移动动画
	//		TranslateAnimation moveAnimation = new TranslateAnimation(
	//				startLocation[0], endLocation[0], startLocation[1],
	//				endLocation[1]);
	//		moveAnimation.setDuration(300L);//动画时间
	//		//动画配置
	//		AnimationSet moveAnimationSet = new AnimationSet(true);
	//		moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
	//		moveAnimationSet.addAnimation(moveAnimation);
	//		mMoveView.startAnimation(moveAnimationSet);
	//		moveAnimationSet.setAnimationListener(new AnimationListener() {
	//
	//			@Override
	//			public void onAnimationStart(Animation animation) {
	//				isMove = true;
	//			}
	//
	//			@Override
	//			public void onAnimationRepeat(Animation animation) {
	//			}
	//
	//			@Override
	//			public void onAnimationEnd(Animation animation) {
	//				moveViewGroup.removeView(mMoveView);
	//				// instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
	//				if (clickGridView instanceof DragGrid) {
	//					otherAdapter.setVisible(true);
	//					otherAdapter.notifyDataSetChanged();
	//					userAdapter.remove();
	//				}else{
	//					userAdapter.setVisible(true);
	//					userAdapter.notifyDataSetChanged();
	//					otherAdapter.remove();
	//				}
	//				isMove = false;
	//			}
	//		});
	//	}
	//
	//	/**
	//	 * 获取移动的VIEW，放入对应ViewGroup布局容器
	//	 * @param viewGroup
	//	 * @param view
	//	 * @param initLocation
	//	 * @return
	//	 */
	//	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
	//		int x = initLocation[0];
	//		int y = initLocation[1];
	//		viewGroup.addView(view);
	//		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	//		mLayoutParams.leftMargin = x;
	//		mLayoutParams.topMargin = y;
	//		view.setLayoutParams(mLayoutParams);
	//		return view;
	//	}
	//
	//	/**
	//	 * 创建移动的ITEM对应的ViewGroup布局容器
	//	 */
	//	private ViewGroup getMoveViewGroup() {
	//		ViewGroup moveViewGroup = (ViewGroup) MainActivity.this.getWindow().getDecorView();
	//		LinearLayout moveLinearLayout = new LinearLayout(MainActivity.this);
	//		moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	//		moveViewGroup.addView(moveLinearLayout);
	//		return moveLinearLayout;
	//	}
	//
	//	/**
	//	 * 获取点击的Item的对应View，
	//	 * @param view
	//	 * @return
	//	 */
	//	private ImageView getView(View view) {
	//		view.destroyDrawingCache();
	//		view.setDrawingCacheEnabled(true);
	//		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
	//		view.setDrawingCacheEnabled(false);
	//		ImageView iv = new ImageView(MainActivity.this);
	//		iv.setImageBitmap(cache);
	//		return iv;
	//	}
	//
	//	/** 退出时候保存选择后数据库的设置  */
	//	private void saveChannel() {
	//		ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).deleteAllChannel();
	//		ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).saveUserChannel(userAdapter.getChannnelLst());
	//		ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).saveOtherChannel(otherAdapter.getChannnelLst());
	//	}
	//点击监听 1126 xj popwindow
	@Override
	protected void onResume() {
		Log.i("dove", "onResume：：：：：");
		ActionBar actionBar = getSupportActionBar();
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.back_002);
		actionBar.setBackgroundDrawable(myDrawable);
		super.onResume();
	}
	@Override
	protected void onStart() {
		Log.i("dove", "onStart：：：：：");
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.i("dove", "onRestart::::");
		super.onRestart();
		//更新list横向链表 xj 1126
		userChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(VLCApplication.getApp().getSQLHelper()).getUserChannel());
//		titleList.clear();
//		titleList.removeAll(titleList);
		//titleList=new ArrayList<String>();
		//fragList=new ArrayList<Fragment>();
//		fragList.clear();
//		fragList.removeAll(fragList);
//		titleList.add("aaaa");
//		fragList.add(new Fragment1());
		titleList.clear();
		titleList.removeAll(titleList);
		fragList.clear();
		fragList.removeAll(fragList);
		String changename="";
		
		for(ChannelItem chnne :userChannelList){
			Log.i("dada", "标题"+chnne.getName());
		}
		
		for (int i = 0; i < userChannelList.size(); i++) {
			titleList.add(userChannelList.get(i).getName());
			changename=userChannelList.get(i).getName();
			Log.i("dada", "changename = "+changename);
			if(changename.equalsIgnoreCase("popualr")){
				fragList.add(new Popualr());
				Log.i("dada", "popualr");
			}
			else if(changename.equalsIgnoreCase("music")){
				fragList.add(new Music());
				Log.i("dada", "music");
			}
			else if(changename.equalsIgnoreCase("movies")){
				fragList.add(new Movies());
				Log.i("dada", "movies");
			}
			if(changename.equalsIgnoreCase("games")){
				fragList.add(new Games());
				Log.i("dada", "games");
			}
			else if(changename.equalsIgnoreCase("Entertainment")){
				fragList.add(new Entertainment());
				Log.i("dada", "Entertainment");
			}
			else if(changename.equalsIgnoreCase("animals")){
				fragList.add(new Animals());
				Log.i("dada", "animals");
			}
			else if(changename.equalsIgnoreCase("sports")){
				fragList.add(new Sports());
				Log.i("dada", "sports");
			}
			
			if(changename.equalsIgnoreCase("news")){
				fragList.add(new News());
				Log.i("dada", "news");
			}
			else if(changename.equalsIgnoreCase("education")){
				fragList.add(new Education());
				Log.i("dada", "education");
			}
			else if(changename.equalsIgnoreCase("technolgy")){
				fragList.add(new Technolgy());
				Log.i("dada", "technolgy");
			}
			else if(changename.equalsIgnoreCase("gag")){
				fragList.add(new Gag());
				Log.i("dada", "gag");
			}
			if(changename.equalsIgnoreCase("lifestyle")){
				fragList.add(new Lifestyle());
				Log.i("dada", "lifestyle");
			}
			else if(changename.equalsIgnoreCase("pets")){
				fragList.add(new Pets());
				Log.i("dada", "pets");
			}
			else if(changename.equalsIgnoreCase("cook")){
				fragList.add(new Cook());
				Log.i("dada", "cook");
			}
			else if(changename.equalsIgnoreCase("trailers")){
				fragList.add(new Trailers());
				Log.i("dada", "trailers");
			}
			else if(changename.equalsIgnoreCase("fun")){
				fragList.add(new Fun());
				Log.i("dada", "fun");
			}
			else if(changename.equalsIgnoreCase("Excitingmoments")){
				fragList.add(new Excitingmoments());
				Log.i("dada", "Excitingmoments");
			}
			else if(changename.equalsIgnoreCase("Travel")){
				fragList.add(new Travel());
				Log.i("dada", "Travel");
			}
			
		}
		
		Log.i("dada", "titleList的大小"+titleList.size());
		Log.i("dada","userChanneList = "+userChannelList.size());
		Log.i("dada","fragList = "+fragList.size());
		
		adapter2.notifyDataSetChanged();
		
//		pager.removeAllViews();
//		
//		adapter2 = new TabPageIndicatorAdapter1(getSupportFragmentManager(), titleList, fragList);
//		
//		pager.setAdapter(adapter2);
		
		
//		indicator.notifyDataSetChanged();
		indicator.setViewPager(pager);
		//indicator.notifyDataSetChanged();
		Log.i("dada", "indicator对象"+indicator);
		indicator.setCurrentItem(0);
		indicator.setOnPageChangeListener(changelistener);
		indicator.notifyDataSetChanged();
		
		
//		 indicator = (TabPageIndicator) findViewById(R.id.indicator);
//			indicator.setViewPager(pager);
//			//Log.i("debug", "setCurrentItem(index)--->"+index);
//			indicator.setCurrentItem(0);//设置选择索引
//			// 如果我们要对ViewPager设置监听，用indicator设置就行了
//			indicator.setOnPageChangeListener(changelistener);
		
		
		//更新list横向链表 更新list横向链表 xj 1126
	}
	@Override
	protected void onPause() {
		Log.i("dove", "onPause::::");
		// TODO Auto-generated method stub
		super.onPause();
		//FragmentPagerAdapter 
	
	}
	@Override
	protected void onStop() {
		Log.i("dove", "onStop::::");
		// TODO Auto-generated method stub
		super.onStop();
		//FragmentPagerAdapter adapter1=null;
		//saveChannel();
		
		//yukun
//		titleList.clear();
//		titleList.removeAll(titleList);
//		fragList.clear();
//		fragList.removeAll(fragList);
		
		
		//adapter1=null;
//		fragList=null;
//		titleList=null;
		
	}
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			finish();
			return false;
		}
		//    return super.onKeyDown(keyCode, event);
		return false;};

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.media_library, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {

			switch (item.getItemId()) {
			case R.id.ml_menu_search://搜索

				break;
			case R.id.ml_menu_dwon://下载
				
				
				Intent intent0=new Intent(MainActivity.this,Youtube_url.class);
				startActivity(intent0);
//				Intent intent0=new Intent(MainActivity.this,GwebActivity.class);
//				startActivity(intent0);
				break;
			case R.id.ml_menu_My_Account://myaccount我的账户
				Intent intent1=new Intent(MainActivity.this,MenSettingMyAccount.class);
				startActivity(intent1);
				break;
			case R.id.ml_menu_5Stars://star 评论
				Intent intent2=new Intent(MainActivity.this,MenSettingStarts.class);
				startActivity(intent2);
				break;
			case R.id.ml_menu_updates://updata
				Intent intent3=new Intent(MainActivity.this,MenSettingUpdate.class);
				startActivity(intent3);
				break;
			case R.id.ml_menu_about://about
				Intent intent4=new Intent(MainActivity.this,MenSettingAbout.class);
				startActivity(intent4);
				break;

			default:
				break;
			}


			return super.onOptionsItemSelected(item);
		}


		/**
		 * ViewPager适配器
		 * 
		 * @author len
		 *
		 */
		class TabPageIndicatorAdapter extends FragmentPagerAdapter {
         //private List<String> titleList;
//			public TabPageIndicatorAdapter(FragmentManager fm,List<String>titleList) {
//				super(fm);
//				this.titleList=titleList;
//			}

			public TabPageIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}


			@Override
			public Fragment getItem(int position) {
				return fragList.get(position);
			}
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				super.destroyItem(container, position, object);
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return titleList.get(position % titleList.size());
			}

			@Override
			public int getCount() {
				return titleList.size();
			}
		}
		
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
		Log.i("dove", "mainactivity屏幕切换");
		
        super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onDestroy() {
		Log.i("dove", "onDestroy::::");
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
