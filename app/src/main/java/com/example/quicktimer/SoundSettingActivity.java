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
    private SoundSettingsAdapter adapter;
    private SoundDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_setting);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new SoundDatabaseHelper(this);
        List<SoundSettingItem> soundSettings = getSoundSettings();
        adapter = new SoundSettingsAdapter(soundSettings, this);
        recyclerView.setAdapter(adapter);
    }

    private List<SoundSettingItem> getSoundSettings() {
        List<SoundSettingItem> soundList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllSoundValues();

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(SoundDatabaseHelper.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(SoundDatabaseHelper.COLUMN_SOUND_NAME);
            int uriIndex = cursor.getColumnIndex(SoundDatabaseHelper.COLUMN_SOUND_URI);

            if (idIndex >= 0 && nameIndex >= 0 && uriIndex >= 0) {
                do {
                    int id = cursor.getInt(idIndex);
                    String soundName = cursor.getString(nameIndex);
                    String soundUri = cursor.getString(uriIndex);
                    soundList.add(new SoundSettingItem(id, soundName, soundUri));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return soundList;
    }
}