package com.poupel.benjamin.moodtracker.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Cette classe est utilisée comme Singleton (instance unique) pour stocker ou retourner selon les besoins les sharedPreferences sauvegardées en local
 * Nous appellerons chacune des méthodes dès que nous en auront besoin via le SaveHelper, qui n'a pas à savoir ce que fait cette classe des SharedPreferences
 */
public class SavedPreferences {
    private static SavedPreferences instance;
    private static String PREFS;

    static {
        PREFS = "PREFS";
    }

    private SharedPreferences prefs;
    private static String MOODS = "MOODS";

    private SavedPreferences(Context context) {
        prefs = context.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
    }

    /**
     * Création du singleTon, pour ne pas créer plusieurs instances de SharedPreferences (nous en avons besoin que d'une seule)
     *
     * @param context contexte de l'activité
     * @return le singleton ou l'instance unique
     */
    public static SavedPreferences getInstance(Context context) {
        if (instance == null)
            instance = new SavedPreferences(context);
        return instance;
    }

    /**
     * Méthode permettant de stocker les moods dans les sharedPreferences via GSON (transformation de l'objet Mood en String, car sinon ce n'est pas possible)
     *
     * @param moods liste de moods à stocker
     */
    public void storeMoods(ArrayList<Mood> moods) {
        //start writing (open the file)
        SharedPreferences.Editor editor = prefs.edit();
        //put the data
        Gson gson = new Gson();
        String json = gson.toJson(moods);
        editor.putString(MOODS, json);
        //close the file
        editor.apply();
    }

    /**
     * Cette méthode permet d'obtenir les SharedPreferences stockées dans le cache local
     *
     * @return la liste de moods sauvegardée dans les sharedPreferences
     */
    public ArrayList<Mood> getMoods() {
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

}
