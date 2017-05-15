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
import android.widget.ListView;

public class Fragment2 extends Fragment {
	private ListView lv;
	private List<FileItem> list;
@Override
@Nullable
public View onCreateView(LayoutInflater inflater,
		@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View root = inflater.inflate(R.layout.view2, container, false);
	list=new ArrayList<FileItem>();
	getdata();
    lv=(ListView) root.findViewById(R.id.listview);
    Fragment_adpter fragment_adpter=new Fragment_adpter(getActivity(), list);
    lv.setAdapter(fragment_adpter);
	return root;
}
private void  getdata(){
	FileItem fileitem;
	for (int i = 0; i < 10; i++) {
		 fileitem=new FileItem();
		 fileitem.setFrom("youku"+i);
		 fileitem.setName("Tbv"+i);
		 fileitem.setSize("100m"+i);
		 fileitem.setTime("5min"+i);
		 list.add(fileitem);
	}
	
}
}
