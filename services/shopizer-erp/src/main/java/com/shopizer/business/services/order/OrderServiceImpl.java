package com.shopizer.business.services.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shopizer.business.entity.order.Order;
import com.shopizer.business.entity.order.OrderId;
import com.shopizer.business.entity.order.OrderStatusEnum;
import com.shopizer.business.entity.order.OrderTotal;
import com.shopizer.business.entity.order.OrderTotalTypeEnum;
import com.shopizer.business.entity.user.User;
import com.shopizer.business.repository.order.OrderIdRepository;
import com.shopizer.business.repository.order.OrderRepository;
import com.shopizer.business.repository.user.UserRepository;
import com.shopizer.business.services.messaging.emails.Email;
import com.shopizer.business.services.messaging.emails.HtmlEmailSender;
import com.shopizer.business.services.price.PriceService;
import com.shopizer.constants.Constants;
import com.shopizer.utils.DateUtil;
import com.shopizer.utils.ErpSystem;

@Service
public class OrderServiceImpl implements OrderService {
	
	
	@Inject
	private OrderIdRepository orderIdRepository;
	
	@Inject
	private OrderRepository orderRepository;
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private PriceService priceService;
	
	@Inject
	private ErpSystem erpSystem;
	
	@Inject HtmlEmailSender htmlEmailSender;
	
	@Value("${messaging.email.from}")
	private String fromEmail;

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
	public Order save(Order order) throws Exception {
		orderRepository.save(order);
		
		List<String> notifiableRoles = new ArrayList<String>();
		
		StringBuilder subject = new StringBuilder();
		
		//determine price
		BigDecimal subTotal = new BigDecimal(0);
		List<OrderTotal> totals = order.getOrderTotals();
		for(OrderTotal total : totals) {
			if(OrderTotalTypeEnum.SUBTOTAL.name().equals(total.getType().name())) {
				subTotal = total.getValue();
			}
		}

		
		if(order.getStatus().name().equals(OrderStatusEnum.READY.name())) {

			notifiableRoles.add("status-notifiable");
			//TODO
			//subject not hard coded
			subject.append("Commande prête pour livraison " + DateUtil.formatDate(new Date()));
		}
		
		if(order.getStatus().name().equals(OrderStatusEnum.CREATED.name())) {
			notifiableRoles.add("status-notifiable");
			//TODO
			//subject not hard coded
			subject.append("Nouvelle commande ajoutée le " + DateUtil.formatDate(order.getCreated()));
		}
		
		if(!CollectionUtils.isEmpty(notifiableRoles)) {
			List<User> notifiableUsers = userRepository.findByRolesIn(notifiableRoles);
			

			//prepare template
			Map<String,String> tokens = new HashMap<String,String>();
			tokens.put("SUBJECT", subject.toString());
			tokens.put("NUMBER", String.valueOf(order.getNumber()));
			tokens.put("SUBTOTAL", priceService.formatAmountWithCurrency(erpSystem.getSupportedCurrency(), subTotal, erpSystem.getLocale()));
			tokens.put("DESCRIPTION", order.getDescription());
			
			Email email = new Email();
			email.setFrom(fromEmail);
			email.setFromEmail(fromEmail);
			email.setTemplateName("status.tpl");
			
			List<String> users = new ArrayList<String>();
			for(User user : notifiableUsers) {
				users.add(user.getUserName());
			}
			email.setTo(users);
			email.setTemplateTokens(tokens);
			
			htmlEmailSender.send(email);
			
		}
		
		return order;
	}

}
