package com.choewang.kmetrify.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class KmetrifyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Day";
    private static final int DB_VERSION = 1;
    private static final String createDayTable = "CREATE TABLE DAY (_id INTEGER PRIMARY KEY " +
            "AUTOINCREMENT, date NUMERIC NOT NULL, meterReading INTEGER NOT NULL, startPlace " +
            "INTEGER NOT NULL, finalPlace INTEGER, dailyKilometers INTEGER," +
            "FOREIGN KEY(startPlace) REFERENCES PLACE(placeId), " +
            "FOREIGN KEY(finalPlace) REFERENCES PLACE(placeId))";
    private static final String createPlaceTable = "CREATE TABLE PLACE(_id INTEGER PRIMARY KEY " +
            "AUTOINCREMENT, address TEXT NOT NULL)";
    private static final String createLinkTable = "CREATE TABLE LINKTABLE(dayId INTEGER NOT NULL, " +
            "placeId INTEGER NOT NULL, " +
            "FOREIGN KEY(dayId) REFERENCES DAY(_id), " +
            "FOREIGN KEY(placeId) REFERENCES PLACE(_id))";

    public KmetrifyDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDayTable);
        db.execSQL(createPlaceTable);
        db.execSQL(createLinkTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertDay(SQLiteDatabase db, Date date, int meterReading, String startPlace){
        ContentValues dayValues = new ContentValues();
        dayValues.put("date", String.valueOf(date));
        dayValues.put("meterReading", meterReading);
        ContentValues placeValues = new ContentValues();
        placeValues.put("address", startPlace);
        db.insert("PLACE", null, placeValues);
        int startPlaceId = 0;
        Cursor c = db.rawQuery("SELECT * FROM PLACE ORDER BY _id DESC LIMIT 1", null);
        if (c.moveToFirst()){
            startPlaceId = c.getInt(0);
        }
        dayValues.put("startPlace", startPlaceId);
        db.insert("DAY",null, dayValues);
    }
}
