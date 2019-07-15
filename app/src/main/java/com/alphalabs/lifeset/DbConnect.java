package com.alphalabs.lifeset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.ResultSet;

public class DbConnect extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lifeset_db";

    public DbConnect(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+KEY_NAME+" TEXT, "+KEY_YEAR+" INT, "+KEY_PT+" TEXT, "+KEY_CG+" REAL, "+KEY_SPRTS+" INT, "+KEY_RELN+" INT)");
        db.execSQL("CREATE TABLE monday (" + KEY_DESC + " TEXT, " + KEY_TIME + " TEXT )");
        db.execSQL("CREATE TABLE tuesday (" + KEY_DESC + " TEXT, " + KEY_TIME + " TEXT )");
        db.execSQL("CREATE TABLE wednesday (" + KEY_DESC + " TEXT, " + KEY_TIME + " TEXT )");
        db.execSQL("CREATE TABLE thursday (" + KEY_DESC + " TEXT, " + KEY_TIME + " TEXT )");
        db.execSQL("CREATE TABLE friday (" + KEY_DESC + " TEXT, " + KEY_TIME + " TEXT )");
        db.execSQL("CREATE TABLE saturday (" + KEY_DESC + " TEXT, " + KEY_TIME + " TEXT )");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    //Username database
    private static final String TABLE_NAME = "userdetails";
    private static final String KEY_NAME = "username";
    private static final String KEY_YEAR = "year";
    private static final String KEY_PT = "pt_string";
    private static final String KEY_CG = "currCG";
    private static final String KEY_SPRTS = "dailysportsHrs";
    private static final String KEY_RELN = "relationship";

    // code to add the new name
    void addName(String uname, int yr, String pt, float cg, int spr, int reln) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, uname); // userName
        values.put(KEY_YEAR, yr);
        values.put(KEY_PT, pt);
        values.put(KEY_CG, cg);
        values.put(KEY_SPRTS, spr);
        values.put(KEY_RELN, reln);
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        //2nd argument is String containing nullColumnHack

        db.close();
    }

    void truncate_names()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }

    // code to get the single contact
    String getUname() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        System.out.println(cursor.getCount());
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(0);
    }

    //Days database
    private static String TABLE_DAY;
    private static final String KEY_DESC = "desc";
    private static final String KEY_TIME = "time";

    // code to add the new name
    void addActDay(int dayid, String desc, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        settableday(dayid);
        ContentValues values = new ContentValues();
        values.put(KEY_DESC, desc); // description
        values.put(KEY_TIME, time); // time duration

        // Inserting Row
        db.insert(TABLE_DAY, null, values);
        //2nd argument is String containing nullColumnHack

        db.close();
    }

    void truncate_day(int dayid)
    {
        settableday(dayid);
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }

    // code to get the single contact
    Cursor getActDay(int dayid) {

        settableday(dayid);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DAY, null);
    }

    private void settableday(int dayid)
    {
        if (dayid == 1) TABLE_DAY = "monday";
        else if (dayid == 2) TABLE_DAY = "tuesday";
        else if (dayid == 3) TABLE_DAY = "wednesday";
        else if (dayid == 4) TABLE_DAY = "thursday";
        else if (dayid == 5) TABLE_DAY = "friday";
        else TABLE_DAY = "saturday";
    }
}