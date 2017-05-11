package com.shopizer.restpopulators.order;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.shopizer.business.entity.order.OrderComment;
import com.shopizer.restentity.order.RESTOrderComment;
import com.shopizer.restpopulators.DataPopulator;
import com.shopizer.utils.DateUtil;

@Component
public class OrderCommentPopulator implements DataPopulator<RESTOrderComment, OrderComment> {

	@Override
	public OrderComment populateModel(RESTOrderComment source, Locale locale) throws Exception {

		OrderComment comment = new OrderComment();
		comment.setComment(source.getComment());
		comment.setCreated(DateUtil.getDate(source.getCreated()));
		
		return comment;
	}

	@Override
	public RESTOrderComment populateWeb(OrderComment source, Locale locale) throws Exception {
		
		RESTOrderComment comment = new RESTOrderComment();
		comment.setComment(source.getComment());
		comment.setCreated(DateUtil.formatDate(source.getCreated()));
		
		return comment;
		
	}



}
