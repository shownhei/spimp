package cn.ccrise.spimp.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * 时间格式转换
 * 
 * @author hexiao
 * 
 */
public class DateUtil {
	/**
	 * 把Date格式转为字符串
	 * 
	 * @param datestr
	 * @return
	 */
	public static String date2String(Date datestr) {
		String format = "yyyyMMdd" + " " + "hhmmss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(datestr);
	}

	/**
	 * 把java.util.Date格式转为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 把java.util.Date格式转为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String date2StringForUpper(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String temp = sdf.format(date);
		StringBuffer buff = new StringBuffer();

		buildString(Integer.parseInt(temp.substring(0, 4)), buff, true);
		buff.append("年");
		buildString(Integer.parseInt(temp.substring(5, 7)), buff, false);
		buff.append("月");
		buildString(Integer.parseInt(temp.substring(8, 10)), buff, false);
		buff.append("日");
		return buff.toString();
	}

	/**
	 * 把java.util.Date格式转为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	/**
	 * 得到某日最后一毫秒。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastMillisecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return new Date(calendar.getTimeInMillis());
	}

	public static void main(String a[]) {
		StringBuffer buff = new StringBuffer();
		System.out.println(buildString(10, buff, false).toString());
	}

	/**
	 * 把String类型的数据date类型的数据
	 * 
	 * @param dateString
	 * @param format格式化date数据的方式
	 * @return
	 */
	public static Date String2date(String dateString) {
		String format = "yyyyMMdd";
		Date utilDate = null;
		try {
			utilDate = new Date((new SimpleDateFormat(format)).parse(dateString).getTime());
		} catch (Exception e) {

		}
		return utilDate;
	}

	public static Date String2date(String dateString, String format) {
		Date utilDate = null;
		try {
			utilDate = new Date((new SimpleDateFormat(format)).parse(dateString).getTime());
		} catch (Exception e) {

		}
		return utilDate;
	}

	/**
	 * 把String类型的数据date类型的数据
	 * 
	 * @param dateString
	 * @param format格式化date数据的方式
	 * @return
	 */
	public static Date String2time(String dateString) {
		String format = "yyyyMMddhhmmss";
		Date utilDate = null;
		try {
			utilDate = new Date((new SimpleDateFormat(format)).parse(dateString).getTime());
		} catch (Exception e) {

		}
		return utilDate;
	}

	public static String time2Iso8601(java.sql.Timestamp timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+08:00'");
		String str = df.format(timestamp);
		return str;
	}

	/**
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String time2String(java.sql.Timestamp timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(timestamp);
		return str;
	}

	/**
	 * java.util.Date 转为java.sql.date类型
	 */
	public static Date util2sql(java.util.Date utilDate) {
		if (utilDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = sdf.format(utilDate);
			return String2date(dateString);

		} else {
			return null;
		}
	}

	private static StringBuffer buildString(int number, StringBuffer buff, boolean withJz) {
		String upper[] = { "○", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < upper.length; i++) {
			map.put(String.valueOf(i), upper[i]);
		}
		if (withJz) {
			String dateString = String.valueOf(number);
			char[] dateStringCahrArray = dateString.toCharArray();
			for (char element : dateStringCahrArray) {
				buff.append(map.get(String.valueOf(element)));
			}
		} else {
			if (number > 19) {
				buff.append(upper[(number / 10)]);
				buff.append("十");
				buff.append(upper[(number % 10)]);
			} else if (19 >= number && number > 10) {
				buff.append("十");
				buff.append(upper[(number % 10)]);
			} else {
				buff.append(upper[number]);
			}
		}
		return buff;
	}
}