package com.poupel.benjamin.moodtracker;

import com.poupel.benjamin.moodtracker.models.Mood;
import com.poupel.benjamin.moodtracker.models.SavedPreferences;

import java.util.ArrayList;
import java.util.Date;

public class SaveHelper {


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

        /*ListIterator<Mood> iterator = historicMoodList.listIterator(0);
        while (iterator.hasNext()) {
            Mood item = iterator.next();
            if(item.getDate() == null || DateUtil.getDateGapWithToday(item.getDate()) > 7)
                historicMoodList.remove(item);
        }*/
        savedPreferences.storeMoods(historicMoodList);
    }
}
