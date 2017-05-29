package com.shopizer.business.services.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.shopizer.business.entity.order.Order;
import com.shopizer.business.entity.order.OrderId;
import com.shopizer.business.entity.order.OrderStatusEnum;
import com.shopizer.business.entity.user.User;
import com.shopizer.business.repository.order.OrderIdRepository;
import com.shopizer.business.repository.order.OrderRepository;
import com.shopizer.business.repository.user.UserRepository;
import com.shopizer.constants.Constants;
import com.shopizer.utils.DateUtil;

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
		
		List<String> notifiableRoles = new ArrayList<String>();
		
		StringBuilder subject = new StringBuilder();
		
		//determine price

		
		if(order.getStatus().name().equals(OrderStatusEnum.READY.name())) {

			notifiableRoles.add("status-notifiable");
			subject.append("Commande prête pour livraison " + DateUtil.formatDate(new Date()));
		}
		
		if(order.getStatus().name().equals(OrderStatusEnum.CREATED.name())) {
			notifiableRoles.add("status-notifiable");
			subject.append("Nouvelle commande ajoutée le " + DateUtil.formatDate(order.getCreated()));
		}
		
		if(!CollectionUtils.isEmpty(notifiableRoles)) {
			List<User> notifiableUsers = userRepository.findByRolesIn(notifiableRoles);
			//TODO send email
			
			//determine subject
			
			
			//prepare template
			Map<String,String> tokens = new HashMap<String,String>();
			tokens.put("SUBJECT", subject.toString());
			tokens.put("NUMBER", String.valueOf(order.getNumber()));
			tokens.put("SUBTOTAL", null);
			tokens.put("DESCRIPTION", order.getDescription());
		}
		
		return order;
	}

}
