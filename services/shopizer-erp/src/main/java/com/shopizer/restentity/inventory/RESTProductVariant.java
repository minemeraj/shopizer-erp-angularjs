package com.shopizer.restentity.inventory;

import java.util.List;

import com.shopizer.restentity.common.RESTEntity;

public class RESTProductVariant extends RESTEntity {
	
	private String sku;
	private double width;
	private double height;
	private double length;
	private String color;
	private boolean defaultVariant;
	private List<String> images;
	private int order;
	
	private RESTPrice price;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public RESTPrice getPrice() {
		return price;
	}

	public void setPrice(RESTPrice price) {
		this.price = price;
	}

	public boolean isDefaultVariant() {
		return defaultVariant;
	}

	public void setDefaultVariant(boolean defaultVariant) {
		this.defaultVariant = defaultVariant;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
