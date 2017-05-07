package com.shopizer.business.repository.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shopizer.business.entity.order.OrderId;

@Repository
public interface OrderIdRepository extends MongoRepository<OrderId, String> {
	
	public OrderId findByIdentifier(String identifier);

}
