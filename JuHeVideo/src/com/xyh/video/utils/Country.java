package com.xyh.video.utils;



public class Country {
	//id
	private Integer id;
	//mcc
	private Integer mcc;
	//country
	private String country;
	//language
	private String language;
	//name
	private String name;
	//ename
	private String ename;
	//区域
	private String remark;
	public Country(Integer id, Integer mcc, String country, String language,
			String name, String ename, String remark) {
		super();
		this.id = id;
		this.mcc = mcc;
		this.country = country;
		this.language = language;
		this.name = name;
		this.ename = ename;
		this.remark = remark;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMcc() {
		return mcc;
	}
	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
