package com.shopizer.restentity.inventory;

import java.util.ArrayList;
import java.util.List;

import com.shopizer.business.entity.common.Description;
import com.shopizer.restentity.common.RESTCodeEntity;

public class RESTCategory extends RESTCodeEntity {
	

	private List<Description> descriptions;
	private RESTCategory parent;
	private List<RESTCategory> children = new ArrayList<RESTCategory>();
	private int order;
	private String path;



	public RESTCategory getParent() {
		return parent;
	}

	public void setParent(RESTCategory parent) {
		this.parent = parent;
	}

	public List<RESTCategory> getChildren() {
		return children;
	}

	public void setChildren(List<RESTCategory> children) {
		this.children = children;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}


	public List<Description> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


}
