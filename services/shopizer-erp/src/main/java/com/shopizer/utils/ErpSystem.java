package com.shopizer.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shopizer.business.repository.common.CurrencyRepository;



@Scope(value="singleton")
@Component
public class ErpSystem {
	
	@Value("${erp.locale}")
	private String locale;
	
	@Inject
	private CurrencyRepository currencyRepository;
	
	private Locale _l;
	
	private Map<String, com.shopizer.business.entity.common.Currency> currencyMap = new HashMap<String, com.shopizer.business.entity.common.Currency>();
	
	
	public Locale getLocale() {
		if(_l == null) {
			_l = new Locale(locale);
			List<com.shopizer.business.entity.common.Currency> currencies =  currencyRepository.findAll();
			for(com.shopizer.business.entity.common.Currency c : currencies) {
				currencyMap.put(c.getCode(), c);
			}
		}
		return _l;
	}
	
	public java.util.Currency getCurrency() {
		return java.util.Currency.getInstance(getLocale());
	}
	
	public com.shopizer.business.entity.common.Currency getSupportedCurrency() {
		java.util.Currency c = getCurrency();
		return currencyMap.get(c.getCurrencyCode());
	}

}
