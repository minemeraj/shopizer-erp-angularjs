package com.shopizer.restpopulators.inventory;

import java.util.Locale;

import javax.inject.Inject;

import com.shopizer.business.entity.inventory.Inventory;
import com.shopizer.business.repository.inventory.ProductRepository;
import com.shopizer.business.repository.inventory.ProductVariantRepository;
import com.shopizer.business.repository.store.StoreRepository;
import com.shopizer.restentity.inventory.RESTInventory;
import com.shopizer.restpopulators.DataPopulator;

public class InventoryPopulator implements DataPopulator<RESTInventory, Inventory> {
	
	
	@Inject
	private ProductRepository productRepository;
	
	@Inject 
	private ProductVariantRepository productVariantRepository;
	
	@Inject
	private StoreRepository storeRepository;

	@Override
	public Inventory populateModel(RESTInventory source, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RESTInventory populateWeb(Inventory source, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
