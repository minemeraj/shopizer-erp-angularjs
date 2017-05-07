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

import static org.assertj.core.api.Assertions.assertThat;

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
import com.shopizer.business.entity.inventory.Category;
import com.shopizer.business.entity.store.Store;
import com.shopizer.business.repository.common.CurrencyRepository;
import com.shopizer.business.repository.inventory.CategoryRepository;
import com.shopizer.business.repository.store.StoreRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ErpApplication.class)
@Ignore
public class CategoryRepositoryTests {

    @Autowired
    StoreRepository storeRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    CurrencyRepository currencyRepository;


    @Before
    public void setUp() {

    	storeRepository.deleteAll();
        currencyRepository.deleteAll();
        categoryRepository.deleteAll();
        
        
        //create a currency
        Currency currency = new Currency("CAD");
        currency.setCode("CAD");
        
        currencyRepository.save(currency);
        
        //create a store
        Store qc = new Store();
        qc.setName("Qc store center");
        
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
        qc.setCurrency(currency);
        
        storeRepository.save(qc);
        
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
        
        //
        path = wood.getPath() + "/ripped";
        Category ripped = new Category();
        ripped.setCode("ripped");
        ripped.setParent(wood);
        ripped.setPath(path);
        
        Description rippedEn = new Description();
        rippedEn.setLang("en");
        rippedEn.setName("Ripped EN");
        rippedEn.setDescription("Ripped wood");
        
        Description rippedFr = new Description();
        rippedEn.setLang("fr");
        rippedEn.setName("Ripped FR");
        rippedEn.setDescription("Bois de rip");
        
        List<Description> descriptionsripped = new ArrayList<Description>();
        descriptionsripped.add(rippedEn);
        descriptionsripped.add(rippedFr);
        
        ripped.setDescriptions(descriptionsripped);
        
        categoryRepository.save(ripped);
        
        //
        path = wood.getPath() + "/soft";
        Category soft = new Category();
        soft.setCode("soft");
        soft.setParent(wood);
        soft.setPath(path);
        
        Description softEn = new Description();
        softEn.setLang("en");
        softEn.setName("Soft EN");
        softEn.setDescription("Soft wood");
        
        Description softFr = new Description();
        softFr.setLang("fr");
        softFr.setName("Soft FR");
        softFr.setDescription("Bois doux");
        
        List<Description> descriptionssoft = new ArrayList<Description>();
        descriptionssoft.add(softEn);
        descriptionssoft.add(softFr);
        
        soft.setDescriptions(descriptionssoft);
        
        categoryRepository.save(soft);
        
        //
        path = soft.getPath() + "/ipe";
        Category ipe = new Category();
        ipe.setCode("ipe");
        ipe.setParent(soft);
        ipe.setPath(path);
        
        Description ipeEn = new Description();
        ipeEn.setLang("en");
        ipeEn.setName("Ipe EN");
        ipeEn.setDescription("Ipe wood");
        
        Description ipeFr = new Description();
        ipeFr.setLang("fr");
        ipeFr.setName("Ipe FR");
        ipeFr.setDescription("Bois ipe");
        
        List<Description> descriptionsipe = new ArrayList<Description>();
        descriptionsipe.add(ipeEn);
        descriptionsipe.add(ipeFr);
        
        ipe.setDescriptions(descriptionsipe);
        
        categoryRepository.save(ipe);
        
        //now build childs
        List<Category> childs = new ArrayList<Category>();
        childs.add(ipe);
        soft.setChildren(childs);
        
        categoryRepository.save(soft);
        
        childs = new ArrayList<Category>();
        childs.add(soft);
        childs.add(ripped);
        wood.setChildren(childs);
        
        categoryRepository.save(wood);
     }
        

/*    @Test
    public void setsIdOnSave() {

        Customer dave = repository.save(new Customer("Dave", "Matthews"));

        assertThat(dave.id).isNotNull();
    }*/

    @Test
    public void findCategory() {

        //find category
    	Category result = categoryRepository.findByCode("wood");

        assertThat(result).isNotNull();
        
        //Find subcategory
        
        
        //Find parent then lis children
        
        


    }

/*    @Test
    public void findsByExample() {

        Customer probe = new Customer(null, "Matthews");

        List<Customer> result = repository.findAll(Example.of(probe));

        assertThat(result).hasSize(2).extracting("firstName").contains("Dave", "Oliver August");
    }*/
}