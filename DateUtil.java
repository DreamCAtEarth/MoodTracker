package com.poupel.benjamin.moodtracker;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Cette classe est statique, elle n'est pas utilisée comme objet, mais contient toutes les fonctions utiles pour les calculs des dates
 */
public class DateUtil {
    /**
     * Cette fonction sert à comparer deux dates et vérifier si elles sont égales au jour près
     *
     * @param dateToCompare 1ère date
     * @param date          2nde date
     * @return true si la date est égale, false sinon
     */
    public static boolean isSameDate(Date dateToCompare, Date date) {
        Format formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        String moodDate = formatter.format(dateToCompare);
        String nowDate = formatter.format(date);

        return moodDate.equals(nowDate);
    }

    /**
     * Fonction servant à indiquer combien de jours d'écart il y a avec la date d'aujourd'hui
     *
     * @param dateToCompare date à comparer avec la date du jour
     * @return retourne un entier qui est le nombre de jours (dernier jour non complet) d'écarts
     */
    public static int getDateGapWithToday(Date dateToCompare) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));

        Date todayDate = new Date();

        Calendar thatDay = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        thatDay.setTime(dateToCompare);
        today.setTime(todayDate);

        setDateToMidnight(thatDay);
        setDateToMidnight(today);

        int dateGapWithToday = 0;
        while (thatDay.before(today)) {
            thatDay.add(Calendar.DAY_OF_MONTH, 1);
            dateGapWithToday++;
        }
        return dateGapWithToday;
    }

    private static void setDateToMidnight(Calendar thatDay) {
        thatDay.set(Calendar.HOUR_OF_DAY, 0);
        thatDay.set(Calendar.MINUTE, 0);
        thatDay.set(Calendar.SECOND, 0);
        thatDay.set(Calendar.MILLISECOND, 0);
    }

}
