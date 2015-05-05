package com.dahuatech.app.common;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * @ClassName DateHelper
 * @Description 时间帮助类
 * @author 21291
 * @date 2014年4月21日 上午10:55:54
 */
@SuppressLint("UseValueOf")
public class DateHelper implements JsonDeserializer<Date> {

	private static final String COMMON_DATE = "yyyy-MM-dd";
	private static final String COMPARE_DATE = "yyyy-MM-dd HH:mm";

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: Date反序列化
	 * </p>
	 * <p>
	 * Description: 对Date数据类型反序列化进行扩展
	 * </p>
	 * 
	 * @param json
	 * @param typeOfT
	 * @param context
	 * @return
	 * @throws JsonParseException
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
	 *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	public Date deserialize(JsonElement json, Type typeOfT,JsonDeserializationContext context) throws JsonParseException {
		String JSONDateToMilliseconds = "\\/(Date\\((.*?)(\\+.*)?\\))\\/";
		Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
		Matcher matcher = pattern.matcher(json.getAsJsonPrimitive().getAsString());
		String result = matcher.replaceAll("$2");
		return new Date(new Long(result));
	}

	/**
	 * @Title: getWeekOfYear
	 * @Description: 取得指定日期是第几周
	 * @param @param c 日期实例
	 * @param @param date
	 * @param @return
	 * @return int
	 * @throws
	 * @author 21291
	 * @date 2014年10月24日 下午3:55:21
	 */
	public static int getWeekOfYear(Calendar c, Date date) {
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * @Title: getWeekOfDate
	 * @Description: 根据时间，获取日期是周几
	 * @param @param c 日期实例
	 * @param @param date 时间实例
	 * @param @return
	 * @return String
	 * @throws
	 * @author 21291
	 * @date 2014年11月18日 下午4:21:45
	 */
	public static String getWeekOfDate(Calendar c, Date date) {
		String[] weekDaysName = { "FSunday", "FMonday", "FTuesday",
				"FWednesday", "FThursday", "FFriday", "FSaturday" };
		c.setTime(date);
		int intWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

	/**
	 * @Title: getNumWeeksForYear
	 * @Description: 得到某一年周的总数
	 * @param @param c 日期实例
	 * @param @param year 年份
	 * @param @return
	 * @return int
	 * @throws
	 * @author 21291
	 * @date 2014年11月20日 下午1:15:08
	 */
	public static int getNumWeeksForYear(Calendar c, int year) {
		c.set(year, 0, 1);
		return c.getActualMaximum(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * @Title: getFirstDayOfWeek
	 * @Description: 得到某年某周的第一天
	 * @param @param c 日期实例
	 * @param @param year 年份
	 * @param @param week 周数
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014年10月24日 下午3:56:52
	 */
	public static Date getFirstDayOfWeek(Calendar c, int year, int week) {
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		return getFirstDayOfWeek(c, cal.getTime());
	}

	/**
	 * @Title: getFirstDayOfWeek
	 * @Description: 取得指定日期所在周的第一天
	 * @param @param c 日期实例
	 * @param @param date 当前日期
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014年10月24日 下午3:59:49
	 */
	public static Date getFirstDayOfWeek(Calendar c, Date date) {
		c.setFirstDayOfWeek(Calendar.MONDAY); // 设置是从周一开始为第一天
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}

	/**
	 * @Title: getLastDayOfWeek
	 * @Description: 取得指定日期所在周的最后一天
	 * @param @param c 日期实例
	 * @param @param date 当前日期
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014年10月24日 下午4:01:05
	 */
	public static Date getLastDayOfWeek(Calendar c, Date date) {
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // 设置是从周日为最后一天
		return c.getTime();
	}

	/**
	 * @Title: getFirstDayOfWeek
	 * @Description: 取得当前日期所在周的第一天
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014年10月24日 上午10:43:43
	 */
	public static Date getFirstDayOfWeek() {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY); // 设置是从周一开始为第一天
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}

	/**
	 * @Title: getLastDayOfWeek
	 * @Description: 取得当前日期所在周的最后一天
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014年10月24日 上午10:44:15
	 */
	public static Date getLastDayOfWeek() {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // 设置是从周日为最后一天
		return c.getTime();
	}

	/**
	 * @Title: getCurrYearFirst
	 * @Description: 获取某年第一天日期
	 * @param @param year
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014年10月24日 上午10:56:50
	 */
	public static Date getCurrYearFirst(int year) {
		Calendar c = new GregorianCalendar();
		c.clear();
		c.set(Calendar.YEAR, year);
		Date currYearFirst = c.getTime();
		return currYearFirst;
	}

	/**
	 * @Title: getCurrYearLast
	 * @Description: 获取某年最后一天日期
	 * @param @param year
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014年10月24日 上午10:56:41
	 */
	public static Date getCurrYearLast(int year) {
		Calendar c = new GregorianCalendar();
		c.clear();
		c.set(Calendar.YEAR, year);
		c.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = c.getTime();
		return currYearLast;
	}

	/**
	 * @Title: getDHWeekList
	 * @Description: 获取指定年份所有周信息
	 * @param @param c 日期实例
	 * @param @param year 指定年份
	 * @param @param weekCounts 周数
	 * @param @return
	 * @return List<DHWeekInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年10月24日 下午4:02:10
	 */
	public static List<DHWeekInfo> getDHWeekList(Calendar c, int year,int weekCounts) {
		List<DHWeekInfo> list = new ArrayList<DHWeekInfo>();
		final DateFormat sdf = new SimpleDateFormat(COMMON_DATE, Locale.CHINA);
		for (int i = weekCounts; i > 0; i--) {
			DHWeekInfo dhWeekInfo = new DHWeekInfo();
			dhWeekInfo.setFYear(year);
			dhWeekInfo.setFIndex(i);
			dhWeekInfo.setFStartDate(sdf.format(getFirstDayOfWeek(c, year,i - 1)));
			dhWeekInfo.setFEndDate(sdf.format(getLastDayOfWeek(c,getFirstDayOfWeek(c, year, i - 1))));
			list.add(dhWeekInfo);
		}
		return list;
	}

	/**
	 * @Title: dateCompare
	 * @Description: 两个时间比较
	 * @param @param date1
	 * @param @param date2
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2015年1月16日 上午10:40:06
	 */
	public static int dateCompare(Calendar c, String date1, String date2) {
		final DateFormat df = new SimpleDateFormat(COMPARE_DATE, Locale.CHINA);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
