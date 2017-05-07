package com.shopizer.restentity.store;

import com.shopizer.business.entity.common.Address;
import com.shopizer.restentity.common.RESTCodeEntity;

public class RESTStore extends RESTCodeEntity {
	

	private String name;
	private String[] location;
	private Address address;
	private String currency;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getLocation() {
		return location;
	}
	public void setLocation(String[] location) {
		this.location = location;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
