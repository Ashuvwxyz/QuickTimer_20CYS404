package com.example.quicktimer;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TimerHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TimerHistoryAdapter adapter;
    private TimerDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_history);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                int id = cursor.getInt(cursor.getColumnIndex(TimerDatabaseHelper.COLUMN_ID));
                String systemTime = cursor.getString(cursor.getColumnIndex(TimerDatabaseHelper.COLUMN_SYSTEM_TIME));
                String timerValue = cursor.getString(cursor.getColumnIndex(TimerDatabaseHelper.COLUMN_TIMER_VALUE));
                historyList.add(new TimerHistoryItem(id, systemTime, timerValue));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return historyList;
    }
}