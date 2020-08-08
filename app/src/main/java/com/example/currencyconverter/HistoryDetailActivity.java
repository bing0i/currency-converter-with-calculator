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
//        setCurrencyInfoArrayList();
        setListView();
    }

    private void setListView() {
        ListView listView = (ListView)findViewById(R.id.listView);
        resultCurrencyAdapter = new ResultCurrencyAdapter(this, 0, currencyInfoArrayList);

        listView.setAdapter(resultCurrencyAdapter);
    }

//    private void setCurrencyInfoArrayList() {
//        int index = getIntent().getIntExtra("position", -1);
//        HistoryInfo historyInfo = null;
//        if (index != -1)
//            historyInfo = HistoryActivity.historyCurrencyInfoArrayList.get(index);
//        for (int i = 0; i < historyInfo.targetCurrencies.size(); i++)
//            currencyInfoArrayList.add(historyInfo.targetCurrencies.get(i));
//    }
}
