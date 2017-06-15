package com.shopizer.restpopulators.order;

import java.math.BigDecimal;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.shopizer.business.entity.common.Currency;
import com.shopizer.business.entity.order.OrderTotal;
import com.shopizer.business.entity.order.OrderTotalTypeEnum;
import com.shopizer.business.entity.order.OrderTotalVariationEnum;
import com.shopizer.business.services.price.PriceService;
import com.shopizer.restentity.order.RESTOrderTotal;
import com.shopizer.restpopulators.DataPopulator;

@Component
public class OrderTotalPopulator implements DataPopulator<RESTOrderTotal, OrderTotal> {
	
	
	@Inject
	private PriceService priceService;

	@Override
	public OrderTotal populateModel(RESTOrderTotal source, Locale locale) throws Exception {
		
		OrderTotal target = new OrderTotal();
		target.setId(source.getId());
		target.setName(source.getName());
		target.setOrder(source.getOrder());
		if(!StringUtils.isEmpty(source.getType())) {
			target.setType(OrderTotalTypeEnum.valueOf(source.getType()));
		}
		if(!StringUtils.isEmpty(source.getVariation())) {
			target.setVariation(OrderTotalVariationEnum.valueOf(source.getVariation()));
		}
		
		
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
		
		String price = priceService.formatAmountNoCurrency(c, source.getValue(), locale);
		
		orderTotal.setValue(price);
		if(source.getType() != null) {
			orderTotal.setType(source.getType().name());
		}
		if(source.getVariation() != null) {
			orderTotal.setVariation(source.getVariation().name());
		}
		
		return orderTotal;
		
	}

}
