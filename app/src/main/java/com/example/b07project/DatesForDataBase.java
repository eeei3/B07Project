package com.example.b07project;

import java.util.Calendar;

/*
 * Helpful methods for working with firebase
 * Can use getStartOfDay() and getEndOfDay() to create a range query
 * that gets all emissions created within that day
 */
public class DatesForDataBase {
    /*
    * getStartOfDay returns the timestamp for the start of the day (00:00:00.000) for that date
    * @TimeMillis takes a time in milliseconds
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

    /*
     * getStartOfDay returns the timestamp for the end of the day (00:00:00.000) for that date
     * @TimeMillis takes a time in milliseconds
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

    /*
     * getStartOfDay returns the timestamp for the start of the month (00:00:00.000) for that date
     * @TimeMillis takes a time in milliseconds
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

    /*
     * getStartOfDay returns the timestamp for the end of the month (00:00:00.000) for that date
     * @TimeMillis takes a time in milliseconds
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
}
