package com.poupel.benjamin.moodtracker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.poupel.benjamin.moodtracker.models.Mood;

import java.text.Format;
import java.text.SimpleDateFormat;

public class MoodViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;

    public MoodViewHolder(View itemView) {
        super(itemView);
         textView = itemView.findViewById(R.id.mood_item_week);
    }

    public void updateWithMood(Mood mood) {
        Format formatter = new SimpleDateFormat("MM-dd-yyyy");
        String moodDate = formatter.format(mood.getDate());
        textView.setText(moodDate);

    }
}
