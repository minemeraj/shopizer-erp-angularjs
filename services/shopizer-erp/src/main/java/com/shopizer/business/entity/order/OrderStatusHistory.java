package com.shopizer.business.entity.order;

import java.util.Date;

public class OrderStatusHistory {
	
	private Date created;
	private String user;
	private OrderStatusEnum status;
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public OrderStatusEnum getStatus() {
		return status;
	}
	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

}
