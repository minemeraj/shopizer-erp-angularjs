package com.shopizer.restpopulators.order;

import java.util.Locale;

import javax.inject.Inject;

import com.shopizer.business.entity.common.Currency;
import com.shopizer.business.entity.order.OrderTotal;
import com.shopizer.business.services.price.PriceService;
import com.shopizer.restentity.order.RESTOrderTotal;
import com.shopizer.restpopulators.DataPopulator;

public class RESTOrderTotalPopulator implements DataPopulator<RESTOrderTotal, OrderTotal> {
	
	
	@Inject
	private PriceService priceService;

	@Override
	public OrderTotal populateModel(RESTOrderTotal source, Locale locale) throws Exception {
		
		throw new Exception("Not implemented");
		
	}

	@Override
	public RESTOrderTotal populateWeb(OrderTotal source, Locale locale) throws Exception {
		
		RESTOrderTotal orderTotal = new RESTOrderTotal();
		orderTotal.setName(source.getName());
		orderTotal.setOrder(source.getOrder());
		
		Currency c = new Currency("CAD");
		
		String price = priceService.formatAmountWithCurrency(c, source.getValue(), locale);
		
		orderTotal.setValue(price);
		
		return orderTotal;
		
	}

}
