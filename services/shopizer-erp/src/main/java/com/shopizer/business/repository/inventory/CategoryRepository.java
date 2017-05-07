package com.shopizer.business.repository.inventory;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shopizer.business.entity.inventory.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
	
	Category findByCode(String code);
	
	List<Category> findByCode(List<String> codes);
	
	List<Category> findByPath(String path);

}
