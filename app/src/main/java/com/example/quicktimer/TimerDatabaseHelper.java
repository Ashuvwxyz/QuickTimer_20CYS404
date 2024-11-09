package com.example.quicktimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimerDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "timers1.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "timers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SYSTEM_TIME = "system_time";
    public static final String COLUMN_TIMER_VALUE = "timer_value";

    public TimerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SYSTEM_TIME + " TEXT, " +
                COLUMN_TIMER_VALUE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTimerValue(String systemTime, String timerValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SYSTEM_TIME, systemTime);
        values.put(COLUMN_TIMER_VALUE, timerValue);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getAllTimerValues() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " DESC");
    }
}