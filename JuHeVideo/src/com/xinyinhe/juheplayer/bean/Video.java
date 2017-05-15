package com.xinyinhe.juheplayer.bean;

public class Video {
	private String title;//视频名字
	private Owner owner;//视频提供者
	private Thumbs thumbs;//视频缩略图
	private int duration;//视频长度（单位秒）
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public Thumbs getThumbs() {
		return thumbs;
	}
	public void setThumbs(Thumbs thumbs) {
		this.thumbs = thumbs;
	}
	
}
