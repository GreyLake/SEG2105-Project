package com.josiah.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseAccount extends SQLiteOpenHelper {

    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_ROLE = "ROLE";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String ACCOUNTS = "ACCOUNTS";
    public static final String COLUMN_ID = "ID";

    // This is our database
    public DatabaseAccount(@Nullable Context context){

        super(context, "account.db", null, 1);
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

    public boolean createAccount(Account_ account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRST_NAME, account.getFirstName());
        cv.put(COLUMN_LAST_NAME, account.getLastName());
        cv.put(COLUMN_USERNAME, account.getUsername());
        cv.put(COLUMN_PASSWORD, account.getPassword());
        cv.put(COLUMN_ROLE, account.getRole());

        long insert = db.insert(ACCOUNTS, null, cv);
        db.close();
        return insert != -1;
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
        db.close();
        return passwordDatabase.equals(password);
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
        db.close();
        return accountRole;
    }

    public int getID(String username){
        String queryString = "SELECT " + COLUMN_ID + " FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] {username});
        int id = 0;
        if(cursor.moveToFirst()){
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        db.close();
        return id;
    }

    public Account_ getAccount(String username){
        String queryString = "SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] {username});
        if(cursor.moveToFirst()) {

            String strUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            String strPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            String strFirstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
            String strLastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));

            cursor.close();
            switch (getRole(username)) {
                case "Admin": {
                    return new Admin(strFirstName, strLastName, strUsername, strPassword);
                }
                case "Member": {
                    return new Member(strFirstName, strLastName, strUsername, strPassword);
                }
                case "Instructor": {
                    return new Instructor(strFirstName, strLastName, strUsername, strPassword);
                }
                default: {
                    db.close();
                    return null;
                }
            }
        }
        db.close();
        return null;
    }

    public ArrayList<Account_> getAllAccounts(){

        ArrayList<Account_> returnList = new ArrayList<>();

        String queryString = "Select * FROM " + ACCOUNTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String strUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                String strPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                String strFirstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
                String strLastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                String strRole = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));

                if(!strRole.equals("Admin")) {
                    Account_ account = new Account_(strFirstName, strLastName, strUsername, strPassword, strRole);
                    returnList.add(account);
                }

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public void updateAccount(int id, String firstName, String lastName, String username, String role, String password){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FIRST_NAME,firstName);
        contentValues.put(COLUMN_LAST_NAME,lastName);
        contentValues.put(COLUMN_USERNAME,username);
        contentValues.put(COLUMN_ROLE,role);
        contentValues.put(COLUMN_PASSWORD,password);

        db.update(ACCOUNTS,contentValues,COLUMN_ID+"= ?",new String[]{String.valueOf(id)});

        db.close();
    }

    public void deleteAccount(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(ACCOUNTS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
    }


}
