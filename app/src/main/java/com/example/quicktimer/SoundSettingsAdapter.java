package com.example.quicktimer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SoundSettingsAdapter extends RecyclerView.Adapter<SoundSettingsAdapter.ViewHolder> {
    private List<SoundSettingItem> soundSettingItems;
    private Context context;
    private SharedPreferences sharedPreferences;

    public SoundSettingsAdapter(List<SoundSettingItem> soundSettingItems, Context context) {
        this.soundSettingItems = soundSettingItems;
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("QuickTimerPrefs", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sound_setting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SoundSettingItem item = soundSettingItems.get(position);
        holder.soundNameTextView.setText(item.getSoundName());
        holder.soundUriTextView.setText(item.getSoundUri());

        holder.previewButton.setOnClickListener(v -> {
            Uri soundUri = Uri.parse(item.getSoundUri());
            MediaPlayer mediaPlayer = MediaPlayer.create(context, soundUri);
            if (mediaPlayer != null) {
                mediaPlayer.setOnCompletionListener(MediaPlayer::release);
                mediaPlayer.start();
            }
        });

        holder.itemView.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("selected_sound_uri", item.getSoundUri());
            editor.apply();
        });
    }

    @Override
    public int getItemCount() {
        return soundSettingItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView soundNameTextView;
        TextView soundUriTextView;
        Button previewButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            soundNameTextView = itemView.findViewById(R.id.sound_name_text_view);
            soundUriTextView = itemView.findViewById(R.id.sound_uri_text_view);
            previewButton = itemView.findViewById(R.id.preview_button);
        }
    }
}