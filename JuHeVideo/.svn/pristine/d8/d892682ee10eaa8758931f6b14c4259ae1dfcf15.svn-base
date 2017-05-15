/**
 * 
 */
package com.shipin.player.gui;

import com.shipin.player.gui.browser.FileBrowserFragment;
import com.shipin.player.gui.video.VideoGridFragment;
import com.videoplayer.bd.R;

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
import android.webkit.WebView.FindListener;

/**
 * @author wjy
 *
 */
public class MyFragment_frg1 extends Fragment {
@Override
@Nullable
public View onCreateView(LayoutInflater inflater,
		@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	 //将堆栈的frgment清除
//	BackStackEntry backstatck;
//	backstatck = getActivity().getSupportFragmentManager().getBackStackEntryAt(num);
//	getActivity().getSupportFragmentManager().popBackStack(backstatck.getId(), 0);
	View view=inflater.inflate(R.layout.myfragment_frg1, container, false);
	FragmentTransaction trans = getFragmentManager().beginTransaction();
	
//	trans.replace(R.id.fragment_video, new Filefragment_test(),"file");
	trans.replace(R.id.fragment_video, new VideoGridFragment(),"video");
	trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	//getActivity().getSupportFragmentManager().getBackStackEntryCount();
	trans.addToBackStack(null);
//	getFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
//		@Override
//		public void onBackStackChanged() {
//			Log.i("liukedong", "onBackStackChanged" + getFragmentManager().getBackStackEntryCount());
//			if(getFragmentManager().getBackStackEntryCount() > 2){
//				//linear.setVisibility(View.VISIBLE);
//				//addView();
//				//addfragment1();
//				lv_hr.setVisibility(View.VISIBLE);
//				hListView.setVisibility(View.VISIBLE);
//				Memory_management.setVisibility(View.VISIBLE);
//	             crateAdpter();
//	         	hListView.scrollTo(1000);
//			}
//			else{
//				//linear.setVisibility(View.GONE);
//				lv_hr.setVisibility(View.GONE);
//				hListView.setVisibility(View.GONE);
//				Memory_management.setVisibility(View.GONE);
//			}
//		}
//	});
	trans.commit();
	return view;
}
}
