package com.poupel.benjamin.moodtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.poupel.benjamin.moodtracker.models.Mood;
import com.poupel.benjamin.moodtracker.models.SaveHelper;
import com.poupel.benjamin.moodtracker.models.SavedPreferences;

import java.util.Date;

/**
 * Voici la classe de l'AlarmManager servant à décrire son comportement selon les évènements.
 * Pour cette application, seule l'event OnReceive est nécessaire.
 */
public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    /**
     * Cette méthode est l'event de réception du mood par défaut
     *
     * @param context le contexte de l'activité
     * @param intent  l'intent pour passer à une autre activité si besoin
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        SaveHelper saveHelper = new SaveHelper();
        Mood happy = new Mood(3, R.drawable.smiley_happy, R.color.greenMood);
        happy.setDate(new Date());
        saveHelper.saveMood(happy, SavedPreferences.getInstance(context));
    }

}
