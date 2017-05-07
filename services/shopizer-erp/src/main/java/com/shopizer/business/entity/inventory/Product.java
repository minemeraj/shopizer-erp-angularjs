package com.shopizer.business.entity.inventory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shopizer.business.entity.common.Description;
import com.shopizer.business.entity.common.Entity;
import com.shopizer.business.repository.common.annotation.CascadeSave;


/**
 * https://docs.mongodb.com/ecosystem/use-cases/product-catalog/
 * https://www.mongodb.com/blog/post/retail-reference-architecture-part-1-building-flexible-searchable-low-latency-product
 * @author c.samson
 *
 */
@Document
public class Product extends Entity {
	
	
	@DBRef(db="brand")
	@CascadeSave
	private Brand brand;
	
	private List<Description> descriptions = new ArrayList<Description>();
	
	@DBRef(db="category")
	@CascadeSave
	private List<Category> categories = new ArrayList<Category>();

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<Description> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
