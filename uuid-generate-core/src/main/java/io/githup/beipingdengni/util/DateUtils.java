package io.githup.beipingdengni.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author tianbeiping
 * @Date 2021/8/1 23:16:28
 * @Description TODO
 */
public abstract class DateUtils {
    /**
     * Patterns
     */
    public static final String DAY_PATTERN = "yyyy-MM-dd";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final Date DEFAULT_DATE = DateUtils.parseByDayPattern("1970-01-01");

    /**
     * Parse date by 'yyyy-MM-dd' pattern
     *
     * @param str
     * @return
     */
    public static Date parseByDayPattern(String str) {
        return parseDate(str, DAY_PATTERN);
    }

    /**
     * Parse date by 'yyyy-MM-dd HH:mm:ss' pattern
     *
     * @param str
     * @return
     */
    public static Date parseByDateTimePattern(String str) {
        return parseDate(str, DATETIME_PATTERN);
    }

    /**
     * Parse date without Checked exception
     *
     * @param str
     * @param pattern
     * @return
     * @throws RuntimeException when ParseException occurred
     */
    public static Date parseDate(String str, String pattern) {
        try {
            return parse(str, pattern);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Format date into string
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        return format(date, pattern);
    }

    /**
     * Format date by 'yyyy-MM-dd' pattern
     *
     * @param date
     * @return
     */
    public static String formatByDayPattern(Date date) {
        if (date != null) {
            return format(date, DAY_PATTERN);
        } else {
            return null;
        }
    }

    /**
     * Format date by 'yyyy-MM-dd HH:mm:ss' pattern
     *
     * @param date
     * @return
     */
    public static String formatByDateTimePattern(Date date) {
        return format(date, DATETIME_PATTERN);
    }

    /**
     * Get current day using format date by 'yyyy-MM-dd HH:mm:ss' pattern
     *
     * @return
     * @author yebo
     */
    public static String getCurrentDayByDayPattern() {
        Calendar cal = Calendar.getInstance();
        return formatByDayPattern(cal.getTime());
    }

    public static String format(Date date, String formatString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(date);
    }

    public static Date parse(String dateStr, String formatString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.parse(dateStr);
    }

}
