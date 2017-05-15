/**
 * 
 */
package com.xinyinhe.juheplayer.mensetting;

import java.util.ArrayList;
import java.util.List;

import com.videoplayer.bd.R;
import com.xinyinhe.juheplayer.FileItem;
import com.xinyinhe.juheplayer.History_adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

/**
 * @author wjy
 *
 */
public class MenSettingLink extends ActionBarActivity {
	private ListView link_list;
	private List<FileItem> list;
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.link);
	link_list=(ListView)findViewById(R.id.link_list);
	list=new ArrayList<FileItem>();
	FileItem fileitem=new FileItem();
	fileitem.setFrom("youku");
	fileitem.setName("tv");
	fileitem.setSize("50m");
	fileitem.setTime("1m45s");
	list.add(fileitem);
	History_adapter adpter=new History_adapter(MenSettingLink.this,list);
	link_list.setAdapter(adpter);

}
}
