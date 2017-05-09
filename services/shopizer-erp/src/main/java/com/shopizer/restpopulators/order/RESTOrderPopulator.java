package com.shopizer.restpopulators.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.shopizer.business.entity.customer.Customer;
import com.shopizer.business.entity.order.Order;
import com.shopizer.business.entity.order.OrderTotal;
import com.shopizer.business.repository.customer.CustomerRepository;
import com.shopizer.business.services.price.PriceService;
import com.shopizer.restentity.order.RESTOrder;
import com.shopizer.restentity.order.RESTOrderTotal;
import com.shopizer.restpopulators.DataPopulator;

@Component
public class RESTOrderPopulator implements DataPopulator<RESTOrder, Order> {
	
	
	@Inject
	private PriceService priceService;
	
	@Inject
	private RESTOrderTotalPopulator orderTotalPopulator;
	
	@Inject
	private CustomerRepository customerRepository;

	@Override
	public Order populateModel(RESTOrder source, Locale locale) throws Exception {
		
		Order target = new Order();
		target.setId(source.getId());
		target.setDescription(source.getDescription());
		target.setNumber(source.getNumber());
		target.setOrder(source.getOrder());
		
		BigDecimal total = priceService.toPrice(source.getTotal());
		target.setTotal(total);
		
		if(!CollectionUtils.isEmpty(source.getOrderTotals())) {
			List<OrderTotal> totals = new ArrayList<OrderTotal>();
			for(RESTOrderTotal ot : source.getOrderTotals()) {
				OrderTotal orderTotal = orderTotalPopulator.populateModel(ot, locale);
				totals.add(orderTotal);
			}
			target.setOrderTotals(totals);
		}
		
		target.setTotal(total);
		
		String customerId = source.getCustomer().getId();
		Customer customer = customerRepository.findOne(customerId);
		target.setCustomer(customer);
		
		//TODO to be finished
		if(source.getCreated() != null) {
			//target.setCreated();
		}
		
		
		target.setCode(String.valueOf(source.getNumber()));
		
		return target;
		

		
	}

	@Override
	public RESTOrder populateWeb(Order source, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
