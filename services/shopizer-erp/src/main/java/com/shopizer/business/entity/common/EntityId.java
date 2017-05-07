package com.shopizer.business.entity.common;

import org.springframework.data.annotation.Id;

public class EntityId {
	
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
