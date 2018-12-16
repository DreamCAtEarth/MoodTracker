package com.poupel.benjamin.moodtracker;

import com.poupel.benjamin.moodtracker.models.Mood;
import com.poupel.benjamin.moodtracker.models.SavedPreferences;

import java.util.ArrayList;
import java.util.Date;

/**
 * Cette classe est dédiée aux conditions et aux opérations de sauvegarde des moods intermédiaires entre l'activité et le singleTon gérant les SharedPreferences
 */
public class SaveHelper {

    /**
     * saveMood est là pour sauvegarder dans les Sharedpreferences des moods choisis durant les 7 derniers jours
     * Si la taille de la liste est trop grande, si un mood arrive alors qu'il est du même jour qu'un autre, ou si la date d'un mood est trop ancienne (plus d'une seumaine)
     * alors on supprime, par contre la sauvegarde proprement dite dans les SharedPreferences est déléguée à SavedPreferences
     * @param mood issu de la classe Mood
     * @param savedPreferences qui est la sharedPreference à utiliser
     */
    public void saveMood(Mood mood, SavedPreferences savedPreferences) {
        ArrayList<Mood> historicMoodList = savedPreferences.getMoods();
        mood.setDate(new Date());
        if (DateUtil.isSameDate(historicMoodList.get(historicMoodList.size() - 1).getDate(),mood.getDate())) {
            historicMoodList.remove(historicMoodList.size() - 1);
        }
        saveToPref(historicMoodList, mood,savedPreferences);
    }

    private void saveToPref(ArrayList<Mood> historicMoodList, Mood moodToSave, SavedPreferences savedPreferences) {
        historicMoodList.add(moodToSave);
        if (historicMoodList.size() > 8) {
            historicMoodList.remove(0);
        }
        ArrayList<Mood> toDelete = new ArrayList<>();
        for(Mood mood:historicMoodList)
        {
            if(mood.getDate() == null || DateUtil.getDateGapWithToday(mood.getDate()) > 7)
                toDelete.add(mood);
        }
        historicMoodList.removeAll(toDelete);
        savedPreferences.storeMoods(historicMoodList);
    }
}
