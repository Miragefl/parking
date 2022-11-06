package com.viscum.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {

	public static final String DATE_TIME_FULL = "yyyy-MM-dd HH:mm:ss";

	/**
	 * LocalDateTime 转 String
	 *
	 * @param localDateTime
	 * @return
	 */
	public static String convent(LocalDateTime localDateTime) {
		return convent(localDateTime, DATE_TIME_FULL);
	}

	/**
	 * Date 转 String
	 * @param date
	 * @return
	 */
	public static String convent(Date date) {
		return convent(date, DATE_TIME_FULL);
	}

	/**
	 * String 转 Date
	 * @param datetime
	 * @return
	 */
	public static Date convent(String datetime) {
		return convent(datetime, DATE_TIME_FULL);
	}

	public static String convent(LocalDateTime localDateTime, String format) {
		return localDateTime.format(DateTimeFormatter.ofPattern(format));
	}

	public static String convent(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}


	public static Date convent(String datetime, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(datetime);
		} catch (ParseException e) {
			return null;
		}
	}


	public static void main(String[] args) {
		convent(LocalDateTime.now());
	}
}
