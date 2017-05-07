package com.shopizer.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopizer.business.entity.common.Description;
import com.shopizer.business.entity.references.Country;
import com.shopizer.business.entity.references.Zone;
import com.shopizer.business.repository.references.CountryRepository;

/**
 * Loads zones (states / provinces) in zone collection
 * @author c.samson
 *
 */
@Component
public class ZonesLoaderUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ZonesLoaderUtil.class);

	
	@Inject
	private CountryRepository countryRepository;
	
	@Value(value = "classpath:references/zoneconfig.json")
	private Resource zoneConfig;
	
	public Map<String, Zone> loadZones(List<String> languages) throws Exception {
		
		
		List<Country> countries = countryRepository.findAll();
		Map<String,Country> countriesMap = new HashMap<String,Country>();
		for(Country country : countries) {
			
			countriesMap.put(country.getCode(), country);
			
		}
		
		ObjectMapper mapper = new ObjectMapper();

              InputStream in =
            		  zoneConfig.getInputStream();
              
              if(in == null) {
            	  throw new Exception("File references/zoneconfig.json not found");
              }

              @SuppressWarnings("unchecked")
              Map<String,Object> data = mapper.readValue(in, Map.class);
              
              Map<String,Zone> zonesMap = new HashMap<String,Zone>();
              Map<String,List<Description>> zonesDescriptionsMap = new HashMap<String,List<Description>>();
              Map<String,String> zonesMark = new HashMap<String,String>();
              
              for(String l : languages) {
	              @SuppressWarnings("rawtypes")
	              List langList = (List)data.get(l);
	              if(langList!=null) {
		              for(Object z : langList) {
		                    @SuppressWarnings("unchecked")
							Map<String,String> e = (Map<String,String>)z;
		                    String zoneCode = e.get("zoneCode");
		                    Description zoneDescription = new Description();
		                    zoneDescription.setLang(l);
		                    zoneDescription.setName(e.get("zoneName"));
		                    Zone zone = null;
		                    List<Description> descriptions = null;
		                    if(!zonesMap.containsKey(zoneCode)) {
		                    	zone = new Zone();
		                    	Country country = countriesMap.get(e.get("countryCode"));
		                    	if(country==null) {
		                    		LOGGER.warn("Country is null for " + zoneCode + " and country code " + e.get("countryCode"));
		                    		continue;
		                    	}
			                    zone.setCountryCode(country.getCode());
		                    	zonesMap.put(zoneCode, zone);
		                    	zone.setCode(zoneCode);

		                    }
		                    
		                    
		                    if(zonesMark.containsKey(l+ "_" + zoneCode)) {
	                    		LOGGER.warn("This zone seems to be a duplicate !  " + zoneCode + " and language code " + l);
	                    		continue;
		                    }
		                    
		                    zonesMark.put(l + "_" + zoneCode, l + "_" + zoneCode);
		                    
		                    if(zonesDescriptionsMap.containsKey(zoneCode)) {
		                    	descriptions = zonesDescriptionsMap.get(zoneCode);
		                    } else {
		                    	descriptions = new ArrayList<Description>();
		                    	zonesDescriptionsMap.put(zoneCode, descriptions);
		                    }
		                    
		                    descriptions.add(zoneDescription);

		                }
		             }

              }
              
              
              for (Map.Entry<String, Zone> entry : zonesMap.entrySet()) {
          	    String key = entry.getKey();
          	    Zone value = entry.getValue();

          	    //get descriptions
          	    List<Description> descriptions = zonesDescriptionsMap.get(key);
          	    if(descriptions!=null) {
          	    	value.setDescriptions(descriptions);
          	    }
            }

              return zonesMap;
	

		
	
	
	
	}

}
