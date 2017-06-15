package com.shopizer.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	
	private Date startDate = new Date(new Date().getTime());
	private Date endDate = new Date(new Date().getTime());
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
	private final static String LONGDATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
	private final static String SHORT_DATE = "yyyy-MM-dd";

	
	
	/**
	 * Generates a time stamp
	 * yyyymmddhhmmss
	 * @return
	 */
	public static String generateTimeStamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSS");
		return format.format(new Date());
	}
	
	/**
	 * yyyy-MM-dd
	 * 
	 * @param dt
	 * @return
	 */
	public static String formatDate(Date dt) {

		if (dt == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(SHORT_DATE);
		return format.format(dt);

	}
	
	public static String formatYear(Date dt) {

		if (dt == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(SHORT_DATE);
		return format.format(dt);

	}
	
	public static String formatLongDate(Date date) {
		
		if (date == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(LONGDATE_FORMAT);
		return format.format(date);
		
	}
	
	public static List<Date> datesBetween(Date d1, Date d2) {
	    List<Date> ret = new ArrayList<Date>();
	    Calendar c = Calendar.getInstance();
	    Calendar c2 = Calendar.getInstance();
	    c.setTime(d1);
	    c.setTime(d2);
	    int d1Month = c.get(Calendar.MONTH);
	    int d12Month = c.get(Calendar.MONTH);
	    ret.add(d1);
	    while (c.getTimeInMillis() < d2.getTime()) {
	        c.add(Calendar.MONTH, 1);
	        ret.add(c.getTime());
	    }
	    
	    if(d1Month != d12Month) {  
	    	ret.add(d2);
	    }
	    return ret;
	}

	/**
	 * yy-MMM-dd
	 * 
	 * @param dt
	 * @return
	 */
	public static String formatDateMonthString(Date dt) {

		if (dt == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(SHORT_DATE);
		return format.format(dt);

	}

	public static Date getDate(String date) throws Exception {
		if(date == null) {
			return null;
		}
		DateFormat myDateFormat = new SimpleDateFormat(SHORT_DATE);
		return myDateFormat.parse(date);
	}

	public static Date addDaysToCurrentDate(int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, days);
		return c.getTime();

	}

	public static Date getDate() {

		return new Date(new Date().getTime());

	}

	public static String getPresentDate() {

		Date dt = new Date();

		SimpleDateFormat format = new SimpleDateFormat(SHORT_DATE);
		return format.format(new Date(dt.getTime()));
	}

	public static String getPresentYear() {

		Date dt = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return format.format(new Date(dt.getTime()));
	}

	
	public static String getYearMonth(Date date) {


		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(date.getTime());
	}
	
}
