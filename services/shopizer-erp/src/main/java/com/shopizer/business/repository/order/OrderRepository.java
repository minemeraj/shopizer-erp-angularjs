package com.shopizer.business.repository.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopizer.business.entity.customer.Customer;
import com.shopizer.business.entity.order.Order;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, String> {
	
	public Order findByNumber(Long id);
	
	@Query("{'estimated' : { '$gte' : ?0}},{'estimated' : { '$1te' : ?1}}")
	//@Query("{'born' : { '$gt' : ?0 }}")
	public List<Order> findOrders(Date startDate, Date endDate);
	//public List<Order> findOrders(Date startDate);
	
	public Page<Order> findAll(Pageable pageable);
	public List<Order> findAllOrderByCreated();

}
