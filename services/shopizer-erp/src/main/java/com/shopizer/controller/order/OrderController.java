package com.shopizer.controller.order;

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopizer.business.services.order.OrderIdService;
import com.shopizer.restpopulators.order.OrderPopulator;

@RestController
public class OrderController {
	
	@Inject
	private OrderPopulator orderPopulator;
	
	@Inject
	private OrderIdService orderIdService;
	
	@GetMapping("/api/nextOrderId")
	public ResponseEntity<Long> nextOrderId(Locale locale) {
		
		long orderId = orderIdService.nextOderId();
		
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Long>(orderId, headers, HttpStatus.OK);
		
	}

}
