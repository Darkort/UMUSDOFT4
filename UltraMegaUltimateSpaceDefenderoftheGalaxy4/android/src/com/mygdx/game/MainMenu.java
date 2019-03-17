package com.mygdx.game;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import DataBase.AndroidDataBase;

/**
 * Starting activity of UMUSDOTG4
 * @author AlexCantos
 * @version 1.0
 */
public class MainMenu extends Activity {
    AndroidDataBase dba;
    boolean serviceLaunched;
    Intent service;
    Music music;

    /**
     * Sets Database connection, the annoying service and the HigherScore on the HighScore TextView
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        dba= new AndroidDataBase(this);

        service=new Intent(this,SoFuckingAnnoyingService.class);
        music= Gdx.audio.newMusic(Gdx.files.internal("data/mymusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.8f);
        music.play();
        TextView scoreText= findViewById(R.id.HighScoreText);
        scoreText.setText(""+dba.getHigherScore());

    }

    /**
     * Updates ImageButton and highScore upon resume
     */
    @Override
    public void onResume(){
        super.onResume();
        music.play();
        ImageButton im= findViewById(R.id.startButton);
        im.setImageResource(R.drawable.empezar);
        TextView scoreText= findViewById(R.id.HighScoreText);
        scoreText.setText(""+dba.getHigherScore().getScore());
    }
    @Override
    public void onPause() {
        music.pause();
        super.onPause();
    }
    /**
     * Launch a new game on clicking Start Button, changing the Image of the button to look like an animation
     * @param view
     */
    public void onClickStart(View view){
        Intent i= new Intent(this,AndroidLauncher.class );
        startActivity(i);
        ImageButton im= findViewById(R.id.startButton);
        im.setImageResource(R.drawable.empezarpressed);

    }

    /**
     * Switch between activate and disable the annoying service, changing the button text to fit with it
     * @param view
     */
    public void onClickServicio(View view){

        ImageButton im= (ImageButton)view;
        if(!serviceLaunched){

            im.setImageResource(R.drawable.serviciomolestodetener);
            serviceLaunched=true;

            startService(service);
        }else{

            im.setImageResource(R.drawable.serviciomolesto);
            serviceLaunched=false;
            stopService(service);
        }
    }

    /**
     * Asks the user if he's sure to quit game
     */
    @Override
    public void onBackPressed()
        {
            new AlertDialog.Builder(this)
            .setMessage("¿Estás TOTALMENTE seguro de querer salir?")
                .setCancelable(false)
                .setPositiveButton("Más o menos...", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            finish();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("¡Jamas!", null)
                .show();
        }

    /**
     * secures user to dont close the application letting the so fucking annoying service orphan and unstoppable
     */
    @Override
        public void onDestroy(){
            stopService(service);
            super.onDestroy();
        }


}
