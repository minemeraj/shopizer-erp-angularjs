package com.shopizer.restpopulators.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.shopizer.business.entity.inventory.Category;
import com.shopizer.business.repository.inventory.CategoryRepository;
import com.shopizer.restentity.inventory.RESTCategory;
import com.shopizer.restpopulators.DataPopulator;

@Component
public class CategoryPopulator implements DataPopulator<RESTCategory, Category>  {
	

	@Inject CategoryRepository categoryRepository;

	@Override
	public Category populateModel(RESTCategory source, Locale locale) throws Exception {
		
		Validate.notNull(source,"RESTCategory must not be null");
		
		Category category = new Category();
		Category parent = null;

		category.setId(source.getId());
		category.setDescriptions(source.getDescriptions());
		category.setCode(source.getCode());
		
		if(CollectionUtils.isEmpty(source.getChildren())) {
			for(RESTCategory restCategory : source.getChildren()) {
				
				Validate.notNull(restCategory.getCode(), "children category code is missing");
				Category c = categoryRepository.findByCode(restCategory.getCode());
				Validate.notNull(c,"Child category with code [" + restCategory.getCode() + "] not found");
				category.getChildren().add(c);
			}
		}
		
		if(source.getParent() != null) {
			parent = categoryRepository.findByCode(source.getParent().getCode());
			Validate.notNull(parent,"Parent category with code [" + source.getParent().getCode() + "] not found");
			category.setParent(parent);
		}
		
		List<String> path = new ArrayList<String>();
		/**
		 * Build path
		 * path should be built starting from root
		 */
		
		//if parent == null then use path
		if(parent != null) {
			path.add(parent.getId());
			boolean reachedRoot = false;
			while (!reachedRoot) {
					Category c = parent(parent);
					if(c!=null) {
						path.add(c.getId());
					} else {
						reachedRoot = true;
					}
			}
		}
		
		StringBuilder pathList = new StringBuilder();
		pathList.append("/");
		/**
		 * Reverse list
		 */
		Collections.reverse(path);
		for(String id : path) {
			pathList.append(id).append("/");
		}
		
		category.setPath(pathList.toString());
		return category;
	}
	
	private Category parent(Category category) {
		

		Category c = categoryRepository.findByCode(category.getCode());
		return c;
		
	}

	@Override
	public RESTCategory populateWeb(Category source, Locale locale) throws Exception {

		Validate.notNull(source,"Category must not be null");

		RESTCategory category = new RESTCategory();


		category.setId(source.getId());
		category.setDescriptions(source.getDescriptions());
		category.setCode(source.getCode());
		category.setPath(source.getPath());
		
		if(CollectionUtils.isEmpty(source.getChildren())) {
			for(Category c : source.getChildren()) {
				RESTCategory child = populateWeb(c, locale);
				category.getChildren().add(child);
			}
		}
		
		if(source.getParent() != null) {
			
			RESTCategory parent = new RESTCategory();
			parent.setId(source.getParent().getId());
			parent.setDescriptions(source.getParent().getDescriptions());

			category.setParent(parent);
		}

		
		return category;
		
		
	}

}
