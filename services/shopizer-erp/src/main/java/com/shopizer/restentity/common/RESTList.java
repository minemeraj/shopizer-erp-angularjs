package com.shopizer.restentity.common;

import java.util.List;

public class RESTList<T> {
	
	public RESTList(List<T> content, int totalCount) {
		this.setContent(content);
		this.setTotalCount(totalCount);
	}
	
	private List<T> content;
	private int totalCount;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}



}
