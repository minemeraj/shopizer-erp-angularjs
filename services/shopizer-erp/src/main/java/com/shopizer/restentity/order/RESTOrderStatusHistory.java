package com.shopizer.restentity.order;

import java.util.Date;

public class RESTOrderStatusHistory {
	
	private String created;
	private String user;
	private String status;
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
