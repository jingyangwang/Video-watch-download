/**
 * 
 */
package com.xinyinhe.juheplayer;

import java.util.List;

import com.videoplayer.bd.R;
import com.xinyinhe.juheplayer.Fragment_adpter.ViewHolder;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author wjy
 *
 */
public class History_adapter extends BaseAdapter {
private List<FileItem> list;
private Context context;
public History_adapter(Context context,List<FileItem> list) {
	// TODO Auto-generated constructor stub
	this.context=context;
	this.list=list;
}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Viewhold holder;
		if(convertView==null){
			holder = new Viewhold();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_video, null);
			holder.image1=(ImageView) convertView.findViewById(R.id.ml_item_thumbnail);
			holder.ml_item_title=(TextView) convertView.findViewById(R.id.ml_item_title);
			holder.item_more=(ImageView) convertView.findViewById(R.id.item_more);
			holder.ml_item_time=(TextView) convertView.findViewById(R.id.ml_item_time);
			holder.ml_item_resolution=(TextView) convertView.findViewById(R.id.ml_item_resolution);
			convertView.setTag(holder);
		}else{
			holder=(Viewhold)convertView.getTag();
		}
		//holder.tv1.setText("   "+list.get(position)+"    ");
		//iconBitmap = getPropThumnail(mIconIDs[position]);
		//holder.mImage.setImageBitmap(iconBitmap);
		//holder.image1.;
		holder.ml_item_title.setText(list.get(position).getName());
		holder.ml_item_time.setText(list.get(position).getTime());
		holder.ml_item_resolution.setText(list.get(position).getSize());
		holder.item_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopupWindow(v, context);
			}
		});
		return convertView;
		
	}
	

	
class Viewhold {
	TextView tv1,ml_item_title,ml_item_time,ml_item_resolution;
	ImageView image1,item_more;
}
OnClickListener poplistener=new OnClickListener() {

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linear1:
			Toast.makeText(context,"分享", 1).show();
			break;
		case R.id.linear2:
			Toast.makeText(context,"喜欢", 1).show();
			break;
		default:
			break;
		}
	}
};

private void showPopupWindow(View view,Context context) {
	// 一个自定义的布局，作为显示的内容
	//		View contentView = LayoutInflater.from(context).inflate(
	//				R.layout.alert_dialog, null);
	//popwindow 控制监听的横条列表 xj 1126
	View contentView = LayoutInflater.from(context).inflate(
			R.layout.history_1, null);
	LinearLayout linear1=(LinearLayout) contentView.findViewById(R.id.linear1);
	LinearLayout linear2=(LinearLayout) contentView.findViewById(R.id.linear2);
	linear1.setOnClickListener(poplistener);
	linear2.setOnClickListener(poplistener);

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
	//	popupWindow.setTouchInterceptor(new OnTouchListener() {
	//		@Override
	//		public boolean onTouch(View v, MotionEvent event) {
	//			Log.i("mengdd", "onTouch : ");
	//			return false;
	//			// 这里如果返回true的话，touch事件将被拦截
	//			// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
	//		}
	//	});
	popupWindow.setTouchInterceptor(new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			return false;
		}
	});
	// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
	// 我觉得这里是API的一个bug
	popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
			R.drawable.ic_menu_search));
	// 设置好参数之后再show
	popupWindow.showAsDropDown(view);
}
}
