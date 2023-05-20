package org.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static String sFormat =  "yyyy-MM-dd";

    public static Date getDate(String sDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(sFormat);
        return format.parse(sDate);
    }

    public static Date getDate(String sDate, String anotherFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(anotherFormat);
        return format.parse(sDate);
    }
}
