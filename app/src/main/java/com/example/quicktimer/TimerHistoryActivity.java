package com.example.quicktimer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TimerHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TimerHistoryAdapter adapter;
    private TimerDatabaseHelper dbHelper;
    private FloatingActionButton clearHistoryFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_history);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clearHistoryFab = findViewById(R.id.clear_history_fab);
        clearHistoryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
            }
        });

        dbHelper = new TimerDatabaseHelper(this);
        List<TimerHistoryItem> timerHistory = getTimerHistory();
        adapter = new TimerHistoryAdapter(timerHistory);
        recyclerView.setAdapter(adapter);
    }

    private List<TimerHistoryItem> getTimerHistory() {
        List<TimerHistoryItem> historyList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllTimerValues();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(TimerDatabaseHelper.COLUMN_ID);
                int systemTimeIndex = cursor.getColumnIndex(TimerDatabaseHelper.COLUMN_SYSTEM_TIME);
                int timerValueIndex = cursor.getColumnIndex(TimerDatabaseHelper.COLUMN_TIMER_VALUE);

                if (idIndex >= 0 && systemTimeIndex >= 0 && timerValueIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    String systemTime = cursor.getString(systemTimeIndex);
                    String timerValue = cursor.getString(timerValueIndex);
                    historyList.add(new TimerHistoryItem(id, systemTime, timerValue));
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        return historyList;
    }

    private void clearHistory() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TimerDatabaseHelper.TABLE_NAME);
        dbHelper.onCreate(db);
        db.close();
        adapter.updateData(new ArrayList<TimerHistoryItem>());
    }
}