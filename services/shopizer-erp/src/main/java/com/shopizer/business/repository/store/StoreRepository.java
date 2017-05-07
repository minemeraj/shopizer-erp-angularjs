package com.shopizer.business.repository.store;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shopizer.business.entity.store.Store;

public interface StoreRepository extends MongoRepository<Store, String> {
	
	
	public List<Store> findByName(String name);
	
	public Store findByCode(String code);

}
