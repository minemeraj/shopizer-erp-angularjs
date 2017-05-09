package com.shopizer.restpopulators.order;

import java.math.BigDecimal;
import java.util.Locale;

import javax.inject.Inject;

import com.shopizer.business.entity.common.Currency;
import com.shopizer.business.entity.common.OrderTotalTypeEnum;
import com.shopizer.business.entity.common.OrderTotalVariationEnum;
import com.shopizer.business.entity.order.OrderTotal;
import com.shopizer.business.services.price.PriceService;
import com.shopizer.restentity.order.RESTOrderTotal;
import com.shopizer.restpopulators.DataPopulator;

public class RESTOrderTotalPopulator implements DataPopulator<RESTOrderTotal, OrderTotal> {
	
	
	@Inject
	private PriceService priceService;

	@Override
	public OrderTotal populateModel(RESTOrderTotal source, Locale locale) throws Exception {
		
		OrderTotal target = new OrderTotal();
		target.setId(source.getId());
		target.setName(source.getName());
		target.setOrder(source.getOrder());
		target.setType(OrderTotalTypeEnum.valueOf(source.getType()));
		target.setVariation(OrderTotalVariationEnum.valueOf(source.getVariation()));
		
		
		BigDecimal price = priceService.toPrice(source.getValue());
		
		target.setValue(price);
		
		return target;
	}

	@Override
	public RESTOrderTotal populateWeb(OrderTotal source, Locale locale) throws Exception {
		
		RESTOrderTotal orderTotal = new RESTOrderTotal();
		orderTotal.setName(source.getName());
		orderTotal.setOrder(source.getOrder());
		orderTotal.setId(source.getId());
		
		Currency c = new Currency("CAD");
		
		String price = priceService.formatAmountWithCurrency(c, source.getValue(), locale);
		
		orderTotal.setValue(price);
		orderTotal.setType(source.getName());
		orderTotal.setVariation(source.getVariation().name());
		
		return orderTotal;
		
	}

}
