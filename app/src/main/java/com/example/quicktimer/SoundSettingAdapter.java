package com.example.quicktimer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SoundSettingAdapter extends RecyclerView.Adapter<SoundSettingAdapter.ViewHolder> {
    private List<SoundSettingItem> soundSettings;

    public SoundSettingAdapter(List<SoundSettingItem> soundSettings) {
        this.soundSettings = soundSettings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sound_setting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SoundSettingItem item = soundSettings.get(position);
        holder.soundNameTextView.setText(item.getSoundName());
        holder.soundUriTextView.setText(item.getSoundUri());
    }

    @Override
    public int getItemCount() {
        return soundSettings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView soundNameTextView;
        public TextView soundUriTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            soundNameTextView = itemView.findViewById(R.id.sound_name_text_view);
            soundUriTextView = itemView.findViewById(R.id.sound_uri_text_view);
        }
    }
}