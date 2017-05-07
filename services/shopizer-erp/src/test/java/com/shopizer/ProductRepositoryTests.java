package com.shopizer;
/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shopizer.application.ErpApplication;
import com.shopizer.business.entity.common.Address;
import com.shopizer.business.entity.common.Currency;
import com.shopizer.business.entity.common.Description;
import com.shopizer.business.entity.inventory.Brand;
import com.shopizer.business.entity.inventory.Category;
import com.shopizer.business.entity.inventory.Price;
import com.shopizer.business.entity.inventory.Product;
import com.shopizer.business.entity.inventory.ProductVariant;
import com.shopizer.business.entity.store.Store;
import com.shopizer.business.repository.common.CurrencyRepository;
import com.shopizer.business.repository.inventory.BrandRepository;
import com.shopizer.business.repository.inventory.CategoryRepository;
import com.shopizer.business.repository.inventory.ProductRepository;
import com.shopizer.business.repository.store.StoreRepository;
import com.shopizer.business.services.inventory.ProductService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ErpApplication.class)
@Ignore
public class ProductRepositoryTests {

    @Autowired
    StoreRepository storeRepository;
    
    @Autowired
    CurrencyRepository currencyRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    BrandRepository brandRepository;
    
    @Autowired
    ProductService productService;
    
    
    @Autowired
    ProductRepository productRepository;


    @Before
    public void setUp() throws Exception {

    	
    	//delete reference tables
    	storeRepository.deleteAll();
    	categoryRepository.deleteAll();
        productRepository.deleteAll();
        brandRepository.deleteAll();
        
        
        
        Currency cad = currencyRepository.findByCode("CAD");

        //create default store
        Store qc = new Store();
        qc.setName("Qc store center");
        qc.setCode("default");
        
        double[] qcl = new double[2];
        
        qcl[0] = 46.798803;
        qcl[1] = -71.257339;
        qc.setLocation(qcl);
        
        Address qcAddress = new Address();
        qcAddress.setAddress("1299 Boulevard Charest O");
        qcAddress.setCity("Québec");
        qcAddress.setCountry("CA");
        qcAddress.setPostalCode("G1N 2C9");
        qcAddress.setStateProvince("QC");
        
        qc.setAddress(qcAddress);
        qc.setCurrency(cad);
        
        storeRepository.save(qc);
        
        
        //create a category
        

        //create a few category
        String path = "/wood";
        Category wood = new Category();
        wood.setCode("wood");
        wood.setPath(path);
        
        Description woodEn = new Description();
        woodEn.setLang("en");
        woodEn.setName("Bois EN");
        woodEn.setDescription("Barn wood");
        
        Description woodFr = new Description();
        woodFr.setLang("fr");
        woodFr.setName("Bois FR");
        woodFr.setDescription("Bois de grange");
        
        List<Description> descriptionsWood = new ArrayList<Description>();
        descriptionsWood.add(woodEn);
        descriptionsWood.add(woodFr);
        
        wood.setDescriptions(descriptionsWood);
        
        categoryRepository.save(wood);
        
        Brand brand = new Brand();
        brand.setCode("bamwood");
        
        Description brandDescription = new Description();
        brandDescription.setLang("en");
        brandDescription.setName("BAM Wood");
        
        brand.getDescriptions().add(brandDescription);
        brandRepository.save(brand);
        
        
        Product p1 = new Product();
        p1.setBrand(brand);
        p1.setCode("123456");
        p1.setOrder(0);
        p1.getCategories().add(wood);
        
        Description productDescription = new Description();
        productDescription.setLang("en");
        productDescription.setName("Wood slidings");
        
        p1.getDescriptions().add(productDescription);
        
        Price price = new Price();
        price.setCurrency(cad);
        price.setPrice(new BigDecimal(199.99));
        
        ProductVariant productVariant = new ProductVariant();
        productVariant.setCode("SKU10005");
        productVariant.setDefaultVariant(true);
        productVariant.getPrices().add(price);
        
        List<ProductVariant> variants = new ArrayList<ProductVariant>();
        variants.add(productVariant);
        
        productService.saveProduct(p1, variants);
        
    }
    
    @Test
    public void test() {
    	
    }
    

}