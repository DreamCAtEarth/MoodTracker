package com.poupel.benjamin.moodtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import com.google.gson.Gson;

import com.poupel.benjamin.moodtracker.models.Mood;
import com.poupel.benjamin.moodtracker.models.SavedPreferences;


public class MainActivity extends AppCompatActivity
{
    private ImageView mSmileyImageView;
    private Button mCommentButton;
    private Button mHistoryButton;

    private ArrayList<Mood> moodList = new ArrayList<>();
    private ListIterator<Mood> moodIterator;
    private int moodIndex;

    private float yUp;
    private float yDown;
    private static final int MIN_MOVE_REQUIRED_FOR_SLIDE=200;

    private final String moodSavedKey = "currentMoodKey";
    private Gson gson = new Gson();
    private String savedMood;
    private SharedPreferences preferences = getPreferences(MODE_PRIVATE);
    private SharedPreferences.Editor editor = preferences.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mood sad = new Mood(0,R.drawable.smiley_sad,R.color.redMood);
        Mood disappointed = new Mood(1,R.drawable.smiley_disappointed,R.color.greyMood);
        Mood normal = new Mood(2,R.drawable.smiley_normal,R.color.blueMood);
        Mood happy = new Mood(3,R.drawable.smiley_happy,R.color.greenMood);
        Mood superHappy = new Mood(4,R.drawable.smiley_super_happy, R.color.yellowMood);

        moodList.add(sad);
        moodList.add(disappointed);
        moodList.add(normal);
        moodList.add(happy);
        moodList.add(superHappy);

        mHistoryButton = findViewById(R.id.ButtonMoodsHistory);
        mCommentButton = findViewById(R.id.ButtonAddComment);
        mSmileyImageView = findViewById(R.id.imageViewHappy);

        moodIterator = moodList.listIterator(happy.getId());
        moodIndex = happy.getId();

        if(preferences.contains(savedMood))
        {
            Log.i("My App","On présente la dernière humeur sauvegardée de la journée.");
            SavedPreferences.getInstance(this).getMoods();
        }
        else
        {
            Log.i("My App","On présente l'humeur par défaut.");
            moodIterator = moodList.listIterator(happy.getId());
            moodIndex = happy.getId();
        }/**/

        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                dialog.show();
                Window window = dialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HistoryActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(HistoryActivity);

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getActionMasked();
        switch(action)
        {
            case (MotionEvent.ACTION_DOWN) :
                yDown = event.getY();
                return true;
            case (MotionEvent.ACTION_UP) :
                yUp = event.getY();
                if (yDown > (yUp + MIN_MOVE_REQUIRED_FOR_SLIDE)) // Move Up
                {
                    if(moodIterator.hasNext())
                    {
                        if(moodIndex < moodList.size() - 1)
                            moodIndex++;
                    }
                }
                else if(yDown < (yUp - MIN_MOVE_REQUIRED_FOR_SLIDE)) // Move Down
                {
                    if(moodIterator.hasPrevious())
                    {
                        if(moodIndex > 0)
                            moodIndex--;
                    }
                }
                mSmileyImageView.setBackgroundColor(Color.parseColor(getResources().getString(moodList.get(moodIndex).getColor())));
                mSmileyImageView.setImageDrawable(getResources().getDrawable(moodList.get(moodIndex).getIcon()));

                Log.i("My App","J'ai sauvegardé une nouvelle humeur du jour !");
                moodList.get(moodIndex).setDate(new Date());
                SavedPreferences.getInstance(this).storeMoods(moodList);

                return true;
            default :
                return super.onTouchEvent(event);
        }
    }
}
