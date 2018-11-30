package com.poupel.benjamin.moodtracker;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.poupel.benjamin.moodtracker.models.Mood;

import java.text.Format;
import java.text.SimpleDateFormat;


public class MoodViewHolder extends RecyclerView.ViewHolder {

    private static final int BASE_ITEM_WIDTH = 150;
    private Context mContext;
    private final TextView textView;
    private final RelativeLayout moodBackgroundColor;
    private final Button mImageView;

    public MoodViewHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        textView = itemView.findViewById(R.id.mood_item_week);
        moodBackgroundColor = itemView.findViewById(R.id.mood_total_item);
        mImageView = itemView.findViewById(R.id.mood_item_week_comment);
    }

    public void updateWithMood(Mood mood) {
        String comment = mood.getComment();
        int numberOfDays = DateUtil.getDateGapWithToday(mood.getDate());
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String moodDate = formatter.format(mood.getDate());
        moodBackgroundColor.setBackgroundColor(Color.parseColor(mContext.getResources().getString(mood.getColor())));
        float density = mContext.getResources().getDisplayMetrics().density;
        moodBackgroundColor.getLayoutParams().width = (int) (BASE_ITEM_WIDTH * density + mood.getId()*65*density);

        if (comment == null || comment.isEmpty())
            mImageView.setVisibility(View.INVISIBLE);
        else
            mImageView.setVisibility(View.VISIBLE);

        switch (numberOfDays) {
            case 0:
                //textView.setText("Aujourd'hui");
                moodBackgroundColor.setVisibility(View.INVISIBLE);
                break;
            case 1:
                textView.setText("Hier");
                break;
            case 2:
                textView.setText("Avant-hier");
                break;
            case 3:
                textView.setText("Il y a trois jours");
                break;
            case 4:
                textView.setText("Il y a quatre jours");
                break;
            case 5:
                textView.setText("Il y a cinq jours");
                break;
            case 6:
                textView.setText("Il y a six jours");
                break;
            case 7:
                textView.setText("Il y a une semaine");
                break;
            default:
                textView.setText(moodDate + " " + String.valueOf(numberOfDays));
                break;
        }
    }
}
