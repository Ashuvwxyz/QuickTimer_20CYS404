// TimerHistoryItem.java
package com.example.quicktimer;

public class TimerHistoryItem {
    private int id;
    private String timerValue;

    public TimerHistoryItem(int id, String timerValue) {
        this.id = id;
        this.timerValue = timerValue;
    }

    public int getId() {
        return id;
    }

    public String getTimerValue() {
        return timerValue;
    }
}