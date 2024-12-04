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
    public static String getFormattedDate(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timeInMillis));
    }
}