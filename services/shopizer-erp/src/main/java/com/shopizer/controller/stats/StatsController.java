package com.shopizer.controller.stats;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.shopizer.business.entity.order.Order;
import com.shopizer.business.entity.order.OrderStatusEnum;
import com.shopizer.business.entity.order.OrderTotal;
import com.shopizer.business.entity.order.OrderTotalTypeEnum;
import com.shopizer.business.repository.order.OrderRepository;
import com.shopizer.restentity.common.RESTKeyListValue;
import com.shopizer.restentity.common.RESTKeyValue;

import com.shopizer.restentity.order.RESTOrder;

import com.shopizer.restentity.stats.RESTHistoricStats;
import com.shopizer.utils.DateUtil;

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
	
	
	@GetMapping("/api/stats/status")
	public ResponseEntity<RESTHistoricStats> statusHistoricStats(@RequestParam(value = "startDate", required=true) String startDate, @RequestParam(value = "endDate", required=true) String endDate, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		
		Date sDate = null;
		Date eDate = null;
		
		try {
			sDate = DateUtil.getDate(startDate);
			eDate = DateUtil.getDate(endDate);
		} catch(Exception e) {
			throw new Exception("Date format is invalid, please use YYYY-MM-DD");
		}
		
		List<Order> orders = orderRepository.findOrders(sDate, eDate);
		
		RESTHistoricStats stats = new RESTHistoricStats();
		
		List<Date> betweens = DateUtil.datesBetween(sDate, sDate);
		
		SimpleDateFormat monthName = new SimpleDateFormat("MMM");
		
		//determine months for each date
		for(Date dt : betweens) {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			
			String mn = monthName.format(cal.getTime());
			
			
		}
		
		/**
		 * Stats
		 * 
		 *     DATE  ,    STATUS,OCCURENCES
		 * Map<String,Map<String,Integer>>
		 * 
		 * Orders
		 * 
		 *     TOTALENUM,AMOUNT
		 * Map<String,BigDecimal>
		 */
		
		Map<String,BigDecimal> costs = new HashMap<String,BigDecimal>();
		Map<String,Map<String,Integer>> counters = new HashMap<String,Map<String,Integer>>();
		
		/**
		 * count current status by date
		 * count all sub totals
		 */
		for(Order order : orders) {
			
			OrderStatusEnum statusEnum = order.getStatus();//current status
			Date lastModified = order.getModified();//last modification date
			
			String stringDate = DateUtil.getYearMonth(lastModified);
			
			if(lastModified!=null) {
				Map<String,Integer> counts = counters.get(stringDate);
				if(counts == null) {
					counts = new HashMap<String,Integer>();
					counters.put(stringDate, counts);
				}
				Integer iCount = counts.get(statusEnum.name());
				if(iCount == null) {
					iCount = new Integer(0);
				}
				iCount = iCount + 1;
				counts.put(statusEnum.name(), iCount);
			}
			
			//calculate all totals
			List<OrderTotal> totals = order.getOrderTotals();
			for(OrderTotal ot : totals) {
				
				OrderTotalTypeEnum otType = ot.getType();
				BigDecimal value = costs.get(otType.name());
				if(value == null) {
					value = new BigDecimal(0);
					costs.put(otType.name(), value);
				}
				
				value = value.add(ot.getValue());
				
			}
			
		}
		
		//build output object
		
		
		//build x axis
		Set<String> keys = counters.keySet();
		List<String> keyList = new LinkedList<String>(keys);
		
		RESTHistoricStats history = new RESTHistoricStats();
		history.setxAxis(keyList);

		List<RESTKeyListValue> keyListValues = new ArrayList<RESTKeyListValue>();
		history.setyAxis(keyListValues);


		//build y axis
		for(String dateKey : counters.keySet()) {
			
			Map<String,Integer> counterData = counters.get(dateKey);//get by date
			
			RESTKeyListValue klv = new RESTKeyListValue();
			klv.setKey(dateKey);
			
			List<RESTKeyValue> kv = new ArrayList<RESTKeyValue>();

			klv.setValues(kv);

			for(String statusKey : counterData.keySet()) {
				
				Integer count = counterData.get(statusKey);
				RESTKeyValue restKeyValue = new RESTKeyValue();
				restKeyValue.setKey(statusKey);
				restKeyValue.setValue(String.valueOf(count.intValue()));
				kv.add(restKeyValue);
			}
			
			keyListValues.add(klv);

		}

		
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<RESTHistoricStats>(null, headers, HttpStatus.OK);

		
	}

}
