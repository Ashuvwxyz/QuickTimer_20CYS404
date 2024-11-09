package com.example.quicktimer;

public class TimerHistoryItem {
    private int id;
    private String systemTime;
    private String timerValue;

    public TimerHistoryItem(int id, String systemTime, String timerValue) {
        this.id = id;
        this.systemTime = systemTime;
        this.timerValue = timerValue;
    }

    public int getId() {
        return id;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public String getTimerValue() {
        return timerValue;
    }
}