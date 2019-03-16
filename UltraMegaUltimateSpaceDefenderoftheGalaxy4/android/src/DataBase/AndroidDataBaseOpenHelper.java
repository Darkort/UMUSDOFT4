package DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Classes.DataBase.DataBase;

public class AndroidDataBaseOpenHelper extends SQLiteOpenHelper {
    public AndroidDataBaseOpenHelper(Context c){
        super(c,DataBase.getDatabaseName(),null,DataBase.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBase.getDatabaseCreationQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataBase.getDatabaseUpdateQuery());

    }
}
