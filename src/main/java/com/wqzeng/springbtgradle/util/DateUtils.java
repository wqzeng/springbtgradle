package com.wqzeng.springbtgradle.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.data.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DateUtils {
    public final static String DefaultDatePattern = "yyyy-MM-dd";
    public final static String DefaultDateTimePattern = "yyyy-MM-dd HH:mm:ss";
    public final static String DefaultDateTime4MinutePattern = "yyyy-MM-dd HH:mm";
    public final static String DefaultDateTime4HourPattern = "yyyy-MM-dd HH";
    public final static String SimpleDatePattern = "yyyyMMdd";
    public final static String DefaultTimePattern = "HH:mm:ss";
    public final static String DefaultPatternHHMM = "HH:mm";
    public final static String DefaultPatternHHMMHOURLY = "HH:00";
    public final static String DefaultPatternHH = "HH";
    public final static String ChineseDatePattern = "y年M月d日";
    public final static String DateTimePattern_yyyyMMddHHmm = "yyyyMMddHHmm";
    public static final Date DB_DEFAULT_DATE = parseDate("1970-01-01 08:00:01", DefaultDateTimePattern);
    public static final Date LONG_DATE = parseDate("2089-01-01 00:00:00", DefaultDateTimePattern);

    /**
     * 参数date不是db中的默认值
     * @param date
     * @return
     */
    public static final boolean isNotDbDefaultDate(Date date) {
        return null != date && date.after(DB_DEFAULT_DATE);
    }

    public static String toString(Date date, String formatString) {
        return FastDateFormat.getInstance(formatString).format(date);
    }

	public static Date parseDate(final String str, final String... parsePatterns) {
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(str, parsePatterns);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

    /**
     * @return yyyy-MM-dd HH:mm:ss的字符串
     */
    public static String toDateTimeString(Date date) {
        return toString(date, DefaultDateTimePattern);
    }

    /**
     * @return yyyy-MM-dd的字符串
     */
    public static String toDateString(Date date) {
        return toString(date, DefaultDatePattern);
    }

    public static Date toDateTime(String str) {
        return toDate(str, DefaultDateTimePattern);
    }

    public static Date toDate(String str) {
        return toDate(str, DefaultDatePattern);
    }

    public static Date toDate(String str, String formatString) {
        if(StringUtils.isBlank(str)) {
            return null;
        }
        try {
            return FastDateFormat.getInstance(formatString).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 毫秒转 yyyy-MM-dd HH:mm:ss的字符串
     */
    public static String msToDateTimeString(Long msecond) {
        if(msecond == null){
            return null;
        }
        return toDateTimeString(new Date(msecond));
    }

    /**
     * 秒转 yyyy-MM-dd HH:mm:ss的字符串
     */
    public static String toDateTimeString(long second) {
        return toDateTimeString(new Date(second * 1000L));
    }

    /**
     * 秒转 yyyy-MM-dd的字符串
     */
    public static String toDateString(long second) {
        return toDateTimeString(new Date(second * 1000L));
    }

    public static String toString(long second, String formatString) {
        return toString(new Date(second * 1000L), formatString);
    }

    public static int unixTimestamp(Date date) {
        return (int) (date.getTime() / 1000L);
    }

    public static int unixTimestamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static Date fromUnixtime(long second) {
        return new Date(second * 1000L);
    }

    /**
     * 返回指定日期的0点0分0秒000
     */
    public static Date toZeroTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 返回两个整点小时时间之间的小时数，入参必须是整点，否则有误差
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> between(Date startDate, Date endDate) {
        List<Date> dateList = Lists.newArrayList();
        long hours = (endDate.getTime() - startDate.getTime()) / (1 * 60 * 60 * 1000); // 相差小时数
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i <= hours; i++) {
            cal.setTime(startDate);
            cal.add(Calendar.HOUR_OF_DAY, i);
            dateList.add(cal.getTime());
        }
        return dateList;
    }

    /**
     * 返回开始到结束时间所有的日期
     *
     * @param startDateStr （yyyy-MM-dd） 开始时间
     * @param endDateStr   （yyyy-MM-dd） 结束时间
     * @return
     */
    public static List<String> between(String startDateStr, String endDateStr) {
        List<String> dateList = new ArrayList<String>();
        Date endDate = toDate(endDateStr);
        Date startDate = toDate(startDateStr);
        long day = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000); // 相差天数
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i <= day; i++) {
            cal.setTime(startDate);
            cal.add(Calendar.DAY_OF_MONTH, i);
            dateList.add(toDateString(cal.getTime()));
        }
        return dateList;
    }

    /**
     * 按照给定的日期格式，返回开始到结束时间所有的日期
     *
     * @param startDateStr （yyyy-MM-dd） 开始时间
     * @param endDateStr   （yyyy-MM-dd） 结束时间
     * @param formatString
     * @return
     */
    public static List<String> between(String startDateStr, String endDateStr, String formatString) {
        List<String> dateList = new ArrayList<String>();
        Date endDate = toDate(endDateStr);
        Date startDate = toDate(startDateStr);
        long day = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000); // 相差天数
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i <= day; i++) {
            cal.setTime(startDate);
            cal.add(Calendar.DAY_OF_MONTH, i);
            dateList.add(toString(cal.getTime(), formatString));
        }
        return dateList;
    }

    /**
     * 将时间转换成昨天
     */
    public static Date toYesterday(Date date) {
        return add(date, Calendar.DAY_OF_YEAR, -1);
    }

    /**
     * 将时间转换成明天
     */
    public static Date toTommorow(Date date) {
        return add(date, Calendar.DAY_OF_YEAR, 1);
    }

    public static Date add(Date date, int field, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(field, value);
        return cal.getTime();
    }

    /**
     * 得到某年某月有多少天
     */
    public static int getMonthDays(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到年份
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.get(Calendar.YEAR);
    }

    /**
     * 得到月份
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到某天的星期.1~7
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SUNDAY ? 7 : dayOfWeek - 1;
    }

    public static boolean isToday(Date date) {
        return isSameDay(new Date(), date);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static Date toDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 判断字符串是否为指定pattern的日期格式
     * @param date
     * @param datePattern
     * @return
     */
    public static boolean isDate(String date, String datePattern) {
        if(StringUtils.isBlank(date)) {
            return false;
        }
        try {
            FastDateFormat.getInstance(datePattern).parse(date);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static Pair<Boolean, Date> parseDateQuietly(String dateStr, String datePattern) {
        if(StringUtils.isBlank(dateStr)) {
            return Pair.of(Boolean.FALSE, null);
        }
        try {
            Date date = FastDateFormat.getInstance(datePattern).parse(dateStr);
            return Pair.of(Boolean.TRUE, date);
        } catch (ParseException e) {
            return Pair.of(Boolean.FALSE, null);
        }
    }



    /**
     * 时间戳转换天数
     */
    public static long transDayNum(Long timetemp){
        if(timetemp != null && timetemp > 0){
            long oneDay = 24L * 60L * 60L * 1000L;
            return timetemp / oneDay;
        }
        return 0;
    }

	public static boolean isBetween(Date date, Date start, Date end) {
		if (date == null || start == null || end == null) {
			return false;
		}
		return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
	}

    /**
     * 计算两个日期之间相差多少个小时
     * @param endTime
     * @param beginTime
     * @return
     */
    public static int getHoursBetweenDays(Date endTime, Date beginTime) {
        if (null == endTime || null == beginTime) {
            return 0;
        }

        return Math.toIntExact((endTime.getTime() - beginTime.getTime()) / 60 / 60 / 1000);
    }

    /**
     * 校验时间字符串是否符合格式
     * @param dateStr 时间字符串
     * @param pattern 格式字符串， 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static boolean isValidDate(String dateStr, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
        	format.setLenient(false);
        	format.parse(dateStr);
        } catch (ParseException e) {
        	return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 对比两个字符串格式时间大小
     * @param date 第一个时间
     * @param anotherDate 第二个时间
     * @param pattern 时间格式
     * @return -1:date<anotherDate 0：date=anotherDate 1:date>anotherDate
     */
	public static int compareDate(String date, String anotherDate, String pattern) {
		try {
			return toDate(date, pattern).compareTo(toDate(anotherDate, pattern));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    public static int hourToMinute(int hours) {
        return hours * 60;
    }

    public static int minuteToHour(int minutes) {
        return minutes / 60;
    }

    /**
     * 获取本周周一0点时间
     */
    public static Date getMondayZeroOfThisWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * mysql 时间戳最大值只能到 2038-01-19 03:14:07
     *
     * @return 2038-01-01 00:00:00
     */
    public static Date getMaxDate() {
        return parseDate("2038-01-01 00:00:00", DefaultDateTimePattern);
    }

	/**
	 * 判断是否 2021-10-11 11：00：00 这样的整点时间
	 * @param date
	 * @return
	 */
	public static Boolean isOclock(Date date) {
		if (Objects.isNull(date)) {
			return false;
		}

		return date.getTime() % (60 * 60 * 1000) == 0;
	}
}
