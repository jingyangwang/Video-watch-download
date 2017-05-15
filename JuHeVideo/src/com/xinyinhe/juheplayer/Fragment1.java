package com.xinyinhe.juheplayer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.videoplayer.bd.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class Fragment1 extends Fragment {
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
		View root = inflater.inflate(R.layout.view1, container, false);
		gview=(GridView) root.findViewById(R.id.gridview1);
		list=new ArrayList<String>();
		list.add("youku");
		list.add("sohu");
		list.add("pptv");
		list.add("ls");
		list.add("tudou");
		gridadpter=new Gridadpter(getActivity(), list);
		gview.setAdapter(gridadpter);
		//�½�List
		//     data_list = new ArrayList<Map<String, Object>>();
		//     //��ȡ���
		//     getData();
		//     //�½�������
		//     String [] from ={"image","text"};
		//     int [] to = {R.id.image,R.id.text};
		//  sim_adapter = new SimpleAdapter(this, data_list, R.layout.gridview_item, from, to);
		//����������
		//   gview.setAdapter(sim_adapter);
		return root;
	}
	private int[] icon = { R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher };
	//private String[] iconName = { "ͨѶ¼", "����", "�����", "ʱ��", "��Ϸ", "����", "����",
	//        "����", "����", "����", "�����", "��Ƶ" };
	//public List<Map<String, Object>> getData(){        
	//    //cion��iconName�ĳ�������ͬ�ģ�������ѡ��һ������
	//    for(int i=0;i<icon.length;i++){
	//        Map<String, Object> map = new HashMap<String, Object>();
	//        map.put("image", icon[i]);
	//        map.put("text", iconName[i]);
	//        data_list.add(map);
	//    }
	//        
	//    return data_list;
	//}
}
