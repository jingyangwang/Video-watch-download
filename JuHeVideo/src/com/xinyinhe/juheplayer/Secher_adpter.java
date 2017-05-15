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
public class Secher_adpter extends BaseAdapter {
private List<String> list;
private Context context;
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
public Secher_adpter(Context context,List<String> list) {
	// TODO Auto-generated constructor stub
this.context=context;
this.list=list;
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
		Viewhold holder;
		if(convertView==null){
			holder = new Viewhold();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.gridview_item_serch, null);
			//holder.mImage=(ImageView)convertView.findViewById(R.id.img_list_item);
			holder.tv1=(TextView)convertView.findViewById(R.id.tv);
			
			convertView.setTag(holder);
		}else{
			holder=(Viewhold)convertView.getTag();
		}
		holder.tv1.setText("   "+list.get(position)+"    ");
		//iconBitmap = getPropThumnail(mIconIDs[position]);
		//holder.mImage.setImageBitmap(iconBitmap);
		return convertView;
		
	}
	
	class Viewhold {
		TextView tv1;
	}

}
