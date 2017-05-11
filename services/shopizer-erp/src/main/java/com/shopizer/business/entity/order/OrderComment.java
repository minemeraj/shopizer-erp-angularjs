package com.shopizer.business.entity.order;

import java.util.Date;

public class OrderComment {
	
	private Date created;
	private String comment;
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
