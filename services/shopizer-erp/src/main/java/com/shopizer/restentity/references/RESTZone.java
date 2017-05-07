package com.shopizer.restentity.references;

import com.shopizer.restentity.common.RESTCodeEntity;

public class RESTZone extends RESTCodeEntity {
	
	private String lang;
	private String name;
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
