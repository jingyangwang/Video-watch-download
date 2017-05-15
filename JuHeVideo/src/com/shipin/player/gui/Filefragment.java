/**
 * 
 */
package com.shipin.player.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.videoplayer.bd.R;

/**
 * @author wjy
 * 
 */
public class Filefragment extends Fragment implements Runnable,OnItemClickListener {

	private ListView lv;
	Context mContext = getActivity();
	FileAdapter adapter;
	//FileAdapter1 adapter1;
	private ArrayList<FileItem> fileItems;
	private String jumpPath = "";
	private String rootPath = null;
	//判断全选
	boolean checked =false;
	//判断隐藏
	boolean  hide=false;
	//判断删除
	boolean dele=false;
	String filename="";
	List<String> vedio;
	Button btndele;
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.list_file, container, false);
		lv = (ListView) view.findViewById(R.id.lv);
		lv.setOnItemClickListener(this);
		jumpPath = Environment.getExternalStorageDirectory().getPath();
		rootPath = Environment.getExternalStorageDirectory().getPath();
		//修改布局文件，添加批处理按钮     xj   1103
		LinearLayout linear=(LinearLayout)view.findViewById(R.id.linear_ly);
		linear.setVisibility(View.VISIBLE);
		LinearLayout linear1=(LinearLayout)view.findViewById(R.id.linear_ly1);
     	LinearLayout linear2=(LinearLayout)view.findViewById(R.id.linear_ly2);
		LinearLayout linear3=(LinearLayout)view.findViewById(R.id.linear_ly3);
		linear1.setOnClickListener(listener);
		linear2.setOnClickListener(listener);
		linear3.setOnClickListener(listener);
		//修改布局文件，添加批处理按钮     xj   1103
		new Thread(this).start();
		return view;
	}
	//修改监听事件   xj 1103
	OnClickListener listener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.linear_ly1:
				Log.i("uuu", ">>>>>>>>>>>>>>>>>>>>");
				//1105
//				if(checked==true){
//					checked=false;
//				}
//				else{
//					checked=true;
//				}
				//1105
				for (int i = 0; i < fileItems.size(); i++) {
					fileItems.get(i).ischeck=true;
				}
				 mHandler.sendEmptyMessage(0);
//				Log.i("uuu", "checked>>>>>>>>>>>>>>>>>>>>");
//	           dele=true;
//				Log.i("uuu", "《《《《《《《《《《《《《《《《删除成功");
//				mHandler.sendEmptyMessage(0);
//				Log.i("uuu", "《《《《《《《《《《《《《《《《fjdfkd");
				break;
			case R.id.linear_ly2:
				//				if (!jumpPath.equals(rootPath)) {
				//					//jumpPath = new File(jumpPath).getParent();
				//					delAllFile(jumpPath);
				//				}
				//				else{
				//					delAllFile(rootPath);
				//				}

//				Log.i("uuu", ">>>>>>>>>>>>>>>>>>>>");
//				File file=new File(jumpPath);
//				Log.i("uuu", ">>>>>>>>>>>>>>>>>>>>");
//				RecursionDeleteFile(file);
//				Log.i("uuu", "《《《《《《《《《《《《《《《《");
//				Toast.makeText(getActivity(), "删除成功", 1).show();
				Log.i("uuu", "《《《《《《《《《《《《《《《《开始");
				//deleteFile(jumpPath);
				dele=true;
				Log.i("uuu", "《《《《《《《《《《《《《《《《删除成功");
				mHandler.sendEmptyMessage(0);
				Log.i("uuu", "《《《《《《《《《《《《《《《《fjdfkd");
				break;
			case R.id.linear_ly3:
				if(hide==true)
				{hide=false;
				checked=false;
				}
				else{hide=true;
				checked=false;}
				mHandler.sendEmptyMessage(0);
				break;

			default:
				break;
			}
		}
	};
	//修改监听事件   xj 1103
	private void delete() {
		// TODO Auto-generated method stub

	}
	//1103 xj
	//删除文件
	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);//再删除空文件夹
				flag = true;
			}
		}
		return flag;
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

	//   删除文件夹 
	//param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); //删除空文件夹
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}




	//1103 xj
	//	@Override
	//	public boolean onKeyDown(int keyCode, KeyEvent event) {
	//		if (keyCode == KeyEvent.KEYCODE_BACK) {
	//			if (!jumpPath.equals(rootPath)) {
	//				jumpPath = new File(jumpPath).getParent();
	//				new Thread(this).start();
	//				return true;
	//			} else {
	//				finish();
	//			}
	//		}
	//		return super.onKeyDown(keyCode, event);
	//	}

	//删除文件 xj 1103
	public static void RecursionDeleteFile(File file){
		if(file.isFile()){
			file.delete();
			return;
		}
		if(file.isDirectory()){
			File[] childFile = file.listFiles();
			if(childFile == null || childFile.length == 0){
				file.delete();
				return;
			}
			for(File f : childFile){
				RecursionDeleteFile(f);
			}
			file.delete();
		}
	}
	//删除文件 xj 1103










	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 0:
				adapter = new FileAdapter(mContext, fileItems);
				lv.setAdapter(adapter);
