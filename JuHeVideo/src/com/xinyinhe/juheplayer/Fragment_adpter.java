/**
 * 
 */
package com.xinyinhe.juheplayer;

import java.util.List;

import com.videoplayer.bd.R;
import com.xinyinhe.juheplayer.Gridadpter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author wjy
 *
 */
public class Fragment_adpter extends BaseAdapter {
	private List<FileItem> list;
	private Context context;
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	public Fragment_adpter(Context context,List<FileItem> list) {
		// TODO Auto-generated constructor stub
		this.list=list;
		this.context=context;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.popular_item, null);
			//holder.mImage=(ImageView)convertView.findViewById(R.id.img_list_item);
			holder.from=(TextView)convertView.findViewById(R.id.from);
			holder.size=(TextView) convertView.findViewById(R.id.size);
			holder.time=(TextView)convertView.findViewById(R.id.time);
			holder.name=(TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		
		holder.from.setText("   "+list.get(position).getFrom()+"    ");
		holder.size.setText("   "+list.get(position).getSize()+"    ");
		holder.time.setText("   "+list.get(position).getTime()+"    ");
		holder.name.setText("   "+list.get(position).getName()+"    ");
		//iconBitmap = getPropThumnail(mIconIDs[position]);
		//holder.mImage.setImageBitmap(iconBitmap);

		return convertView;
		
		
		//return null;
	}
	class ViewHolder{
		TextView from,name,size,time;
	}

}
