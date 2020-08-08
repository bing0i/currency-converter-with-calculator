package com.example.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    public static ArrayList<HistoryInfo> historyCurrencyInfoArrayList = new ArrayList<>();
    public static HistoryAdapter historyAdapter = null;
    private static boolean readTXT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        if (!readTXT) {
            readHistory();
            readTXT = true;
        }
        setListView();
    }

    private void setListView() {
        ListView listView = (ListView)findViewById(R.id.listView);
        historyAdapter = new HistoryAdapter(this, 0, historyCurrencyInfoArrayList);

        setEventListView(listView);

        listView.setAdapter(historyAdapter);
    }

    private void setEventListView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), HistoryDetailActivity.class);
                HistoryDetailActivity.currencyInfoArrayList = new ArrayList<CurrencyInfo>();
                HistoryDetailActivity.currencyInfoArrayList.addAll(historyCurrencyInfoArrayList.get(position).targetCurrencies);
                startActivity(intent);
            }
        });
    }

    private void readHistory() {
        ArrayList<String> jsonStrings = new ArrayList<String>();
        WriteSDCard readSDCard = new WriteSDCard();
        if (readSDCard.readFromSDFile(" historyList.txt"))
            jsonStrings = WriteSDCard.strings;
        else
            return;
        try {
            for (int i = 0; i < jsonStrings.size(); i++) {
                JSONObject JSONobj = new JSONObject(jsonStrings.get(i));
                String dateAndTime = JSONobj.getString("dateAndTime");
                String amountBase = String.valueOf(JSONobj.getDouble("amount"));
                JSONArray currencyInfos = JSONobj.getJSONArray("targetCurrencies");
                ArrayList<CurrencyInfo> targets = new ArrayList<>();
                for (int j = 0; j < currencyInfos.length(); j++) {
                    JSONObject currencyObj = (JSONObject) currencyInfos.get(j);
                    String currency = currencyObj.getString("currency");
                    String fullCurrency = currencyObj.getString("fullCurrency");
                    int flagPath = currencyObj.getInt("flagPath");
                    double amount = currencyObj.getDouble("amount");
                    CurrencyInfo currencyInfo = new CurrencyInfo(currency, fullCurrency, flagPath, amount);
                    targets.add(currencyInfo);
                }
                HistoryInfo historyInfo = new HistoryInfo(targets, dateAndTime, amountBase);
                historyCurrencyInfoArrayList.add(historyInfo);
            }
        } catch (JSONException e) {
            Log.d("FAILLL", "Fail to parse JSON (historyList.txt)");
        }
    }
}
