package com.example.polymerization.frgment;


import java.util.ArrayList;
import java.util.List;

import com.shipin.player.gui.video.VideoPlayerActivity;
import com.videoplayer.bd.R;
import com.xinyinhe.juheplayer.FileItem;
import com.xinyinhe.juheplayer.Fragment_adpter;
import com.xinyinhe.juheplayer.Fragment_list_adpter;
import com.xinyinhe.juheplayer.bean.Data;
import com.xinyinhe.juheplayer.bean.MyTask;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Entertainment extends Fragment {
	private ListView lv;
	private List<Data>  list;
	// 参考id [/146834156, /143657394, /144857118, /146742515]
	private String urlString = "https://player.vimeo.com/video/145836531/config";
	private ProgressBar probar;
	private SharedPreferences mSPF;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	
		View view=inflater.inflate(R.layout.view2, container, false);
		mSPF = getActivity().getSharedPreferences("playMovies", getActivity().MODE_WORLD_WRITEABLE);
		TextView tv=(TextView) view.findViewById(R.id.tv);
	//	tv.setText("Music");
		list = new ArrayList<Data>();
	    lv=(ListView) view.findViewById(R.id.listview);
	    probar = (ProgressBar) view.findViewById(R.id.fragment_view2_progressBar);
	    getdata();
		return view;
	}

	private void  getdata(){
//		FileItem fileitem;
//		for (int i = 0; i < 10; i++) {
//			 fileitem=new FileItem();
//			 fileitem.setFrom("youku"+i);
//			 fileitem.setName("Tbv"+i);
//			 fileitem.setSize("100m"+i);
//			 fileitem.setTime("5min"+i);
//			 list.add(fileitem);
//		}
		probar.setVisibility(View.VISIBLE);
		MyTask myTask = new MyTask(urlString , handler ,getActivity());
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String st = "file:///storage/emulated/0/%E6%96%B0%E6%96%87%E4%BB%B6%E5%A4%B9%20(6)/w.3gp";
				String s = "https://skyfiregcs-a.akamaihd.net/exp=1449228637~acl=%2A%2F440573261.mp4%2A~hmac=ae51f9f64fcbe87864e8b68c689dae360add5b0c40bb6bc80f08098574493b55/vimeo-prod-skyfire-std-us/01/4167/5/145836531/440573261.mp4";
				mSPF.edit().putString("playMovie", st).commit();
	  		    VideoPlayerActivity.start(getActivity(), Uri.parse(st));
//				VideoPlayerActivity.start(getActivity(), Uri.parse(list.get(position).getUrl()));
			}
		});
	}
	
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==1){
				list = (List<Data>) msg.obj;
//				FileItem fileitem;
//				for (int i = 0; i < 10; i++) {
//					 fileitem=new FileItem();
//					 fileitem.setFrom("vimeo");
//					 fileitem.setName(video.getVideo().getTitle());
//					 fileitem.setSize("10M views");
//					 fileitem.setTime("02:35");
//					 fileitem.setImg(video.getVideo().getThumbs().getBase());
//					 fileitem.setUrl(video.getRequest().getFiles().getProgressive().get(0).getUrl());
//					 list.add(fileitem);
////			}
				Fragment_list_adpter adpter = new Fragment_list_adpter(getActivity(), list);
			    lv.setAdapter(adpter);
			    new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						probar.setVisibility(View.GONE);
					}
				}, 6000);
			}
		}
	};
	
}

	