package com.example.testing2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TRUSTED_TABLE = "TRUSTED_TABLE";
    public static final String NAME = "NAME";
    public static final String PHONENO = "PHONENO";
    public static final String ALLOWTRACK = "ALLOWTRACK";
    public static final String ID = "ID";

    public DatabaseHelper(Context context) {
        super(context, "Trusted.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + TRUSTED_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + PHONENO + " TEXT, " + ALLOWTRACK + " BOOL)";
        sqLiteDatabase.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(String phone,String name, Boolean allow)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PHONENO,phone);
        contentValues.put(NAME,name);
        contentValues.put(ALLOWTRACK,allow);

        long insert = database.insert(TRUSTED_TABLE, null, contentValues);
        if (insert == -1)
        {
            return false;
        }
        else {
            return true;
        }


    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TRUSTED_TABLE + " SET " + NAME +
                " = '" + newName + "' WHERE " + ID + " = '" + id + "'" +
                " AND " + NAME + " = '" + oldName + "'";
        Log.d("Status", "updateName: query: " + query);
        Log.d("TAG", "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TRUSTED_TABLE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + ID + " FROM " + TRUSTED_TABLE +
                " WHERE " + NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TRUSTED_TABLE + " WHERE "
                + ID + " = '" + id + "'" +
                " AND " + NAME + " = '" + name + "'";
        Log.d("TAG", "deleteName: query: " + query);
        Log.d("TAG", "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }



}
