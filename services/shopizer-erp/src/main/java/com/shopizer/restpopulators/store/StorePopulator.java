package com.shopizer.restpopulators.store;

import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.shopizer.business.entity.common.Currency;
import com.shopizer.business.entity.store.Store;
import com.shopizer.business.repository.common.CurrencyRepository;
import com.shopizer.restentity.store.RESTStore;
import com.shopizer.restpopulators.DataPopulator;

@Component
public class StorePopulator implements DataPopulator<RESTStore, Store> {
	
	@Inject
	private CurrencyRepository currencyRepository;

	@Override
	public Store populateModel(RESTStore source, Locale local) throws Exception {
		
		
		Validate.notNull(source, "RESTStore is required");
		
		Store target = new Store();
		target.setId(source.getId());
		target.setCode(source.getCode());
		target.setAddress(source.getAddress());
		
		
		if(!StringUtils.isEmpty(source.getCurrency())) {
			Currency currency = currencyRepository.findByCode(source.getCurrency());
			target.setCurrency(currency);
		}
		
		target.setName(source.getName());
		
		String[] s = source.getLocation();
		
		if(s != null) {
			
			double[] d = new double[s.length];
			for (int i = 0; i < s.length; i++)
			    d[i] = Double.valueOf(s[i]).doubleValue();
			
		}
		
		

		
		return target;
		
	}

	@Override
	public RESTStore populateWeb(Store source, Locale local) throws Exception {
		
		Validate.notNull(source, "Store is required");
		
		RESTStore target = new RESTStore();
		target.setId(source.getId());
		target.setCode(source.getCode());
		target.setAddress(source.getAddress());
		
		
		if(!StringUtils.isEmpty(source.getCurrency())) {
			target.setCurrency(source.getCurrency().getCode());
		}
		
		target.setName(source.getName());
		
		double[] d = source.getLocation();
		
		
		String[] s = new String[d.length];

		for (int i = 0; i < s.length; i++)
		    s[i] = String.valueOf(d[i]);


		return target;
	}

}
