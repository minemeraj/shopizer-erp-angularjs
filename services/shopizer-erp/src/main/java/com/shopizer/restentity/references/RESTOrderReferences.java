package com.shopizer.restentity.references;

import java.util.List;

public class RESTOrderReferences {
	
	private List<RESTOrderStatus> status;
	private List<RESTOrderTotalVariation> variations;
	private List<RESTOrderTotalType> types;
	public List<RESTOrderStatus> getStatus() {
		return status;
	}
	public void setStatus(List<RESTOrderStatus> status) {
		this.status = status;
	}
	public List<RESTOrderTotalVariation> getVariations() {
		return variations;
	}
	public void setVariations(List<RESTOrderTotalVariation> variations) {
		this.variations = variations;
	}
	public List<RESTOrderTotalType> getTypes() {
		return types;
	}
	public void setTypes(List<RESTOrderTotalType> types) {
		this.types = types;
	}

}
