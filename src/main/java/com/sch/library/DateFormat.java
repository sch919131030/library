package com.sch.library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DateFormat {
    private static final String PATTERN = "yyyy-MM-dd";

    private static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat(PATTERN));

    public static Date formatDate(String date) throws ParseException {
        return dateFormatThreadLocal.get().parse(date);
    }

    public static String formatDateToString(Date date) {
        return dateFormatThreadLocal.get().format(date);
    }
}
