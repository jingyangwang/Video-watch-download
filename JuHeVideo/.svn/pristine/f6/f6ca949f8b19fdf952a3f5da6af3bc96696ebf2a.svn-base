/**
 * 
 */
package com.shipin.player.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.shipin.player.gui.Filefragment.FileItem;
import com.shipin.player.util.SharePreUtil;
import com.videoplayer.bd.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author wjy
 *
 */
public class frgadpter1 extends BaseAdapter {
	private Context context;
	private ArrayList<com.shipin.player.gui.FileItem> list;
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;
	private LayoutInflater inflater = null;
//	public frgadpter(Context context, ArrayList<com.shipin.player.gui.FileItem> fileItems) {
//		// TODO Auto-generated constructor stub
//	
//		
//	}
	public frgadpter1(Context context,
			ArrayList<com.shipin.player.gui.FileItem> fileItems) {
		// TODO Auto-generated constructor stub
		this.list=fileItems;
		this.context=context;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		// 初始化数据
		initDate();
	
	}
	
	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
			
		}}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;
		//View view = convertView;

		if (convertView == null) {
//			view = LayoutInflater.from(context).inflate(
//					R.layout.file_view_item, parent, false);
			//						R.layout.directory_view_item, parent, false);
			//convertView = inflater.inflate(R.layout.file_view_item, null);
			//int i=5;
			
			
			convertView = inflater.inflate(SharePreUtil.getedit(context)?R.layout.file_view_item:R.layout.file_view_item_nocheck, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.title);
			holder.check=(CheckBox) convertView.findViewById(R.id.browser_checkbox);
			//holder.check.setVisibility(View.VISIBLE);
//			holder.check.setVisibility(View.VISIBLE);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final com.shipin.player.gui.FileItem item = list.get(position);
	
		holder.name.setText(item.fileName);
		//全选 xj 1103
//		if(checked==true){
//			Log.i("ssssss", "显示前");
//			holder.check.setVisibility(View.VISIBLE);
//			Log.i("ssssss", "显示后");
//			holder.check.setChecked(true);
//			Log.i("ssssss", "选中");
//			//checked=false;
//		}
		
//		if(item.ischeck==true){
//			Log.i("ssssss", "显示前");
//			holder.check.setVisibility(View.VISIBLE);
//			Log.i("ssssss", "显示后");
//			holder.check.setChecked(false);
//			Log.i("ssssss", "选中");
//		}
		
//		holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				// TODO Auto-generated method stub
//				if(item.ischeck==true){
//					item.ischeck=false;
//				}
//				else{
//					item.ischeck=true;
//				}
//			}
//		});
		holder.check.setChecked(getIsSelected().get(position));
		
//		if(holder.check.isChecked()&&delet){
//			
//			
//			
//		}
		
		
		
		
		return convertView;
	}
	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		frgadpter1.isSelected = isSelected;
	}
	//删除文件和文件夹 xj 1105
			/** 
			 * 删除文件、文件夹 
			 */  
			public static void deleteFile(String path) {  
			    File file = new File(path);  
			    if (file.isDirectory()) {  
			        File[] ff = file.listFiles();  
			        for (int i = 0; i < ff.length; i++) {  
			            deleteFile(ff[i].getPath());  
			        }  
			    }  
			    file.delete();  
			}
			//删除文件和文件夹 xj 1105
	public static class ViewHolder {
		TextView name;
		CheckBox check;
	}

}