//				adapter1 = new FileAdapter1(mContext,vedio);
//				lv.setAdapter(adapter1);
				break;
				
			case 1:
				//if(adapter!=null){
					Log.i("dada", "<<<<<<<<");
					adapter.notifyDataSetChanged();
					
			//	}
//				adapter = new FileAdapter(mContext, fileItems);
//				lv.setAdapter(adapter);
				break;
			default:
				break;
			}
		}
	};





	@Override
	public void run() {
		fileItems = getFileList(jumpPath);
		//vedio=getVideoList(jumpPath);
		mHandler.sendEmptyMessage(0);
	}

	public ArrayList<FileItem> getFileList(String jumpPath) {
		ArrayList<FileItem> fileItems = new ArrayList<FileItem>();
		File rootFile = new File(jumpPath);
		File[] files = rootFile.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					FileItem fileItem = new FileItem();
					fileItem.fileName = file.getName();
					fileItem.filePath = file.getAbsolutePath();
					fileItem.isDirectory = file.isDirectory();
					fileItems.add(fileItem);
				}
			}
		}
		return fileItems;
	}


	//判断取出带视频的文件夹   xj 1104
	public List<String> getVideoList(String jumpPath) {
		vedio=new ArrayList<String>();
		ArrayList<FileItem> fileItems = new ArrayList<FileItem>();
		File rootFile = new File(jumpPath);
		File[] files = rootFile.listFiles();

		if (files != null) {
			for (File file : files) {
				if(file.isFile()){
					filename=file.getName();
					if(filename.contains(".mp4")||filename.contains(".3gp")){
						String str=file.getName();
						vedio.add(str);
					}
				}
				else{
					getVideoList(jumpPath);
				}
				//				if (file.isDirectory()) {
				//					FileItem fileItem = new FileItem();
				//					fileItem.fileName = file.getName();
				//					fileItem.filePath = file.getAbsolutePath();
				//					fileItem.isDirectory = file.isDirectory();
				//					fileItems.add(fileItem);
				//				}
			}
		}
		return vedio;
	}

	//判断取出带视频的文件夹   xj 1104
	//判断视频文件夹的适配器
//	class FileAdapter1 extends BaseAdapter{
//		private Context context;
//		private  List<String> list;
//		public FileAdapter1(Context context,List<String> list) {
//			// TODO Auto-generated constructor stub
//			this.context=context;
//			this.list=list;
//		}
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return list.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return list.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			Holder1 holder;
//			View view=convertView;
//			if(view==null){
//				view =LayoutInflater.from(context).inflate(R.layout.file_view_item,parent, false);
//				holder=new Holder1();
//				holder.tv=(TextView) view.findViewById(R.id.title);
//				holder.check1=(CheckBox) view.findViewById(R.id.browser_checkbox);
//				view.setTag(holder);
//			}
//			else{
//				holder=(Holder1) view.getTag();
//			}
//			holder.tv.setText(list.get(position));
//			return view;
//		}
//
//		class Holder1 {
//			TextView tv;
//			CheckBox check1;
//		}
//	}
	//判断视频文件夹的适配器
	// 适配器
	class FileAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<FileItem> list;
		// private static HashMap<Integer, Boolean> isSelected;  
		private HashMap<Integer, Boolean> isselect;
		public FileAdapter(Context context, ArrayList<FileItem> list) {
			this.context = getActivity();
			this.list = list;
			//设置map 进行存储选中状态  xj   1105
			isselect = new HashMap<Integer, Boolean>();  
		}
		
		
		
