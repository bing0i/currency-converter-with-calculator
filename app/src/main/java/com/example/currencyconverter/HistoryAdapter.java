package com.example.currencyconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryInfo> {
    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List<HistoryInfo> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HistoryAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.history_item_layout, null);

            viewHolder = new HistoryAdapter.ViewHolder();

            viewHolder.tvDateAndTime = (TextView)convertView.findViewById(R.id.dateAndTime);
            viewHolder.tvAmount = (TextView)convertView.findViewById(R.id.amount);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (HistoryAdapter.ViewHolder)convertView.getTag();
        }

        HistoryInfo historyInfo = getItem(position);
        viewHolder.tvDateAndTime.setText(historyInfo.dateAndTime);
        viewHolder.tvAmount.setText(historyInfo.amount);

        return convertView;
    }

    private class ViewHolder {
        TextView tvDateAndTime = null;
        TextView tvAmount = null;
    }
}
