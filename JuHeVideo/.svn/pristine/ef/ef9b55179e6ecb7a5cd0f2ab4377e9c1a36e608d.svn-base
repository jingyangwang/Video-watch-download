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

public class Fragment5 extends Fragment {
	private ListView history_listview;
	private ListView earlier_listview;
	private List<FileItem> list;
@Override
@Nullable
public View onCreateView(LayoutInflater inflater,
		@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	
	View view = inflater.inflate(R.layout.history, container, false);
	//View view=LayoutInflater.from(getActivity()).inflate(R.layout.history, null);
	history_listview=(ListView) view.findViewById(R.id.today_listview);
	earlier_listview=(ListView) view.findViewById(R.id.earlier_listview);
	list=new ArrayList<FileItem>();
	FileItem fileitem=new FileItem();
	fileitem.setFrom("youku");
	fileitem.setName("tv");
	fileitem.setSize("50m");
	fileitem.setTime("1m45s");
	list.add(fileitem);
	History_adapter adpter=new History_adapter(getActivity(),list);
	history_listview.setAdapter(adpter);
	earlier_listview.setAdapter(adpter);
	return view;}
}
