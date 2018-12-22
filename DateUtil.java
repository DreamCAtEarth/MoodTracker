package com.poupel.benjamin.moodtracker;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        int todayDateInt = Integer.parseInt(dateFormat.format(todayDate));
        int dateToCompareInt = Integer.parseInt(dateFormat.format(dateToCompare));

        int dateGapWithToday = 0;
        while (dateToCompareInt < todayDateInt) {
            dateToCompareInt++;
            dateGapWithToday++;
        }
        return dateGapWithToday;
    }

}
