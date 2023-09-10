package com.example.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_yyyy = "yyyy";
    public static final String FORMAT_yyyy_MM = "yyyy-MM";
    public static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String FORMAT_yyyy_MM_dd_HH = "yyyy-MM-dd HH";
    public static final String FORMAT_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_yyyy_MM_dd_T_HH_mm_ss_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;

    public static final Date parseDate(String strDate, String format) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(format);

        try {
            date = df.parse(strDate);
        } catch (ParseException e) {}

        return (date);
    }

    public static Date now() {
        return new Date();
    }

    public static int getYear(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        return calendar.get(1);
    }

    public static int getDayOfMonth(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        return calendar.get(5);
    }

    public static int getMonthOfYear(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        return calendar.get(2) + 1;
    }

    public static int getDayOfWeek(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.setFirstDayOfWeek(2);
        int day = calendar.get(7) - 1;
        if (day == 0) {
            day = 7;
        }
        return day;
    }

    public static Date getTodayEndTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }

    public static int getDayOfYear(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        return calendar.get(6);
    }

    public static int getHourField(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        return calendar.get(11);
    }

    public static int getMinuteField(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        return calendar.get(12);
    }

    public static int getSecondField(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        return calendar.get(13);
    }

    public static int getMillisecondField(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        return calendar.get(14);
    }

    public static long getDays(Date datetime) {
        return datetime.getTime() / 86400000L;
    }

    public static long getHours(Date datetime) {
        return datetime.getTime() / 3600000L;
    }

    public static long getMinutes(Date datetime) {
        return datetime.getTime() / 60000L;
    }

    public static long getSeconds(Date datetime) {
        return datetime.getTime() / 1000L;
    }

    public static long getMilliseconds(Date datetime) {
        return datetime.getTime();
    }

    public static int getWeekOfMonth(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.setFirstDayOfWeek(2);
        return calendar.get(4);
    }

    public static int getWeekOfYear(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.setFirstDayOfWeek(2);
        return calendar.get(3);
    }

    public static int getMaxDateOfMonth(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        return calendar.getActualMaximum(5);
    }

    public static int getMaxDateOfYear(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        return calendar.getActualMaximum(6);
    }

    public static int getMaxWeekOfMonth(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.setFirstDayOfWeek(2);
        return calendar.getActualMaximum(4);
    }

    public static int getMaxWeekOfYear(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.setFirstDayOfWeek(2);
        return calendar.getActualMaximum(3);
    }

    public static Date toDatetime(String datetimeString, String format) {
        Date result = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            result = simpleDateFormat.parse(datetimeString);
        } catch (Exception e) {
            log.error("Fail to parse datetime.", e);
        }
        return result;
    }

    public static String toString(Date datetime) {
        return toString(datetime, null);
    }

    public static String toString(Date datetime, String format) {
        String result = null;
        SimpleDateFormat simpleDateFormat = null;
        try {
            if (StringUtils.isNoneBlank(new CharSequence[] { format })) {
                simpleDateFormat = new SimpleDateFormat(format);
            } else {
                simpleDateFormat = new SimpleDateFormat(FORMAT_yyyy_MM_dd_HH_mm_ss);
            }
            result = simpleDateFormat.format(datetime);
        } catch (Exception e) {
            log.error("Fail to parse datetime.", e);
        }
        return result;
    }

    public static int diffYears(Date datetime1, Date datetime2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime1);
        int month1 = calendar.get(1);
        calendar.setTime(datetime2);
        int month2 = calendar.get(1);
        return month1 - month2;
    }

    public static int diffMonths(Date datetime1, Date datetime2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime1);
        int month1 = calendar.get(2);
        calendar.setTime(datetime2);
        int month2 = calendar.get(2);
        return month1 - month2;
    }

    public static long diffDays(Date datetime1, Date datetime2) {
        return (datetime1.getTime() - datetime2.getTime()) / 86400000L;
    }

    public static long diffDaysByCalendar(Date datetime1, Date datetime2)
            throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy_MM_dd);
            return diffDays(sdf.parse(sdf.format(datetime1)),
                    sdf.parse(sdf.format(datetime2)));
        } catch (Exception e) {
            throw e;
        }
    }

    public static long diffHours(Date datetime1, Date datetime2) {
        return (datetime1.getTime() - datetime2.getTime()) / 3600000L;
    }

    public static long diffMinutes(Date datetime1, Date datetime2) {
        return (datetime1.getTime() - datetime2.getTime()) / 60000L;
    }

    public static long diffSeconds(Date datetime1, Date datetime2) {
        return (datetime1.getTime() - datetime2.getTime()) / 1000L;
    }

    public static Date add(Date datetime1, Date datetime2) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(datetime1.getTime() + datetime2.getTime());
        return date;
    }

    public static Date minus(Date datetime1, Date datetime2) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(datetime1.getTime() - datetime2.getTime());
        return date;
    }

    public static boolean isBefore(Date datetime1, Date datetime2) {
        return datetime2.getTime() - datetime1.getTime() > 0L;
    }

    public static boolean isBeforeOrEqual(Date datetime1, Date datetime2) {
        return datetime2.getTime() - datetime1.getTime() >= 0L;
    }

    public static boolean isEqual(Date datetime1, Date datetime2) {
        return datetime2.getTime() == datetime1.getTime();
    }

    public static boolean isAfter(Date datetime1, Date datetime2) {
        return datetime2.getTime() - datetime1.getTime() < 0L;
    }

    public static boolean isAfterOrEqual(Date datetime1, Date datetime2) {
        return datetime2.getTime() - datetime1.getTime() <= 0L;
    }

    public static Date addMilliseconds(Date datetime, int milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.roll(14, milliseconds);
        return calendar.getTime();
    }

    public static Date addSeconds(Date datetime, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.roll(13, seconds);
        return calendar.getTime();
    }

    public static Date addMinutes(Date datetime, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.roll(12, minutes);
        return calendar.getTime();
    }

    public static Date addHours(Date datetime, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.roll(10, hours);
        return calendar.getTime();
    }

    public static Date addDays(Date datetime, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.roll(5, days);
        return calendar.getTime();
    }

    public static Date addMonths(Date datetime, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.roll(2, months);
        return calendar.getTime();
    }

    /**
     * 将普通时间格式转换成相差8小时的timestamp格式，参数Date类型
     * 比如普通时间格式：new Date()
     * 转换后的timestamp时间：2018-01-05T03:02:53Z
     * @param datetime 类型为Date
     * @return
     */
    public static String dateToTimestamp(Date datetime, String format) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)-8);
        Date endDate=null;
        endDate =sdf.parse(sdf.format(calendar.getTime()));
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String date=sdf.format(endDate);

        return date;
    }

    /**
     * 将timestamp格式转换成正常的时间格式，参数String类型
     * 比如timestamp格式时间："2018-01-05T03:03:05Z"
     * 转换后的DateString时间："2018-01-05 03:03:05"
     * @param timestamp  参数格式timestamp
     * @return
     */
    public static String timestampToDateString(String timestamp, String sourceFormat, String targetFormat){
        String date = "";
        try {
            SimpleDateFormat source = new SimpleDateFormat(sourceFormat);
            SimpleDateFormat target = new SimpleDateFormat(targetFormat);

            long time = source.parse(timestamp).getTime();
            date = target.format(new Date(time).getTime());
        } catch (Exception e1) {
            return "";
        }
        return date;
    }

    /**
     * 将普通时间格式转换成相差8小时的timestamp时间格式，参数String类型
     * 比如string时间："2018-01-05 11:03:05"
     * 转换后的timestamp时间：2018-01-05T03:03:05Z
     * @param date 类型为string
     * @return
     */
    public static String formatStringToTimestamp(String date, String sourceFormat, String targetFormat) {
        String timestamp = "";
        SimpleDateFormat source = new SimpleDateFormat(sourceFormat);
        SimpleDateFormat target = new SimpleDateFormat(targetFormat);
        try {
            long time = source.parse(date).getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            timestamp = target.format(calendar.getTime());
        } catch (ParseException e1) {
            return "";
        }

        return timestamp;
    }

    /**
     * 时间戳转String
     * @param time 时间戳
     * @param format 时间格式
     * @return
     */
    public static String formatLongToString(Long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        // 设置时区,解决生产端时间戳格式化差8小时的问题
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String timestamp = sdf.format(time);

        return timestamp;
    }

    public static boolean isSameSecond(Date datetime1, Date datetime2) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy_MM_dd_HH_mm_ss);
        return sdf.format(datetime1).equals(sdf.format(datetime2));
    }

    public static boolean isSameMinute(Date datetime1, Date datetime2) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy_MM_dd_HH_mm);
        return sdf.format(datetime1).equals(sdf.format(datetime2));
    }

    public static boolean isSameHour(Date datetime1, Date datetime2) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy_MM_dd_HH);
        return sdf.format(datetime1).equals(sdf.format(datetime2));
    }

    public static boolean isSameDay(Date datetime1, Date datetime2) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy_MM_dd);
        return sdf.format(datetime1).equals(sdf.format(datetime2));
    }

    public static boolean isSameMonth(Date datetime1, Date datetime2) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy_MM);
        return sdf.format(datetime1).equals(sdf.format(datetime2));
    }

    public static boolean isSameYear(Date datetime1, Date datetime2) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy);
        return sdf.format(datetime1).equals(sdf.format(datetime2));
    }

    public static boolean isSameWeek(Date datetime1, Date datetime2) {
        boolean result = false;
        int year1 = getYear(datetime1);
        int year2 = getYear(datetime2);
        int month1 = getMonthOfYear(datetime1);
        int month2 = getMonthOfYear(datetime2);
        int dayOfWeek1 = getDayOfWeek(datetime1);
        int dayOfWeek2 = getDayOfWeek(datetime2);
        long diffDays = diffDays(datetime1, datetime2);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        if (Math.abs(diffDays) < calendar.getMaximum(7)) {
            if (year1 == year2) {
                if (getWeekOfYear(datetime1) == getWeekOfYear(datetime2)) {
                    result = true;
                }
            } else if ((year1 - year2 == 1) && (month1 == 1) && (month2 == 12)) {
                calendar.set(year1, 0, 1);
                int dayOfWeek = getDayOfWeek(calendar.getTime());
                if ((dayOfWeek2 < dayOfWeek) && (dayOfWeek <= dayOfWeek1)) {
                    result = true;
                }
            } else if ((year2 - year1 == 1) && (month1 == 12) && (month2 == 1)) {
                calendar.set(year2, 0, 1);
                int dayOfWeek = getDayOfWeek(calendar.getTime());
                if ((dayOfWeek1 < dayOfWeek) && (dayOfWeek <= dayOfWeek2)) {
                    result = true;
                }
            }
        }
        return result;
    }
}
