package com.example.b07project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Helpful methods for working with firebase
 * Can use getStartOfDay() and getEndOfDay() to create a range query
 * that gets all emissions created within that day
 */
public class DatesForDataBase {
    /**
     * getStartOfDay returns the timestamp for the start of the day (00:00:00.000) for that date
     *
     * @param TimeMillis  takes a time in milliseconds
     */
    public static long getStartOfDay(long TimeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(TimeMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * getStartOfDay returns the timestamp for the end of the day (00:00:00.000) for that date
     *
     * @param TimeMillis  takes a time in milliseconds
     */
    public static long getEndOfDay(long TimeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(TimeMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * getStartOfDay returns the timestamp for the start of the month (00:00:00.000) for that date
     *
     * @param TimeMillis  takes a time in milliseconds
     */
    public static long getStartOfMonth(long TimeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(TimeMillis);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * getStartOfDay returns the timestamp for the end of the month (00:00:00.000) for that date
     *
     * @param currentTimeMillis  takes a time in milliseconds
     */
    public static long getEndOfMonth(long currentTimeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * getStartOfYear returns the start of the year.
     *
     * @param timeInMillis time in milliseconds
     * @return the start of the year in milliseconds
     */
    public static long getStartOfYear(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * getEndOfYear returns the start of the year.
     *
     * @param timeInMillis time in milliseconds
     * @return the end of the year in milliseconds
     */
    public static long getEndOfYear(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * getFormattedDate formats the date from milliseconds to YYYY-MM-DD HH:mm:ss
     *
     * @param timeInMillis time in milliseconds
     * @return the formated date
     */
    public static String getFormattedDate(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timeInMillis));
    }
}