package com.xdzl.common.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtils {

	public static final TimeZone TIMEZONE = TimeZone.getTimeZone("Asia/Shanghai");
	
	/**
	 * 日期类型yyyyMMdd
	 */
	public static final String DT_TYPE_DATE = "yyyyMMdd";
	/**
	 * 日期类型yyyy-MM-dd
	 */
	public static final String DT_TYPE_DATE_FAT1 = "yyyy-MM-dd";
	/**
	 * 日期类型yyyy/MM/dd
	 */
	public static final String DT_TYPE_DATE_FAT2 = "yyyy/MM/dd";
	/**
	 * 日期类型yyyy年MM月dd日
	 */
	public static final String DT_TYPE_DATE_FAT3 = "yyyy年MM月dd日";
	
	public static Date dateAdd(int field, Date date, int amount) {
		Calendar calendar = getCalendar();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 将时间转换为秒。例如"00:12:30"->750
	 * @param time
	 * @return
	 */
	public static int parseTime2Seconds(String time){

		String[] split = time.split(":");
		if(split.length!=3){
			return -1;
		}
		try {
			int hour=Integer.parseInt(split[0]);
			int minute=Integer.parseInt(split[1]);
			int second=Integer.parseInt(split[2]);
			return hour*60*60+minute*60+second;
		}
		catch (NumberFormatException e) {
			return -1;
		}
	}
	
	public static Calendar getCalendar() {
		return Calendar.getInstance(TIMEZONE);
	}
	
	/**
	 * 按照指定的格式格式化输入日期
	 * @param date		输入日期
	 * @param pattern	日期格式
	 * @return			指定的格式格式化后的日期
	 */
	public static String formatDate(Date date, String pattern) {
		return date == null ? null : getDateFormat(pattern).format(date);
	}

	public static SimpleDateFormat getDateFormat(String strFmt) {
		SimpleDateFormat sdf = null;
		if (strFmt == null)
			sdf = new SimpleDateFormat();
		else
			sdf = new SimpleDateFormat(strFmt);
		sdf.setTimeZone(TIMEZONE);
		sdf.setLenient(false);
		return sdf;
	}
	
	/**
	 * 按照常规格式yyyyMMdd 解析日期型字符串
	 * @param sDate        输入日期型字符串
	 * @return             常规格式DT_TYPE_DATE 解析后的日期形式的日期
	 * @throws ParseException
	 */
	public static Date parseDate(String sDate) throws ParseException {
		String sft = DT_TYPE_DATE;
		if (!(StringUtils.isEmpty(sDate)) && sDate.length() > 8) {
			int len = sDate.length();
			if (len == 10) {
				if (sDate.indexOf("-") > 0)
					sft = DT_TYPE_DATE_FAT1;
				else if (sDate.indexOf("/") > 0)
					sft = DT_TYPE_DATE_FAT2;
				else if (sDate.indexOf("年") > 0)
					sft = DT_TYPE_DATE_FAT3;
			}
		}
		return parseDate(sDate, sft);
	}
	
	/**
	 * 按照指定格式解析日期型字符串
	 * @param sDate	          输入日期型字符串
	 * @param pattern   解析格式
	 * @return			指定格式解析后日期形式的日期
	 */
	public static Date parseDate(String sDate, String pattern) throws ParseException {
		return  !(StringUtils.isEmpty(sDate)) ? getDateFormat(pattern).parse(sDate) : null;
	}
	
	/**
	 * 返回两个日期之间相隔的(年|月|日)数量
	 * @param field			差值类型  对应取值   年Calendar.YEAR|月Calendar.MONTH|日Calendar.DAY_OF_YEAR
	 * @param firstDate		第一个日期
	 * @param secondDate    第二个日期
	 * @return              两个日期之间相隔的(年|月|日)数量
	 */
	public static int dateDiff(int field, Date firstDate, Date secondDate) {
		int result = 0;
		if (firstDate.compareTo(secondDate) > 0) {
			Date temp = firstDate;
			firstDate = secondDate;
			secondDate = temp;
		}
		Calendar first = getCalendar();
		Calendar second = getCalendar();
		
		first.setTime(firstDate);
		second.setTime(secondDate);
		
		first.set(Calendar.SECOND, 0);
		first.set(Calendar.MINUTE, 0);
		first.set(Calendar.HOUR_OF_DAY, 0);
		second.set(Calendar.SECOND, 0);
		second.set(Calendar.MINUTE, 0);
		second.set(Calendar.HOUR_OF_DAY, 0);
		
		if (Calendar.DAY_OF_YEAR == field) {
			long l = second.getTimeInMillis() - first.getTimeInMillis();
			result = (int) (l / (1000.0D * 60.0D * 60.0D * 24.0D));
		}
		else if (Calendar.MONTH == field) {
			result = (second.get(Calendar.YEAR) - first.get(Calendar.YEAR)) * 12
						+ (second.get(Calendar.MONTH) - first.get(Calendar.MONTH));
		}
		else if (Calendar.YEAR == field) {
			result = second.get(field) - first.get(field);
		}
		
		return result;
	}
	
	/**
	 * @return  日期形式的当前机器日期(格式为yyyyMMdd)
	 */
	public static Date nowDate() throws ParseException {
		return nowDate(DT_TYPE_DATE);
	}
	
	/**
	 * 按照输入的格式返回日期形式的当前机器日期/时间
	 * @param pattern   时间格式
	 * @return			日期形式的当前机器日期/时间
	 */
	public static Date nowDate(String pattern) throws ParseException {
		return parseDate(nowString(pattern), pattern);
	}
	
	/**
	 * 按照输入的格式返回字符串形式的当前机器日期/时间
	 * @param pattern	时间格式
	 * @return			字符串形式的当前机器日期/时间
	 */
	public static String nowString(String pattern) {
		return formatDate(new Date(), pattern);
	}

	/**
	 * 获取当前日期字符串 yyyy-MM-dd
	 * @return
	 */
	public static String nowDateString(){
		return nowString("yyyy-MM-dd");
	}

	/**
	 * 获取当前时间字符串 "yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String nowTimeString(){
		return nowString("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 判断输入的字符串是否符合指定的日期格式
	 * @param str
	 * @param pattern  时间格式
	 * @return
	 */
	public static boolean isValidDate(String str, String pattern) {
	    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try{  
            Date date = (Date)formatter.parse(str);
            return str.equals(formatter.format(date));  
        }catch(Exception e){
            return false;  
        }  
	}
	
	public static void main(String[] args) throws Exception {
		String text = "2014-05-01";
		Date date2 = parseDate(text);
		System.out.println(date2.toString());
		int i = dateDiff(Calendar.YEAR,nowDate(),date2);
		System.out.println("相差年：" + i);

	}
}
