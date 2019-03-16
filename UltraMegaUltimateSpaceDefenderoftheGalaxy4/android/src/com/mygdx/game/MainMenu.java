package com.mygdx.game;

import android.annotation.TargetApi;
import android.app.Activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import DataBase.AndroidDataBase;


public class MainMenu extends Activity {
    AndroidDataBase dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        dba= new AndroidDataBase(this);

        TextView scoreText= findViewById(R.id.HighScoreText);
        scoreText.setText(""+dba.getHigherScore());

    }
    @Override
    public void onResume(){
        super.onResume();
        ImageButton im= findViewById(R.id.startButton);
        im.setImageResource(R.drawable.empezar);
        TextView scoreText= findViewById(R.id.HighScoreText);
        scoreText.setText(""+dba.getHigherScore().getScore());
    }


    public void onClickStart(View view){
        Intent i= new Intent(this,AndroidLauncher.class );
        startActivity(i);
        ImageButton im= findViewById(R.id.startButton);
        im.setImageResource(R.drawable.empezarpressed);

    }


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


}
