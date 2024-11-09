package com.example.quicktimer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TimerHistoryAdapter extends RecyclerView.Adapter<TimerHistoryAdapter.ViewHolder> {
    private List<TimerHistoryItem> timerHistory;

    public TimerHistoryAdapter(List<TimerHistoryItem> timerHistory) {
        this.timerHistory = timerHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timer_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimerHistoryItem item = timerHistory.get(position);
        holder.idTextView.setText(String.valueOf(item.getId()));
        holder.systemTimeTextView.setText(item.getSystemTime());
        holder.timerValueTextView.setText(item.getTimerValue());
    }

    @Override
    public int getItemCount() {
        return timerHistory.size();
    }

    public void updateData(List<TimerHistoryItem> newTimerHistory) {
        this.timerHistory = newTimerHistory;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idTextView;
        public TextView systemTimeTextView;
        public TextView timerValueTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.id_text_view);
            systemTimeTextView = itemView.findViewById(R.id.system_time_text_view);
            timerValueTextView = itemView.findViewById(R.id.timer_value_text_view);
        }
    }
}