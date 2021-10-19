package com.josiah.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_ROLE = "ROLE";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String ACCOUNTS = "ACCOUNTS";
    public static final String COLUMN_ID = "ID";

    // This is our database
    public Database(@Nullable Context context){

        super(context, "accounts.db", null, 1);
    }

    // Creates the database for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + ACCOUNTS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ROLE + ", " + COLUMN_FIRST_NAME + ", " + COLUMN_LAST_NAME + ", " + COLUMN_USERNAME + ", " + COLUMN_PASSWORD + ")";
        db.execSQL(createTableStatement);
    }

    // Updates the database on different app versions
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean createAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRST_NAME, account.getFirstName());
        cv.put(COLUMN_LAST_NAME, account.getLastName());
        cv.put(COLUMN_USERNAME, account.getUsername());
        cv.put(COLUMN_PASSWORD, account.getPassword());
        cv.put(COLUMN_ROLE, account.getRole());

        long insert = db.insert(ACCOUNTS, null, cv);
        if(insert == -1)
            return false;
        else
            return true;
    }

    public boolean loginAccount(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        return true;
    }

    public boolean checkUsername(String username){

        String queryString = "SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] {username});
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public Boolean checkPassword(String username, String password){
        String queryString = "SELECT " + COLUMN_PASSWORD + " FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] {username});
        String passwordDatabase = "";
        if(cursor.moveToFirst()){
            passwordDatabase = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
        }
        cursor.close();
        if(passwordDatabase.equals(password)) {
            return true;
        }
        return false;
    }

    public String getRole(String username){
        String queryString = "SELECT " + COLUMN_ROLE + " FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] {username});
        String accountRole = "";
        if(cursor.moveToFirst()){
            accountRole = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));
        }
        cursor.close();

        return accountRole;
    }

    public Account getAccount(String username){
        String queryString = "SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] {username});
        if(cursor.moveToFirst()) {

            String strUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            String strPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            String strFirstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
            String strLastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));

            if (getRole(username).equals("Admin")) {
                Account account = new Admin(strFirstName, strLastName, strUsername, strPassword);
                return account;
            } else if (getRole(username).equals("Member")) {
                Account account = new Member(strFirstName, strLastName, strUsername, strPassword);
                return account;
            } else if (getRole(username).equals("Instructor")) {
                Account account = new Instructor(strFirstName, strLastName, strUsername, strPassword);
                return account;
            } else
                return null;
        }
        return null;
    }
}
