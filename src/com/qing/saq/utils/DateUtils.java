package com.qing.saq.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	public static int days(long d1, long d2) {
		Calendar c = Calendar.getInstance();
		c.set((int)d1/10000, (int)d1%10000/100-1, (int)d1%100);
		int day = c.get(Calendar.DAY_OF_YEAR);
		c.set((int)d2/10000, (int)d2%10000/100, (int)d2%100);
		return c.get(Calendar.DAY_OF_YEAR) - day;
	}
	
	public static int days(long day, Date date) {
		Calendar c = Calendar.getInstance();
		c.set((int)day/10000, (int)day%10000/100-1, (int)day%100);
		int day0 = c.get(Calendar.DAY_OF_YEAR);
		c.setTime(date);
		return c.get(Calendar.DAY_OF_YEAR) - day0;
	}
	
	/**
	 * 
	 * @param date default new Date()
	 * @param pattern default yyyyMMdd
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if(date == null)
			date = new Date();
		if(pattern == null)
			pattern = "yyyyMMdd";
		
		return new SimpleDateFormat(pattern, Locale.ENGLISH).format(date);
	}
	
	public static Date afterDate(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, days);
		
		return c.getTime();
	}
	
	public static String afterDate(Date date, int days, String pattern) {
		Date d = afterDate(date, days);
		if(pattern == null)
			pattern = "yyyyMMdd";
		return new SimpleDateFormat(pattern, Locale.ENGLISH).format(d);
	}
	
	public static String formatDate(long date, String pattern) {
		if(pattern == null)
			pattern = "yyyy-MM-dd";
		
		int year = (int) (date / 10000);
		int month = (int) (date % 10000 / 100);
		int day = (int) (date % 100);
		
		String y = String.valueOf(year);
		String m = month < 10 ? "0"+month : ""+month;
		String d = day < 10 ? "0"+day : ""+day;
		return pattern.replaceFirst("yyyy", y).replaceFirst("MM", m).replaceFirst("dd", d);
	}
}
