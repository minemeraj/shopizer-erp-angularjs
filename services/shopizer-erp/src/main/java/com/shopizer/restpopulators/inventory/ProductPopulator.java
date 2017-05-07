package com.shopizer.restpopulators.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.shopizer.business.entity.inventory.Brand;
import com.shopizer.business.entity.inventory.Category;
import com.shopizer.business.entity.inventory.Product;
import com.shopizer.business.repository.inventory.BrandRepository;
import com.shopizer.business.repository.inventory.CategoryRepository;
import com.shopizer.restentity.inventory.RESTBrand;
import com.shopizer.restentity.inventory.RESTCategory;
import com.shopizer.restentity.inventory.RESTProduct;
import com.shopizer.restpopulators.DataPopulator;

@Component
public class ProductPopulator implements DataPopulator<RESTProduct, Product> {
	
	
	@Inject
	private BrandRepository brandRepository;
	
	@Inject
	private CategoryRepository categoryRepository;
	
	@Inject
	private BrandPopulator brandPopulator;
	
	@Inject
	private CategoryPopulator categoryPopulator;

	@Override
	public Product populateModel(RESTProduct source, Locale locale) throws Exception {
		
		
		Product target = new Product();
		target.setDescriptions(source.getDescriptions());
		target.setId(source.getId());
		target.setOrder(source.getOrder());
		
		if(source.getBrand()!=null) {
			Validate.notNull(source.getBrand().getClass(),"Brand.code must not be null");
			Brand brand = brandRepository.findByCode(source.getBrand().getCode());
			Validate.notNull(brand,"Brand with code [" + source.getBrand().getCode() + "]");
		}
		
		List<RESTCategory> category = source.getCategories();
		if(!CollectionUtils.isEmpty(category)) {
			List<String> codes = new ArrayList<String>();
			for(RESTCategory c : category) {
				codes.add(c.getCode());
			}
			List<Category> categories = categoryRepository.findByCode(codes);
			target.setCategories(categories);
		}
		
		return target;
	}

	@Override
	public RESTProduct populateWeb(Product source, Locale locale) throws Exception {

		
		
		RESTProduct target = new RESTProduct();
		target.setDescriptions(source.getDescriptions());
		target.setId(source.getId());
		target.setOrder(source.getOrder());
		
		if(source.getBrand()!=null) {
			Validate.notNull(source.getBrand().getClass(),"Brand.code must not be null");
			Brand brand = brandRepository.findByCode(source.getBrand().getCode());
			RESTBrand restBrand = brandPopulator.populateWeb(brand, locale);
			target.setBrand(restBrand);
		}
		
		List<Category> category = source.getCategories();
		if(!CollectionUtils.isEmpty(category)) {
			
			List<RESTCategory> restCategories = new ArrayList<RESTCategory>();
			for(Category c : category) {
				RESTCategory restCategory = categoryPopulator.populateWeb(c, locale);
				restCategories.add(restCategory);
			}
			
			target.setCategories(restCategories);
		}
		
		return target;
	}

}
