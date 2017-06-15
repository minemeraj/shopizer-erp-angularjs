package com.shopizer.restentity.common;

public class RESTValue<T> extends RESTEntity {
	
	private T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
