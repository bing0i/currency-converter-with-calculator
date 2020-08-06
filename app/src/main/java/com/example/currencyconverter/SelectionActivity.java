package com.example.currencyconverter;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {
    public static ArrayList<CurrencyInfo> selectionCurrencyInfoArrayList = new ArrayList<>();
    public static CurrencyAdapter currencyAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        setListView();
    }

    private void setListView() {
        ListView listView = (ListView)findViewById(R.id.listView);
        currencyAdapter = new CurrencyAdapter(this, 0, selectionCurrencyInfoArrayList);

        setEventListView(listView);

        listView.setAdapter(currencyAdapter);
    }

    private void setEventListView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CurrencyInfo currencyInfo = (CurrencyInfo)adapterView.getAdapter().getItem(position);
                selectionCurrencyInfoArrayList.remove(currencyInfo);
                currencyAdapter.notifyDataSetChanged();

                String text = getIntent().getStringExtra("expression");

                MainActivity.currencyInfoArrayList.add(setAmountOfSingleRow(text, currencyInfo));
                MainActivity.resultCurrencyAdapter.notifyDataSetChanged();

                finish();
            }
        });
    }

    private CurrencyInfo setAmountOfSingleRow(String text, CurrencyInfo currencyInfo) {
        Calculator cal = new Calculator(text);
        double resultCal = cal.calculate();
        double resultConv = 0.0;
        CurrencyConverter converter = new CurrencyConverter(resultCal, currencyInfo.currency, MainActivity.getRatesTask.getRate(currencyInfo.currency));
        resultConv = MainActivity.round(converter.convert(), 2);
        currencyInfo.amount = resultConv;
        return currencyInfo;
    }
}