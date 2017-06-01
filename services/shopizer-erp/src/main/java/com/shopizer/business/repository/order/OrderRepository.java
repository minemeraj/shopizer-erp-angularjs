package com.shopizer.business.repository.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopizer.business.entity.order.Order;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, String> {
	
	public Order findByNumber(Long id);
	
	@Query("estimated gte $1 and estimated lte $2")
	public List<Order> findOrders(Date startDate, Date endDate);

}
