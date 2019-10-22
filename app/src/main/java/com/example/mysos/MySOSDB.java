package com.example.mysos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MySOSDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contact";
    private static final String USER_DETAIL = "user";
    private static final String TABLE_NAME = "contact";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final int MAXUSER =1;
    private static final int MAXCONTACT =5;


    public MySOSDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + USER_DETAIL + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_DETAIL);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, String phone){
        if (getNumOfRows(TABLE_NAME)<MAXCONTACT) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_PHONE, phone);
            long result = db.insert(TABLE_NAME, null, values);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else{
            return false;
        }

    }

    public boolean insertUser(String name, String phone){
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

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }

    public Cursor getUserDetail(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+USER_DETAIL, null);
        return res;
    }

    public long getNumOfRows(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return count;
    }

    public void deleteRow(int n){
        SQLiteDatabase db = this.getWritableDatabase();
        if(nthRow(n) != null)
            db.delete(TABLE_NAME,COLUMN_NAME + "=?",new String[]{nthRow(n)});
    }
    public void deleteUser(int n){
        SQLiteDatabase db = this.getWritableDatabase();
        if(nthRow(n) != null)
            db.delete(USER_DETAIL,COLUMN_NAME + "=?",new String[]{userRow(n)});
    }

    public String nthRow(int n){
        Cursor allData = getAllData();
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

    public String userRow(int n){
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

    public ArrayList<String> getAllContactNumbers(){
        ArrayList<String> allPhoneNumbers = new ArrayList<>();
        Cursor allData = getAllData();
        while(allData.moveToNext()){
            allPhoneNumbers.add( allData.getString(1));
        }
        return  allPhoneNumbers;
    }

    public String getUserName(){
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

