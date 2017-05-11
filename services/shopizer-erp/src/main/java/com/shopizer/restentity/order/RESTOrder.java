package com.shopizer.restentity.order;

import java.util.List;

import com.shopizer.restentity.common.RESTEntity;
import com.shopizer.restentity.customer.RESTCustomer;


public class RESTOrder extends RESTEntity {

	private Long number;
	
	private String estimated;
	
	private String created;
	
	private int order;
	
	private RESTCustomer customer;
	
	private List<RESTOrderTotal> orderTotals;
	
	private List<RESTOrderComment> comments;
	
	private String total;
	
	private String description;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

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

}
