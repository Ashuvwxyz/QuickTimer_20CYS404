package com.example.quicktimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {
    private EditText timerText;
    private Button startButton, stopButton, resetButton;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerText = findViewById(R.id.timer_text);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        resetButton = findViewById(R.id.reset_button);

        startButton.setOnClickListener(view -> startTimer());
        stopButton.setOnClickListener(view -> stopTimer());
        resetButton.setOnClickListener(view -> resetTimer());

        timerText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateTimerInput(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void startTimer() {
        if (isRunning) return; // Prevent multiple clicks from starting multiple timers

        String[] timeParts = timerText.getText().toString().split(":");
        if (timeParts.length != 3) return; // Ensure correct format

        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        int seconds = Integer.parseInt(timeParts[2]);

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
                timerText.setText("00:00:00");
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
        timerText.setText("00:00:00");
    }

    private void updateTimerDisplay(long millisUntilFinished) {
        int hours = (int) (millisUntilFinished / 3600000);
        int minutes = (int) (millisUntilFinished % 3600000) / 60000;
        int seconds = (int) (millisUntilFinished % 60000) / 1000;
        timerText.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    private void validateTimerInput(String input) {
        String[] timeParts = input.split(":");
        if (timeParts.length != 3) {
            timerText.setError("Invalid format. Use HH:MM:SS");
            return;
        }

        try {
            int hours = Integer.parseInt(timeParts[0]);
            int minutes = Integer.parseInt(timeParts[1]);
            int seconds = Integer.parseInt(timeParts[2]);

            if (minutes >= 60 || seconds >= 60) {
                timerText.setError("Minutes and seconds must be less than 60");
            } else {
                timerText.setError(null);
            }
        } catch (NumberFormatException e) {
            timerText.setError("Invalid number format");
        }
    }
}