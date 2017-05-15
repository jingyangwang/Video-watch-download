/**
 * 
 */
package com.shipin.player.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.shipin.player.gui.Filefragment.FileAdapter;
import com.shipin.player.gui.Filefragment.FileItem;
import com.videoplayer.bd.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * @author wjy
 *
 */
public class Filefragment1 extends Fragment implements Runnable,OnItemClickListener {
	private ListView lv;
	Context mContext = getActivity();
	FileAdapter1 adapter1;
	private String jumpPath1 = "";

	private ArrayList<FileItem> fileItems;
	private String jumpPath = "";
	private String rootPath = null;
	//判断全选
	boolean checked =false;
	//判断隐藏
	boolean  hide=false;
	String filename="";
	boolean file_add=false;
	List<String> vedio;
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
		linear1.setOnClickListener(listener);
		linear1.setOnClickListener(listener);
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
				if(checked==true){checked=false;}
				else{checked=true;}
				mHandler.sendEmptyMessage(0);
				Log.i("uuu", "checked>>>>>>>>>>>>>>>>>>>>");
				break;
//			case R.id.linear_ly2:
//				//				if (!jumpPath.equals(rootPath)) {
//				//					//jumpPath = new File(jumpPath).getParent();
//				//					delAllFile(jumpPath);
//				//				}
//				//				else{
//				//					delAllFile(rootPath);
//				//				}
//
//				Log.i("uuu", ">>>>>>>>>>>>>>>>>>>>");
//				File file=new File(jumpPath);
//				Log.i("uuu", ">>>>>>>>>>>>>>>>>>>>");
//				RecursionDeleteFile(file);
//				Log.i("uuu", "《《《《《《《《《《《《《《《《");
//				Toast.makeText(getActivity(), "删除成功", 1).show();
//				mHandler.sendEmptyMessage(0);
//				Log.i("uuu", "《《《《《《《《《《《《《《《《fjdfkd");
//				break;
//			case R.id.linear_ly3:
//				if(hide==true)
//				{hide=false;
//				checked=false;
//				}
//				else{hide=true;
//				checked=false;}
//				mHandler.sendEmptyMessage(0);
//				break;
//
//			default:
//				break;
			}
		}
	};

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
				adapter1 = new FileAdapter1(mContext, fileItems);
				lv.setAdapter(adapter1);
				Log.i("fileg", "适配器执行|||||||||||||||||||||||||||||||||||||||||||||||||");
				//					adapter1 = new FileAdapter1(mContext,vedio);
				//					lv.setAdapter(adapter1);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void run() {
		//fileItems = getFileList(jumpPath);
		//vedio=getVideoList(jumpPath);
		fileItems = new ArrayList<Filefragment1.FileItem>();
		getVideoList(jumpPath);
		mHandler.sendEmptyMessage(0);
	}
