package com.poupel.benjamin.moodtracker.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poupel.benjamin.moodtracker.MoodViewHolder;
import com.poupel.benjamin.moodtracker.R;

import java.util.List;

public class MoodAdapter extends RecyclerView.Adapter<MoodViewHolder> {

    // FOR DATA
    private List<Mood> moodsList;

    // CONSTRUCTOR
    public MoodAdapter(List<Mood> historicMoodList) {
        this.moodsList = historicMoodList;
    }

    @Override
    public MoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mood_item, parent, false);

        return new MoodViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A GITHUBUSER
    @Override
    public void onBindViewHolder(MoodViewHolder viewHolder, int position) {
        viewHolder.updateWithMood(this.moodsList.get(position));
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.moodsList.size();
    }
}
