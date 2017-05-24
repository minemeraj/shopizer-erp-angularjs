package com.shopizer.business.services.order;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.shopizer.business.entity.order.Order;
import com.shopizer.business.entity.order.OrderId;
import com.shopizer.business.entity.order.OrderStatusEnum;
import com.shopizer.business.entity.user.User;
import com.shopizer.business.repository.order.OrderIdRepository;
import com.shopizer.business.repository.order.OrderRepository;
import com.shopizer.business.repository.user.UserRepository;
import com.shopizer.constants.Constants;

@Service
public class OrderServiceImpl implements OrderService {
	
	
	@Inject
	private OrderIdRepository orderIdRepository;
	
	@Inject
	private OrderRepository orderRepository;
	
	@Inject
	private UserRepository userRepository;

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

	@Override
	public Order saveOrder(Order order) {
		orderRepository.save(order);
		
		if(order.getStatus().name().equals(OrderStatusEnum.READY.name())) {
		
			//check status
			List<String> notifiablePermissions = new ArrayList<String>();
			notifiablePermissions.add("intern");
			List<User> notifiableUsers = userRepository.findByPermissionsIn(notifiablePermissions);
			
		}
		
		return order;
	}

}
