package com.xinyinhe.juheplayer.bean;

import java.io.Serializable;
import java.util.List;

public class MyDatas implements Serializable{
	private List<Data> datas;

	public List<Data> getDatas() {
		return datas;
	}

	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "MyDatas [datas=" + datas + "]";
	}
	
	
}
