package com.shopizer.business.repository.order;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopizer.business.entity.order.Order;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, String> {

}
