package com.shopizer.business.entity.order;

import com.shopizer.business.entity.common.EntityId;

public class OrderId extends EntityId {
	
	private long nextOrderNumber;
	
	private String identifier;

	public long getNextOrderNumber() {
		return nextOrderNumber;
	}

	public void setNextOrderNumber(long nextOrderNumber) {
		this.nextOrderNumber = nextOrderNumber;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
