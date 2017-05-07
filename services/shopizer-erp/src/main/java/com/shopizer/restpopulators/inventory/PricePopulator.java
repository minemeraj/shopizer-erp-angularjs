package com.shopizer.restpopulators.inventory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.shopizer.business.entity.common.Currency;
import com.shopizer.business.entity.inventory.Price;
import com.shopizer.business.repository.common.CurrencyRepository;
import com.shopizer.business.services.price.PriceService;
import com.shopizer.restentity.inventory.RESTPrice;
import com.shopizer.restpopulators.DataPopulator;
import com.shopizer.utils.DateUtil;

@Component
public class PricePopulator implements DataPopulator<RESTPrice, Price> {
	
	@Inject
	private CurrencyRepository currencyRepository;
	
	@Inject
	private PriceService priceService;

	@Override
	public Price populateModel(RESTPrice source, Locale locale) throws Exception {
		Price target = new Price();
		target.setLastUpdated(new Date());
		target.setPrice(new BigDecimal(source.getPrice()));
		target.setDefaultPrice(source.isDefaultPrice());
		
		if(!StringUtils.isEmpty(source.getCurrencyCode())) {
			Currency currency = currencyRepository.findByCode(source.getCurrencyCode());
			target.setCurrency(currency);
		}

		return target;
		
	}

	@Override
	public RESTPrice populateWeb(Price source, Locale locale) throws Exception {
		RESTPrice target = new RESTPrice();
		target.setLastUpdated(DateUtil.formatDate(source.getLastUpdated()));
		target.setPrice(priceService.formatAmountWithCurrency(source.getCurrency(), source.getPrice(), locale));
		target.setDiscountedPrice(priceService.formatAmountWithCurrency(source.getCurrency(), source.getDiscountedPrice(), locale));
		target.setSpecialEndDate(DateUtil.formatDate(source.getSpecialEndDate()));
		
		target.setDefaultPrice(source.isDefaultPrice());
		
		if(!StringUtils.isEmpty(source.getCurrency())) {
			target.setCurrencyCode(source.getCurrency().getCode());
		}

		return target;
	}

}
