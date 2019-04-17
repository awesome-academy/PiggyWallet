package com.thailam.piggywallet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;

public class TypeFormatUtils {
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
    private static final String FORMAT_D_M_Y = "%d-%d-%d";
    private static final int TRILLION_THRESHOLD = 1000000000;
    private static final String TRILLION_SUBFIX = "T";
    private static final int MILLION_THRESHOLD = 1000000;
    private static final String MILLION_SUBFIX = "M";
    private static final int THOUSAND_THRESHOLD = 1000;
    private static final String THOUSAND_SUBFIX = "K";

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

    public static String doubleToMoneyString(double amount) {
        double formattedAmount = Math.round(amount * 10) / 10.0;
        String result = "0";
        if (formattedAmount > TRILLION_THRESHOLD) {
            result = formattedAmount / TRILLION_THRESHOLD + TRILLION_SUBFIX;
        } else if (formattedAmount > MILLION_THRESHOLD) {
            result = amount / MILLION_THRESHOLD + MILLION_SUBFIX;
        } else if (formattedAmount > THOUSAND_THRESHOLD) {
            result = formattedAmount / THOUSAND_THRESHOLD + THOUSAND_SUBFIX;
        }
        return result;
    }
}