//1105 修改目录跳转
	public ArrayList<FileItem> getFileList(String jumpPath) {
		ArrayList<FileItem> fileItems = new ArrayList<FileItem>();
		File rootFile = new File(jumpPath);
		if(rootFile==null){
			
			Toast.makeText(getActivity(), "下层没有文件了哦", 1).show();
			return null;
		}
		else{
			//File rootFile = new File(jumpPath);

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
			
		}
		return fileItems;
	}
	//1105 修改目录跳转

	//判断取出带视频的文件夹   xj 1104
	public ArrayList<FileItem> getVideoList(String jumpPath) {
		//vedio=new ArrayList<String>();
		
		File rootFile = new File(jumpPath);
		File[] files = rootFile.listFiles();

		if (files != null) {
			for (File file : files) {
				if(file.isFile()){
					filename=file.getName();
					Log.i("fileg", "判断前");
					Log.i("fileg", filename.endsWith("mkv")+""+filename.endsWith(".mkv"));
					if(filename.endsWith(".mkv")||filename.endsWith(".rmvb")||filename.endsWith(".mov")||filename.endsWith(".mp4")){
					//if(filename.endsWith(".mkv")){
					//String str=file.getParentFile().getName();
						//String str=file.getName();
						//vedio.add(str);
						Log.i("fileg", "判断后。。。。。。。。。。。。。。。。。。。。。。。。。。");
						FileItem fileItem = new FileItem();
						//fileItem.fileName = file.getName();
						//fileItem.filePath = file.getAbsolutePath();
						File parent=file.getParentFile();
						fileItem.isDirectory = parent.isDirectory();
						fileItem.fileName = parent.getName();
					    fileItem.filepraPath=parent.getAbsolutePath();
						//fileItem.filePath = parent.getAbsolutePath();
						fileItem.filesonPath=file.getAbsolutePath();
						fileItem.filePath = parent.getParent();
						String str_fileItem=fileItem.filePath;
						//判断路径是否已经存在，如存在将不再将路径数据加到集合，
						if(fileItems.size()>0){
							for (int i = 0; i <fileItems.size(); i++) {
								String str_fileItems=fileItems.get(i).filePath;
								if(str_fileItem.equals(str_fileItems)){
									file_add=true;
								}
							}
						}
						
						if(file_add||fileItems.size()==0){
							Log.i("fileg", "添加结束《《《《《《《《《《《《《《《《《《《《《《《《《");
							fileItems.add(fileItem);
							
							
						}
						Log.i("fileg", "执行结束《《《《《《《《《《《《《《《《《《《《《《《《《");
					}
				}
				else{
					//判断如果文件类型是目录的情况，递归操作遍历文件
					Log.i("fileg", "》》》》》》》》》》执行非文件开始《《《《《《《《《《《《《《");
					String str=file.getAbsolutePath();
					
					getVideoList(str);
					Log.i("fileg", "..................执行非文件结束..................");
					//getVideoList(jumpPath);
				}
			}
		}
		return fileItems;
	}

	//判断取出带视频的文件夹   xj 1104
	//判断视频文件夹的适配器
	class FileAdapter1 extends BaseAdapter{
		private Context context;
		private ArrayList<FileItem> list;

		public FileAdapter1(Context context, ArrayList<FileItem> list) {
			this.context = getActivity();
			this.list = list;
		}

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
			Log.i("fileg", "适配器执行<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			if (view == null) {
				view = LayoutInflater.from(context).inflate(
						R.layout.file_view_item, parent, false);
				//						R.layout.directory_view_item, parent, false);
				holder = new Holder();
				holder.name = (TextView) view.findViewById(R.id.title);
				holder.check=(CheckBox) view.findViewById(R.id.browser_checkbox);
				//holder.check.setVisibility(View.VISIBLE);
				view.setTag(holder);
			} else {
				holder = (Holder) view.getTag();
			}
			final FileItem item = list.get(position);
			holder.name.setText(item.fileName);
			Log.i("fileg", "适配器执行>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
							//全选 xj 1103
							if(checked==true){
								Log.i("ssssss", "显示前");
								holder.check.setVisibility(View.VISIBLE);
								Log.i("ssssss", "显示后");
								holder.check.setChecked(true);
								Log.i("ssssss", "选中");
							}
							//全选 xj 1103
			//
			//				holder.name.setText(item.fileName);
			//				//隐藏 xj 1103
			//				if(hide==true&&holder.check.isChecked()){
			//					//view.setVisibility(View.GONE);
			//					//lv.removeViewAt(view.getId());
			//					lv.removeViewAt(position);
			//				}
			//				//			else{
			//				//				view.setVisibility(View.VISIBLE);	
			//				//			}
			//				//隐藏 xj 1103
			return view;
		}

		private class Holder {
			TextView name;
			CheckBox check;
		}

	}











	//判断视频文件夹的适配器


	// 适配器
	//		class FileAdapter extends BaseAdapter {
	//			private Context context;
	//			private ArrayList<FileItem> list;
	//
	//			public FileAdapter(Context context, ArrayList<FileItem> list) {
	//				this.context = getActivity();
	//				this.list = list;
	//			}
	//
	//			@Override
	//			public int getCount() {
	//				return list.size();
	//			}
	//
	//			@Override
	//			public Object getItem(int position) {
	//				return list.get(position);
	//			}
	//
	//			@Override
	//			public long getItemId(int position) {
	//				return position;
	//			}
	//
	//			@Override
	//			public View getView(int position, View convertView, ViewGroup parent) {
	//				Holder holder;
	//				View view = convertView;
	//
	//				if (view == null) {
	//					view = LayoutInflater.from(context).inflate(
	//							R.layout.file_view_item, parent, false);
	//					//						R.layout.directory_view_item, parent, false);
	//					holder = new Holder();
	//					holder.name = (TextView) view.findViewById(R.id.title);
	//					holder.check=(CheckBox) view.findViewById(R.id.browser_checkbox);
	//					//holder.check.setVisibility(View.VISIBLE);
	//					view.setTag(holder);
	//				} else {
	//					holder = (Holder) view.getTag();
	//				}
	//
	//				final FileItem item = list.get(position);
	//				//全选 xj 1103
	//				if(checked==true){
	//					Log.i("ssssss", "显示前");
	//					holder.check.setVisibility(View.VISIBLE);
	//					Log.i("ssssss", "显示后");
	//					holder.check.setChecked(true);
	//					Log.i("ssssss", "选中");
	//				}
	//				//全选 xj 1103
	//
	//				holder.name.setText(item.fileName);
	//				//隐藏 xj 1103
	//				if(hide==true&&holder.check.isChecked()){
	//					//view.setVisibility(View.GONE);
	//					//lv.removeViewAt(view.getId());
	//					lv.removeViewAt(position);
	//				}
	//				//			else{
	//				//				view.setVisibility(View.VISIBLE);	
	//				//			}
	//				//隐藏 xj 1103
	//				return view;
	//			}
	//
	//			private class Holder {
	//				TextView name;
	//				CheckBox check;
	//			}
	//
	//		}

	class FileItem {
		public boolean isDirectory;
		public String filePath;
		public String fileName;
		public String filesonPath;
		public String filepraPath;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		Log.i("debug", "1111111111111111111111");
		FileItem fileItem = (FileItem) parent.getItemAtPosition(position);
		if (fileItem.isDirectory) {
			//new Thread(this).start();
			//xj 1104  添加启动跳转线程
			jumpPath = fileItem.filePath;
			//1105 xj
			//jumpPath = fileItem.filepraPath;
			//jumpPath1=fileItem.filesonPath;
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Log.i("BUG", "线程启动");
					fileItems = getFileList(jumpPath);
					//Looper.prepare();
					mHandler.sendEmptyMessage(0);
				}
			}).start();
			//xj 1104  添加启动跳转线程
		}
		// 适配器
	}


}