//		public static HashMap<Integer, Boolean> getselect(){
//			
//			return isselect;
//		}
//		public static HashMap<Integer, Boolean> setselect(HashMap<Integer, Boolean> isSelected){
//			
//			return   
//		}
		@Override
		public int getCount() {
			return list.size();
		}
		@Override
		public Object getItem(int position) {
			return list.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			View view = convertView;

			if (view == null) {
				view = LayoutInflater.from(context).inflate(
						R.layout.file_view_item, parent, false);
				//						R.layout.directory_view_item, parent, false);
				holder = new Holder();
				holder.name = (TextView) view.findViewById(R.id.title);
				holder.check=(CheckBox) view.findViewById(R.id.browser_checkbox);
				//holder.check.setVisibility(View.VISIBLE);
				//holder.check.setVisibility(View.VISIBLE);
				view.setTag(holder);
			} else {
				holder = (Holder) view.getTag();
			}
			final FileItem item = list.get(position);
		
			holder.name.setText(item.fileName);
			//全选 xj 1103
//			if(checked==true){
//				Log.i("ssssss", "显示前");
//				holder.check.setVisibility(View.VISIBLE);
//				Log.i("ssssss", "显示后");
//				holder.check.setChecked(true);
//				Log.i("ssssss", "选中");
//				//checked=false;
//			}
			
			if(item.ischeck==true){
				Log.i("ssssss", "显示前");
				holder.check.setVisibility(View.VISIBLE);
				Log.i("ssssss", "显示后");
				holder.check.setChecked(false);
				Log.i("ssssss", "选中");
			}
			
			holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(item.ischeck==true){
						item.ischeck=false;
					}
					else{
						item.ischeck=true;
					}
				}
			});
			//隐藏 xj 1103
			//			else{
			//				view.setVisibility(View.VISIBLE);	
			//			}
			//隐藏 xj 1103
			//删除 xj 1105
			if((dele==true)&&(holder.check.isChecked())){
				deleteFile(item.filePath);
				fileItems.remove(position);
				Toast.makeText(getActivity(), "删除完毕", 1).show();
				notifyDataSetChanged();
				//holder.check.setChecked(false);
				//mHandler.sendEmptyMessage(1);
				//adapter = new FileAdapter(mContext, fileItems);
				Log.i("uuu", "删除完毕");
			}
			//删除 xj 1105
			//隐藏 xj 1105
			//adapter = new FileAdapter(getActivity(), fileItems);
//			if(hide==true&&holder.check.isChecked()){
//				//view.setVisibility(View.GONE);
//				//lv.removeViewAt(view.getId());
//				fileItems.remove(position);
//				notifyDataSetChanged();
//				//lv.removeViewAt(position);
//			}
			//隐藏 xj 1105
			
//			//全选 xj 1103
			
			
			

			
			
			return view;
		}
//		  public static HashMap<Integer, Boolean> getIsSelected() {  
//		        return isSelected;  
//		    }  
//		  
//		    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {  
//		        MyAdapter.isSelected = isSelected;  
//		    } 
		
		private class Holder {
			TextView name;
			CheckBox check;
		}
	}
	class FileItem {
		public boolean isDirectory;
		public String filePath;
		public String fileName;
		//添加属性 check属性 1105
		public boolean  ischeck;
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		Log.i("debug", "1111111111111111111111");
		FileItem fileItem = (FileItem) parent.getItemAtPosition(position);
		if (fileItem.isDirectory) {
			jumpPath = fileItem.filePath;
			new Thread(this).start();
		}
		// 适配器
	}
}
