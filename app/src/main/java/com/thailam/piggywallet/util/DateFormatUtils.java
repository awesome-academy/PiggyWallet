package com.thailam.piggywallet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;

public class DateFormatUtils {
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
    private static final String FORMAT_D_M_Y = "%d-%d-%d";

    public static long getLongFromDate(int year, int month, int dayOfMonth) throws ParseException {
        String dateStr = String.format(Locale.US, FORMAT_D_M_Y, dayOfMonth, month, year);
        Date d = TIME_FORMATTER.parse(dateStr);
        return d.getTime();
    }

    public static String getDateFromLong(long time) {
        return android.text.format.DateUtils.getRelativeTimeSpanString(time,
                new Date().getTime(),
                DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE).toString();
    }
}
