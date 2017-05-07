package com.shopizer.business.repository.inventory;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopizer.business.entity.inventory.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, String> {
	
	List<Product> findAll();
	
	Product findByCode(String code);

}
