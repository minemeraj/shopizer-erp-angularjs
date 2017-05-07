package com.shopizer.business.entity.common;

import javax.validation.constraints.NotNull;

public class Description {
	
	@NotNull
	private String lang;
	private String name;
	private String description;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
