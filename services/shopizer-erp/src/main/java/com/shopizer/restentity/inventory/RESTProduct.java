package com.shopizer.restentity.inventory;

import java.util.ArrayList;
import java.util.List;

import com.shopizer.business.entity.common.Description;
import com.shopizer.restentity.common.RESTCodeEntity;

public class RESTProduct extends RESTCodeEntity {
	
	private List<RESTCategory> categories = new ArrayList<RESTCategory>();//belonging categories
	private RESTBrand brand;
	private List<Description> descriptions = new ArrayList<Description>();
	private List<RESTProductVariant> variants;
	public List<RESTCategory> getCategories() {
		return categories;
	}
	public void setCategories(List<RESTCategory> categories) {
		this.categories = categories;
	}
	public RESTBrand getBrand() {
		return brand;
	}
	public void setBrand(RESTBrand brand) {
		this.brand = brand;
	}
	public List<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}
	public List<RESTProductVariant> getVariants() {
		return variants;
	}
	public void setVariants(List<RESTProductVariant> variants) {
		this.variants = variants;
	}

}
