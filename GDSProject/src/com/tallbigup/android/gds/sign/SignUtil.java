package com.tallbigup.android.gds.sign;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SignUtil {
	
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	
	/**
	 * 得到两个日期相差的天数(date2 - date1的天数)。
	 * @param date1
	 * @param date2
	 * @return -1 参数不合法。
	 */
	public static int getBetweenDay(Date date1, Date date2) {
		if(date1 == null || date2 == null) {
			return  -1;
		}
		Calendar d1 = new GregorianCalendar();
		d1.setTime(date1);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(date2);
		int days = d2.get(Calendar.DAY_OF_YEAR)- d1.get(Calendar.DAY_OF_YEAR);
		System.out.println("days="+days);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
	
	/**
	 * 格式转换：String->Date
	 * @param dstr
	 * @return
	 */
	public static Date getDateByString(String dstr) {
		SimpleDateFormat sdf=new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		try {
			return sdf.parse(dstr);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 格式转换：Date->String
	 * @param date
	 * @return
	 */
	public static String getStringByDate(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat(DEFAULT_DATE_FORMAT);  
		return sdf.format(date); 
	}
}
