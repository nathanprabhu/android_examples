package com.cs478.main.musicplayerservice;

/**
 * Created by Varshini on 4/7/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// This class handles database creation, table creation, database deletion
public class MyDatabaseHelper extends SQLiteOpenHelper {

    final static String TABLE_NAME = "Transactions";
    final static String _ID = "_id";
    final static String TIME = "time";
    final static String CURRENT_STATE = "current_state";
    final static String TYPE = "type";
    final static String CLIP_NO = "clip_no";
    final static String[] columns = {_ID, TIME, CURRENT_STATE, TYPE, CLIP_NO};
    final private Context myContext;

    // Defines a string for table creation with all the columns
    final private static String CREATE_CMD =

            "CREATE TABLE Transactions (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TIME + " TEXT NOT NULL, "
                    + CURRENT_STATE + " TEXT NOT NULL, "
                    + TYPE + " TEXT NOT NULL, "
                    + CLIP_NO + " INTEGER NOT NULL)";



    // Creates the database "TransactionsDB"
    public MyDatabaseHelper(Context context) {
        super(context, "TransactionsDB", null, 1);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    void deleteDatabase() {
        myContext.deleteDatabase("TransactionsDB");
    }
}