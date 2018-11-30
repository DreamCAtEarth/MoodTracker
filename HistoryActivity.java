package com.poupel.benjamin.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.poupel.benjamin.moodtracker.models.MoodAdapter;
import com.poupel.benjamin.moodtracker.models.SavedPreferences;


public class HistoryActivity extends AppCompatActivity {

    //FOR DATA

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.historic_layout);

        RecyclerView recyclerView = findViewById(R.id.mood_main_recycler_view);
        //ButterKnife.bind(this, view);
        this.configureRecyclerView(recyclerView); // - 4 Call during UI creation
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(RecyclerView recyclerView){
        // 3.1 - Reset list
        // List<Mood> historicMoodList = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        MoodAdapter adapter = new MoodAdapter(SavedPreferences.getInstance(this).getMoods());
        // 3.3 - Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // 3.4 - Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
