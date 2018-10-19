package com.poupel.benjamin.moodtracker.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SavedPreferences
{
    private static SavedPreferences instance;
    private static String PREFS = "PREFS";

    private SharedPreferences prefs;

    private static String MOODS = "MOODS";

    private SavedPreferences(Context context)
    {
        prefs = context.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
    }

    public static SavedPreferences getInstance(Context context) {
        if (instance == null)
            instance = new SavedPreferences(context);
        return instance;
    }

    public void storeMoods(ArrayList<Mood> moods)
    {
        //start writing (open the file)
        SharedPreferences.Editor editor = prefs.edit();
        //put the data
        Gson gson = new Gson();
        String json = gson.toJson(moods);
        editor.putString(MOODS, json);
        //close the file
        editor.apply();
    }

    public ArrayList<Mood> getMoods()
    {
        Gson gson = new Gson();
        String json = prefs.getString(MOODS, "");

        ArrayList<Mood> moods;

        if (json.length() < 1) {
            moods = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<Mood>>() {
            }.getType();
            moods = gson.fromJson(json, type);
        }
        return moods;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }
}
