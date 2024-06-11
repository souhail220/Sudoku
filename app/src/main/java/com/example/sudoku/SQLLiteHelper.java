package com.example.sudoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLLiteHelper extends SQLiteOpenHelper {

    Context context;
    private static final String databaseName = "USERLIBRARY.db";
    private static final int databaseVersion = 1;

    private static final String tableName = "Score_Table";
    private static final String columnID = "_id";
    private static final String columnRank = "Rank";
    private static final String columnName = "User_Name";
    private static final String columnScore = "Highest_score";
    public SQLLiteHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        Log.d("souha", "onCreate: database 1");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + tableName + " (" +
                        columnID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        columnRank + " INTEGER, " +
                        columnName + " TEXT, " +
                        columnScore + " INTEGER);";
        db.execSQL(query);
        Log.d("souha", "onCreate: create Table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  tableName);
        onCreate(db);
    }

    public void addUser(int rank, String userName, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(columnRank,rank);
        cv.put(columnName,userName);
        cv.put(columnScore,score);
        long result = db.insert(tableName,null,cv);
        if(result == -1){
            Toast.makeText(context, "Information inserting failed", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
        }
    }

    public int getHighestScore(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT min("+columnScore+"),"+columnName+" FROM " + tableName, null);
        if(cursor.getCount() == 0){
            return -1;
        }
        cursor.moveToFirst();
        int x = cursor.getInt(0);
        Log.d("HomeAct", "dataBase Created: "+x);
        cursor.close();
        return x;
    }
}
