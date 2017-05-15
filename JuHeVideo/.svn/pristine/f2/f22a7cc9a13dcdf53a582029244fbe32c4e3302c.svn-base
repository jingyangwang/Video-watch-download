/**
 * 
 */
package com.xinyinhe.juheplayer.mensetting;

import java.util.ArrayList;
import java.util.List;

import com.videoplayer.bd.R;
import com.xinyinhe.juheplayer.FileItem;
import com.xinyinhe.juheplayer.History_adapter;



import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;


/**
 * @author wjy
 *
 */
public class MenSettingHistory extends ActionBarActivity {
	private ListView history_listview;
	private ListView earlier_listview;
	private List<FileItem> list;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		history_listview=(ListView)findViewById(R.id.today_listview);
		earlier_listview=(ListView) findViewById(R.id.earlier_listview);
		list=new ArrayList<FileItem>();
		FileItem fileitem=new FileItem();
		fileitem.setFrom("youku");
		fileitem.setName("tv");
		fileitem.setSize("50m");
		fileitem.setTime("1m45s");
		FileItem fileitem1=new FileItem();
		fileitem1.setFrom("youku");
		fileitem1.setName("tv");
		fileitem1.setSize("50m");
		fileitem1.setTime("1m45s");
		list.add(fileitem);
		list.add(fileitem1);
		History_adapter adpter=new History_adapter(MenSettingHistory.this,list);
		history_listview.setAdapter(adpter);
		earlier_listview.setAdapter(adpter);
		// 为所有列表项注册上下文菜单
		MenSettingHistory.this.registerForContextMenu(history_listview);
		MenSettingHistory.this.registerForContextMenu(earlier_listview);
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		
		   AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
		   FileItem fileitem=list.get(info.position);
		   if(fileitem==null||fileitem instanceof FileItem){
			   return;
		   }
	        MenuInflater inflater = MenSettingHistory.this.getMenuInflater();
	        inflater.inflate(R.menu.video_list, menu);
    	  //  setContextMenuItems(menu, fileitem);
		   // set context menu title
	 
	    // add context menu item
//	    menu.add(0, 1, Menu.NONE, "share");
//	    menu.add(0, 2, Menu.NONE, "like");
	}
// private void setContextMenuItems(Mean menu, Fileitem item) {
//	// TODO Auto-generated method stub
//
//}
	
//	 @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//	    public void onContextPopupMenu(View anchor, final int position) {
////	        if (!AndroidUtil.isHoneycombOrLater()) {
////	            // Call the "classic" context menu
////	            anchor.performLongClick();
////	            return;
////	        }
//
//	        PopupMenu popupMenu = new PopupMenu(MenSettingHistory.this, anchor);
//	        popupMenu.getMenuInflater().inflate(R.menu.video_list, popupMenu.getMenu());
//	        FileItem fileItem = list.get(position);
//	        if (fileItem == null)
//	            return;
//	     //   setContextMenuItems(popupMenu.getMenu(), fileItem);
//	        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//	            @Override
//	            public boolean onMenuItemClick(MenuItem item) {
//	                return handleContextItemSelected(item, position);
//	            }
//	        });
//	        popupMenu.show();
//	    }
//	  private void setContextMenuItems(Menu menu, FileItem mediaWrapper) {
//	        boolean hasInfo = false;
//	        if (media.getMeta(Media.Meta.Title) != null)
//	            hasInfo = true;
//	        media.release();
//	        menu.findItem(R.id.video_list_info).setVisible(hasInfo);
//	        menu.findItem(R.id.video_list_delete).setVisible(!AndroidUtil.isLolliPopOrLater() ||
//	                mediaWrapper.getLocation().startsWith("file://" + AndroidDevices.EXTERNAL_PUBLIC_DIRECTORY));
//	    }
	  
//	  @Override
//	    public boolean onContextItemSelected(MenuItem menu) {
//	        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menu.getMenuInfo();
//	        if (info != null && handleContextItemSelected(menu, info.position))
//	            return true;
//	        return super.onContextItemSelected(menu);
//	    }
//	  private boolean handleContextItemSelected(MenuItem menu, int position) {
//	        if (position >= list.size())
//	            return false;
//	        FileItem media = list.get(position);
//	        if (media == null)
//	            return false;
//	        switch (menu.getItemId()){
//	         
//	            case R.id.share:
//	                return true;
//	            case R.id.like:
//	            	return true;
//	        }
//	        return false;
//	    }
//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		// TODO Auto-generated method stub
//		
//		 AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
//		 switch (item.getItemId()) {
//		case 1:
//			
//			break;
//
//		default:
//			super.onContextItemSelected(item);
//			break;
//		}
//		 return true;
//	}
	  
	  
	  
	  
	  
	  
	  
			private void showPopupWindow(View view,Context context) {
				// 一个自定义的布局，作为显示的内容
				//		View contentView = LayoutInflater.from(context).inflate(
				//				R.layout.alert_dialog, null);
				//popwindow 控制监听的横条列表 xj 1126
				View contentView = LayoutInflater.from(context).inflate(
						R.layout.history_1, null);
				//popwindow 控制监听的横条列表 xj 1126
				//		GridView pop_windows=(GridView)contentView.findViewById(R.id.gridview_popwindow);
				//		POP_list=new ArrayList<String>();
				//		for (int i = 0; i < 10; i++) {
				//			POP_list.add("a"+i);
				//		}
				//		Secher_adpter adpter=new Secher_adpter(context, POP_list);
				//		pop_windows.setAdapter(adpter);
				final PopupWindow popupWindow = new PopupWindow(contentView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
				popupWindow.setTouchable(true);
//				popupWindow.setTouchInterceptor(new OnTouchListener() {
//					@Override
//					public boolean onTouch(View v, MotionEvent event) {
//						Log.i("mengdd", "onTouch : ");
//						return false;
//						// 这里如果返回true的话，touch事件将被拦截
//						// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//					}
//				});
				popupWindow.setTouchInterceptor(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						return false;
					}
				});
				// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
				// 我觉得这里是API的一个bug
				popupWindow.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.picture));
				// 设置好参数之后再show
				popupWindow.showAsDropDown(view);
			}
}
