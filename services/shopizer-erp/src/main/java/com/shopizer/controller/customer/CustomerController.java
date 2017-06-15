package com.shopizer.controller.customer;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.shopizer.business.entity.customer.Customer;
import com.shopizer.business.repository.customer.CustomerRepository;
import com.shopizer.restentity.common.RESTList;
import com.shopizer.restentity.customer.RESTCustomer;
import com.shopizer.restpopulators.customer.CustomerPopulator;

@RestController
public class CustomerController {
	
	
	@Inject
	CustomerPopulator customerPopulator;
	
	@Inject
	CustomerRepository customerRepository;
	
	@PostMapping("/api/customer")
	public ResponseEntity<RESTCustomer> createCustomer(@Valid @RequestBody RESTCustomer customer, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {


		Customer c = customerPopulator.populateModel(customer, locale);
		
		//check if customer exists
		Customer lookupCustomer = customerRepository.findByEmailAddress(c.getEmailAddress());
		if(lookupCustomer != null) {
			throw new Exception("Customer with email " + c.getEmailAddress() + " already exists");
		}
		
		c.setCreated(new Date());
		customerRepository.save(c);
		
		RESTCustomer restCustomer = customerPopulator.populateWeb(c, locale);
			
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/customer/{id}").buildAndExpand(c.getId()).toUri());
		return new ResponseEntity<RESTCustomer>(restCustomer, headers, HttpStatus.CREATED);

		
	}
	

	@PutMapping("/api/customer/{id}")
	public ResponseEntity<RESTCustomer> updateCustomer(@PathVariable String id, @Valid @RequestBody RESTCustomer customer, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		Customer c = customerPopulator.populateModel(customer, locale);
		
		c.setModified(new Date());
		customerRepository.save(c);
		
		RESTCustomer rc = customerPopulator.populateWeb(c, locale);
			
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/customer/{id}").buildAndExpand(c.getId()).toUri());
		return new ResponseEntity<RESTCustomer>(rc, headers, HttpStatus.OK);

		
	}
	
	@GetMapping("/api/customer/{id}")
	public ResponseEntity<RESTCustomer> getCustomer(@PathVariable String id, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		Customer c = customerRepository.findOne(id);
		
		if(c==null) {
			throw new Exception("Customer id " + id + " not found");
		}
		
		RESTCustomer rc = customerPopulator.populateWeb(c, locale);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/customer/{id}").buildAndExpand(c.getId()).toUri());
		return new ResponseEntity<RESTCustomer>(rc, headers, HttpStatus.OK);

		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/api/customers")
	public ResponseEntity<RESTList<RESTCustomer>> getCustomers(@RequestParam(value = "page", required=false) int page, @RequestParam(value = "size", required=false) int size, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		
		HttpHeaders headers = new HttpHeaders();
		
		if(size > 0) {
			Pageable pageable = new PageRequest(page,size,new Sort(
				    new Order(Direction.DESC, "created")));
			Page<Customer> customers = customerRepository.findAll(pageable);
			RESTList returnList = new RESTList(customers.getContent(), customers.getSize());
			return new ResponseEntity<RESTList<RESTCustomer>>(returnList, headers, HttpStatus.OK);
		} else {
			List<Customer> c = (List<Customer>) customerRepository.findAll();
			RESTList returnList = new RESTList(c, c.size());
			return new ResponseEntity<RESTList<RESTCustomer>>(returnList, headers, HttpStatus.OK);
		}

		
		

	} 
	
	@DeleteMapping("/api/customer/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable String id, @Valid @RequestBody RESTCustomer customer, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		Customer c = customerPopulator.populateModel(customer, locale);

		customerRepository.delete(c);

			
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.OK);

		
	}

}
