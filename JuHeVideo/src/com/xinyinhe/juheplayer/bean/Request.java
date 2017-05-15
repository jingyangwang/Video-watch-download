package com.xinyinhe.juheplayer.bean;

public class Request {
	private Files files;

	public Files getFiles() {
		return files;
	}

	public void setFiles(Files files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "Request [files=" + files + "]";
	}
	
}
