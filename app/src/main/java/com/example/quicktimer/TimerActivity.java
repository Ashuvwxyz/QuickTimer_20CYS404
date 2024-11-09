package com.example.quicktimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TimerActivity extends AppCompatActivity {
    private EditText hoursInput, minutesInput, secondsInput;
    private Button startButton, stopButton, resetButton;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        hoursInput = findViewById(R.id.hours_input);
        minutesInput = findViewById(R.id.minutes_input);
        secondsInput = findViewById(R.id.seconds_input);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        resetButton = findViewById(R.id.reset_button);
        FloatingActionButton fabSoundSettings = findViewById(R.id.fab_sound_settings);
        FloatingActionButton fabTimerHistory = findViewById(R.id.fab_timer_history);

        startButton.setOnClickListener(view -> startTimer());
        stopButton.setOnClickListener(view -> stopTimer());
        resetButton.setOnClickListener(view -> resetTimer());

        fabSoundSettings.setOnClickListener(view -> {
            Intent intent = new Intent(TimerActivity.this, SoundSettingActivity.class);
            startActivity(intent);
        });

        fabTimerHistory.setOnClickListener(view -> {
            Intent intent = new Intent(TimerActivity.this, TimerHistoryActivity.class);
            startActivity(intent);
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateTimerInput();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        hoursInput.addTextChangedListener(textWatcher);
        minutesInput.addTextChangedListener(textWatcher);
        secondsInput.addTextChangedListener(textWatcher);
    }

    private void startTimer() {
        if (isRunning) return; // Prevent multiple clicks from starting multiple timers

        int hours = Integer.parseInt(hoursInput.getText().toString());
        int minutes = Integer.parseInt(minutesInput.getText().toString());
        int seconds = Integer.parseInt(secondsInput.getText().toString());

        timeInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;
        if (timeInMillis <= 0) return; // Ignore if time is zero

        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isRunning = true;
                updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                resetTimer();
                isRunning = false;
            }
        }.start();

        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isRunning = false;
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private void resetTimer() {
        stopTimer();
        hoursInput.setText("00");
        minutesInput.setText("00");
        secondsInput.setText("00");
    }

    private void updateTimerDisplay(long millisUntilFinished) {
        int hours = (int) (millisUntilFinished / 3600000);
        int minutes = (int) (millisUntilFinished % 3600000) / 60000;
        int seconds = (int) (millisUntilFinished % 60000) / 1000;
        hoursInput.setText(String.format("%02d", hours));
        minutesInput.setText(String.format("%02d", minutes));
        secondsInput.setText(String.format("%02d", seconds));
    }

    private void validateTimerInput() {
        try {
            int hours = Integer.parseInt(hoursInput.getText().toString());
            int minutes = Integer.parseInt(minutesInput.getText().toString());
            int seconds = Integer.parseInt(secondsInput.getText().toString());

            if (minutes >= 60 || seconds >= 60) {
                minutesInput.setError("Minutes and seconds must be less than 60");
                secondsInput.setError("Minutes and seconds must be less than 60");
            } else {
                minutesInput.setError(null);
                secondsInput.setError(null);
            }
        } catch (NumberFormatException e) {
            hoursInput.setError("Invalid number format");
            minutesInput.setError("Invalid number format");
            secondsInput.setError("Invalid number format");
        }
    }
}