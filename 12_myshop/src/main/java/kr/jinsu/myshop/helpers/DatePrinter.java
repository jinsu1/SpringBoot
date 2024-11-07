package kr.jinsu.myshop.helpers;

import java.util.Calendar;

public class DatePrinter {
    public static void printDateTime(Calendar cal) {
        int yy = cal.get(Calendar.YEAR);
        int mm = cal.get(Calendar.MONTH) + 1;
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        int hh = cal.get(Calendar.HOUR_OF_DAY);
        int mi = cal.get(Calendar.MINUTE);
        int ss = cal.get(Calendar.SECOND);

        System.out.printf("%04d년 %02d월 %02d일 %02d시 %02d분 %02d초\n", yy, mm, dd, hh, mi, ss);
    }
}
