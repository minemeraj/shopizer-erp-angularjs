package com.shopizer.business.repository.inventory;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopizer.business.entity.inventory.ProductVariant;

public interface ProductVariantRepository extends PagingAndSortingRepository<ProductVariant, String> {
	
	@Query("{ 'product.code' : ?0 }")
	List<ProductVariant> findByProductCode(String productCode);

}
