package com.shopizer.controller.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.shopizer.business.entity.inventory.Product;
import com.shopizer.business.entity.inventory.ProductVariant;
import com.shopizer.business.repository.inventory.BrandRepository;
import com.shopizer.business.repository.inventory.CategoryRepository;
import com.shopizer.business.repository.inventory.ProductRepository;
import com.shopizer.business.repository.inventory.ProductVariantRepository;
import com.shopizer.business.repository.store.StoreRepository;
import com.shopizer.business.services.inventory.ProductService;
import com.shopizer.restentity.inventory.RESTProduct;
import com.shopizer.restentity.inventory.RESTProductVariant;
import com.shopizer.restpopulators.inventory.BrandPopulator;
import com.shopizer.restpopulators.inventory.CategoryPopulator;
import com.shopizer.restpopulators.inventory.ProductPopulator;
import com.shopizer.restpopulators.inventory.ProductVariantPopulator;
import com.shopizer.restpopulators.store.StorePopulator;

@RestController
public class ProductController {
	
	@Inject
	private CategoryPopulator categoryPopulator;
	
	@Inject
	private CategoryRepository categoryRepository;
	
	@Inject
	private BrandPopulator brandPopulator;
	
	@Inject
	private StorePopulator storePopulator;
	
	@Inject
	private StoreRepository storeRepository;
	
	@Inject
	private BrandRepository brandRepository;
	
	@Inject
	private ProductPopulator productPopulator;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private ProductRepository productRepository;
	
	@Inject
	private ProductVariantRepository productVariantRepository;
	
	@Inject
	private ProductVariantPopulator productVariantPopulator;
	
	@PostMapping("/product")
	public ResponseEntity<Void> createProduct(@Valid @RequestBody RESTProduct product, Locale locale, UriComponentsBuilder ucBuilder) throws Exception  {
		
		Validate.notNull(product, "Product is required");

		Product productModel = productPopulator.populateModel(product, locale);
			
			
		List<RESTProductVariant> variants = product.getVariants();
		List<ProductVariant> modelVariants = new ArrayList<ProductVariant>();
			
		for(RESTProductVariant variant : variants) {
				
				ProductVariant v = productVariantPopulator.populateModel(variant, locale);
				modelVariants.add(v);
				
		}
			
		productService.saveProduct(productModel, modelVariants);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/product/{code}").buildAndExpand(productModel.getCode()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}
	
	

	
	@RequestMapping(value = "/product/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<RESTProduct> getProduct(@PathVariable String code, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {
	    
		
		
			//get product
		    Product productModel =  productRepository.findByCode(code);
		    
		    if(productModel == null) {
		    	throw new Exception("Product with code [" + code + "] not found");
		    }
		
			//get variants
		    List<ProductVariant> variants = productVariantRepository.findByProductCode(productModel.getCode());
		    List<RESTProductVariant> restVariants = new ArrayList<RESTProductVariant>();
		    
		    for(ProductVariant variant : variants) {
				
				RESTProductVariant v = productVariantPopulator.populateWeb(variant, locale);
				restVariants.add(v);
				
		    }
		    
		    RESTProduct restProduct = productPopulator.populateWeb(productModel, locale);
		    
		    restProduct.setVariants(restVariants);


			
		    HttpHeaders headers = new HttpHeaders();
		    return new ResponseEntity<RESTProduct>(restProduct, headers, HttpStatus.OK);


	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<Void> updateProduct(@PathVariable String id, @Valid @RequestBody RESTProduct product, Locale locale, UriComponentsBuilder ucBuilder) throws Exception  {
		
		Validate.notNull(product, "Product is required");

		Product productModel = productPopulator.populateModel(product, locale);
			
			
		List<RESTProductVariant> variants = product.getVariants();
		List<ProductVariant> modelVariants = new ArrayList<ProductVariant>();
			
		for(RESTProductVariant variant : variants) {
				
				ProductVariant v = productVariantPopulator.populateModel(variant, locale);
				modelVariants.add(v);
				
		}
			
		productService.saveProduct(productModel, modelVariants);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/product/{code}").buildAndExpand(productModel.getCode()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);

	}
	


}
