package com.poupel.benjamin.moodtracker;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.poupel.benjamin.moodtracker.models.Mood;

/**
 * Le MoodViewHolder est une classe particulière de ViewHolder qui servira à gérer ses items et leurs conditions d'affichage
 * C'est aussi ici qu'on passe les commandes pour changer les éléments graphiques de l'item de la View
 */
public class MoodViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private TextView textView;
    private RelativeLayout moodBackgroundColor;
    private Button mImageView;

    private ViewGroup parent;

    private static final int MAX_HISTORIC_SIZE = 7;

    /**
     * Lz constructeur sert à initialiser le contexte d'activité et chacun des éléments de tout l'item
     *
     * @param itemView la View concernée qui contiendra la liste d'items sencée représenter chacun un mood
     * @param context  le contexte de l'activité (History Activity)
     */
    public MoodViewHolder(View itemView, Context context, ViewGroup parent) {
        super(itemView);
        mContext = context;
        textView = itemView.findViewById(R.id.mood_item_week);
        moodBackgroundColor = itemView.findViewById(R.id.mood_total_item);
        mImageView = itemView.findViewById(R.id.mood_item_week_comment);

        this.parent = parent;
    }

    /**
     * Voici le comportement lorsqu'un item est ajouté à la liste à afficher :
     * On a un calcul du nombre de jours d'écart avec la date du jour, en fonction on affiche l'item dans la vue par ordre anti chronologique avec son textView indiquant depuis quand il date
     * On allonge ou raccourcit la longueur de l'item en fonction de sa valeur d'humeur (du plus petit au plus grand en partant du plus triste au plus joyeux)
     * On colore le fond de l'item avec la couleur du mood qui est fonction de la valeur d'humeur (chaque niveau d'humeur a sa couleur propre)
     * On affiche ou non la bulle permettant de toaster le commentaire qui a été écrit
     *
     * @param mood mood à ajouter
     */
    public void updateWithMood(Mood mood) {
        final String comment = mood.getComment();
        int numberOfDays = DateUtil.getDateGapWithToday(mood.getDate());
        moodBackgroundColor.setBackgroundColor(Color.parseColor(mContext.getResources().getString(mood.getColor())));

        if (comment == null || comment.isEmpty())
            mImageView.setVisibility(View.INVISIBLE);
        else {
            mImageView.setVisibility(View.VISIBLE);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, comment, Toast.LENGTH_LONG).show();
                }
            });

        }

        int widthToSet = parent.getWidth();
        int heightToSet = parent.getHeight();

        float widthToGet = widthToSet * ((mood.getId() + 1.0f) / 5.0f);
        float heightToGet = heightToSet / MAX_HISTORIC_SIZE;

        int widthResult = Math.round(widthToGet);
        int heightResult = Math.round(heightToGet);

        moodBackgroundColor.getLayoutParams().width = widthResult;
        moodBackgroundColor.getLayoutParams().height = heightResult;

        switch (numberOfDays) {
            case 1:
                textView.setText(R.string.yesterday);
                break;
            case 2:
                textView.setText(R.string.beforeYesterday);
                break;
            case 3:
                textView.setText(R.string.threeDaysAgo);
                break;
            case 4:
                textView.setText(R.string.fourDaysAgo);
                break;
            case 5:
                textView.setText(R.string.fiveDaysAgo);
                break;
            case 6:
                textView.setText(R.string.sixDaysAgo);
                break;
            case 7:
                textView.setText(R.string.sevenDaysAgo);
                break;
        }
    }
}
