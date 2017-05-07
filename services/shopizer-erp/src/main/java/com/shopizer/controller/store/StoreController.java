package com.shopizer.controller.store;

import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.shopizer.business.entity.store.Store;
import com.shopizer.business.repository.store.StoreRepository;
import com.shopizer.restentity.store.RESTStore;
import com.shopizer.restpopulators.store.StorePopulator;

@RestController
public class StoreController {
	
	@Inject 
	private StorePopulator storePopulator;
	
	@Inject
	private StoreRepository storeRepository;
	
	
	/**
	 * Creates a store
	 * @return
	 */
	@PostMapping(value = "/api/store")
	@ResponseBody
	public ResponseEntity<Void> postStore(@Valid @RequestBody RESTStore store, Locale locale, UriComponentsBuilder ucBuilder) throws Exception  {
	    
		
		Store entity = null;

		entity = storePopulator.populateModel(store, locale);
		storeRepository.save(entity);


		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(ucBuilder.path("/store/{code}").buildAndExpand(entity.getCode()).toUri());
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}
	
	/**
	 * Get a store
	 * @return
	 */
	@RequestMapping(value = "/api/store/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<RESTStore> getStore(@PathVariable String code, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {
	    
		
		Store entity = storeRepository.findByCode(code);
		RESTStore store = null;

		store = storePopulator.populateWeb(entity, locale);

	    HttpHeaders headers = new HttpHeaders();
	    return new ResponseEntity<RESTStore>(store, headers, HttpStatus.OK);

	}

}
