package com.videoplayer.bd;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.youtube.Video;
import main.youtube.YouTubeUtility;

import com.shipin.player.gui.video.VideoPlayerActivity;
import com.xyh.video.activity.GwebActivity;
import com.xyh.video.download.DownloadUtils;
import com.xyh.video.download.providers.DownloadManager;
import com.xyh.video.download.providers.DownloadManager.Request;
import com.xyh.video.utils.RLog;

import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Youtube_url extends ActionBarActivity {

	private boolean isplayer=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_youtube_url);

		Button btn=(Button) findViewById(R.id.button1);
		btn.setOnClickListener(listener);
		Button btn1=(Button) findViewById(R.id.button2);
		btn1.setOnClickListener(listener);





	}
	OnClickListener listener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String id = getYoutubeVideoIdFrom("https://www.youtube.com/watch?v=7gcCRAl58u4");
			switch (v.getId()) {
			case R.id.button1:

				String data="https://www.youtube.com";
				////		title = getIntent().getStringExtra("title");
				//		String title="aaaaa";
				//		Pattern places = Pattern.compile("src=\"(.*?)\"");
				//		Matcher matcher = places.matcher(data);
				//		String url = null;
				//		if (matcher.find()) {
				//			url = matcher.group(1);
				//		} else {
				//			url = data;
				//		}
				//		url = url.replace("nsfwyoutube", "youtube");
				//		RLog.i("ffa", "url: " + url);
//				String id = getYoutubeVideoIdFrom("https://www.youtube.com/watch?v=7gcCRAl58u4");
				//		String id = getYoutubeVideoIdFrom("https://www.youtube.com/watch?v=7gcCRAl58u4");
				//		7gcCRAl58u4
				//		Log.i("ffa", "webView"+webView.getUrl());

				Log.i("ffa", "id"+id);
				if (!TextUtils.isEmpty(id)) {
					Log.i("ffa", ""+id);
					new ParseYoutubeVideo(Youtube_url.this, id).execute();
					//			Toast.makeText(GwebActivity.this, "下载启动中", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.button2:
//				Video video =
//				break;
//				String id = getYoutubeVideoIdFrom("https://www.youtube.com/watch?v=7gcCRAl58u4");
				//		String id = getYoutubeVideoIdFrom("https://www.youtube.com/watch?v=7gcCRAl58u4");
				//		7gcCRAl58u4
				//		Log.i("ffa", "webView"+webView.getUrl());
				isplayer=true;
				Log.i("qqq", "id"+id);
				if (!TextUtils.isEmpty(id)) {
					Log.i("qqq", ""+id);
					new ParseYoutubeVideo(Youtube_url.this, id).execute();}
//				isplayer=false;
				
				
				
			default:
				break;
			}





		}
	};





	public String getYoutubeVideoIdFrom(String url) {
		String videoId;
		if (url != null) {
			if (url.startsWith("http") && url.contains("youtube.com") && url.contains("watch?")) {
				try {
					String[] x = url.split("v=");
					String y = x[1];
					x = y.split("&");
					y = x[0];
					videoId = y;
					if (videoId != null)
						//					
						return videoId;
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.youtube_url, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}




	class ParseYoutubeVideo extends AsyncTask<String, String, String> {

		private String youtubeWebPageSource;
		private ProgressDialog loading;
		private ArrayList<Video> videos;
		private Context mContext;
		private String videoId;

		public ParseYoutubeVideo(Context mContext, String videoId) {
			this.mContext = mContext;
			this.videoId = videoId;
		}

		@Override
		protected void onPreExecute() {
			loading = new ProgressDialog(mContext);
			loading.setMessage("loading");
			videos = new ArrayList<Video>();
			loading.show();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				youtubeWebPageSource = YouTubeUtility.getInfoString(videoId);
				Log.i("ffa","youtubeWebPageSource的值"+youtubeWebPageSource);
				Log.i("ffa","videos的值"+videos);
				videos = YouTubeUtility.func_get_video_script1(youtubeWebPageSource);
				Log.i("ffa","videos大小的值"+videos.size());
				Log.i("ffa","videos的值"+videos);

				//				if (youtubeWebPageSource != null && youtubeWebPageSource.length() > -1) {
				//					videos = YouTubeUtility.func_get_video_script1(youtubeWebPageSource);
				//					Log.i("ffa","videos的值"+videos);
				//				}
			} catch (Exception error) {
				error.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String streamingUrl) {
			Log.i("ffa", "videos="+videos);
			Log.i("ffa", "videos.size()"+videos.size());
			//			showVideoDialog(videos);
//			if (!isplayer&&videos != null && videos.size() > 0) {
			if (videos != null && videos.size() > 0) {
				if(!isplayer){
					showVideoDialog(videos);
				}
				else{
					Video video = videos.get(0);
					
					Uri srcUri = Uri.parse(video.url);
					Log.i("qqq", ""+srcUri);
					VideoPlayerActivity.start(mContext, srcUri);
					isplayer=false;
				}
				
			}
//			else{
//				Video video = videos.get(0);
//				
//				Uri srcUri = Uri.parse(video.url);
//				Log.i("qqq", ""+srcUri);
//				VideoPlayerActivity.start(mContext, srcUri);
//			}

			if (loading != null) {
				loading.dismiss();
			}
		}
	}

	private AlertDialog alertDialog = null;
	private TextView banner_left;

	public void showVideoDialog(ArrayList<Video> videos) {
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_youtube_video_quality_layout, null);
		ListView listView = (ListView) view.findViewById(R.id.quality_list);
		YoutubeVideoAdapter youtubeVideoAdapter = new YoutubeVideoAdapter(this, videos);
		listView.setAdapter(youtubeVideoAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Video video = (Video) parent.getItemAtPosition(position);
				String extension = video.type.substring(video.type.indexOf("|") + 1, video.type.length()).trim();
				String title="下载的文件列表";
				String fileName = title + video.type ;
				fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-_]+", "_") + "." +  extension;
				Log.i("AAAA", "fileName: " + fileName);
				Uri srcUri = Uri.parse(video.url);
//				  VideoPlayerActivity.start(Youtube_url.this, srcUri);
				DownloadManager.Request request = new Request(srcUri);
				Log.i("AAAA", "video.url: " + video.url);
				Log.i("AAAA", "srcUri: " + srcUri);
				Log.i("AAAA", "request: " + request);
				request.setDestinationInExternalPublicDir(DownloadUtils.getDownloadPath(Youtube_url.this), "");
				request.setMimeType("video/*");
				request.setDescription(fileName);
				request.setTitle(fileName);
				DownloadManager mDownloadManager = new DownloadManager(getContentResolver(), getPackageName());
				mDownloadManager.enqueue(request);
				if (alertDialog != null) {
					alertDialog.dismiss();
				}
			}
		});

		Builder builder = new Builder(this);
		builder.setView(view);
		alertDialog = builder.create();
		alertDialog.show();

	}

	public static String getMimeType(String suffix) {
		if (suffix == null) {
			return "file/*";
		}
		String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
		if (type != null && !type.isEmpty()) {
			return type;
		}
		return "file/*";
	}



	class YoutubeVideoAdapter extends BaseAdapter {

		ArrayList<Video> dataList;
		LayoutInflater inflater;
		Context context;

		public YoutubeVideoAdapter(Context context, ArrayList<Video> list) {
			this.context = context;
			inflater = LayoutInflater.from(context);
			dataList = list;
		}

		public Video getVideoByIndex(int position) {
			return dataList.get(position);
		}

		public void notifyData() {
			notifyDataSetChanged();
		}

		public int getCount() {
			return dataList.size();
		}

		public Object getItem(int position) {
			return dataList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		public View getView(int position, View view, ViewGroup viewGroup) {
			ViewHolder viewHolder;
			if (view == null) {
				view = this.inflater.inflate(R.layout.youtube_quality_list_row_layout, null);
				viewHolder = new ViewHolder();
				viewHolder.format = (ImageView) view.findViewById(R.id.format);
				viewHolder.quality = (TextView) view.findViewById(R.id.title);
				viewHolder.format.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_video_catalog_blue));
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.quality.setText(dataList.get(position).type);
			return view;
		}

		class ViewHolder {
			public TextView quality;
			public ImageView format;
		}
	}


}
