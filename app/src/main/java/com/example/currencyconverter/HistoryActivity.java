package com.example.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    public static ArrayList<HistoryInfo> historyCurrencyInfoArrayList = new ArrayList<>();
    public static HistoryAdapter historyAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        initializeHistoryCurrencyInfoArrayList();
        setListView();
    }

    private void setListView() {
        ListView listView = (ListView)findViewById(R.id.listView);
        historyAdapter = new HistoryAdapter(this, 0, historyCurrencyInfoArrayList);

        setEventListView(listView);

        listView.setAdapter(historyAdapter);
    }

    private void initializeHistoryCurrencyInfoArrayList () {
        historyCurrencyInfoArrayList.add(new HistoryInfo("45645656", "2.0000"));
        historyCurrencyInfoArrayList.add(new HistoryInfo("45645656", "2.0000"));
        historyCurrencyInfoArrayList.add(new HistoryInfo("45645656", "2.0000"));
        historyCurrencyInfoArrayList.add(new HistoryInfo("45645656", "2.0000"));
        historyCurrencyInfoArrayList.add(new HistoryInfo("45645656", "2.0000"));
    }

    private void setEventListView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                CurrencyInfo currencyInfo = (CurrencyInfo)adapterView.getAdapter().getItem(position);
//                historyCurrencyInfoArrayList.remove(currencyInfo);
//                historyAdapter.notifyDataSetChanged();
//
//                String text = getIntent().getStringExtra("expression");
//
//                MainActivity.currencyInfoArrayList.add(setAmountOfSingleRow(text, currencyInfo));
//                MainActivity.resultCurrencyAdapter.notifyDataSetChanged();
//
//                finish();
            }
        });
    }
}
