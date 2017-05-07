package com.shopizer.restentity.common;

public abstract class RESTCodeEntity extends RESTEntity {
	
	private String code;
	private int order;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
