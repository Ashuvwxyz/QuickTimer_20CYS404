package com.example.quicktimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SoundDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sounds.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "sounds";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SOUND_NAME = "sound_name";
    public static final String COLUMN_SOUND_URI = "sound_uri";

    public SoundDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SOUND_NAME + " TEXT, " +
                COLUMN_SOUND_URI + " TEXT)";
        db.execSQL(CREATE_TABLE);

        // Insert default sounds
        insertDefaultSounds(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//    private void insertDefaultSounds(SQLiteDatabase db) {
//        insertSound(db, "Notification 1", "android.resource://com.example.quicktimer/" + R.raw.notification1);
//        insertSound(db, "Notification 2", "android.resource://com.example.quicktimer/" + R.raw.notification2);
//        insertSound(db, "Notification 3", "android.resource://com.example.quicktimer/" + R.raw.notification3);
//    }
    private void insertDefaultSounds(SQLiteDatabase db) {
        insertSound(db, "Default Notification 1", "content://settings/system/notification_sound");
        insertSound(db, "Default Notification 2", "content://settings/system/ringtone");
        insertSound(db, "Default Notification 3", "content://settings/system/alarm_alert");
    }

    private void insertSound(SQLiteDatabase db, String name, String uri) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOUND_NAME, name);
        values.put(COLUMN_SOUND_URI, uri);
        db.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllSoundValues() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " ASC");
    }
}