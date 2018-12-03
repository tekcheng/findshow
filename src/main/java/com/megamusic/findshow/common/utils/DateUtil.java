package com.megamusic.findshow.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtil {


    public static final DateTimeFormatter FORMATTER_YYYY_MM = DateTimeFormatter.ofPattern("yyyy-MM")
            .withZone(ZoneId.systemDefault());
    public static final DateTimeFormatter FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.systemDefault());
    public static final DateTimeFormatter FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd")
            .withZone(ZoneId.systemDefault());
    public static final DateTimeFormatter FORMATTER_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());
    public static final DateTimeFormatter FORMATTER_HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss")
            .withZone(ZoneId.systemDefault());
    public static final DateTimeFormatter FORMATTER_MD = DateTimeFormatter.ofPattern("M月d日")
            .withZone(ZoneId.systemDefault());
    public static final DateTimeFormatter FORMATTER_YMD = DateTimeFormatter.ofPattern("Y年M月d日")
            .withZone(ZoneId.systemDefault());
    public static final DateTimeFormatter FORMATTER_M = DateTimeFormatter.ofPattern("M月")
            .withZone(ZoneId.systemDefault());
    private static final ZoneId zone = ZoneId.systemDefault();

    public static String timestampToString(Long timestamp, DateTimeFormatter formatter) {
        if (timestamp == null) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zone);
        return localDateTime.format(formatter);
    }

    public static Date strDateTimeToDate(String dateTimeStr) {
        return Date.from(
                Instant.from(LocalDateTime.parse(dateTimeStr, FORMATTER_YYYY_MM_DD_HH_MM_SS).atZone(ZoneId.systemDefault())));
    }

    /**
     * 将yyyy-MM-dd时间格式转换为date
     * @param dateTimeStr
     * @return
     */
    public static Date strDateTimeToDate2(String dateTimeStr) {
        return Date.from(
                Instant.from(LocalDateTime.parse(dateTimeStr, FORMATTER_YYYY_MM_DD).atZone(ZoneId.systemDefault())));
    }

    public static LocalDate parseStrToLocalDate(String text, DateTimeFormatter formatter) {
        return LocalDate.parse(text, formatter);
    }

    /**
     * 获取当前日期的字符串值，格式为“YYYYMMDD”
     *
     * @return
     */
    public static String getNowDateYYYYMMDDString() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), zone)
                .format(FORMATTER_YYYYMMDD);
    }

    /**
     * 获取指定时间的字符串格式，格式为“YYYYMMDD”
     *
     * @param timeMillis
     * @return
     */
    public static String getNowDateYYYYMMDDString(long timeMillis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), zone).format(FORMATTER_YYYYMMDD);
    }


    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);

        return Date.from(zdt.toInstant());
    }

    /**
     * 将日期格式为yyyy-MM-dd转换为yyyy年MM月
     * @param date
     * @return
     */
    public static String dateTransfertoYearString(String date){
        date = date.substring(0,7);
        String[] time = date.split("-");
        return time[0] + "年" + Integer.valueOf(time[1]) + "月";
    }

    /**
     * 将日期格式为yyyy-MM-dd转换为yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static String dateStringParse(String date){
        return date == null ? null : date + " 00:00:00";
    }

    public static Long dateToLong(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        }catch (Exception e){
            log.error("时间格式转换异常:{}",e);
            return null;
        }

        return date.getTime();
    }

    public static String IntegerDateToString(Integer dueDay){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        String dueDayString = null;
        if(dueDay < 10){
            dueDayString = new String("0" + dueDay);
        }else {
            dueDayString = String.valueOf(dueDay);
        }
        return dateStringParse(date.substring(0, 8) + dueDayString);
    }

    /**
     * 比较目标日期和当前日期是否属于同一个月（yyyy-MM-dd）
     * @param fromDate
     * @return
     */
    public static Boolean compareTwoMonth(String fromDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(fromDate);
        } catch (ParseException e) {
            log.error("时间格式转换异常!");
        }
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(date);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.add(Calendar.MONTH, -1);
        return fromCalendar.get(Calendar.MONTH) == toCalendar.get(Calendar.MONTH)? true : false;
    }
}
