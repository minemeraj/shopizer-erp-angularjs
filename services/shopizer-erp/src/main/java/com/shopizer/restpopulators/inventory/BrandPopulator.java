package com.shopizer.restpopulators.inventory;

import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.shopizer.business.entity.inventory.Brand;
import com.shopizer.business.entity.store.Store;
import com.shopizer.business.repository.store.StoreRepository;
import com.shopizer.restentity.inventory.RESTBrand;
import com.shopizer.restpopulators.DataPopulator;

@Component
public class BrandPopulator implements DataPopulator<RESTBrand, Brand> {
	
	@Inject
	private StoreRepository storeRepository;

	@Override
	public Brand populateModel(RESTBrand source, Locale local) throws Exception {
		
		
		Validate.notNull(source,"RESTBrand must not be null");

		
		Brand target = new Brand();
		target.setId(source.getId());
		target.setDescriptions(source.getDescriptions());
		target.setOrder(source.getOrder());
		target.setCode(source.getCode());
		

		return target;
	}

	@Override
	public RESTBrand populateWeb(Brand source, Locale local) throws Exception {

		RESTBrand target = new RESTBrand();
		target.setId(source.getId());
		target.setDescriptions(source.getDescriptions());
		target.setOrder(source.getOrder());
		target.setCode(source.getCode());
		
		return target;
	}

}
