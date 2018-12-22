package com.poupel.benjamin.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.poupel.benjamin.moodtracker.models.Mood;
import com.poupel.benjamin.moodtracker.models.MoodAdapter;
import com.poupel.benjamin.moodtracker.models.SavedPreferences;

import java.util.ArrayList;

/**
 * L'acitivité sert de controleur général pour toute la View des items de moods à afficher dynamiquement grâce au principe économique de la RecyclerView
 */
public class HistoryActivity extends AppCompatActivity {

    //FOR DATA

    /**
     * Création de l'activité et intégration de la RecyclerView (puis configuration de celle-ci)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.historic_layout);

        RecyclerView recyclerView = findViewById(R.id.mood_main_recycler_view);
        //ButterKnife.bind(this, view);
        this.configureRecyclerView(recyclerView); // - 4 Call during UI creation
    }

    /**
     * Lorsque l'activité est fermée, il ne se passe rien de spécial, on appelle le destructeur de la classe supérieure
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // -----------------
    // CONFIGURATION
    // -----------------
    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(RecyclerView recyclerView) {
        // 3.1 - Reset list
        // List<Mood> historicMoodList = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        ArrayList<Mood> moods = SavedPreferences.getInstance(this).getMoods();
        if (!moods.isEmpty() && (moods.get(moods.size() - 1).getDate() == null || DateUtil.getDateGapWithToday(moods.get(moods.size() - 1).getDate()) == 0))
            moods.remove(moods.size() - 1);
        MoodAdapter adapter = new MoodAdapter(moods);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // 3.4 - Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
