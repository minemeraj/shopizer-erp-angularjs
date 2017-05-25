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
	
	private List<OrderComment> comments;
	
	private List<OrderStatusHistory> statusHistory;
	
	private BigDecimal total;
	
	private String description;
	
	private OrderStatusEnum status;
	
	private OrderChannelEnum channel;
	
	private String creator;
	
	private String lastUpdator;

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

	public List<OrderComment> getComments() {
		return comments;
	}

	public void setComments(List<OrderComment> comments) {
		this.comments = comments;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	public OrderChannelEnum getChannel() {
		return channel;
	}

	public void setChannel(OrderChannelEnum channel) {
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

	public List<OrderStatusHistory> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<OrderStatusHistory> statusHistory) {
		this.statusHistory = statusHistory;
	}


}
