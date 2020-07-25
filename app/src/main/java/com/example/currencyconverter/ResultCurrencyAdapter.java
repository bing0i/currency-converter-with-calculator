package com.example.currencyconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ResultCurrencyAdapter extends ArrayAdapter<CurrencyInfo> {

    public ResultCurrencyAdapter(@NonNull Context context, int resource, @NonNull List<CurrencyInfo> currencyInfoList) {
        super(context, resource, currencyInfoList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ResultCurrencyAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.result_item_layout, null);

            viewHolder = new ResultCurrencyAdapter.ViewHolder();

            viewHolder.tvCurrency = (TextView)convertView.findViewById(R.id.currency);
            viewHolder.tvFullCurrency = (TextView)convertView.findViewById(R.id.fullCurrency);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.flag);
            viewHolder.tvAmount = (TextView)convertView.findViewById(R.id.amount);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ResultCurrencyAdapter.ViewHolder)convertView.getTag();
        }

        CurrencyInfo currencyInfo = getItem(position);
        viewHolder.tvCurrency.setText(currencyInfo.currency);
        viewHolder.tvFullCurrency.setText(currencyInfo.fullCurrency);
        viewHolder.imageView.setImageResource(currencyInfo.flagPath);
        viewHolder.imageView.setTag(currencyInfo.flagPath);
        viewHolder.tvAmount.setText(String.valueOf(currencyInfo.amount));

        return convertView;
    }

    private class ViewHolder {
        TextView tvCurrency = null;
        TextView tvFullCurrency = null;
        ImageView imageView = null;
        TextView tvAmount = null;
    }
}
