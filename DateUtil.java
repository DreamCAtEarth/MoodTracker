package com.poupel.benjamin.moodtracker;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static boolean isCurrentDate(Date dateToCompare)
    {
        Format formatter = new SimpleDateFormat("MM-dd-yyyy");
        String moodDate = formatter.format(dateToCompare);
        String nowDate = formatter.format(new Date());
        return moodDate.equals(nowDate);
    }
}
