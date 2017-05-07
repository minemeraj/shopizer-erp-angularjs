package com.shopizer.business.entity.inventory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shopizer.business.entity.common.Entity;

@Document
public class ProductVariant extends Entity {
	
	@DBRef(db="product")
	private Product product;

	private double width;
	private double height;
	private double length;
	private String color;
	private List<String> images;
	private boolean defaultVariant;
	
	private List<Price> prices = new ArrayList<Price>();
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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
	public boolean isDefaultVariant() {
		return defaultVariant;
	}
	public void setDefaultVariant(boolean defaultVariant) {
		this.defaultVariant = defaultVariant;
	}
	public List<Price> getPrices() {
		return prices;
	}
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

}
