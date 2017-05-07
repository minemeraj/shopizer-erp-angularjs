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

import com.shopizer.business.entity.references.Country;
import com.shopizer.business.entity.references.Zone;
import com.shopizer.business.repository.references.CountryRepository;
import com.shopizer.business.repository.references.ZoneRepository;
import com.shopizer.constants.Constants;
import com.shopizer.restentity.references.RESTCountry;
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

}
