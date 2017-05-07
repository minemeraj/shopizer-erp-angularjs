package com.shopizer.business.entity.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shopizer.business.entity.common.Entity;
import com.shopizer.business.entity.customer.Customer;

@Document
public class Order extends Entity {
	
	private Long number;
	
	private Date estimated;
	
	@DBRef(db="customer")
	private Customer customer;
	
	private List<OrderTotal> orderTotals;
	
	private BigDecimal total;
	
	private String description;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Date getEstimated() {
		return estimated;
	}

	public void setEstimated(Date estimated) {
		this.estimated = estimated;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderTotal> getOrderTotals() {
		return orderTotals;
	}

	public void setOrderTotals(List<OrderTotal> orderTotals) {
		this.orderTotals = orderTotals;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
