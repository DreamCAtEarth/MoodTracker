package com.poupel.benjamin.moodtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Color;

import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ListIterator;

import com.poupel.benjamin.moodtracker.models.Mood;
import com.poupel.benjamin.moodtracker.models.SavedPreferences;


public class MainActivity extends AppCompatActivity
{
    private ImageView mSmileyImageView;
    private Button mCommentButton;
    private Button mHistoryButton;

    private ArrayList<Mood> moodList = new ArrayList<>();
    private ArrayList<Mood> historicMoodList = new ArrayList<>();
    private ListIterator<Mood> moodIterator;
    private int moodIndex;

    private float yUp;
    private float yDown;
    private static final int MIN_MOVE_REQUIRED_FOR_SLIDE=200;

    private Calendar nowDate = new GregorianCalendar();
    private Calendar moodDate = new GregorianCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMood();

        mHistoryButton = findViewById(R.id.ButtonMoodsHistory);
        mCommentButton = findViewById(R.id.ButtonAddComment);
        mSmileyImageView = findViewById(R.id.imageViewHappy);

        displayMood();

        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                EditText editText = new EditText(MainActivity.this);
                dialog.setView(editText);
                dialog.show();
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
    protected void onStop() {
        super.onStop();
        //saveMood();
        historicMoodList.remove(historicMoodList.size()-1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                yDown = event.getY();
                return true;
            case (MotionEvent.ACTION_UP) :
                yUp = event.getY();
                if (yDown > (yUp + MIN_MOVE_REQUIRED_FOR_SLIDE)) /* Move Up */ {
                    if(moodIterator.hasNext()) {
                        if(moodIndex < moodList.size() - 1)
                            moodIndex++;
                    }
                }
                else if(yDown < (yUp - MIN_MOVE_REQUIRED_FOR_SLIDE)) /* Move Down*/ {
                    if(moodIterator.hasPrevious()) {
                        if(moodIndex > 0)
                            moodIndex--;
                    }
                }
                displayMood();

                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    private void initMood() {
        Mood selectedMood;

        Mood sad = new Mood(0, R.drawable.smiley_sad, R.color.redMood);
        Mood disappointed = new Mood(1, R.drawable.smiley_disappointed, R.color.greyMood);
        Mood normal = new Mood(2, R.drawable.smiley_normal, R.color.blueMood);
        Mood happy = new Mood(3, R.drawable.smiley_happy, R.color.greenMood);
        Mood superHappy = new Mood(4, R.drawable.smiley_super_happy, R.color.yellowMood);

        moodList.add(sad);
        moodList.add(disappointed);
        moodList.add(normal);
        moodList.add(happy);
        moodList.add(superHappy);

        if (SavedPreferences.getInstance(this).getMoods().size() > 0) {
            selectedMood = SavedPreferences.getInstance(this).getMoods().get(historicMoodList.size()-1);
        }
        else{
            selectedMood = new Mood(happy.getId(),happy.getIcon(),happy.getColor());
            historicMoodList.add(selectedMood);
        }
        moodIndex = selectedMood.getId();
        moodIterator = moodList.listIterator(moodIndex);
    }

    private void displayMood() {
        mSmileyImageView.setBackgroundColor(Color.parseColor(getResources().getString(moodList.get(moodIndex).getColor())));
        mSmileyImageView.setImageDrawable(getResources().getDrawable(moodList.get(moodIndex).getIcon()));
    }

    private void saveMood() {
        historicMoodList.add(moodList.get(moodIndex));
        historicMoodList.get(moodIndex).setDate(new Date());
        SavedPreferences.getInstance(this).storeMoods(historicMoodList);
    }

}
