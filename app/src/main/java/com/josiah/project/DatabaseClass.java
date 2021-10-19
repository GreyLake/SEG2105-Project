package com.josiah.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseClass extends SQLiteOpenHelper {

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String CLASSES = "CLASSES";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_DAY = "DAY";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_DIFFICULTY = "DIFFICULTY";
    public static final String COLUMN_CAPACITY = "CAPACITY";

    // This is our database
    public DatabaseClass(@Nullable Context context) {

        super(context, "class.db", null, 1);
    }

    // Creates the database for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + CLASSES + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_TYPE + ", " + COLUMN_DAY + ", " + COLUMN_TIME + ", " + COLUMN_DIFFICULTY + ", " + COLUMN_CAPACITY + " )";
        db.execSQL(createTableStatement);
    }

    // Updates the database on different app versions
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean createClass(Class_ c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CAPACITY, c.getCapacity());
        cv.put(COLUMN_DAY, c.getDay());
        cv.put(COLUMN_DESCRIPTION, c.getDescription());
        cv.put(COLUMN_DIFFICULTY, c.getDifficulty());
        cv.put(COLUMN_NAME, c.getName());
        cv.put(COLUMN_TYPE, c.getType());
        cv.put(COLUMN_TIME, c.getTime());

        long insert = db.insert(CLASSES, null, cv);
        db.close();
        return insert != -1;
    }

    public ArrayList<Class_> getAllClasses(){

        ArrayList<Class_> returnList = new ArrayList<>();

        String queryString = "Select * FROM " + CLASSES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String strName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String strDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String strType = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                String strDay = cursor.getString(cursor.getColumnIndex(COLUMN_DAY));
                String strTime = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                String strDifficulty = cursor.getString(cursor.getColumnIndex(COLUMN_DIFFICULTY));
                String strCapacity = cursor.getString(cursor.getColumnIndex(COLUMN_CAPACITY));

                    Class_ c = new Class_(strName, strDescription, strType, strDay, strTime, strDifficulty, strCapacity);
                    returnList.add(c);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public int getID(String name){
        String queryString = "SELECT " + COLUMN_ID + " FROM " + CLASSES + " WHERE " + COLUMN_NAME + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] {name});
        int id = 0;
        if(cursor.moveToFirst()){
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        db.close();
        return id;
    }

    public void deleteClass(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(CLASSES, COLUMN_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
    }

    public boolean checkName(String name){

        String queryString = "SELECT * FROM " + CLASSES + " WHERE " + COLUMN_NAME + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] {name});
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public void updateClass(int id, String name, String description){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_DESCRIPTION,description);

        db.update(CLASSES,contentValues,COLUMN_ID+"= ?",new String[]{String.valueOf(id)});

        db.close();
    }
}
