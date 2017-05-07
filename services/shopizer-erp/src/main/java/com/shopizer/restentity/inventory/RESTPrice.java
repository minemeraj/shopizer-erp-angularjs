package com.shopizer.restentity.inventory;

import com.shopizer.restentity.common.RESTEntity;

public class RESTPrice extends RESTEntity {
	
	private String lastUpdated;
	private String price;
	private String discountedPrice;
	private String specialEndDate;
	private String currencyCode;
	private String sku;
	private boolean defaultPrice;

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(String discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getSpecialEndDate() {
		return specialEndDate;
	}

	public void setSpecialEndDate(String specialEndDate) {
		this.specialEndDate = specialEndDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public boolean isDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(boolean defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

}
