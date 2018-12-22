package com.poupel.benjamin.moodtracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.graphics.Color;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.ListIterator;

import com.poupel.benjamin.moodtracker.models.Mood;
import com.poupel.benjamin.moodtracker.models.SaveHelper;
import com.poupel.benjamin.moodtracker.models.SavedPreferences;

/**
 * <b>La MainActivity est l'écran principal de l'application Android.</b>
 * <p>
 * Vous avez la possibilité depuis cette activité de :
 * <ul>
 * <li>Sélectionner une humeur (positive vers le haut, négative vers le bas).</li>
 * <li>Associer un commentaire à l'humeur.</li>
 * <li>Regarder l'historique des 7 dernières humeurs de la semaine.</li>
 * </ul>
 * </p>
 *
 * @author Benjamin POUPEL
 * @version 1.0
 */


public class MainActivity extends AppCompatActivity {
    /**
     * Il s'agit du conteneur de l'émoticône
     */
    private ImageView mSmileyImageView;
    /**
     * L'humeur est contenue dans une liste de moods à présenter à l'utilisateur
     */
    private ArrayList<Mood> moodList = new ArrayList<>();
    /**
     * Il nous en faut une seconde pour stocker l'historique de moods (attention ce n'est pas la même liste)
     */
    private ArrayList<Mood> historicMoodList = new ArrayList<>();
    /**
     * Un iterator est nécessaire pour passer aisément d'un mood à l'autre
     */
    private ListIterator<Mood> moodIterator;
    /**
     * L'index est nécessaire aussi pour connaitre à un instant t sa position exacte
     */
    private int moodIndex;
    /**
     * YDown correspond à la coordonnée ordonnée de relâchement du doigt lors du OnTouchEvent.
     * On en a besoin car on doit réactualiser cette donnée en permanence
     */
    private float yDown;
    /**
     * Cette constante est là pour indiquer la distance nécessaire parcourue en pixels pour valider un changement de mood
     */
    private static final int MIN_MOVE_REQUIRED_FOR_SLIDE = 200;

    /**
     * @see MainActivity#onCreate(Bundle)
     * Le OnCreate est le process de départ de l'activité, c'est là qu'on récupère les ids du layout et qu'on charge les paramètres par défaut
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTheTimeToUpdateTables(this);

        historicMoodList = SavedPreferences.getInstance(this).getMoods();

        initMood();

        Button historyButton = findViewById(R.id.ButtonMoodsHistory);
        Button commentButton = findViewById(R.id.ButtonAddComment);
        mSmileyImageView = findViewById(R.id.imageViewHappy);

        displayMood();

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                final EditText editText = new EditText(MainActivity.this);
                dialog.setView(editText);

                dialog.setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moodList.get(moodIndex).setComment(editText.getText().toString());
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButton(R.string.cancelate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.setTitle(R.string.commentTitle);
                dialog.show();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HistoryActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(HistoryActivity);
            }
        });
    }

    /**
     * @see MainActivity#onStop()
     * Le OnStop s'enclenche lorsque cette activité se termine (fermeture ou passage à une autre activité), c'est là qu'on y sauvegarde le mood
     */
    @Override
    protected void onStop() {
        super.onStop();
        SaveHelper saveHelper = new SaveHelper();
        saveHelper.saveMood(moodList.get(moodIndex), SavedPreferences.getInstance(this));
    }

    /**
     * @see MainActivity#onTouchEvent(MotionEvent)
     * On se sert du OnTouchEvent pour slider d'une humeur à l'autre, on a besoin de la coordonnée sur l'axe des ordonnées pour ça.
     * On met à jour aussi l'index puis on affiche le mood via une méthode privée (pour ne pas surcharger cette méthode) et on joue le son.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.guitar);
        int action = event.getActionMasked();
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                yDown = event.getY();
                return true;
            case (MotionEvent.ACTION_UP):
                float yUp = event.getY();
                if (yDown > (yUp + MIN_MOVE_REQUIRED_FOR_SLIDE)) /* Move Up */ {
                    if (moodIterator.hasNext() && moodIndex < moodList.size() - 1) {
                        moodIndex++;
                        moodIterator.next();
                        mp.start();
                    }
                } else if (yDown < (yUp - MIN_MOVE_REQUIRED_FOR_SLIDE)) /* Move Down*/ {
                    if (moodIterator.hasPrevious() && moodIndex > 0) {
                        moodIndex--;
                        moodIterator.previous();
                        mp.start();
                    }
                }
                displayMood();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    /**
     * 3 méthodes privées existent pour soi sélectionner le mood par défaut ou choisi précédemment,
     * soi afficher le mood (avec les Ressources et le layout), soi enclencher l'alarmeManager (pour actualiser le mood par défaut à minuit).
     * L'alarmManager est un service qui prends en compte l'heure du téléphone même lorsque l'appli ne tourne pas.
     */
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

        if (historicMoodList.size() > 0) {
            selectedMood = historicMoodList.get(historicMoodList.size() - 1);
        } else {
            selectedMood = happy;
        }
        moodIndex = selectedMood.getId();
        moodIterator = moodList.listIterator(moodIndex);
    }

    private void displayMood() {
        mSmileyImageView.setBackgroundColor(Color.parseColor(getResources().getString(moodList.get(moodIndex).getColor())));
        mSmileyImageView.setImageDrawable(getResources().getDrawable(moodList.get(moodIndex).getIcon()));
    }

    private void setTheTimeToUpdateTables(Context context) {
        Log.i("Update table function", "Yes");
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Calendar alarmStartTime = Calendar.getInstance();
            alarmStartTime.set(Calendar.HOUR_OF_DAY, 0);
            alarmStartTime.set(Calendar.MINUTE, 0);
            alarmStartTime.set(Calendar.SECOND, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

}
