package com.shopizer.business.services.price;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.CurrencyValidator;
import org.springframework.stereotype.Service;

import com.shopizer.business.entity.common.Currency;

@Service("priceService")
public class PriceServiceImpl implements PriceService {
	
	private final static char DECIMALCOUNT = '2';
	private final static char DECIMALPOINT = '.';
	private final static char THOUSANDPOINT = ',';

	@SuppressWarnings("unused")
	@Override
	public BigDecimal toPrice(String price) throws Exception {


		// validations
		/**
		 * 1) remove decimal and thousand
		 * 
		 * String.replaceAll(decimalPoint, ""); String.replaceAll(thousandPoint,
		 * "");
		 * 
		 * Should be able to parse to Integer
		 */
		StringBuffer newAmount = new StringBuffer();
		for (int i = 0; i < price.length(); i++) {
			if (price.charAt(i) != DECIMALPOINT
					&& price.charAt(i) != THOUSANDPOINT) {
				newAmount.append(price.charAt(i));
			}
		}

		try {
			Integer.parseInt(newAmount.toString());
		} catch (Exception e) {
			throw new Exception("Cannot parse " + price + " to valid price");
		}

		if (!price.contains(Character.toString(DECIMALPOINT))
				&& !price.contains(Character.toString(THOUSANDPOINT))
				&& !price.contains(" ")) {

			if (matchPositiveInteger(price)) {
				BigDecimalValidator validator = CurrencyValidator.getInstance();
				BigDecimal bdamount = validator.validate(price, Locale.US);
				if (bdamount == null) {
					throw new Exception("Cannot parse " + price);
				} else {
					return bdamount;
				}
			} else {
				throw new Exception("Not a positive integer "
						+ price);
			}

		} else {
			//TODO should not go this path in this current release
			StringBuffer pat = new StringBuffer();

			if (!StringUtils.isBlank(Character.toString(THOUSANDPOINT))) {
				pat.append("\\d{1,3}(" + THOUSANDPOINT + "?\\d{3})*");
			}

			pat.append("(\\" + DECIMALPOINT + "\\d{1," + DECIMALCOUNT + "})");

			Pattern pattern = Pattern.compile(pat.toString());
			Matcher matcher = pattern.matcher(price);

			if (matcher.matches()) {

				Locale locale = Locale.US;
				//TODO validate amount using old test case
				if (DECIMALPOINT == ',') {
					locale = Locale.GERMAN;
				}

				BigDecimalValidator validator = CurrencyValidator.getInstance();
				BigDecimal bdamount = validator.validate(price, locale);

				return bdamount;
			} else {
				throw new Exception("Cannot parse " + price);
			}
		}
	
	}
	
	private boolean matchPositiveInteger(String amount) {

		Pattern pattern = Pattern.compile("^[+]?\\d*$");
		Matcher matcher = pattern.matcher(amount);
		if (matcher.matches()) {
			return true;

		} else {
			return false;
		}
	}
	
	public String formatAmountNoCurrency(Currency currency, BigDecimal amount, Locale locale) throws Exception {
		Validate.notNull(currency, "Currency must not be null");
		Validate.notNull(amount,"amount must not be null");
		
		DecimalFormat df = new DecimalFormat("####.00");
		
		BigDecimal returnValue = new BigDecimal(amount.doubleValue());
		returnValue.setScale(2, RoundingMode.HALF_EVEN);

		
		return df.format(returnValue.doubleValue());
	}
		
		
	
	
	public String formatAmountWithCurrency(Currency currency, BigDecimal amount, Locale locale) throws Exception {
		
		
		

		Validate.notNull(currency, "Currency must not be null");
		Validate.notNull(amount,"amount must not be null");
		

		String currencyCode = currency.getCode();
		java.util.Currency curr = java.util.Currency.getInstance(currencyCode);
		
		//Currency currency = Constants.DEFAULT_CURRENCY;
		//Locale locale = Constants.DEFAULT_LOCALE; 
		

		NumberFormat currencyInstance = null;
		
		
		//if(store.isCurrencyFormatNational()) {
			currencyInstance = NumberFormat.getCurrencyInstance(locale);//national
		//} else {
		//	currencyInstance = NumberFormat.getCurrencyInstance();//international
		//}
		
	    currencyInstance.setCurrency(curr);
		
	    currencyInstance.setMaximumFractionDigits(Integer.parseInt(Character
				.toString(DECIMALCOUNT)));
	    currencyInstance.setMinimumFractionDigits(Integer.parseInt(Character
				.toString(DECIMALCOUNT)));
	    
	    return currencyInstance.format(amount.doubleValue());
		

    }

}
