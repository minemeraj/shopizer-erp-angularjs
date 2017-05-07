package com.shopizer.restentity.inventory;

import java.util.List;

import com.shopizer.restentity.common.RESTEntity;
import com.shopizer.restentity.store.RESTStore;

public class RESTInventory extends RESTEntity {
	
	
	private RESTStore store;
	private List<RESTProductVariant> vars;
	private RESTProduct product;
	public RESTStore getStore() {
		return store;
	}
	public void setStore(RESTStore store) {
		this.store = store;
	}
	public List<RESTProductVariant> getVars() {
		return vars;
	}
	public void setVars(List<RESTProductVariant> vars) {
		this.vars = vars;
	}
	public RESTProduct getProduct() {
		return product;
	}
	public void setProduct(RESTProduct product) {
		this.product = product;
	}

}
