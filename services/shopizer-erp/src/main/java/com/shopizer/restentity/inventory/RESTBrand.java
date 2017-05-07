package com.shopizer.restentity.inventory;

import java.util.List;

import com.shopizer.business.entity.common.Description;
import com.shopizer.restentity.common.RESTCodeEntity;

public class RESTBrand extends RESTCodeEntity {
	

	private List<Description> descriptions;

	public List<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}
	

}
