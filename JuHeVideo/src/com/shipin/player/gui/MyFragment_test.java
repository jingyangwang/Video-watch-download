/**
 * 
 */
package com.shipin.player.gui;

import java.util.ArrayList;
import java.util.List;

import com.shipin.player.gui.browser.FileBrowserFragment;
import com.shipin.player.gui.browser.StorageBrowserFragment;
import com.videoplayer.bd.R;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author wjy
 *
 */
public class MyFragment_test extends Fragment {//implements OnClickListener
	private Button btn;
	private  LinearLayout linear;
	private LinearLayout linear1;
	private TextView tbv;
	private TextView Memory_management;
	public int count=0;
	public int i=0;
	 public static final String KEY_MEDIA_LIST = "key_media_list";
	MyFragment1Adapter mAdapter;
	HorizontalListView hListView;
	private LinearLayout lv_hr;
	HorizontalListViewAdapter hListViewAdapter;
//	BaseBrowserAdapter mAdapter;
//	public MyPageFragment1() {
//		// TODO Auto-generated constructor stub
//		//mAdapter=new BaseBrowserAdapter(this);
//	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//Log.d("DEBUG", "onCreateView");
		View root = inflater.inflate(R.layout.mytest_fg, container, false);
//		tbv=(TextView) root.findViewById(R.id.title);
//		hListView = (HorizontalListView) root.findViewById(R.id.horizon_listview);
//		Memory_management=(TextView) root.findViewById(R.id.Memory_management);
//	lv_hr= (LinearLayout) root.findViewById(R.id.lv_hr);
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		//trans.add(R.id.fragment_placeholder, new FileBrowserFragment());
		//fragment_placeholder111
		trans.add(R.id.fragment_placeholder111, new StorageBrowserFragment());
		//StorageBrowserFragment
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		trans.addToBackStack(null);
		//linear= (LinearLayout) root.findViewById(R.id.linear_back);
		//linear.setOnClickListener(this);
		//linar_tv
	//	linear1=(LinearLayout) root.findViewById(R.id.linar_tv);
//		linear.setVisibility(View.VISIBLE);
//		addfragment1();
//		Memory_management.setOnClickListener(listener);
		
		
		
		
		//1103 xj
		getFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
			@Override
			public void onBackStackChanged() {
				Log.i("liukedong", "onBackStackChanged" + getFragmentManager().getBackStackEntryCount());
				if(getFragmentManager().getBackStackEntryCount() > 2){
					//linear.setVisibility(View.VISIBLE);
					//addView();
					//addfragment1();
					lv_hr.setVisibility(View.VISIBLE);
					hListView.setVisibility(View.VISIBLE);
					Memory_management.setVisibility(View.VISIBLE);
		             crateAdpter();
		         	hListView.scrollTo(1000);
				}
				else{
					//linear.setVisibility(View.GONE);
					lv_hr.setVisibility(View.GONE);
					hListView.setVisibility(View.GONE);
					Memory_management.setVisibility(View.GONE);
				}
			}
		});
		trans.commit();
		return root;
	}
	OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			BackStackEntry backstatck;
			backstatck = getActivity().getSupportFragmentManager().getBackStackEntryAt(0);
			getActivity().getSupportFragmentManager().popBackStack(backstatck.getId(), 0);
			//修改宽度
			//setListViewHeightBasedOnChildren(hListView);
			//自动滚动
			hListViewAdapter.notifyDataSetChanged();
		}
	};
	
	//1103 xj
//	@Override
//	public void onClick(View v) {
////		if(v.getId() == R.id.linear_back){
////			if(getActivity() instanceof OnButtonClickListener){
////				((OnButtonClickListener)getActivity()).onBackClick(v);
////			}
////		}
//
//	}
	public 	String[] strArr;
	
	
	
public void crateAdpter(){
	String str=(String) SharedPreferencesUtils.getParam(getActivity(), "key", "");
	//str.replace("//storage/sdcard0", " ");
	//List<String> title_name=new ArrayList<String>();
	   List<String> title_name = new ArrayList<String>();
	if(str!=null){
		//String[] strArr = str.split("/");
		strArr = str.split("/");}
//	int length1=strArr.length-3;
//	String []b = new String[length1] ;
	for (int i = 3; i < strArr.length; i++) {
		title_name.add(strArr[i]);
	}
	
	
	hListViewAdapter=new HorizontalListViewAdapter(getActivity() , title_name);
	hListView.setAdapter(hListViewAdapter);

	hListView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			hListView.setSelection(position);
			//hListViewAdapter.setSelectIndex(position);
			BackStackEntry backstatck;
			backstatck = getActivity().getSupportFragmentManager().getBackStackEntryAt(position+1);
			getActivity().getSupportFragmentManager().popBackStack(backstatck.getId(), 0);
			//修改宽度
			//setListViewHeightBasedOnChildren(hListView);
			hListViewAdapter.notifyDataSetChanged();
			
		}
	});
	
	
	
	
	
}
	public  void addfragment1() {
		// TODO Auto-generated method stub
		linear.removeAllViews();
		String str=(String) SharedPreferencesUtils.getParam(getActivity(), "key", "");
		//str.replace("//storage/sdcard0", " ");
		if(str!=null){
			//String[] strArr = str.split("/");
			strArr = str.split("/");
			for ( i = 3; i < strArr.length; i++) {
				 TextView tv=new TextView(getActivity());
				tv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						BackStackEntry backstatck;
						backstatck = getActivity().getSupportFragmentManager().getBackStackEntryAt(i-3);
						getActivity().getSupportFragmentManager().popBackStack(backstatck.getId(), 0);
						int num = getActivity().getSupportFragmentManager().getBackStackEntryCount();
					}
				});
				tv.setText("/"+strArr[i]);
				count=count+1;
				linear.addView(tv);
			}
			}
	}

	//定义一个函数将dp转换为像素
	public int Dp2Px(Context context, float dp) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (dp * scale + 0.5f); 
	}


	//定义函数动态控制listView的高度
	public void setListViewHeightBasedOnChildren(HorizontalListView listView) {

	    //获取listview的适配器
		HorizontalListViewAdapter listAdapter = (HorizontalListViewAdapter) listView.getAdapter();
	    //item的长度
	    int itemlength = 46;

	    if (listAdapter == null) {
	        return;
	    }

	    int totallong = 0;

	    for (int i = 0; i < listAdapter.getCount(); i++) {
	    totallong += Dp2Px(getActivity(),itemlength)+listView.getHorizontalFadingEdgeLength();
	    }

	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	  //  params.height = totalHeight;
	    params.width= totallong;

	    listView.setLayoutParams(params);
	}


}
