package com.shopizer.business.repository.common;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shopizer.business.entity.common.Currency;

public interface CurrencyRepository extends MongoRepository<Currency, String> {
	
	Currency findByCode(String code);

}
