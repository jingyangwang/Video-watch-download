/**
 * 
 */
package com.shipin.player.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.shipin.player.gui.Frgadpter.ViewHolder;
import com.shipin.player.gui.dialogs.SleepTimerDialog;
import com.shipin.player.gui.video.VideoPlayerActivity;
import com.shipin.player.util.SharePreUtil;
import com.shipin.player.widget.FlingViewGroup;
import com.videoplayer.bd.R;

/**
 * @author wjy
 * 
 */
public class Filefragment_test extends Fragment implements Runnable,OnItemClickListener {

	public final static String KEY_GROUP = "key_group";
	protected String mGroup;
	
	private ListView lv;
	private Context mContext = getActivity();
	Frgadpter adapter;
	private ArrayList<FileItem> fileItems;
	private ArrayList<FileItem> savefileItems;
	private ArrayList<FileItem> save;
	private String jumpPath = "";
	private String rootPath = null;
	//判断全选
	boolean checked =false;
	private static int ly1_count=0;
	//判断隐藏
	boolean  hide=false;
	boolean file_add=false;
	//判断删除
	boolean dele=false;
	
	String filename="";
	List<String> vedio;
	
	//jj 编辑功能shared保存
	SharedPreferences editFunction;
	private LinearLayout ly;
	private String show;
	
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
		
		if (savedInstanceState != null)
            setGroup(savedInstanceState.getString(KEY_GROUP));
		
	    //jj 编辑功能
		boolean edit=false;
		SharePreUtil.setedit(getActivity(), edit);
		ly =(LinearLayout)view.findViewById(R.id.linear_ly);
		editFunction = getActivity().getSharedPreferences("jmydata",getActivity().MODE_WORLD_WRITEABLE);
		show = editFunction.getString("show", "0");
		if(show.equals("0")){
			edit = false;
			SharePreUtil.setedit(getActivity(), edit);
			ly.setVisibility(View.GONE);
		}else{
			edit = true;
			SharePreUtil.setedit(getActivity(), edit);
			ly.setVisibility(View.VISIBLE);
		}
			
