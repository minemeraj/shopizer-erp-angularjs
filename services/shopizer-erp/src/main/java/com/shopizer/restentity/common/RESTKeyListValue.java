package com.shopizer.restentity.common;

import java.util.List;



public class RESTKeyListValue {
	
	private String key;

	private List<RESTKeyValue> values;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public List<RESTKeyValue> getValues() {
		return values;
	}
	public void setValues(List<RESTKeyValue> values) {
		this.values = values;
	}


}
