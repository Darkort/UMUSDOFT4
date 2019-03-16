package DataBase;

import java.sql.SQLData;
import java.sql.Timestamp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.badlogic.gdx.Gdx;

import Classes.DataBase.DataBase;
import Classes.DataBase.Score;

public class AndroidDataBase extends DataBase {
    private AndroidDataBaseOpenHelper openHelper;
    private SQLiteDatabase database;



    public AndroidDataBase(Context c){
        super();
        openHelper=new AndroidDataBaseOpenHelper(c);
        database=openHelper.getWritableDatabase();
    }

    @Override
    public Score getHigherScore() {
        //Hago la consulta a ver si hay una partida en curso (Con fecha de fin igual a null)
        Cursor c=database.query(DataBase.getScoreTablename(),null,
                null,null,
                null,null,null);
        //Si existe una partida guardada ya
        if(c.getCount()>0){
            //Cargo la partida en un objeto Puntuaciones y lo devuelvo
            c.moveToFirst();
            int newScore= c.getInt(c.getColumnIndex(DataBase.getScoreField()));
            Score ret=new Score(newScore);
            return ret;
        }else{ //Si no se ha encontrado una partida empezada en la base de datos
            //Creo una nueva partida que comienza ahora
            return new Score(0);
        }
    }

    @Override
    public void savenewHighScore(int score) {
        //Primero compruebo si hay una partida abierta para insertarla o actualizar la que hay
        Cursor c=database.query(DataBase.getScoreTablename(),null,
                null,null,
                null,null,null);

        //Si la partida con fecha final = null existe ya, la modifico
        if(c.getCount()>0) { //Modificar la partida ya abierta
            c.moveToFirst();
            ContentValues values=new ContentValues();
            values.put(DataBase.getScoreField(),score);
            database.update(DataBase.getScoreTablename(),values,
                    null,null);

            //Si la partida con fecha final = null aún no existe, la inserto
        }else{ //insertar una nueva partida
            ContentValues values=new ContentValues();
            values.put(DataBase.getScoreField(),score);
            Long id=database.insert(DataBase.getScoreTablename(),null,values);
        }
    }
}