		//修改布局文件，添加批处理按钮     xj   1103
		LinearLayout linear1=(LinearLayout)view.findViewById(R.id.linear_ly1);
     	LinearLayout linear2=(LinearLayout)view.findViewById(R.id.linear_ly2);
		LinearLayout linear3=(LinearLayout)view.findViewById(R.id.linear_ly3);
		linear1.setOnClickListener(listener);
		linear2.setOnClickListener(listener);
		linear3.setOnClickListener(listener);
		//修改布局文件，添加批处理按钮     xj   1103
		new Thread(this).start();
		//adpter=new frgadpter(mContext, fileItems);
		return view;
	}
	private void setGroup(String string) {
		mGroup = string;
	}
	//修改监听事件   xj 1103
	OnClickListener listener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
		
			case R.id.linear_ly1:
				// 遍历list的长度，将已选的设为未选，未选的设为已选
				if(ly1_count%2==0){
					for(int i = 0; i < fileItems.size(); i++) {
						Frgadpter.getIsSelected().put(i, true);
					}
				}
				else{
					for(int i = 0; i < fileItems.size(); i++) {
						Frgadpter.getIsSelected().put(i, false);
					}
				}
				
				ly1_count++;
				//dataChanged();
				 mHandler.sendEmptyMessage(1);
				break;
			case R.id.linear_ly2:
				//Log.i("uuu", "《《《《《《《《《《《《《《《《删除开始");
				int key_count=0;
		        Set set1 = Frgadpter.getIsSelected().entrySet();
		        Iterator it1 = set1.iterator();
		        while (it1.hasNext()) {
		        	//Log.i("uuu", "进入循环"+j+"次");
		            Entry entry1 = (Entry) it1.next();
		            Integer key1 = (Integer) entry1.getKey();
		            Boolean value1 = (Boolean) entry1.getValue();
		            System.out.println(key1 + "=" + value1);
		            if(value1==true){
		            	int mm=key1-key_count;
						FileItem fileitema= fileItems.get(mm);
						String str= fileitema.getFilesonPath();
						deleteFile(str);
						fileItems.remove(mm);
						key_count++;
						Toast.makeText(getActivity(), "删除成功", 1).show();
					}
		        }
		        mHandler.sendEmptyMessage(0);
				
				break;
			case R.id.linear_ly3:
				//Log.i("uuu", "《《《《《《《《《《《《《《《《隐藏开始");
				int key_count_hide=0;
		        Set set = Frgadpter.getIsSelected().entrySet();
		        Iterator it = set.iterator();
		        while (it.hasNext()) {
		        	//Log.i("uuu", "进入循环"+j+"次");
		            Entry entry = (Entry) it.next();
		            Integer key = (Integer) entry.getKey();
		            Boolean value = (Boolean) entry.getValue();
		            if(value==true){
		            	int m=key-key_count_hide;
		            	fileItems.remove(m);
		            	key_count_hide++;
					}
		        }
			    mHandler.sendEmptyMessage(0);
				break;
			default:
				break;
			}
		}
	};
	
	// 刷新listview和TextView的显示
		private void dataChanged() {
			// 通知listView刷新
			adapter.notifyDataSetChanged();
			// TextView显示最新的选中数目
		};
	//修改监听事件   xj 1103
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

	//显示文件视频列表  1110 xj -start
	public ArrayList<FileItem> getVideo(String jumpPath) {
	
		File rootFile = new File(jumpPath);
		File[] files = rootFile.listFiles();

		if (files != null) {
			for (File file : files) {
				//1110 xj改  显示视频格式文件在listview中
				if(file.isFile()){
					filename=file.getName();
					Log.i("fileg", "判断前：：：：："+filename.endsWith("mkv")+""+filename.endsWith(".mkv"));
					if(filename.endsWith(".mkv")||filename.endsWith(".rmvb")||filename.endsWith(".mov")||filename.endsWith(".avi")||filename.endsWith(".3gp")||filename.endsWith(".mpg")||filename.endsWith(".vob")||filename.endsWith(".wmv")||filename.endsWith(".mp4")){
						FileItem fileItem = new FileItem();
						File parent = file.getParentFile();//取得上层目录
						fileItem.setDirectory(file.isDirectory());//判断上层文件名是否是目录，并设置文件目录属性中
						fileItem.setFileName(file.getName());//取得文件名
						fileItem.setFilepraPath(parent.getAbsolutePath());//取得上层文件的绝对目录
						fileItem.setFilesonPath(file.getAbsolutePath());//获取自己的item绝对路径
						fileItem.setFilePath(file.getParent());//返回父目录的路径名
						fileItem.setFolder_count(0);
						fileItems.add(fileItem);
						Log.i("fileg", file.getParent()+"执行结束《《《《《《"+parent.getAbsolutePath());
					}
				}else{
					//判断如果文件类型是目录的情况，递归操作遍历文件
					Log.i("fileg", "》》》》》》执行非文件开始《《《《《《《《");
					String str = file.getAbsolutePath();
					getVideoList(str);
					Log.i("fileg", "..................执行非文件结束..................");
					//getVideoList(jumpPath);
				}
			}
		}	
		return fileItems;
	}
	//1110 xj-end	


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

	/*
	 * 更改数据刷新
	 */
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				adapter = new Frgadpter(getActivity(), fileItems);
				lv.setAdapter(adapter);
				break;
				
			case 1:
				adapter.notifyDataSetChanged();
				lv.setAdapter(adapter);
				break;
			default:
				break;
			}
		}
	};


	@Override
	public void run() {
		//fileItems = getFileList(jumpPath);
		//隐藏文件处理
		fileItems = new ArrayList<FileItem>();
		getVideo(jumpPath);
		mHandler.sendEmptyMessage(0);
	}
	
	/*
	 * 获取根目录下所有文件夹
	 */
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


	/*
	 * 判断取出带视频的文件夹   xj 1104
	 */
	public ArrayList<FileItem> getVideoList(String jumpPath) {
		//vedio=new ArrayList<String>();

		File rootFile = new File(jumpPath);
		File[] files = rootFile.listFiles();
		if (files != null) {
			int folder_count=0;
			for (File file : files) {
				//1110 xj改  显示视频格式文件在listview中
				if(file.isFile()){
					filename = file.getName();
					if(filename.endsWith(".mkv")||filename.endsWith(".rmvb")||filename.endsWith(".mov")||filename.endsWith(".avi")||filename.endsWith(".3gp")||filename.endsWith(".mpg")||filename.endsWith(".vob")||filename.endsWith(".wmv")||filename.endsWith(".mp4")){
						FileItem fileItem = new FileItem();
						folder_count++;//记录文件数量
						File parent = file.getParentFile();//取得上层文件
						fileItem.setDirectory(parent.isDirectory());//判断上层文件名是否是目录，并设置文件目录属性中
						fileItem.setFileName(parent.getName());//取得上层文件名
						fileItem.setFilepraPath(parent.getAbsolutePath());//取得上层文件的绝对目录
						fileItem.setFilesonPath(file.getAbsolutePath());//获取自己的item绝对路径
						fileItem.setFilePath(parent.getParent());//返回父目录的路径名
						String str_fileItem = fileItem.getFilePath();//将父目录的名字 赋值给一个字符串
						String str_filItem = fileItem.getFilepraPath();//将父目录的名字 赋值给一个字符串
						fileItem.setFolder_count(folder_count);
						//判断路径是否已经改变
						if(fileItems.size()>0){
							for (int i = 0; i <fileItems.size(); i++) {
								String str_fileItems = fileItems.get(i).getFilepraPath();
								if(str_filItem.equals(str_fileItems)){
									file_add = false;
									fileItems.remove(i);
									fileItems.add(fileItem);
									break;
								}else{
									file_add = true;
								}
							}
						}
						if(file_add||fileItems.size()==0){
							fileItems.add(fileItem);
							file_add = false;
		
						}
					}
				}else{
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

	//1107 xj 再次检测
	public ArrayList<FileItem> getFileList1(String jumpPath) {
		ArrayList<FileItem> fileItems = new ArrayList<FileItem>();
		File rootFile = new File(jumpPath);
		if(rootFile==null){
			Toast.makeText(getActivity(), "下层没有文件了哦", 1).show();
			return null;
		}else{
			//File rootFile = new File(jumpPath);
			File[] files = rootFile.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						FileItem fileItem = new FileItem();
						fileItem.setFileName(file.getName()); 
						fileItem.setFilePath(file.getAbsolutePath());
						File parent = file.getParentFile(); 
						fileItem.setDirectory(parent.isDirectory());
						//fileItem.isDirectory = file.isDirectory();
						fileItems.add(fileItem);
					}else{
						filename=file.getName();
						if(filename.endsWith(".mkv")||filename.endsWith(".rmvb")||filename.endsWith(".mov")||filename.endsWith(".avi")||filename.endsWith(".3gp")||filename.endsWith(".mpg")||filename.endsWith(".vob")||filename.endsWith(".wmv")||filename.endsWith(".mp4")){
							Log.i("fileg", "判断后。。。。。。。。。。。。。。。。。。。。。。。。。。");
							filename=file.getName();
							Log.i("fileg", "判断前");
							Log.i("fileg", filename.endsWith("mkv")+""+filename.endsWith(".mkv"));
							if(filename.endsWith(".mkv")||filename.endsWith(".rmvb")||filename.endsWith(".mov")||filename.endsWith(".avi")||filename.endsWith(".3gp")||filename.endsWith(".mpg")||filename.endsWith(".vob")||filename.endsWith(".wmv")||filename.endsWith(".mp4")){
								Log.i("fileg", "判断后。。。。。。。。。。。。。。。。。。。。。。。。。。");
								FileItem fileItem = new FileItem();
								//File parent=file.getParentFile();//取得上层文件
								fileItem.setDirectory(file.isDirectory());//判断上层文件名是否是目录，并设置文件目录属性中
								fileItem.setFileName(file.getName());//取得上层文件名
								fileItem.setFilepraPath(file.getAbsolutePath());//取得上层文件的绝对目录
								fileItem.setFilesonPath(file.getAbsolutePath());//获取自己的item绝对路径
								fileItem.setFilePath(file.getParent());//返回父目录的路径名
								fileItems.add(fileItem);
								Log.i("fileg", "执行结束《《《《《《《《《《《《《《《《《《《《《《《《《");
							}
						}
					}
				}
			}

		}
		return fileItems;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		show = editFunction.getString("show", "0");
		if(show.equals("1")){
			FileItem fileItem = (FileItem) parent.getItemAtPosition(position);
			// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
			ViewHolder holder = (ViewHolder) arg1.getTag();
			// 改变CheckBox的状态
			holder.check.toggle();
			// 将CheckBox的选中状况记录下来
			adapter.getIsSelected().put(position, holder.check.isChecked());
		}else{
			Log.i("debug", "1111111111111111111111");
			//将查询集合保存
			if(save==null){
				save = new ArrayList<FileItem>();
				save = fileItems;
			}
			if(savefileItems==null){
				savefileItems = new ArrayList<FileItem>();
				savefileItems = save;
			}
			FileItem fileItem = (FileItem) parent.getItemAtPosition(position);
			if (fileItem.isDirectory) {
				
				//1110 xj——未改 -start
				jumpPath = fileItem.getFilepraPath();
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.i("BUG", "线程启动");
						//fileItems = getFileList(jumpPath);
						fileItems = getFileList1(jumpPath);
						try {//添加睡眠延迟
							new Thread().sleep(350);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//Looper.prepare();
						mHandler.sendEmptyMessage(0);
					}
				}).start();
//				//xj 1104  添加启动跳转线程
//				//1110 xj——未改-end
				
			}else{
				//Log.i("kkkk", "线程启动播放");
				VideoPlayerActivity.start(getActivity(), Uri.fromFile(new File(fileItem.getFilesonPath())),new File(fileItem.getFilesonPath()).getName());
			}
		}
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString(KEY_GROUP, mGroup);
	}
	
	/*
	 * linearlayout组件get,set方法
	 */
	public LinearLayout getLy() {
		return ly;
	}
	public void setLy(LinearLayout ly) {
		this.ly = ly;
	}
	public ListView getLv() {
		return lv;
	}
	public void setLv(ListView lv) {
		this.lv = lv;
	}
	public ArrayList<FileItem> getFileItems() {
		return fileItems;
	}
	public void setFileItems(ArrayList<FileItem> fileItems) {
		this.fileItems = fileItems;
	}
	public String getJumpPath() {
		return jumpPath;
	}
	public void setJumpPath(String jumpPath) {
		this.jumpPath = jumpPath;
	}
	public ArrayList<FileItem> getSavefileItems() {
		return savefileItems;
	}
	public void setSavefileItems(ArrayList<FileItem> savefileItems) {
		this.savefileItems = savefileItems;
	}
	
}
