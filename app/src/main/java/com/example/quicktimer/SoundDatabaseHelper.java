package com.example.quicktimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.RingtoneManager;
import android.net.Uri;

public class SoundDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sounds1.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SOUNDS = "sounds";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SOUND_NAME = "sound_name";
    public static final String COLUMN_SOUND_URI = "sound_uri";

    public SoundDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_SOUNDS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SOUND_NAME + " TEXT, " +
                COLUMN_SOUND_URI + " TEXT)";
        db.execSQL(createTable);

        addInbuiltSounds(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOUNDS);
        onCreate(db);
    }

    private void addInbuiltSounds(SQLiteDatabase db) {
        addSound(db, "Notification Sound 1", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString());
        addSound(db, "Notification Sound 2", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString());
        addSound(db, "Notification Sound 3", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString());
    }

    private void addSound(SQLiteDatabase db, String name, String uri) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOUND_NAME, name);
        values.put(COLUMN_SOUND_URI, uri);
        db.insert(TABLE_SOUNDS, null, values);
    }

    public Cursor getAllSoundValues() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_SOUNDS, null, null, null, null, null, null);
    }
}