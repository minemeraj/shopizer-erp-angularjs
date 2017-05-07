package com.shopizer.restpopulators.customer;

import java.util.Locale;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.shopizer.business.entity.common.Address;
import com.shopizer.business.entity.customer.Customer;
import com.shopizer.restentity.customer.RESTCustomer;
import com.shopizer.restpopulators.DataPopulator;

@Component
public class CustomerPopulator implements DataPopulator<RESTCustomer, Customer> {

	@Override
	public Customer populateModel(RESTCustomer source, Locale locale) throws Exception {
		
		Validate.notNull(source,"Customer must not be null");
		Validate.notNull(source.getFirstName(),"Customer first name must not be null");
		Validate.notNull(source.getLastName(),"Customer last name must not be null");
		Validate.notNull(source.getCountry(),"Customer country must not be null");
		Validate.notNull(source.getZone(),"Customer zone/province must not be null");
		Validate.notNull(source.getEmailAddress(),"Customer email must not be null");
		Validate.notNull(source.getPhoneNumber(),"Customer phone number must not be null");
		
		Customer target = new Customer();
		
		target.setId(source.getId());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setPhoneNumber(source.getPhoneNumber());
		target.setEmailAddress(source.getEmailAddress());
		
		Address address = new Address();
		address.setAddress(source.getAddress());
		address.setCity(source.getCity());
		address.setPostalCode(source.getPostalCode());
		address.setCountry(source.getCountry());
		address.setStateProvince(source.getZone());
		
		target.setAddress(address);
		
		target.setCompanyName(source.getCompanyName());
		

		return target;
	}

	@Override
	public RESTCustomer populateWeb(Customer source, Locale locale) throws Exception {
		RESTCustomer target = new RESTCustomer();
		
		target.setId(source.getId());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setPhoneNumber(source.getPhoneNumber());
		target.setAddress(source.getAddress().getAddress());
		target.setCity(source.getAddress().getCity());
		target.setPostalCode(source.getAddress().getPostalCode());
		target.setZone(source.getAddress().getStateProvince());
		target.setCountry(source.getAddress().getCountry());
		target.setCompanyName(source.getCompanyName());
		target.setEmailAddress(source.getEmailAddress());
		

		return target;
	}

}
