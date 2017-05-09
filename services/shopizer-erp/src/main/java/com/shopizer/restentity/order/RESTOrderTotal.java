package com.shopizer.restentity.order;

import com.shopizer.restentity.common.RESTEntity;

public class RESTOrderTotal extends RESTEntity {
	
	private String value;
	private String name;
	private int order;
	private String type;
	private String variation;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVariation() {
		return variation;
	}
	public void setVariation(String variation) {
		this.variation = variation;
	}

}
