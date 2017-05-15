package com.example.polymerization.frgment;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.videoplayer.bd.R;
import com.xinyinhe.juheplayer.Gridadpter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Popualr extends Fragment {
	private GridView gview;
	private List<Map<String, Object>> data_list;
	private SimpleAdapter sim_adapter;
	private Gridadpter gridadpter;
	private List<String> list;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.view1, container, false);
		TextView tv=(TextView) view.findViewById(R.id.tv);
		tv.setText("Popualr");
		gview=(GridView) view.findViewById(R.id.gridview1);
		list=new ArrayList<String>();
		list.add("youku");
		list.add("sohu");
		list.add("pptv");
		list.add("ls");
		list.add("tudou");
		gridadpter=new Gridadpter(getActivity(), list);
		gview.setAdapter(gridadpter);
		return view;
	}
	
	
	
}

