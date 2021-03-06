package com.xinyinhe.juheplayer.bean;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable{
	private int id;
	private String mfrom;
	private String imgbase;
	private String lenght;
	private List<Loaddata> loadurl;
	private String title;
	private String url;
	private String views;
	private String time;
	private String type;
	public Data(int id, String mfrom, String imgbase, String lenght,
			List<Loaddata> loadurl, String title, String url, String views,
			String time, String type) {
		super();
		this.id = id;
		this.mfrom = mfrom;
		this.imgbase = imgbase;
		this.lenght = lenght;
		this.loadurl = loadurl;
		this.title = title;
		this.url = url;
		this.views = views;
		this.time = time;
		this.type = type;
	}
	public Data(String mfrom, String imgbase, String lenght,
			List<Loaddata> loadurl, String title, String url, String views,
			String time, String type) {
		super();
		this.mfrom = mfrom;
		this.imgbase = imgbase;
		this.lenght = lenght;
		this.loadurl = loadurl;
		this.title = title;
		this.url = url;
		this.views = views;
		this.time = time;
		this.type = type;
	}
	public Data() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMfrom() {
		return mfrom;
	}
	public void setMfrom(String mfrom) {
		this.mfrom = mfrom;
	}
	public String getImgbase() {
		return imgbase;
	}
	public void setImgbase(String imgbase) {
		this.imgbase = imgbase;
	}
	public String getLenght() {
		return lenght;
	}
	public void setLenght(String lenght) {
		this.lenght = lenght;
	}
	public List<Loaddata> getLoadurl() {
		return loadurl;
	}
	public void setLoadurl(List<Loaddata> loadurl) {
		this.loadurl = loadurl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Data [id=" + id + ", mfrom=" + mfrom + ", imgbase=" + imgbase
				+ ", lenght=" + lenght + ", loadurl=" + loadurl + ", title="
				+ title + ", url=" + url + ", views=" + views + ", time="
				+ time + ", type=" + type + "]";
	}
	
	
}
