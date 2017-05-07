package com.shopizer.controller.inventory;

import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.shopizer.business.entity.inventory.Brand;
import com.shopizer.business.repository.inventory.BrandRepository;
import com.shopizer.business.repository.store.StoreRepository;
import com.shopizer.restentity.inventory.RESTBrand;
import com.shopizer.restpopulators.inventory.BrandPopulator;

@RestController
public class BrandController {
	
	@Inject
	private BrandPopulator brandPopulator;

	
	@Inject
	private BrandRepository brandRepository;
	
	@PostMapping("/api/brand")
	public ResponseEntity<Void> createBrand(@Valid @RequestBody RESTBrand brand, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {
		
		Validate.notNull(brand, "Brand is required");


		Brand b = brandPopulator.populateModel(brand, locale);
			
		brandRepository.save(b);
			
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/brand/{code}").buildAndExpand(b.getCode()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

		
	}
	

	@PutMapping("/api/brand/{code}")
	public ResponseEntity<Void> updateBrand(@PathVariable String code, @Valid @RequestBody RESTBrand brand, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {
		
		Validate.notNull(brand, "Brand is required");


		Brand b = brandPopulator.populateModel(brand, locale);
			
		brandRepository.save(b);
			
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/brand/{code}").buildAndExpand(b.getCode()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);

		
	}
	
	@DeleteMapping("/api/brand/{code}")
	public ResponseEntity<Void> deleteBrand(@PathVariable String code, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {


		Brand b = brandRepository.findByCode(code);
			
		brandRepository.delete(b);
			
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/brand/{code}").buildAndExpand(b.getCode()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);

		
	}

	
	@RequestMapping(value = "/api/brand/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<RESTBrand> getBrand(@PathVariable String code, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {
	    

			Brand brand = brandRepository.findByCode(code);
			RESTBrand restBrand = brandPopulator.populateWeb(brand, locale);
			
		    HttpHeaders headers = new HttpHeaders();
		    return new ResponseEntity<RESTBrand>(restBrand, headers, HttpStatus.OK);

		


	}

}
