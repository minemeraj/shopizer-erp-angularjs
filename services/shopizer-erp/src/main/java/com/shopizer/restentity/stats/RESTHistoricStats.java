package com.shopizer.restentity.stats;

import java.util.List;

import com.shopizer.restentity.common.RESTKeyListValue;

public class RESTHistoricStats extends Stats {
	
	private List<String> xAxis;
	private List<RESTKeyListValue> yAxis;
	public List<String> getxAxis() {
		return xAxis;
	}
	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}
	public List<RESTKeyListValue> getyAxis() {
		return yAxis;
	}
	public void setyAxis(List<RESTKeyListValue> yAxis) {
		this.yAxis = yAxis;
	}

}
