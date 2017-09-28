package com.znz.compass.znzlibray.bean;

import java.util.List;

public class CityBean {
	private String name;
	private List<DistrictBean> districtList;
	
	public CityBean() {
		super();
	}

	public CityBean(String name, List<DistrictBean> districtList) {
		super();
		this.name = name;
		this.districtList = districtList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DistrictBean> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictBean> districtList) {
		this.districtList = districtList;
	}

	@Override
	public String toString() {
		return "CityBean [name=" + name + ", districtList=" + districtList
				+ "]";
	}
	
}
