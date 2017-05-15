package com.xyh.video.activity;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.videoplayer.bd.R;
import com.xyh.video.base.BaseFragmentActivity;
import com.xyh.video.download.DownloadUtils;
import com.xyh.video.download.providers.DownloadManager;
import com.xyh.video.download.providers.DownloadManager.Request;
import com.xyh.video.utils.RLog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import main.youtube.Video;
import main.youtube.YouTubeUtility;


public class GwebActivity extends BaseFragmentActivity implements OnClickListener {
	private WebChromeClient.CustomViewCallback customViewCallback;
	private FrameLayout customViewContainer;
	private View mCustomView;
	private myWebChromeClient mWebChromeClient;
	private myWebViewClient mWebViewClient;
	private WebView webView;
	private String TAG = "GwebActivity";
	private ImageView download_imageview;
	private String title;
	public void hideCustomView() {
		this.mWebChromeClient.onHideCustomView();
	}

	public boolean inCustomView() {
		return this.mCustomView != null;
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);

		
		setContentView(R.layout.player_web);
		banner_left = (TextView) findViewById(R.id.banner_left);
		banner_left.setOnClickListener(this);
		// 友盟推送
//		PushAgent.getInstance(getApplicationContext()).onAppStart();

//		String data = getIntent().getStringExtra("url");
//		String data="https://www.youtube.com/watch?v=zv26xcdp_4s";
		String data="https://www.youtube.com";
//		title = getIntent().getStringExtra("title");
		title="aaaaa";
		Pattern places = Pattern.compile("src=\"(.*?)\"");
		Matcher matcher = places.matcher(data);
		String url = null;
		if (matcher.find()) {
			url = matcher.group(1);
		} else {
			url = data;
		}
		url = url.replace("nsfwyoutube", "youtube");
		RLog.i("ffa", "url: " + url);
		download_imageview = (ImageView) findViewById(R.id.download_imageview);
		download_imageview.setOnClickListener(this);
		this.webView = ((WebView) findViewById(R.id.webView));
		this.mWebViewClient = new myWebViewClient();
		this.webView.setWebViewClient(this.mWebViewClient);
		this.mWebChromeClient = new myWebChromeClient();
		this.webView.setWebChromeClient(this.mWebChromeClient);
		this.webView.getSettings().setJavaScriptEnabled(true);
		this.webView.getSettings().setAppCacheEnabled(true);
		this.webView.getSettings().setBuiltInZoomControls(true);
		this.webView.getSettings().setSaveFormData(true);
		this.webView.loadUrl(url);

		// Banner广告
//		new AdViewLayout(this, Utils.AD_GENRE_BANNER);
//		// 全屏
//		new AdViewLayout(this, Utils.AD_GENRE_SCREEN);

	}

	public boolean onKeyDown(int code, KeyEvent event) {
		if (code == 4) {
			if (inCustomView()) {
				hideCustomView();
				return true;
			}
			if ((this.mCustomView == null) && (this.webView.canGoBack())) {
				this.webView.goBack();
				return true;
			}
		}
		return super.onKeyDown(code, event);
	}

	protected void onPause() {
		super.onPause();
		this.webView.onPause();
	}

	protected void onResume() {
		super.onResume();
		this.webView.onResume();
	}

	protected void onStop() {
		super.onStop();
		if (inCustomView())
			hideCustomView();
	}

	class myWebChromeClient extends WebChromeClient {
		private View mVideoProgressView;

		myWebChromeClient() {
		}

		@SuppressLint("InflateParams")
		public View getVideoLoadingProgressView() {
			if (this.mVideoProgressView == null)
				this.mVideoProgressView = LayoutInflater.from(GwebActivity.this).inflate(R.layout.video_progress, null);
			return this.mVideoProgressView;
		}

		public void onHideCustomView() {
			super.onHideCustomView();
			if (GwebActivity.this.mCustomView == null) {
				return;
			}
			GwebActivity.this.webView.setVisibility(0);
			GwebActivity.this.customViewContainer.setVisibility(8);
			GwebActivity.this.mCustomView.setVisibility(8);
			GwebActivity.this.customViewContainer.removeView(GwebActivity.this.mCustomView);
			GwebActivity.this.customViewCallback.onCustomViewHidden();
			GwebActivity.this.mCustomView = null;
		}

		public void onShowCustomView(View paramView, int paramInt, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
			onShowCustomView(paramView, paramCustomViewCallback);
		}

		public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
			if (GwebActivity.this.mCustomView != null) {
				paramCustomViewCallback.onCustomViewHidden();
				return;
			}
			GwebActivity.this.mCustomView = paramView;
			GwebActivity.this.webView.setVisibility(8);
			GwebActivity.this.customViewContainer.setVisibility(0);
			GwebActivity.this.customViewContainer.addView(paramView);
			GwebActivity.this.customViewCallback = paramCustomViewCallback;
		}
	}

	class myWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			RLog.i("shouldOverrideUrlLoading: ", url);
			view.loadUrl(url);
			showDownloadIcon(false);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			RLog.i("onPageFinished: ", url);
			if (url.contains("youtube.com")) {
				showDownloadIcon(true);
			} else {
				showDownloadIcon(false);
			}
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			showDownloadIcon(false);
		}

	}

	private void showDownloadIcon(boolean show) {
		if (show) {
			download_imageview.setVisibility(View.VISIBLE);
		} else {
			download_imageview.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.download_imageview:
			String id = getYoutubeVideoIdFrom(webView.getUrl());
//			String id = getYoutubeVideoIdFrom("https://www.youtube.com/watch?v=7gcCRAl58u4");
//			7gcCRAl58u4
			Log.i("ffa", "webView"+webView.getUrl());
			
			Log.i("ffa", "id"+id);
			if (!TextUtils.isEmpty(id)) {
				Log.i("ffa", ""+id);
				new ParseYoutubeVideo(GwebActivity.this, id).execute();
//				Toast.makeText(GwebActivity.this, "下载启动中", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.banner_left:
			finish();
			break;
		default:
			break;
		}
		
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
			if (videos != null && videos.size() > 0) {
				showVideoDialog(videos);
			}

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
				String fileName = title + video.type ;
				fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-_]+", "_") + "." +  extension;
				Log.i("AAAA", "fileName: " + fileName);
				Uri srcUri = Uri.parse(video.url);
				DownloadManager.Request request = new Request(srcUri);
				Log.i("AAAA", "video.url: " + video.url);
				Log.i("AAAA", "srcUri: " + srcUri);
				Log.i("AAAA", "request: " + request);
				request.setDestinationInExternalPublicDir(DownloadUtils.getDownloadPath(GwebActivity.this), "");
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
