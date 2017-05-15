package com.xinyinhe.juheplayer;

import java.util.ArrayList;

import com.example.polymerization.adapter.DragAdapter;
import com.example.polymerization.adapter.OtherAdapter;
import com.example.polymerization.app.AppApplication;
import com.example.polymerization.bean.ChannelItem;
import com.example.polymerization.bean.ChannelManage;
import com.example.polymerization.view.DragGrid;
import com.example.polymerization.view.OtherGridView;
import com.shipin.player.VLCApplication;
import com.videoplayer.bd.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * @Author RA
 * @Blog http://blog.csdn.net/vipzjyno1
 */
public class ChannelActivity extends Activity {//implements OnItemClickListener 
	private DragGrid userGridView;
	private OtherGridView otherGridView;
	DragAdapter userAdapter;
	OtherAdapter otherAdapter;
	ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
	ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
	boolean isMove = false;
	boolean checked=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subscribe_activity);
		ImageView image1=(ImageView) findViewById(R.id.image_chanel);
		image1.setOnClickListener(listener);
		initView();
		initData();
		//xj 1128
				otherGridView.setClickable(false);
				userGridView.setClickable(false);
				//xj 1128
	}
	
	//1128 xj
	OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		int count=	SharePreUtil.getcout(ChannelActivity.this);
			if(count%2==0){
			checked=true;
			
			SharePreUtil.setcheck(ChannelActivity.this, checked);
			otherGridView.setClickable(true);
			userGridView.setClickable(true);
			
			userAdapter.notifyDataSetChanged();
			otherAdapter.notifyDataSetChanged();
			//userAdapter.notifyDataSetInvalidated();
			count++;
			SharePreUtil.setcout(ChannelActivity.this, count);
			}
			else{
				checked=false;
				SharePreUtil.setcheck(ChannelActivity.this, checked);
				otherGridView.setClickable(false);
				userGridView.setClickable(false);
				userAdapter.notifyDataSetChanged();
				otherAdapter.notifyDataSetChanged();
				count++;
				SharePreUtil.setcout(ChannelActivity.this, count);
			}
		}
	};
	//1128 xj
	
	/** ��ʼ�����*/
	private void initData() {
	    userChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(VLCApplication.getApp().getSQLHelper()).getUserChannel());
	    otherChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(VLCApplication.getApp().getSQLHelper()).getOtherChannel());
	    userAdapter = new DragAdapter(ChannelActivity.this, userChannelList);
	    userGridView.setAdapter(userAdapter);
	    otherAdapter = new OtherAdapter(ChannelActivity.this, otherChannelList);
	    otherGridView.setAdapter(ChannelActivity.this.otherAdapter);
	    //����GRIDVIEW��ITEM�ĵ������
	    //otherGridView.setOnItemClickListener(this);
	   // userGridView.setOnItemClickListener(this);
	    //1128  xj
	    otherGridView.setOnItemClickListener(listener1);
	    userGridView.setOnItemClickListener(listener1);
	    //1128 xj
	    
	}
	//1128 xj
	OnItemClickListener listener1=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent,final  View view,final int position,
				long id) {
			// TODO Auto-generated method stub
			if(isMove){
				return;
			}
			switch (parent.getId()) {
			case R.id.userGridView:
				//positionΪ 0��1 �Ĳ����Խ����κβ���
				if (position != 0&& position != 1) {// 
					final ImageView moveImageView = getView(view);
					if (moveImageView != null) {
						TextView newTextView = (TextView) view.findViewById(R.id.text_item);
						final int[] startLocation = new int[2];
						newTextView.getLocationInWindow(startLocation);
						final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);//��ȡ�����Ƶ������
						otherAdapter.setVisible(false);
						//��ӵ����һ��
						otherAdapter.addItem(channel);
						new Handler().postDelayed(new Runnable() {
							public void run() {
								try {
									int[] endLocation = new int[2];
									//��ȡ�յ�����
									otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
									MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
									userAdapter.setRemove(position);
									
								} catch (Exception localException) {
								}
							}
						}, 50L);
					}
				}
				break;
			case R.id.otherGridView:
				final ImageView moveImageView = getView(view);
				if (moveImageView != null){
					TextView newTextView = (TextView) view.findViewById(R.id.text_item);
					final int[] startLocation = new int[2];
					newTextView.getLocationInWindow(startLocation);
					final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
					userAdapter.setVisible(false);
					//��ӵ����һ��
					userAdapter.addItem(channel);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							try {
								int[] endLocation = new int[2];
								//��ȡ�յ�����
								userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
								MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);
								otherAdapter.setRemove(position);
							} catch (Exception localException) {
							}
						}
					}, 50L);
				}
				break;
			default:
				break;
			}
		}
	};
	//1128 xj
	/** ��ʼ������*/
	private void initView() {
		userGridView = (DragGrid) findViewById(R.id.userGridView);
		otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** GRIDVIEW��Ӧ��ITEM�������ӿ�  */
//	@Override
//	public void onItemClick(AdapterView<?> parent, final View view, final int position,long id) {
//		//�������ʱ��֮ǰ������û������ô���õ���¼���Ч
//		if(isMove){
//			return;
//		}
//		switch (parent.getId()) {
//		case R.id.userGridView:
//			//positionΪ 0��1 �Ĳ����Խ����κβ���
//			if (position != 0&& position != 1) {// 
//				final ImageView moveImageView = getView(view);
//				if (moveImageView != null) {
//					TextView newTextView = (TextView) view.findViewById(R.id.text_item);
//					final int[] startLocation = new int[2];
//					newTextView.getLocationInWindow(startLocation);
//					final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);//��ȡ�����Ƶ������
//					otherAdapter.setVisible(false);
//					//��ӵ����һ��
//					otherAdapter.addItem(channel);
//					new Handler().postDelayed(new Runnable() {
//						public void run() {
//							try {
//								int[] endLocation = new int[2];
//								//��ȡ�յ�����
//								otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
//								MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
//								userAdapter.setRemove(position);
//								
//							} catch (Exception localException) {
//							}
//						}
//					}, 50L);
//				}
//			}
//			break;
//		case R.id.otherGridView:
//			final ImageView moveImageView = getView(view);
//			if (moveImageView != null){
//				TextView newTextView = (TextView) view.findViewById(R.id.text_item);
//				final int[] startLocation = new int[2];
//				newTextView.getLocationInWindow(startLocation);
//				final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
//				userAdapter.setVisible(false);
//				//��ӵ����һ��
//				userAdapter.addItem(channel);
//				new Handler().postDelayed(new Runnable() {
//					public void run() {
//						try {
//							int[] endLocation = new int[2];
//							//��ȡ�յ�����
//							userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
//							MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);
//							otherAdapter.setRemove(position);
//						} catch (Exception localException) {
//						}
//					}
//				}, 50L);
//			}
//			break;
//		default:
//			break;
//		}
//	}
	
	
	
	
	/**
	 * ���ITEM�ƶ�����
	 * @param moveView
	 * @param startLocation
	 * @param endLocation
	 * @param moveChannel
	 * @param clickGridView
	 */
	private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final ChannelItem moveChannel,
			final GridView clickGridView) {
		int[] initLocation = new int[2];
		//��ȡ���ݹ�����VIEW�����
		moveView.getLocationInWindow(initLocation);
		//�õ�Ҫ�ƶ���VIEW,�������Ӧ��������
		final ViewGroup moveViewGroup = getMoveViewGroup();
		final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
		//�����ƶ�����
		TranslateAnimation moveAnimation = new TranslateAnimation(
				startLocation[0], endLocation[0], startLocation[1],
				endLocation[1]);
		moveAnimation.setDuration(300L);//����ʱ��
		//��������
		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);//����Ч��ִ����Ϻ�View���󲻱�������ֹ��λ��
		moveAnimationSet.addAnimation(moveAnimation);
		mMoveView.startAnimation(moveAnimationSet);
		moveAnimationSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				isMove = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				moveViewGroup.removeView(mMoveView);
				// instanceof �����ж�2��ʵ���ǲ���һ���жϵ������DragGrid����OtherGridView
				if (clickGridView instanceof DragGrid) {
					otherAdapter.setVisible(true);
					otherAdapter.notifyDataSetChanged();
					userAdapter.remove();
				}else{
					userAdapter.setVisible(true);
					userAdapter.notifyDataSetChanged();
					otherAdapter.remove();
				}
				isMove = false;
			}
		});
	}
	
	/**
	 * ��ȡ�ƶ���VIEW�������ӦViewGroup��������
	 * @param viewGroup
	 * @param view
	 * @param initLocation
	 * @return
	 */
	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
		int x = initLocation[0];
		int y = initLocation[1];
		viewGroup.addView(view);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mLayoutParams.leftMargin = x;
		mLayoutParams.topMargin = y;
		view.setLayoutParams(mLayoutParams);
		return view;
	}
	
	/**
	 * �����ƶ���ITEM��Ӧ��ViewGroup��������
	 */
	private ViewGroup getMoveViewGroup() {
		ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
		LinearLayout moveLinearLayout = new LinearLayout(this);
		moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		moveViewGroup.addView(moveLinearLayout);
		return moveLinearLayout;
	}
	
	/**
	 * ��ȡ�����Item�Ķ�ӦView��
	 * @param view
	 * @return
	 */
	private ImageView getView(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(cache);
		return iv;
	}
	
	/** �˳�ʱ�򱣴�ѡ�����ݿ������  */
	private void saveChannel() {
		ChannelManage.getManage(VLCApplication.getApp().getSQLHelper()).deleteAllChannel();
		userAdapter.notifyDataSetChanged();
		Log.i("dada", "channel:list de size"+ userAdapter.getChannnelLst().size());
		ChannelManage.getManage(VLCApplication.getApp().getSQLHelper()).saveUserChannel(userAdapter.getChannnelLst());
		for (int i = 0; i < userAdapter.getChannnelLst().size(); i++) {
		Log.i("dada", "name: "+userAdapter.getChannnelLst().get(i).getName());
		}
		otherAdapter.notifyDataSetChanged();
		ChannelManage.getManage(VLCApplication.getApp().getSQLHelper()).saveOtherChannel(otherAdapter.getChannnelLst());
	}
	
	@Override
	public void onBackPressed() {
		saveChannel();
		Log.i("dada", "save");
		checked=false;
		SharePreUtil.setcheck(ChannelActivity.this, checked);
		super.onBackPressed();
	}
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//		saveChannel();
//		Log.i("dada", "save");
//	}
}
