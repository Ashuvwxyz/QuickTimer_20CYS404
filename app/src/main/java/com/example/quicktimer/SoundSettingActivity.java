package com.example.quicktimer;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SoundSettingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SoundSettingAdapter adapter;
    private SoundDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_setting);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new SoundDatabaseHelper(this);
        List<SoundSettingItem> soundSettings = getSoundSettings();
        adapter = new SoundSettingAdapter(soundSettings);
        recyclerView.setAdapter(adapter);
    }

    private List<SoundSettingItem> getSoundSettings() {
        List<SoundSettingItem> soundList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllSoundValues();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(SoundDatabaseHelper.COLUMN_ID));
                String soundName = cursor.getString(cursor.getColumnIndex(SoundDatabaseHelper.COLUMN_SOUND_NAME));
                String soundUri = cursor.getString(cursor.getColumnIndex(SoundDatabaseHelper.COLUMN_SOUND_URI));
                soundList.add(new SoundSettingItem(id, soundName, soundUri));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return soundList;
    }
}