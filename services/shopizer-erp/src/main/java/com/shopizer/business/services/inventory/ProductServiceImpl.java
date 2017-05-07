package com.shopizer.business.services.inventory;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopizer.business.entity.inventory.Product;
import com.shopizer.business.entity.inventory.ProductVariant;
import com.shopizer.business.repository.inventory.ProductRepository;
import com.shopizer.business.repository.inventory.ProductVariantRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Inject
	private ProductRepository productRepository;
	
	@Inject
    private ProductVariantRepository productVariantRepository;
	
	@Transactional
	public void saveProduct(Product product, List<ProductVariant> variants) throws Exception {
		
		Validate.notNull(product,"Product must not be null");

		productRepository.save(product);
		
		for(ProductVariant variant : variants) {
			
			variant.setProduct(product);
			productVariantRepository.save(variant);
			
		}


	}

}
