package com.poupel.benjamin.moodtracker.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poupel.benjamin.moodtracker.MoodViewHolder;
import com.poupel.benjamin.moodtracker.R;

import java.util.List;

/**
 * Voici le gestionnaire de données de la recycler View, qui va s'occuper d'adapter l'affichage des moods
 */
public class MoodAdapter extends RecyclerView.Adapter<MoodViewHolder> {

    // FOR DATA
    private List<Mood> moodsList;

    // CONSTRUCTOR

    /**
     * Le constructeur prends en compte l'historique de moods de l'utilisateur pour gérer sa liste de Moods
     */
    public MoodAdapter(List<Mood> historicMoodList) {
        this.moodsList = historicMoodList;
    }

    /**
     * Évènement de création de la viewHolder en intégrant l'item xml
     *
     * @param parent conteneur parent du conteneur (ici View, qui est un ViewGroup)
     * @return un ViewHolder contenant des moods
     */
    @NonNull
    @Override
    public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mood_item, parent, false);

        return new MoodViewHolder(view, context, parent);
    }

    // UPDATE VIEW HOLDER WITH A MOOD

    /**
     * Évènement d'association du ViewHolder lorsqu'un nouveau mood doit être positionné et adapté (on appelle directement updateWithMood de ViewHolder)
     *
     * @param viewHolder le viewHolder concerné (ici, celui de moods)
     * @param position   la position entre 1 et 7 de l'item (vu qu'il n'y a que 7 items à afficher de la liste)
     */
    @Override
    public void onBindViewHolder(@NonNull MoodViewHolder viewHolder, int position) {
        viewHolder.updateWithMood(this.moodsList.get(position));
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST

    /**
     * permet d'obtenir la taille de la liste de moods à afficher
     *
     * @return taille de la liste de moods
     */
    @Override
    public int getItemCount() {
        return this.moodsList.size();
    }
}
