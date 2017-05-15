/**
 * 
 */
package com.shipin.player.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.shipin.player.gui.FileItem;
import com.shipin.player.util.SharePreUtil;
import com.videoplayer.bd.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author wjy
 *
 */
public class Frgadpter extends BaseAdapter {
	private Context context;
	private ArrayList<FileItem> list;
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;
	private LayoutInflater inflater = null;
//	public frgadpter(Context context, ArrayList<com.shipin.player.gui.FileItem> fileItems) {
//		// TODO Auto-generated constructor stub
//	
//		
//	}
	public Frgadpter(Context context,
			ArrayList<FileItem> fileItems) {
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
		}
	}
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
			Log.i("fileg", SharePreUtil.getedit(context)+"!!!!!!!!");
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.title);
			holder.text=(TextView) convertView.findViewById(R.id.text);
			holder.check=(CheckBox) convertView.findViewById(R.id.browser_checkbox);
			holder.imageview= (ImageView) convertView.findViewById(R.id.dvi_icon);
			//holder.check.setVisibility(View.VISIBLE);
//			holder.check.setVisibility(View.VISIBLE);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final FileItem item = list.get(position);
	
		holder.name.setText(item.fileName);
		if(item.getFolder_count()!=0){
			holder.text.setText(context.getResources().getString(R.string.exist)+item.getFolder_count()+context.getResources().getString(R.string.file));
			holder.imageview.setImageResource(R.drawable.ic_menu_folder);
		}
		else{
			holder.text.setText(context.getResources().getString(R.string.Video_file));
			holder.imageview.setImageResource(R.drawable.icon1);
		}

		holder.check.setChecked(getIsSelected().get(position));
		
		return convertView;
	}
	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		Frgadpter.isSelected = isSelected;
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
		TextView text;
		ImageView imageview;
	}

}
