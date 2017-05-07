package com.shopizer.business.entity.inventory;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shopizer.business.entity.common.Currency;

@Document
public class Price {
	

	
	private Date lastUpdated;
	
	private BigDecimal price;
	
	private BigDecimal discountedPrice;
	
	private Date specialEndDate;
	
	private boolean defaultPrice;

	@DBRef(db="currency")
	private Currency currency;


	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public Date getSpecialEndDate() {
		return specialEndDate;
	}

	public void setSpecialEndDate(Date specialEndDate) {
		this.specialEndDate = specialEndDate;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public boolean isDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(boolean defaultPrice) {
		this.defaultPrice = defaultPrice;
	}



}
