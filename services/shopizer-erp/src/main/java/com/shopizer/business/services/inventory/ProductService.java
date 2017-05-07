package com.shopizer.business.services.inventory;

import java.util.List;

import com.shopizer.business.entity.inventory.Price;
import com.shopizer.business.entity.inventory.Product;
import com.shopizer.business.entity.inventory.ProductVariant;

public interface ProductService {
	
	void saveProduct(Product product, List<ProductVariant> variants) throws Exception;

}
