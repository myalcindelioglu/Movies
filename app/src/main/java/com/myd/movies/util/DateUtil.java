package com.myd.movies.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MYD on 4/12/18.
 *
 */

public class DateUtil {
    public static String epochToString(long epochDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return sdf.format(new Date(epochDate));
    }
}
