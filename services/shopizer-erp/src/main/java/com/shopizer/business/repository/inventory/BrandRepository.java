package com.shopizer.business.repository.inventory;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shopizer.business.entity.inventory.Brand;

public interface BrandRepository extends MongoRepository<Brand, String> {
	
	Brand findByCode(String code);


}
