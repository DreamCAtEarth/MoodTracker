package com.poupel.benjamin.moodtracker;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil
{

    public static boolean isSameDate(Date dateToCompare, Date date)
    {
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");

        String moodDate = formatter.format(dateToCompare);
        String nowDate = formatter.format(date);

        return moodDate.equals(nowDate);
    }

    public static int getDateGapWithToday(Date dateToCompare)
    {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));

        Date todayDate = new Date();

        Calendar thatDay = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        thatDay.setTime(dateToCompare);
        thatDay.set(Calendar.HOUR_OF_DAY, 0);
        thatDay.set(Calendar.MINUTE, 0);
        thatDay.set(Calendar.SECOND, 0);
        thatDay.set(Calendar.MILLISECOND, 0);

        today.setTime(todayDate);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        int dateGapWithToday = 0;
        while (thatDay.before(today))
        {
            thatDay.add(Calendar.DAY_OF_MONTH, 1);
            dateGapWithToday++;
        }
        return dateGapWithToday;
    }

}
