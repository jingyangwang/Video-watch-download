/**
 * 
 */
package com.xinyinhe.juheplayer;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.videoplayer.bd.R;
import com.xinyinhe.juheplayer.Gridadpter.ViewHolder;
import com.xinyinhe.juheplayer.bean.Data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author wjy
 *
 */
public class Fragment_list_adpter extends BaseAdapter {
	private List<Data> list;
	private Context context;
	private ViewHolder holder;
	private HashMap<Integer, RelativeLayout> hmap = null;
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	public Fragment_list_adpter(Context context,List<Data> list) {
		// TODO Auto-generated constructor stub
		this.list=list;
		this.context=context;
		hmap = new HashMap<Integer, RelativeLayout>();
	}
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		holder = null;
		if(convertView==null){
			holder = new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.movies_list_item, null);
			holder.bimg = (RelativeLayout) convertView.findViewById(R.id.movies_list_relativeLayout);
			holder.img = (ImageView)convertView.findViewById(R.id.movies_list_img);
			holder.from = (TextView)convertView.findViewById(R.id.movies_list_from);
			holder.size = (TextView) convertView.findViewById(R.id.movies_list_size);
			holder.time = (TextView)convertView.findViewById(R.id.movies_list_time);
			holder.name = (TextView) convertView.findViewById(R.id.movies_list_name);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		
		final Data da = list.get(position);
		holder.from.setText(da.getMfrom());
		holder.size.setText(da.getViews());
		holder.time.setText(da.getLenght());
		holder.name.setText(da.getTitle());
		
		BitmapUtils bu = new BitmapUtils(context);
		bu.display(holder.img, da.getImgbase());
//		URL url;
//		try {
//			url = new URL(list.get(position).getImgbase());
//			Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
//			Drawable background = new BitmapDrawable(bitmap);
//			holder.img.setBackgroundDrawable(background);
//			holder.img.setImageBitmap(bitmap);
//		} catch (Exception e) {
//			Log.i("dove", "背景图片设置异常");
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		URL url;
//		try {
//			Log.i("dove", "进入背景图片设置");
//			url = new URL(list.get(position).getImgbase());
//			URLConnection conn = url.openConnection();
//            conn.connect();
//            InputStream in = conn.getInputStream();
//            Log.i("dove", "背景图片网络获取成功");
//            Bitmap map = BitmapFactory.decodeStream(in);
//            Drawable background = new BitmapDrawable(map);
//			holder.bimg.setBackground(background);
//		}catch (Exception e) {
//			Log.i("dove", "背景图片设置异常");
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Log.i("dove", "bbbbbbbb");
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				hmap.put(position, holder.bimg);
//				Message msg = new Message();
//				msg.what = position;
//				URL url;
////				Log.i("dove", "aaaaaaaaaa");
//				try {
//					url = new URL(da.getImgbase());
//					URLConnection conn = url.openConnection();
//	                conn.connect();
//	                InputStream in = conn.getInputStream();
//	                Bitmap map = BitmapFactory.decodeStream(in);
//	                msg.obj = map;
////	                Log.i("dove", "000000000");
//	                handler.sendMessage(msg);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
////					Log.i("dove", "111111111");
//					e.printStackTrace();
//				}
//			}
//		}).start();
		return convertView;
		
		//return null;
	}
	class ViewHolder{
		RelativeLayout bimg;
		ImageView img;
		TextView from,name,size,time;
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Bitmap bm = (Bitmap) msg.obj;
			Drawable background = new BitmapDrawable(bm);
//			holder.img.setBackgroundDrawable(background);
			hmap.get(msg.what).setBackground(background);
//			holder.img.setImageBitmap(bm);
		}
	};

}