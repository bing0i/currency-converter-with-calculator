package com.example.currencyconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CurrencyAdapter extends ArrayAdapter<CurrencyInfo> {
    public CurrencyAdapter(@NonNull Context context, int resource, @NonNull List<CurrencyInfo> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_layout, null);

            viewHolder = new ViewHolder();

            viewHolder.tvCurrency = (TextView)convertView.findViewById(R.id.currency);
            viewHolder.tvFullCurrency = (TextView)convertView.findViewById(R.id.fullCurrency);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.flag);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        CurrencyInfo currencyInfo = getItem(position);
        viewHolder.tvCurrency.setText(currencyInfo.currency);
        viewHolder.tvFullCurrency.setText(currencyInfo.fullCurrency);
        viewHolder.imageView.setImageResource(currencyInfo.flagPath);
        viewHolder.imageView.setTag(currencyInfo.flagPath);

        return convertView;
    }

    private class ViewHolder {
        TextView tvCurrency = null;
        TextView tvFullCurrency = null;
        ImageView imageView = null;
    }

//    private View createRow(int position, ListView parent) {
//        View itemView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_layout, null);
//        CurrencyInfo currencyInfo = getItem(position);
//        setInfo(itemView, currencyInfo);
//        return itemView;
//    }

//    private void setInfo(View itemView, CurrencyInfo currencyInfo) {
//        TextView tvCurrency = (TextView)itemView.findViewById(R.id.currency);
//        tvCurrency.setText(currencyInfo.currency);
//
//        TextView tvFullCurrency = (TextView)itemView.findViewById(R.id.fullCurrency);
//        tvFullCurrency.setText(currencyInfo.fullCurrency);
//
//        ImageView imageView = (ImageView)itemView.findViewById(R.id.flag);
//        imageView.setImageResource(currencyInfo.flagPath);
//        imageView.setTag(currencyInfo.flagPath);
//    }

}
