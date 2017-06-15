package com.shopizer.restentity.order;

import java.util.List;

import com.shopizer.restentity.common.RESTEntity;
import com.shopizer.restentity.customer.RESTCustomer;


public class RESTOrder extends RESTEntity {

	private String orderNumber;
	
	private String estimated;
	private String deliveryEstimated;
	
	private String modified;
	
	private String created;
	
	private String creator;
	
	private String lastUpdator;
	
	private int order;
	
	private RESTCustomer customer;
	
	private List<RESTOrderTotal> orderTotals;
	
	private List<RESTOrderComment> comments;
	
	private List<RESTOrderStatusHistory> orderStatusHistory;
	
	private String total;
	
	private String description;
	
	private String status;
	
	private String channel;

	public String getEstimated() {
		return estimated;
	}

	public void setEstimated(String estimated) {
		this.estimated = estimated;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public RESTCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(RESTCustomer customer) {
		this.customer = customer;
	}

	public List<RESTOrderTotal> getOrderTotals() {
		return orderTotals;
	}

	public void setOrderTotals(List<RESTOrderTotal> orderTotals) {
		this.orderTotals = orderTotals;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public List<RESTOrderComment> getComments() {
		return comments;
	}

	public void setComments(List<RESTOrderComment> comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLastUpdator() {
		return lastUpdator;
	}

	public void setLastUpdator(String lastUpdator) {
		this.lastUpdator = lastUpdator;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<RESTOrderStatusHistory> getOrderStatusHistory() {
		return orderStatusHistory;
	}

	public void setOrderStatusHistory(List<RESTOrderStatusHistory> orderStatusHistory) {
		this.orderStatusHistory = orderStatusHistory;
	}

	public String getDeliveryEstimated() {
		return deliveryEstimated;
	}

	public void setDeliveryEstimated(String deliveryEstimated) {
		this.deliveryEstimated = deliveryEstimated;
	}

}
