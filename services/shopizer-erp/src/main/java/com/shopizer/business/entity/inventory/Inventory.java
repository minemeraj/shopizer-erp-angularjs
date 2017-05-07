package com.shopizer.business.entity.inventory;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shopizer.business.entity.store.Store;

@Document
public class Inventory {
	
	@Id
	private String id;
	
	@DBRef(db="store")
	private Store store;
	
	@DBRef(db="product")
	private Product product;
	
	@DBRef(db="productVariant")
	private List<ProductVariant> vars;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<ProductVariant> getVars() {
		return vars;
	}

	public void setVars(List<ProductVariant> vars) {
		this.vars = vars;
	}

}
