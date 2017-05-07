package com.shopizer.business.entity.store;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shopizer.business.entity.common.Address;
import com.shopizer.business.entity.common.Currency;
import com.shopizer.business.entity.common.Entity;

/**
 * https://www.mongodb.com/blog/post/retail-reference-architecture-part-2-approaches-inventory-optimization
 * @author c.samson
 *
 */
@Document
public class Store extends Entity {
	

	private String name;
	@GeoSpatialIndexed(type=GeoSpatialIndexType.GEO_2DSPHERE)
	private double[] location;
	private Address address;
	@DBRef(db="currency")
	private Currency currency;
	
	
/*	@PersistenceConstructor
	Store(String name, double[] location) {
	    super();
	    this.name = name;
	    this.location = location;    
	}
	*/
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double[] getLocation() {
		return location;
	}
	public void setLocation(double[] location) {
		this.location = location;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
