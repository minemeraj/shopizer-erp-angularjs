package com.shopizer.business.entity.common;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;

public class Entity extends EntityId {

	
	@NotNull
	@Indexed(unique=true)
	private String code;
	
	private int order = 0;
	
	private Date modified;
	private Date created;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}


}
