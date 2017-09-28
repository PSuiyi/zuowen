package com.znz.compass.znzlibray.bean;

public class DistrictBean {
	private String name;
	private String zipcode;
	
	public DistrictBean() {
		super();
	}

	public DistrictBean(String name, String zipcode) {
		super();
		this.name = name;
		this.zipcode = zipcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "DistrictBean [name=" + name + ", zipcode=" + zipcode + "]";
	}

}
