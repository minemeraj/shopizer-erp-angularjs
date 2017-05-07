package com.shopizer.business.entity.references;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.shopizer.business.entity.common.Description;
import com.shopizer.business.entity.common.Entity;

@Document
public class Country extends Entity {
	

	List<Description> descriptions = new ArrayList<Description>();
	private List<Zone> zones;
	
	public Country(String code) {
		setCode(code);
	}

	public List<Zone> getZones() {
		return zones;
	}
	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	public List<Description> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}
	
	

}
