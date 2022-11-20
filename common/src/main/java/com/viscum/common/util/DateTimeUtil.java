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
	 * @param dateTime LocalDateTime日期
	 * @return String格式日期
	 */
	public static String convent(LocalDateTime dateTime) {
		return convent(dateTime, DATE_TIME_FULL);
	}

	/**
	 * Date 转 String
	 * @param dateTime Date格式日期
	 * @return String格式日期
	 */
	public static String convent(Date dateTime) {
		return convent(dateTime, DATE_TIME_FULL);
	}

	/**
	 * String 转 Date
	 * @param datetime String格式日期
	 * @return Date格式日期
	 */
	public static Date convent(String datetime) {
		return convent(datetime, DATE_TIME_FULL);
	}

	/**
	 * LocalDateTime 转 String
	 * @param dateTime LocalDateTime格式日期
	 * @param format 日期格式
	 * @return String格式日期
	 */
	public static String convent(LocalDateTime dateTime, String format) {
		return dateTime.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * Date 转 String
	 * @param dateTime Date格式日期
	 * @param format 日期格式
	 * @return String格式日期
	 */
	public static String convent(Date dateTime, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(dateTime);
	}

	/**
	 * String 转 Date
	 * @param datetime String格式日期
	 * @param format 日期格式
	 * @return Date格式日期
	 */
	public static Date convent(String datetime, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(datetime);
		} catch (ParseException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		String.valueOf(1);

	}
}
