package com.xinyinhe.juheplayer;

import java.util.ArrayList;
import java.util.List;

import com.videoplayer.bd.R;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class Fragment3 extends Fragment {
	private GridView history,hotserche;
	private List<String> list_history;
	private List<String> list_hotserche;
	private Context context=getActivity();
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//View root = inflater.inflate(R.layout.view3, container, false);
		View root = inflater.inflate(R.layout.searching, container, false);
		history=(GridView) root.findViewById(R.id.gridview2_history);
		hotserche=(GridView) root.findViewById(R.id.gridview2_hot_Serches);
		list_history=new ArrayList<String>();
		list_hotserche=new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list_history.add("history"+i);
			list_hotserche.add("hotserche"+i);
		}
		Secher_adpter adpter=new Secher_adpter(getActivity(), list_history);
		Secher_adpter adpter1=new Secher_adpter(getActivity(), list_hotserche);
		history.setAdapter(adpter);
		hotserche.setAdapter(adpter1);
		
		return root;
	}
}
