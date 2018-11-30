package com.poupel.benjamin.moodtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.poupel.benjamin.moodtracker.models.Mood;
import com.poupel.benjamin.moodtracker.models.SavedPreferences;

import java.util.Date;


public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    //final public static String ONE_TIME = "onetime";

    @Override
    public void onReceive(Context context, Intent intent) {
        SaveHelper saveHelper = new SaveHelper();
        Mood happy = new Mood(3, R.drawable.smiley_happy, R.color.greenMood);
        happy.setDate(new Date());
        saveHelper.saveMood(happy, SavedPreferences.getInstance(context));
    }

}
