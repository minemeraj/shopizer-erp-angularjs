package com.shopizer.controller.inventory;

import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang3.Validate;
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

import com.shopizer.business.entity.inventory.Category;
import com.shopizer.business.repository.inventory.BrandRepository;
import com.shopizer.business.repository.inventory.CategoryRepository;
import com.shopizer.business.repository.store.StoreRepository;
import com.shopizer.restentity.inventory.RESTCategory;
import com.shopizer.restpopulators.inventory.BrandPopulator;
import com.shopizer.restpopulators.inventory.CategoryPopulator;
import com.shopizer.restpopulators.store.StorePopulator;

@RestController
public class CategoryController {
	
	@Inject
	private CategoryPopulator categoryPopulator;
	
	@Inject
	private CategoryRepository categoryRepository;
	

	
	@PostMapping("/api/category")
	public ResponseEntity<Void> createCategory(@Valid @RequestBody RESTCategory category, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {
		
		Validate.notNull(category, "Category is required");

		Category c = categoryPopulator.populateModel(category, locale);
			
		categoryRepository.save(c);
			
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/category/{code}").buildAndExpand(c.getCode()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

		

		
	}
	
	

	
	@RequestMapping(value = "/api/category/{categoryCode}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<RESTCategory> getCategory(@PathVariable String categoryCode, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

			Category category = categoryRepository.findByCode(categoryCode);
			RESTCategory restCategory = categoryPopulator.populateWeb(category, locale);
			
		    HttpHeaders headers = new HttpHeaders();
		    return new ResponseEntity<RESTCategory>(restCategory, headers, HttpStatus.OK);


	}

}
