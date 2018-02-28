package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public DateUtil() {
    }

    public static long getCurDate() {
        return (new Date()).getTime();
    }

    public static String getCurDate(String partten) {
        SimpleDateFormat format = new SimpleDateFormat(partten);
        return format.format(new Date());
    }

    public static java.sql.Date converterToSqldate(String sdate, String partten) {
        SimpleDateFormat format = new SimpleDateFormat(partten);

        try {
            Date e = format.parse(sdate);
            return new java.sql.Date(e.getTime());
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Timestamp converterToSqlDateTime(String sdate, String partten) {
        SimpleDateFormat format = new SimpleDateFormat(partten);

        try {
            Date e = format.parse(sdate);
            return new Timestamp(e.getTime());
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static String getSectionFormatDate(long dateValue) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd EEEE");
        Date date = new Date(dateValue);
        return format.format(date);
    }

    public static String formatSqlDate(java.sql.Date sqlDate, String partten) {
        if(sqlDate == null) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat(partten);
            Date date = new Date(sqlDate.getTime());
            return format.format(date);
        }
    }

    public static String formatDate(Date date, String partten) {
        if(date == null) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat(partten);
            return format.format(date);
        }
    }

    public static long addDay(long curDate, int days) {
        Date date = new Date(curDate);
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(5, days);
        return calender.getTime().getTime();
    }

    public static long getDaysBetweenTimes(long date) {
        return (getCurDate() - date) / 1000L / 86400L;
    }

    public static long getHoursBetweenTimes(long date) {
        return (getCurDate() - date) / 1000L / 3600L;
    }

    public static long buildMsgId() {
        return 0L;
    }
}
