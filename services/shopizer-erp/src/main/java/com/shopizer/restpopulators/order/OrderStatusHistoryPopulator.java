package com.shopizer.restpopulators.order;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.shopizer.business.entity.order.OrderStatusEnum;
import com.shopizer.business.entity.order.OrderStatusHistory;
import com.shopizer.restentity.order.RESTOrderStatusHistory;
import com.shopizer.restpopulators.DataPopulator;
import com.shopizer.utils.DateUtil;

@Component
public class OrderStatusHistoryPopulator implements DataPopulator<RESTOrderStatusHistory, OrderStatusHistory> {

	@Override
	public OrderStatusHistory populateModel(RESTOrderStatusHistory source, Locale locale) throws Exception {
		
		
		OrderStatusHistory target = new OrderStatusHistory();
		target.setCreated(DateUtil.getDate(source.getCreated()));
		target.setStatus(OrderStatusEnum.valueOf(source.getStatus()));
		target.setUser(source.getUser());
		return target;
	}

	@Override
	public RESTOrderStatusHistory populateWeb(OrderStatusHistory source, Locale locale) throws Exception {

		RESTOrderStatusHistory target = new RESTOrderStatusHistory();
		target.setCreated(DateUtil.formatDate(source.getCreated()));
		target.setStatus(source.getStatus().name());
		target.setUser(source.getUser());
		return target;
		
	}

}
