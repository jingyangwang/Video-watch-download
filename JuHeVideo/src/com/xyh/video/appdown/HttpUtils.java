package com.xyh.video.appdown;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

public class HttpUtils {
	private final static int CONNECTION_TIMEOUT = 60 * 1000;

	public static DefaultHttpClient getThreadSafeClient() {
		DefaultHttpClient client = new DefaultHttpClient();
		ClientConnectionManager mgr = client.getConnectionManager();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECTION_TIMEOUT);
		HttpParams params = client.getParams();
		return new DefaultHttpClient(new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);
	}
	
	public static HttpURLConnection getHttpURLConnection(String url) throws MalformedURLException, IOException{
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setConnectTimeout(CONNECTION_TIMEOUT);
		return connection;
	}
}
