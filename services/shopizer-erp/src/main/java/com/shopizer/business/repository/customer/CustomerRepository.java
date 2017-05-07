package com.shopizer.business.repository.customer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopizer.business.entity.customer.Customer;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, String> {

	public Page<Customer> findAllOrderByCreated(Pageable pageable);
    public List<Customer> findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByPhoneNumber(String phoneNumber);
    public Customer findByEmailAddress(String emailAddress);
    

}
