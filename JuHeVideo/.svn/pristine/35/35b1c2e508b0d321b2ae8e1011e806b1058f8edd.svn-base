package com.xinyinhe.juheplayer;


import java.util.ArrayList;
import java.util.List;

import com.videoplayer.bd.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.GridView;

public class Fragment4 extends Fragment {
	private GridView My_channel,add_chennel;
	private List<String> list_add_chennel,list_My_channel;
	
@Override
@Nullable
public View onCreateView(LayoutInflater inflater,
		@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View root=inflater.inflate(R.layout.channels, container, false);
	My_channel=(GridView) root.findViewById(R.id.gridview3_channel);
	add_chennel=(GridView) root.findViewById(R.id.gridview3_add_channel);
	list_add_chennel=new ArrayList<String>();
	list_My_channel=new ArrayList<String>();
    for (int i = 0; i < 7; i++) {
    	list_add_chennel.add("add_chennel"+i);
    	list_My_channel.add("My_channel"+i);
	}
	Mychannels_adpter mychannels_adpter=new Mychannels_adpter(getActivity(), list_add_chennel);
	Mychannels_adpter mychannels_adpter1=new Mychannels_adpter(getActivity(), list_My_channel);
	My_channel.setAdapter(mychannels_adpter1);
	add_chennel.setAdapter(mychannels_adpter);
	return root;}
}
