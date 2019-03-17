package com.mygdx.game;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Random;

import Classes.DataBase.DataBase;
import DataBase.AndroidDataBase;

public class SoFuckingAnnoyingService extends Service{
    private Handler h;
    private Random rd;
    private int aux;
   public DataBase db;


    @Override
    public void onCreate() {
        db= new AndroidDataBase(this);
        h= new Handler();
        rd=new Random();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        h.postDelayed(toaster,30);

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        h.removeCallbacks(toaster);
        Toast.makeText(this,"OK BYE",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
    public Runnable toaster= new Runnable() {
        @Override
        public void run() {
            aux=rd.nextInt(5);
            if(aux==4){
                Toast.makeText(SoFuckingAnnoyingService.this, "¿UNA PARTIDITA?", Toast.LENGTH_SHORT).show();
            }else if(aux==3) {
                Toast.makeText(SoFuckingAnnoyingService.this, "!¿A QUE TE APETECE SUPERAR EL RECORD DE " + db.getHigherScore().getScore() + " PUNTOS!", Toast.LENGTH_SHORT).show();

            }else if(aux==2){
                Toast.makeText(SoFuckingAnnoyingService.this, "ECHATE UNA", Toast.LENGTH_SHORT).show();
            }else if(aux==1){
                Toast.makeText(SoFuckingAnnoyingService.this, db.getHigherScore().getScore()+" SE PASAN RAPIDO", Toast.LENGTH_SHORT).show();
            }else if(aux==0){

            }
            h.postDelayed(this, 3000);
        }
    };
}
