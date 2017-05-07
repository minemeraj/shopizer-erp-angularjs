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

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shopizer.application.ErpApplication;
import com.shopizer.business.entity.customer.Customer;
import com.shopizer.business.repository.customer.CustomerRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ErpApplication.class)
@Ignore
public class CustomerRepositoryTests {

    @Autowired
    CustomerRepository repository;

    Customer dave, oliver, carter;

    @Before
    public void setUp() {

        repository.deleteAll();

        dave = repository.save(new Customer("Dave", "Matthews", "444-555-6666"));
        oliver = repository.save(new Customer("Oliver August", "Matthews", "555-666-7777"));
        carter = repository.save(new Customer("Carter", "Beauford", "888-888-9999"));
    }

    @Test
    public void setsIdOnSave() {

        Customer dave = repository.save(new Customer("Dave", "Matthews", "888-999-9999"));

        assertThat(dave.id).isNotNull();
    }

    @Test
    public void findsByLastName() {

        List<Customer> result = repository.findByLastName("Beauford");

        assertThat(result).hasSize(1).extracting("firstName").contains("Carter");
    }


}