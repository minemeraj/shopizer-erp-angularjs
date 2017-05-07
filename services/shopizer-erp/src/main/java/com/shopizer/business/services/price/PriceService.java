package com.shopizer.business.services.price;

import java.math.BigDecimal;
import java.util.Locale;

import com.shopizer.business.entity.common.Currency;

public interface PriceService {
	
	
	BigDecimal toPrice(String price) throws Exception;
	
	String formatAmountWithCurrency(Currency currency, BigDecimal amount, Locale locale) throws Exception;

}
