package com.example.mysos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySOSDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // database name
    private static final String DATABASE_NAME = "contact";
    // tables
    private static final String USER_DETAIL = "user";
    private static final String CONTACT_TABLE = "contact";
    private static final String HISTORY_TABLE = "history";
    // contact table and user detail table column names
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    // history table column name
    private static final String INDEX = "itemIndex";
    private static final String HISTORY_ITEM ="history_item";

    // only 1 user
    private static final int MAXUSER =1;
    // only up to 5 contact persons
    private static final int MAXCONTACT =5;


    public MySOSDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // contact table
        sqLiteDatabase.execSQL("CREATE TABLE " + CONTACT_TABLE + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT" + ")");
        // user detail table
        sqLiteDatabase.execSQL("CREATE TABLE " + USER_DETAIL + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT" + ")");
        // history table
        sqLiteDatabase.execSQL("CREATE TABLE " + HISTORY_TABLE + " (" +
                INDEX + " TEXT, " +
                HISTORY_ITEM + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_DETAIL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE);
        onCreate(sqLiteDatabase);
    }

    // insert data to history table
    boolean insertHistory(String message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long currentIndex = getNumOfRows(HISTORY_TABLE);
        values.put(INDEX, Long.toString(currentIndex+1));
        values.put(HISTORY_ITEM, message);
        long result = db.insert(HISTORY_TABLE, null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // insert data to contact list
    boolean insertData(String name, String phone){
        if (getNumOfRows(CONTACT_TABLE)<MAXCONTACT) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_PHONE, phone);
            long result = db.insert(CONTACT_TABLE, null, values);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else{
            return false;
        }
    }




    // update user details
    boolean insertUser(String name, String phone){
        if(getNumOfRows(USER_DETAIL) < MAXUSER) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_PHONE, phone);
            long result = db.insert(USER_DETAIL, null, values);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    // get all contact details
    Cursor getAllContact(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+CONTACT_TABLE, null);
        return res;
    }

    // get user details
    Cursor getUserDetail(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+USER_DETAIL, null);
        return res;
    }

    // get all history
    Cursor getAllHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+HISTORY_TABLE, null);
        return res;
    }


    // get number of rows in a table
    private long getNumOfRows(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, tableName);
        return count;
    }

    // delete a row in a table according to the row number
    void deleteRow(int n){
        SQLiteDatabase db = this.getWritableDatabase();
        if(nthRow(n) != null)
            System.out.println(nthRow(n));
            db.delete(CONTACT_TABLE,COLUMN_NAME + "=?",new String[]{nthRow(n)});
    }
    
    // delete the user details
    void deleteUser(int n){
        SQLiteDatabase db = this.getWritableDatabase();
        if(nthRow(n) != null)
            db.delete(USER_DETAIL,COLUMN_NAME + "=?",new String[]{userRow(n)});
    }

    // find the nth row string
    String nthRow(int n){
        Cursor allData = getAllContact();
        if(allData.getCount() != 0){
            int i=0;
            String nthRowString = null;
            while(i<n && allData.moveToNext()){
                if (i==n-1) {
                    nthRowString = allData.getString(0);
                }
                i++;
            }
            return nthRowString;
        }else {
            return null;
        }
    }

    // find the user String
    private String userRow(int n){
        Cursor allData = getUserDetail();
        if(allData.getCount() != 0){
            int i=0;
            String nthRowString = null;
            while(i<n && allData.moveToNext()){
                nthRowString = allData.getString(0);
                i++;
            }
            return nthRowString;
        }else {
            return null;
        }
    }

    // get all contact numbers
    ArrayList<String> getAllContactNumbers(){
        ArrayList<String> allPhoneNumbers = new ArrayList<>();
        Cursor allData = getAllContact();
        while(allData.moveToNext()){
            allPhoneNumbers.add( allData.getString(1));
        }
        return  allPhoneNumbers;
    }

    // get all contact names
    String getUserName(){
        Cursor userName = getUserDetail();
        int i =0;
        String name = null;
        while(i<1&&userName.moveToNext()){
            name = userName.getString(0);
            i++;
        }
        return name;
    }

    
}

