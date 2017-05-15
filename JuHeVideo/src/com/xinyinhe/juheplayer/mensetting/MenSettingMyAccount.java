/**
 * 
 */
package com.xinyinhe.juheplayer.mensetting;

import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.videoplayer.bd.R;
import com.xinyinhe.juheplayer.bean.DBTable;
import com.xinyinhe.juheplayer.bean.Data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author wjy
 *
 */
public class MenSettingMyAccount extends ActionBarActivity {
	
	private List<Data> listHistroy;
	private Context mContext;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaccount);
		mContext = this;
		DbUtils db = DbUtils.create(this,"myhistroy");
		try {
			listHistroy = db.findAll(Data.class);
//			List<DBTable> list = db.findAll(DBTable.class);
//			Log.i("dove", "数据库lsit::::"+listHistroy.toString());
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(listHistroy!=null){
			if(listHistroy.size()>0){
				Log.i("dove", "listHistroy.size()="+listHistroy.size());
				initHistroy();
			}
		}
		RelativeLayout history=(RelativeLayout) findViewById(R.id.myaccount_history); 
		RelativeLayout link=(RelativeLayout) findViewById(R.id.myaccount_link); 
		history.setOnClickListener(listener);
		link.setOnClickListener(listener);
	}
	
	private void initHistroy() {
		BitmapUtils bu = new BitmapUtils(mContext);
		
		ImageView img1 = (ImageView) findViewById(R.id.history_image1);
		bu.display(img1, listHistroy.get(listHistroy.size()-1).getImgbase());
		TextView tv1 = (TextView) findViewById(R.id.history_text1);
		tv1.setText(listHistroy.get(listHistroy.size()-1).getTitle());
		
		ImageView img2 = (ImageView) findViewById(R.id.history_image2);
		bu.display(img2, listHistroy.get(listHistroy.size()-2).getImgbase());
		TextView tv2 = (TextView) findViewById(R.id.history_text2);
		tv2.setText(listHistroy.get(listHistroy.size()-2).getTitle());
		
		ImageView img3 = (ImageView) findViewById(R.id.history_image3);
		bu.display(img3, listHistroy.get(listHistroy.size()-3).getImgbase());
		TextView tv3 = (TextView) findViewById(R.id.history_text3);
		tv3.setText(listHistroy.get(listHistroy.size()-3).getTitle());
		
	}
	
	OnClickListener listener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.myaccount_history:
				Intent intent=new Intent(MenSettingMyAccount.this,MenSettingHistory.class);
				startActivity(intent);
				break;
			case R.id.myaccount_link:
				Intent intent1=new Intent(MenSettingMyAccount.this,MenSettingLink.class);
				startActivity(intent1);
				break;
			default:
				break;
			}

		}
	};
}
