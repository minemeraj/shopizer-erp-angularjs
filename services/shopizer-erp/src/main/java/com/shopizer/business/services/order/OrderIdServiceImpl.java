package com.shopizer.business.services.order;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.shopizer.business.entity.order.OrderId;
import com.shopizer.business.repository.order.OrderIdRepository;
import com.shopizer.constants.Constants;

@Service
public class OrderIdServiceImpl implements OrderIdService {
	
	
	@Inject
	private OrderIdRepository orderIdRepository;

	@Override
	public long nextOderId() {
		
		OrderId orderId = orderIdRepository.findByIdentifier(Constants.ORDERIDIDENTIFIER);
		increment(orderId);
		long id = orderId.getNextOrderNumber();
		
		return id;
		
	}
	
	private synchronized void increment(OrderId orderId) {
		if(orderId == null) {
			throw new RuntimeException("orderId does not exists");
		}
        long orderIdSequence = orderId.getNextOrderNumber();
        orderIdSequence ++;
        orderId.setNextOrderNumber(orderIdSequence);
        orderIdRepository.save(orderId);
    }

}
