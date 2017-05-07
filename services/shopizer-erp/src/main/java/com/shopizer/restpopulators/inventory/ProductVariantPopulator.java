package com.shopizer.restpopulators.inventory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.shopizer.business.entity.common.Currency;
import com.shopizer.business.entity.inventory.Price;
import com.shopizer.business.entity.inventory.ProductVariant;
import com.shopizer.business.repository.common.CurrencyRepository;
import com.shopizer.business.services.price.PriceService;
import com.shopizer.restentity.inventory.RESTPrice;
import com.shopizer.restentity.inventory.RESTProductVariant;
import com.shopizer.restpopulators.DataPopulator;
import com.shopizer.utils.DateUtil;

@Component
public class ProductVariantPopulator implements DataPopulator<RESTProductVariant, ProductVariant> {

	
	@Inject
	private CurrencyRepository currencyRepository;
	
	
	@Inject
	private PriceService priceService;
	
	@Inject
	private PricePopulator pricePopulator;
	
	@Override
	public ProductVariant populateModel(RESTProductVariant source, Locale locale) throws Exception {
		
		Validate.notNull(source, "product variant must not be null");
		Validate.notNull(source.getPrice(),"product price variant must not be null");
		
		ProductVariant target = new ProductVariant();
		target.setId(source.getId());
		target.setCode(source.getSku());
		target.setDefaultVariant(source.isDefaultVariant());
		target.setImages(source.getImages());
		target.setColor(source.getColor());
		target.setHeight(source.getHeight());
		target.setLength(source.getLength());
		target.setOrder(source.getOrder());
		
		
		RESTPrice restPrice = source.getPrice();
		Price price = new Price();
		
		
		BigDecimal p = priceService.toPrice(source.getPrice().getPrice());
		price.setPrice(p);
		
		BigDecimal discount = priceService.toPrice(source.getPrice().getDiscountedPrice());
		price.setDiscountedPrice(discount);
		
		
		if(!StringUtils.isEmpty(restPrice.getSpecialEndDate())) {
			price.setSpecialEndDate(DateUtil.getDate(restPrice.getSpecialEndDate()));
		}
		
		
		if(!StringUtils.isEmpty(source.getPrice().getCurrencyCode())) {
			Currency currency = currencyRepository.findByCode(source.getPrice().getCurrencyCode());
			price.setCurrency(currency);
		}
		
		target.getPrices().add(price);
		return target;
		
	}

	@Override
	public RESTProductVariant populateWeb(ProductVariant source, Locale locale) throws Exception {

		
		Validate.notNull(source, "product variant must not be null");

		
		RESTProductVariant target = new RESTProductVariant();
		target.setId(source.getId());
		target.setSku(source.getCode());
		target.setDefaultVariant(source.isDefaultVariant());
		target.setImages(source.getImages());
		target.setColor(source.getColor());
		target.setHeight(source.getHeight());
		target.setLength(source.getLength());
		target.setOrder(source.getOrder());
		
		
		List<Price> prices = source.getPrices();
		RESTPrice restPrice = null;
		
		for(Price p : prices) {
			if(p.isDefaultPrice()) {
				restPrice = pricePopulator.populateWeb(p, locale);
				target.setPrice(restPrice);
				break;
			}
		}
		
		
		return target;
		
		
		
	}

}
