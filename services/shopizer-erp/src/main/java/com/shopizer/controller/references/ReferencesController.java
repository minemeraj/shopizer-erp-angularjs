package com.shopizer.controller.references;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.shopizer.business.entity.order.OrderStatusEnum;
import com.shopizer.business.entity.order.OrderTotalTypeEnum;
import com.shopizer.business.entity.order.OrderTotalVariationEnum;
import com.shopizer.business.entity.references.Country;
import com.shopizer.business.entity.references.Zone;
import com.shopizer.business.repository.references.CountryRepository;
import com.shopizer.business.repository.references.ZoneRepository;
import com.shopizer.constants.Constants;
import com.shopizer.restentity.references.RESTCountry;
import com.shopizer.restentity.references.RESTOrderReferences;
import com.shopizer.restentity.references.RESTOrderStatus;
import com.shopizer.restentity.references.RESTOrderTotalType;
import com.shopizer.restentity.references.RESTOrderTotalVariation;
import com.shopizer.restentity.references.RESTZone;
import com.shopizer.restpopulators.references.CountryPopulator;
import com.shopizer.restpopulators.references.ZonePopulator;

@RestController
public class ReferencesController {
	
	
	@Inject
	private CountryRepository countryRepository;
	
	@Inject
	private CountryPopulator countryPopulator;
	
	@Inject
	private ZonePopulator zonePopulator;
	
	@Inject
	private ZoneRepository zoneRepository;
	
	
	
	@GetMapping(value = "/api/countries")
	@ResponseBody
	public ResponseEntity<List<RESTCountry>> getCountries(@RequestParam(value = "lang", required=false) String lang, Locale locale, UriComponentsBuilder ucBuilder) throws Exception  {
	    
		
		
		if(StringUtils.isBlank(lang)) {
			lang = Constants.DEFAULT_LANGUAGE;
		}
		
		List<Country> countries = countryRepository.findAll();
		
		Locale l = new Locale(lang);
		
		List<RESTCountry> countryList = new ArrayList<RESTCountry>();
		
		for(Country country : countries) {
			RESTCountry c =countryPopulator.populateWeb(country, l);
			countryList.add(c);
		}


		
	    HttpHeaders headers = new HttpHeaders();
	    return new ResponseEntity<List<RESTCountry>>(countryList, headers, HttpStatus.OK);

	}
	
	@GetMapping(value = "/api/country/{code}/zones")
	@ResponseBody
	public ResponseEntity<List<RESTZone>> getZones(@PathVariable String code, @RequestParam(value = "lang", required=false) String lang, Locale locale, UriComponentsBuilder ucBuilder) throws Exception  {
	    
		
		
		if(StringUtils.isBlank(lang)) {
			lang = Constants.DEFAULT_LANGUAGE;
		}
		
		List<Zone> zones = zoneRepository.findByCountryCode(code);
		
		Locale l = new Locale(lang);
		
		List<RESTZone> zoneList = new ArrayList<RESTZone>();
		
		if(!CollectionUtils.isEmpty(zones)) {
		
			for(Zone zone : zones) {
				RESTZone z = zonePopulator.populateWeb(zone, l);
				zoneList.add(z);
			}
		
		}


		
	    HttpHeaders headers = new HttpHeaders();
	    return new ResponseEntity<List<RESTZone>>(zoneList, headers, HttpStatus.OK);

	}
	
	@GetMapping(value = "/api/references/order")
	@ResponseBody
	public ResponseEntity<RESTOrderReferences> getOrderReferences(@RequestParam(value = "lang", required=false) String lang, Locale locale, UriComponentsBuilder ucBuilder) throws Exception  {
	    
		if(StringUtils.isBlank(lang)) {
			lang = Constants.DEFAULT_LANGUAGE;
		}
		
		Locale l = new Locale(lang);

		
		
		RESTOrderReferences references = new RESTOrderReferences();
		
		OrderStatusEnum[] statusKeys = OrderStatusEnum.values();
		List<RESTOrderStatus> statusList = new ArrayList<RESTOrderStatus>();
		for(int i = 0; i < statusKeys.length; i++) {
			OrderStatusEnum key = statusKeys[i];
			RESTOrderStatus orderStatus = new RESTOrderStatus();
			orderStatus.setKey(key.name());
			//TODO get from bundle
			orderStatus.setValue(key.name());
			statusList.add(orderStatus);
		}
		references.setStatus(statusList);
		
		
		OrderTotalTypeEnum[] totalKeys = OrderTotalTypeEnum.values();
		List<RESTOrderTotalType> totalList = new ArrayList<RESTOrderTotalType>();
		for(int i = 0; i < totalKeys.length; i++) {
			OrderTotalTypeEnum key = totalKeys[i];
			RESTOrderTotalType orderTotalType = new RESTOrderTotalType();
			orderTotalType.setKey(key.name());
			//TODO get from bundle
			orderTotalType.setValue(key.name());
			totalList.add(orderTotalType);
		}
		references.setTypes(totalList);
		
		
		OrderTotalVariationEnum[] variationKeys = OrderTotalVariationEnum.values();
		List<RESTOrderTotalVariation> variationList = new ArrayList<RESTOrderTotalVariation>();
		for(int i = 0; i < variationKeys.length; i++) {
			OrderTotalVariationEnum key = variationKeys[i];
			RESTOrderTotalVariation orderTotalVariationType = new RESTOrderTotalVariation();
			orderTotalVariationType.setKey(key.name());
			//TODO get from bundle
			orderTotalVariationType.setValue(key.name());
			variationList.add(orderTotalVariationType);
		}
		references.setVariations(variationList);


		
	    HttpHeaders headers = new HttpHeaders();
	    return new ResponseEntity<RESTOrderReferences>(references, headers, HttpStatus.OK);

	}

}
