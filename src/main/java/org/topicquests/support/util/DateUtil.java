package org.topicquests.support.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class DateUtil {
	public static final long millisInDay = 86400000L;
	private static final String formatDefaultDate = "dd.MM.yyyy";
	private static final String formatDefaultDateMinimal = "d.M.yy";
	private static final String formatDefaultTimestamp = "yyyy-MM-dd HH:mm:ss";
	private static final String formatFriendlyTimestamp = "dd.MM.yyyy HH:mm:ss";
	private static final String format6chars = "yyyyMM";
	private static final String format8chars = "yyyyMMdd";
	private static final String formatIso8601 = "yyyy-MM-dd\'T\'HH:mm:ssZ";
	private static final String formatIso8601Day = "yyyy-MM-dd";
	private static final String formatRfc822 = "EEE, d MMM yyyy HH:mm:ss Z";

	public DateUtil() {
	}

	public static Date getStartOfDay(Date day) {
		return getStartOfDay(day, Calendar.getInstance());
	}

	public static Date getStartOfDay(Date day, Calendar cal) {
		if (day == null) {
			day = new Date();
		}

		cal.setTime(day);
		cal.set(11, cal.getMinimum(11));
		cal.set(12, cal.getMinimum(12));
		cal.set(13, cal.getMinimum(13));
		cal.set(14, cal.getMinimum(14));
		return cal.getTime();
	}

	public static Date getEndOfDay(Date day) {
		return getEndOfDay(day, Calendar.getInstance());
	}

	public static Date getEndOfDay(Date day, Calendar cal) {
		if (day == null) {
			day = new Date();
		}

		cal.setTime(day);
		cal.set(11, cal.getMaximum(11));
		cal.set(12, cal.getMaximum(12));
		cal.set(13, cal.getMaximum(13));
		cal.set(14, cal.getMaximum(14));
		return cal.getTime();
	}

	public static Date getStartOfHour(Date day) {
		return getStartOfHour(day, Calendar.getInstance());
	}

	public static Date getStartOfHour(Date day, Calendar cal) {
		if (day == null) {
			day = new Date();
		}

		cal.setTime(day);
		cal.set(12, cal.getMinimum(12));
		cal.set(13, cal.getMinimum(13));
		cal.set(14, cal.getMinimum(14));
		return cal.getTime();
	}

	public static Date getEndOfHour(Date day) {
		return getEndOfHour(day, Calendar.getInstance());
	}

	public static Date getEndOfHour(Date day, Calendar cal) {
		if (day != null && cal != null) {
			cal.setTime(day);
			cal.set(12, cal.getMaximum(12));
			cal.set(13, cal.getMaximum(13));
			cal.set(14, cal.getMaximum(14));
			return cal.getTime();
		} else {
			return day;
		}
	}

	public static Date getStartOfMinute(Date day) {
		return getStartOfMinute(day, Calendar.getInstance());
	}

	public static Date getStartOfMinute(Date day, Calendar cal) {
		if (day == null) {
			day = new Date();
		}

		cal.setTime(day);
		cal.set(13, cal.getMinimum(13));
		cal.set(14, cal.getMinimum(14));
		return cal.getTime();
	}

	public static Date getEndOfMinute(Date day) {
		return getEndOfMinute(day, Calendar.getInstance());
	}

	public static Date getEndOfMinute(Date day, Calendar cal) {
		if (day != null && cal != null) {
			cal.setTime(day);
			cal.set(13, cal.getMaximum(13));
			cal.set(14, cal.getMaximum(14));
			return cal.getTime();
		} else {
			return day;
		}
	}

	public static Date getStartOfMonth(Date day) {
		return getStartOfMonth(day, Calendar.getInstance());
	}

	public static Date getStartOfMonth(Date day, Calendar cal) {
		if (day == null) {
			day = new Date();
		}

		cal.setTime(day);
		cal.set(11, cal.getMinimum(11));
		cal.set(12, cal.getMinimum(12));
		cal.set(13, cal.getMinimum(13));
		cal.set(14, cal.getMinimum(14));
		cal.set(5, 1);
		return cal.getTime();
	}

	public static Date getEndOfMonth(Date day) {
		return getEndOfMonth(day, Calendar.getInstance());
	}

	public static Date getEndOfMonth(Date day, Calendar cal) {
		if (day == null) {
			day = new Date();
		}

		cal.setTime(day);
		cal.set(11, cal.getMaximum(11));
		cal.set(12, cal.getMaximum(12));
		cal.set(13, cal.getMaximum(13));
		cal.set(14, cal.getMaximum(14));
		cal.set(5, 1);
		cal.add(2, 1);
		cal.add(5, -1);
		return cal.getTime();
	}

	public static Date getNoonOfDay(Date day, Calendar cal) {
		if (day == null) {
			day = new Date();
		}

		cal.setTime(day);
		cal.set(11, 12);
		cal.set(12, cal.getMinimum(12));
		cal.set(13, cal.getMinimum(13));
		cal.set(14, cal.getMinimum(14));
		return cal.getTime();
	}

	public static Timestamp now() {
		return new Timestamp((new Date()).getTime());
	}

	public static String format(Date aDate, SimpleDateFormat aFormat) {
		if (aDate != null && aFormat != null) {
			synchronized (aFormat) {
				return aFormat.format(aDate);
			}
		} else {
			return "";
		}
	}

	public static Date parse(String aValue, SimpleDateFormat aFormat) throws ParseException {
		if (!StringUtils.isEmpty(aValue) && aFormat != null) {
			synchronized (aFormat) {
				return aFormat.parse(aValue);
			}
		} else {
			return null;
		}
	}

	public static boolean isValidDateRange(Date startDate, Date endDate) {
		return isValidDateRange(startDate, endDate, true);
	}

	public static boolean isValidDateRange(Date startDate, Date endDate, boolean equalOK) {
		return startDate != null && endDate != null ? (equalOK && startDate.equals(endDate) ? true : endDate.after(startDate)) : false;
	}

	public static SimpleDateFormat defaultDateFormat() {
		return friendlyDateFormat(true);
	}

	public static SimpleDateFormat minimalDateFormat() {
		return friendlyDateFormat(true);
	}

	public static SimpleDateFormat fullDateFormat() {
		return friendlyDateFormat(false);
	}

	public static SimpleDateFormat friendlyDateFormat(boolean minimalFormat) {
		return minimalFormat ? new SimpleDateFormat("d.M.yy") : new SimpleDateFormat("dd.MM.yyyy");
	}

	public static SimpleDateFormat defaultTimestampFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public static SimpleDateFormat friendlyTimestampFormat() {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	}

	public static SimpleDateFormat get8charDateFormat() {
		return new SimpleDateFormat("yyyyMMdd");
	}

	public static SimpleDateFormat get6charDateFormat() {
		return new SimpleDateFormat("yyyyMM");
	}

	public static SimpleDateFormat getIso8601DateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ssZ");
	}

	public static SimpleDateFormat getIso8601DayDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	public static SimpleDateFormat getRfc822DateFormat() {
		return new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
	}

	public static String defaultDate(Date date) {
		return format(date, defaultDateFormat());
	}

	public static String minimalDate(Date date) {
		return format(date, minimalDateFormat());
	}

	public static String fullDate(Date date) {
		return format(date, fullDateFormat());
	}

	public static String friendlyDate(Date date, boolean minimalFormat) {
		return format(date, friendlyDateFormat(minimalFormat));
	}

	public static String friendlyDate(Date date) {
		return format(date, friendlyDateFormat(true));
	}

	public static String defaultTimestamp(long timestamp) {
		return defaultTimestamp(new Date(timestamp));
	}

	public static Date fromIso8601(String timestamp) {
		System.out.println("DateUtil.fromIso8601- " + timestamp);
		Date result = null;
		String[] parts = timestamp.split("-");
		String big = parts[0];
		String middle = parts[1];
		String small = parts[2];
		String last = parts[3];
		GregorianCalendar c = new GregorianCalendar();
		String[] bigger = big.split("-");
		String day = StringUtils.substring(small, 0, 2);
		int yr = Integer.parseInt(big);
		int mo = Integer.parseInt(middle);
		int da = Integer.parseInt(day);
		day = StringUtils.substring(small, 3, 11);
		String[] smaller = day.split(":");
		int hr = Integer.parseInt(smaller[0]);
		int min = Integer.parseInt(smaller[1]);
		String x = smaller[2];
		int where = x.indexOf(46);
		if (where > -1) {
			x = StringUtils.substring(x, 0, where);
		}

		int sec = Integer.parseInt(x);
		c.set(yr, mo, da, hr, min, sec);
		result = new Date(c.getTimeInMillis());
		return result;
	}

	public static Date fromDefaultTimestamp(String timestamp) {
		System.out.println("DateUtil.fromDefaultTimestamp- " + timestamp);
		Date result = null;
		String[] parts = timestamp.split(" ");
		String big = parts[0];
		String small = parts[1];
		GregorianCalendar c = new GregorianCalendar();
		String[] bigger = big.split("-");
		int yr = Integer.parseInt(bigger[0]);
		int mo = Integer.parseInt(bigger[1]);
		int da = Integer.parseInt(bigger[2]);
		String[] smaller = small.split(":");
		int hr = Integer.parseInt(smaller[0]);
		int min = Integer.parseInt(smaller[1]);
		String x = smaller[2];
		int where = x.indexOf(46);
		if (where > -1) {
			x = StringUtils.substring(x, 0, where);
		}

		int sec = Integer.parseInt(x);
		c.set(yr, mo, da, hr, min, sec);
		result = new Date(c.getTimeInMillis());
		return result;
	}

	public static String defaultTimestamp(Date date) {
		return format(date, defaultTimestampFormat());
	}

	public static String friendlyTimestamp(Date date) {
		return format(date, friendlyTimestampFormat());
	}

	public static String format8chars(long timestamp) {
		return format8chars(new Date(timestamp));
	}

	public static String format8chars(Date date) {
		return format(date, get8charDateFormat());
	}

	public static String format6chars(Date date) {
		return format(date, get6charDateFormat());
	}

	public static String formatIso8601Day(Date date) {
		return format(date, getIso8601DayDateFormat());
	}

	public static String formatRfc822(Date date) {
		return format(date, getRfc822DateFormat());
	}

	public static String formatIso8601(Date date) {
		if (date == null) {
			return "";
		} else {
			String str = format(date, getIso8601DateFormat());
			StringBuffer sb = new StringBuffer();
			sb.append(StringUtils.substring(str, 0, str.length() - 2));
			sb.append(":");
			sb.append(StringUtils.substring(str, str.length() - 2));
			return sb.toString();
		}
	}

	public static Date parseIso8601(String value) throws Exception {
		return ISO8601DateParser.parse(value);
	}

	public static Date parseWeblogURLDateString(String dateString, TimeZone tz, Locale locale) {
		Date ret = new Date();
		SimpleDateFormat char8DateFormat = get8charDateFormat();
		SimpleDateFormat char6DateFormat = get6charDateFormat();
		ParsePosition pos;
		Calendar todayCal;
		if (dateString != null && dateString.length() == 8 && StringUtils.isNumeric(dateString)) {
			pos = new ParsePosition(0);
			ret = char8DateFormat.parse(dateString, pos);
			todayCal = null;
			Calendar todayCal = Calendar.getInstance();
			todayCal = Calendar.getInstance(tz, locale);
			todayCal.setTime(new Date());
			Date today = todayCal.getTime();
			if (ret.after(today)) {
				ret = today;
			}
		} else if (dateString != null && dateString.length() == 6 && StringUtils.isNumeric(dateString)) {
			pos = new ParsePosition(0);
			ret = char6DateFormat.parse(dateString, pos);
			todayCal = Calendar.getInstance();
			todayCal = Calendar.getInstance(tz, locale);
			todayCal.setTime(new Date());
			Date today = todayCal.getTime();
			if (ret.after(today)) {
				ret = today;
			}
		}

		return ret;
	}
}
