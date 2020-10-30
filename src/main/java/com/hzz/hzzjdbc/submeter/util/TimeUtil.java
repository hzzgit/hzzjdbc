package com.hzz.hzzjdbc.submeter.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static final String PROTOCOL_DATE = "yyyyMMdd";
	public static final String DATE1 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE2 = "yyyyMMddHHmmss";
	public static final String DATE3 = "yyyy-MM-dd";
	private static long nd = 1000 * 24 * 60 * 60;		//天
	private static long nh = 1000 * 60 * 60;			//小时
	private static long nm = 1000 * 60;				//分钟
	private static long ns = 1000;						//秒

	public static final int YEAR = 1;
	public static final int MONTH = 2;
	public static final int DAY = 3;
	public static final int WEEK = 4;
	public static final int HOUR = 10;		//12小时制
	public static final int HOUR_OF_DAY = 11;//24小时制
	public static final int MINUTE = 12;
	public static final int SECOND = 13;

	public static final int POOR_D = 1;
	public static final int POOR_H = 2;
	public static final int POOR_M = 3;
	public static final int POOR_S = 4;


	public static Date dateAdd(int iField, int iValue, Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch (iField) {
			case 1:
				cal.add(1, iValue);
				break;
			case 2:
				cal.add(2, iValue);
				break;
			case 3:
				cal.add(5, iValue);
				break;
			case 4:
				cal.add(5, iValue * 7);
				break;
			case 10:
				cal.add(10, iValue);
				break;
			case 11:
				cal.add(11, iValue);
				break;
			case 12:
				cal.add(12, iValue);
				break;
			case 13:
				cal.add(13, iValue);
				break;
		}
		return cal.getTime();
	}
	
	public static void main(String[] args){
		System.out.println(TimeUtil.date2str(TimeUtil.dateAdd(11,1,new Date()), TimeUtil.DATE1));
	}

	private static SimpleDateFormat sdf;
	
	public static String date2str(Date date,String format) {
		sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	public static String date2str(Date date) {
		sdf = new SimpleDateFormat(DATE1);
		return sdf.format(date);
	}

	public static Date str2date(String date,String format) throws ParseException {
		sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
	public static Date str2date(String date) throws ParseException {
		sdf = new SimpleDateFormat(DATE1);
		return sdf.parse(date);
	}

	/**
	 * 获取时间差
	 * @param endDate		结束时间
	 * @param startDate	开始时间
	 * @param model			模式
	 * @return
	 */
	public static Long getDatePoor(Date endDate, Date startDate ,int poorModel) {
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - startDate.getTime();
		switch (poorModel){
			case POOR_D:
				return diff / nd;
			case POOR_H:
				return diff / nh;
			case POOR_M:
				return diff / nm;
			case POOR_S:
				return diff / ns;
			default: return null;
		}
//		switch (model){
//			case POOR_D:
//				return diff / nd;
//			case POOR_H:
//				return diff % nd / nh;
//			case POOR_M:
//				return diff % nd % nh / nm;
//			case POOR_S:
//				return diff % nd % nh % nm / ns;
//			default: return null;
//		}

	}

	/**
	 * 时间段比较
	 * @param startDate	开始时间
	 * @param nowDate		比较时间
	 * @return
	 */
	public static boolean compareStartTimeQuantum(Date startDate , Date nowDate) {
		// 判断一个时间是否某个时间段内
		return startDate.before(nowDate) || startDate.equals(nowDate);
	}

	/**
	 * 获取时间差（带小数点）
	 * @param endDate		结束时间
	 * @param startDate	开始时间
	 * @param model			模式
	 * @return
	 */
	public static Double getDoubleDatePoor(Date endDate, Date startDate ,int model) {
		// 获得两个时间的毫秒时间差异
		double diff = endDate.getTime() - startDate.getTime();
		switch (model){
			case POOR_D:
				return diff / nd;
			case POOR_H:
				return diff / nh;
			case POOR_M:
				return diff / nm;
			case POOR_S:
				return diff / ns;
			default: return null;
		}
	}

	/**
	 * 时间段比较
	 * @param startDate	开始时间
	 * @param endDate		结束时间
	 * @param nowDate		比较时间
	 * @return
	 */
	public static boolean compareTimeQuantum(Date startDate ,Date endDate, Date nowDate) {
		// 判断一个时间是否某个时间段内
		return startDate.before(nowDate) && endDate.after(nowDate);
	}

	/**
	 * 时间段比较
	 * @param startDateStr	 开始时间
	 * @param endDateStr	 结束时间
	 * @param nowDate		 比较时间
	 * @return
	 */
	public static boolean compareTimeQuantum(String startDateStr ,String endDateStr, Date nowDate) throws Exception{
		Date startDate = str2date(startDateStr,DATE1);
		Date endDate = str2date(endDateStr,DATE1);
		return compareTimeQuantum(startDate,endDate,nowDate);
	}

	/**
	 * Date转换为LocalDateTime
	 * @param date
	 */
	public static LocalDateTime date2LocalDateTime(Date date){
		Instant instant = date.toInstant();//An instantaneous point on the time-line.(时间线上的一个瞬时点。)
		ZoneId zoneId = ZoneId.systemDefault();//A time-zone ID, such as {@code Europe/Paris}.(时区)
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
		//DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE1);//This class is immutable and thread-safe.@since 1.8
		//System.out.println(dateTimeFormatter.format(localDateTime));//2018-03-27 14:52:57
		return localDateTime;
	}

	/**
	 * LocalDateTime转换为Date
	 * @param localDateTime
	 */
	public static Date localDateTime2Date( LocalDateTime localDateTime){
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDateTime.atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
		Date date = Date.from(zdt.toInstant());
		//System.out.println(date.toString());//Tue Mar 27 14:17:17 CST 2018
		return date;
	}

	public static Date removeMillisecond(Date date){
		try {
			String dateStr = date2str(date);
			return str2date(dateStr);
		}catch (Exception e){
			//logger.debug("移除毫秒失败，"+e);
		}
		return date;
	}

}
