package com.shopizer.controller.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.shopizer.business.entity.order.Order;
import com.shopizer.business.entity.order.OrderStatusEnum;
import com.shopizer.business.entity.order.OrderStatusHistory;
import com.shopizer.business.entity.order.OrderTotalTypeEnum;
import com.shopizer.business.repository.order.OrderRepository;
import com.shopizer.business.services.order.OrderService;
import com.shopizer.restentity.common.RESTList;
import com.shopizer.restentity.common.RESTValue;
import com.shopizer.restentity.order.RESTOrder;
import com.shopizer.restentity.order.RESTOrderTotal;
import com.shopizer.restentity.order.RESTTotal;
import com.shopizer.restpopulators.order.OrderPopulator;

@RestController
public class OrderController {
	
	@Inject
	private OrderPopulator orderPopulator;
	
	@Inject
	private OrderService orderService;
	
	@Inject
	private OrderRepository orderRepository;

	
	@GetMapping("/api/order/nextOrderId")
	public ResponseEntity<RESTValue<String>> nextOrderId(Locale locale) throws Exception {
		
		long orderId = orderService.nextOderId();
		
		RESTValue<String> restValue= new RESTValue<String>();
		restValue.setValue(String.valueOf(orderId));
		
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<RESTValue<String>>(restValue, headers, HttpStatus.OK);
		
	}
	
	@PostMapping("/api/order")
	public ResponseEntity<RESTOrder> createOrder(@Valid @RequestBody RESTOrder order, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {


		if(!CollectionUtils.isEmpty(order.getOrderTotals())) {
			BigDecimal ot = this.calculateTotal(order.getOrderTotals());
			order.setTotal(ot.toString());
		}

		Order o = orderPopulator.populateModel(order, locale);
		
		//check if order exists
		if(o.getNumber() != null) {
			Order lookupOrder = orderRepository.findByNumber(o.getNumber());
			if(lookupOrder != null) {
				throw new Exception("Order with number " + o.getNumber().longValue()+ " already exists, use the update method");
			}
		}
		
		if(o.getCreated() == null) {
			o.setCreated(new Date());
		}
		

		orderService.save(o);
		
		RESTOrder restOrder = orderPopulator.populateWeb(o, locale);
			
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/order/{id}").buildAndExpand(o.getId()).toUri());
		return new ResponseEntity<RESTOrder>(restOrder, headers, HttpStatus.CREATED);

		
	}
	
	@PutMapping("/api/order/{id}")
	public ResponseEntity<RESTOrder> updateOrder(@PathVariable String id, @Valid @RequestBody RESTOrder order, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		
		if(!CollectionUtils.isEmpty(order.getOrderTotals())) {
			BigDecimal ot = this.calculateTotal(order.getOrderTotals());
			order.setTotal(ot.toString());
		}

		Order o = orderPopulator.populateModel(order, locale);

		orderService.save(o);
		
		RESTOrder restOrder = orderPopulator.populateWeb(o, locale);
			
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/order/{id}").buildAndExpand(o.getId()).toUri());
		return new ResponseEntity<RESTOrder>(restOrder, headers, HttpStatus.OK);

		
	}
	
	@GetMapping("/api/order/{id}")
	public ResponseEntity<RESTOrder> getOrder(@PathVariable String id, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		Order o = orderRepository.findOne(id);
		
		if(o==null) {
			throw new Exception("Order id " + id + " not found");
		}
		
		RESTOrder restOrder = orderPopulator.populateWeb(o, locale);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/order/{id}").buildAndExpand(o.getId()).toUri());
		return new ResponseEntity<RESTOrder>(restOrder, headers, HttpStatus.OK);

		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/api/orders")
	public ResponseEntity<RESTList<RESTOrder>> getOrders(@RequestParam(value = "page", required=false) int page, @RequestParam(value = "size", required=false) int size, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		
		HttpHeaders headers = new HttpHeaders();
		
		if(size > 0) {
			Pageable pageable = new PageRequest(page,size,new Sort(new org.springframework.data.domain.Sort.Order(Direction.DESC, "number")));
			Page<Order> orders = orderRepository.findAll(pageable);
			List<RESTOrder> restOrders = new LinkedList<RESTOrder>();
			for(Order o : orders) {
				RESTOrder ro = orderPopulator.populateWeb(o, locale);
				restOrders.add(ro);
			}
			
			RESTList returnList = new RESTList(restOrders, restOrders.size());
			return new ResponseEntity<RESTList<RESTOrder>>(returnList, headers, HttpStatus.OK);
		} else {
			List<Order> c = (List<Order>) orderRepository.findAllOrderByCreated();
			
			List<RESTOrder> restOrders = new ArrayList<RESTOrder>();
			for(Order o : c) {
				RESTOrder ro = orderPopulator.populateWeb(o, locale);
				restOrders.add(ro);
			}
			
			RESTList returnList = new RESTList(restOrders, restOrders.size());
			return new ResponseEntity<RESTList<RESTOrder>>(returnList, headers, HttpStatus.OK);
		}

		
		

	} 
	
	@PostMapping("/api/order/total")
	public ResponseEntity<RESTTotal> calculateOrderTotal(@RequestBody List<RESTOrderTotal> orderTotals, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {


		Validate.notNull(orderTotals, "Requires a list of order total to calculate price");
		BigDecimal orderTotal = this.calculateTotal(orderTotals);
		
		//prepare display
		NumberFormat totalFormat = NumberFormat.getCurrencyInstance(Locale.US);
		totalFormat.setMinimumFractionDigits( 2 );
		totalFormat.setMaximumFractionDigits( 2 );
		
		String totalText = totalFormat.format(orderTotal.doubleValue());

		RESTTotal restTotal = new RESTTotal();
		restTotal.setTotal(totalText);


		return new ResponseEntity<RESTTotal>(restTotal, HttpStatus.OK);

		
	}
	
	private BigDecimal calculateTotal(List<RESTOrderTotal> orderTotals) throws Exception {
		
		Validate.notNull(orderTotals,"orderTotals is null");
		BigDecimal orderTotal = new BigDecimal(0);
		orderTotal.setScale(2, RoundingMode.HALF_EVEN);
		
		for(RESTOrderTotal ot : orderTotals) {
			
			BigDecimal numericValue = null;
			try {
				numericValue = new BigDecimal(ot.getValue());
			} catch(Exception e) {
				throw new Exception("Cannot parse " + ot.getValue() + " numeric value");
			}
			
			OrderTotalTypeEnum type = OrderTotalTypeEnum.OTHER;
			
			if(!StringUtils.isBlank(ot.getType())) {
				type = OrderTotalTypeEnum.valueOf(ot.getType());
			}
	
			
			if(type.name().equals(OrderTotalTypeEnum.DEPOSIT.name())) {
				orderTotal = orderTotal.subtract(numericValue);
			} else {
				orderTotal = orderTotal.add(numericValue);
			}
			
			
		}
		
		return orderTotal;
		
	}
	
	

}
