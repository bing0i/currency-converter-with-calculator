package com.example.currencyconverter;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistoryDetailActivity extends AppCompatActivity {
    public static ArrayList<CurrencyInfo> currencyInfoArrayList = new ArrayList<>();
    public static ResultCurrencyAdapter resultCurrencyAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        initializeCurrencyInfoArrayList();
        setListView();
    }

    private void setListView() {
        ListView listView = (ListView)findViewById(R.id.listView);
        resultCurrencyAdapter = new ResultCurrencyAdapter(this, 0, currencyInfoArrayList);

        listView.setAdapter(resultCurrencyAdapter);
    }

    private void initializeCurrencyInfoArrayList() {
        currencyInfoArrayList.add(new CurrencyInfo("USD", "United States Dollar US$", R.drawable.ic_united_states_of_america_flag, 0.16484));
//        currencyInfoArrayList.add(new CurrencyInfo("EUR", "Euro €", R.drawable.ic_europe_flag));
        currencyInfoArrayList.add(new CurrencyInfo("JPY", "Japanese Yen ¥", R.drawable.ic_japan_flag, 0.16484));
        currencyInfoArrayList.add(new CurrencyInfo("GBP", "Pound Sterling £", R.drawable.ic_united_kingdom_flag, 0.16484));
        currencyInfoArrayList.add(new CurrencyInfo("CAD", "Canadian Dollar C$", R.drawable.ic_canada_flag, 0.16484));
        currencyInfoArrayList.add(new CurrencyInfo("AUD", "Australian Dollar A$", R.drawable.ic_australia_flag, 0.16484));
        currencyInfoArrayList.add(new CurrencyInfo("KRW", "South Korean Won ₩", R.drawable.ic_south_korea_flag, 0.16484));
//        currencyInfoArrayList.add(new CurrencyInfo("TWD", "New Taiwan Dollar NT$", R.drawable.ic_taiwan_flag));
        currencyInfoArrayList.add(new CurrencyInfo("RUB", "Russian Ruble ₽", R.drawable.ic_russia_flag, 0.16484));
        currencyInfoArrayList.add(new CurrencyInfo("NZD", "New Zealand Dollar NZ$", R.drawable.ic_new_zealand_flag, 0.16484));
        currencyInfoArrayList.add(new CurrencyInfo("THB", "Thai Baht ฿", R.drawable.ic_thailand_flag, 0.16484));
    }
}
