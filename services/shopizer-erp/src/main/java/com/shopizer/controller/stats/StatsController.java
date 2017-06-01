package com.shopizer.controller.stats;

import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.shopizer.business.entity.order.Order;
import com.shopizer.business.repository.order.OrderRepository;
import com.shopizer.restentity.order.RESTOrder;

/**
 * public static List<Date> datesBetween(Date d1, Date d2) {
    List<Date> ret = new ArrayList<Date>();
    Calendar c = Calendar.getInstance();
    c.setTime(d1);
    while (c.getTimeInMillis()<d2.getTime()) {
        c.add(Calendar.MONTH, 1);
        ret.add(c.getTime());
    }
    return ret;
}
 * @author c.samson
 *
 */
@RestController
public class StatsController {
	
	@Inject
	private OrderRepository orderRepository;
	
	
	@GetMapping("/api/stats")
	public ResponseEntity<RESTOrder> historicStats(@PathVariable String id, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		//TODO complete query
		Query query = new Query(Criteria.where("b").elemMatch(
			    Criteria.where("startDate").gte(new Date())
			    .and("endDate").lte(new Date())));
		
		//orderRepository.f

		
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<RESTOrder>(null, headers, HttpStatus.OK);

		
	}

}
