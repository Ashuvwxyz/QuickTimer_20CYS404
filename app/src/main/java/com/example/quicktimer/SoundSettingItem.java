package com.example.quicktimer;

public class SoundSettingItem {
    private int id;
    private String soundName;
    private String soundUri;

    public SoundSettingItem(int id, String soundName, String soundUri) {
        this.id = id;
        this.soundName = soundName;
        this.soundUri = soundUri;
    }

    public int getId() {
        return id;
    }

    public String getSoundName() {
        return soundName;
    }

    public String getSoundUri() {
        return soundUri;
    }
}