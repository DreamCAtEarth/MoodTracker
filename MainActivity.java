package com.poupel.benjamin.moodtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Color;


public class MainActivity extends AppCompatActivity
{

    private Button mHistoryButton;
    private Button mCommentButton;
    private ImageView[] moodsSmileys = {null,null,null,null,null};
    private int[] moodsImagesRes = {
            R.drawable.smiley_super_happy,
            R.drawable.smiley_happy,
            R.drawable.smiley_normal,
            R.drawable.smiley_disappointed,
            R.drawable.smiley_sad,
    };
    private RelativeLayout mRelativeLayout;
    private FrameLayout mFrameLayout;
    private String[] moodsColors = {
            "#F8EC65", //jaune
            "#B8E98E", //vert
            "#85B2E3", //bleu
            "#9B9B9B", //gris
            "#DD3F52"}; //rouge
    private float yAppui;
    private float yRelache;
    private int indexMoods=0;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getActionMasked();

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                this.yAppui = event.getY();
                return true;
            case (MotionEvent.ACTION_UP) :
                this.yRelache = event.getY();
                if(this.yAppui < (this.yRelache-200)) //descente
                {
                    if(this.indexMoods >= 4 )
                        this.indexMoods=4;
                    else
                        this.indexMoods++;
                    mFrameLayout.setBackgroundColor(Color.parseColor(moodsColors[this.indexMoods]));
                    moodsSmileys[this.indexMoods].setImageResource(moodsImagesRes[this.indexMoods]);
                    for(int i=0;i<moodsSmileys.length;i++)
                        moodsSmileys[i].setVisibility(View.INVISIBLE);
                    moodsSmileys[this.indexMoods].setVisibility(View.VISIBLE);
                }
                else if (this.yAppui > (this.yRelache+200)) //montée
                {
                    if(this.indexMoods <= 0 )
                        this.indexMoods=0;
                    else
                        this.indexMoods--;
                    mFrameLayout.setBackgroundColor(Color.parseColor(moodsColors[this.indexMoods]));
                    moodsSmileys[this.indexMoods].setImageResource(moodsImagesRes[this.indexMoods]);
                    for(int i=0;i<moodsSmileys.length;i++)
                        moodsSmileys[i].setVisibility(View.INVISIBLE);
                    moodsSmileys[this.indexMoods].setVisibility(View.VISIBLE);
                }
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    //Log.d("TAG","onTouch: "+event);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayout = findViewById(R.id.RelativeLayout);
        mFrameLayout = findViewById(R.id.FrameLayout);
        mHistoryButton = findViewById(R.id.ButtonMoodsHistory);
        mCommentButton = findViewById(R.id.ButtonAddComment);

        moodsSmileys[0]=findViewById(R.id.imageViewSuperHappy);
        moodsSmileys[1]=findViewById(R.id.imageViewHappy);
        moodsSmileys[2]=findViewById(R.id.imageViewNormal);
        moodsSmileys[3]=findViewById(R.id.imageViewDisappointed);
        moodsSmileys[4]=findViewById(R.id.imageViewSad);

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

}
