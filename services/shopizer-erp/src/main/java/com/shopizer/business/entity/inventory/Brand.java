package com.shopizer.business.entity.inventory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shopizer.business.entity.common.Description;
import com.shopizer.business.entity.common.Entity;
import com.shopizer.business.entity.store.Store;

@Document
public class Brand extends Entity {
	

	
	private List<Description> descriptions = new ArrayList<Description>();

	public List<Description> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}

	


}
