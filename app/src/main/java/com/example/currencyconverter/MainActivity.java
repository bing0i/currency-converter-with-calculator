package com.example.currencyconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<CurrencyInfo> currencyInfoArrayList = new ArrayList<>();
    public static ResultCurrencyAdapter resultCurrencyAdapter = null;
    private static String expression = "";
    private static String baseAmount = "0.0";
    public static GetRatesTask getRatesTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_main);
        else
            setContentView(R.layout.landscape_layout);
        setListView();
        createColumnsOfNumpad();
        initializeSelectionCurrencyInfoArrayList();
        getRatesTask = new GetRatesTask(getApplicationContext());
        getRatesTask.getJSON();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.landscape_layout);
        }
        setListView();
        createColumnsOfNumpad();
        setBaseAmountAndExpression();
    }

    private void setBaseAmountAndExpression() {
        TextView tvBaseAmount = (TextView)findViewById(R.id.amountBase);
        tvBaseAmount.setText(baseAmount);
        TextView tvExpression = (TextView)findViewById(R.id.expression);
        tvExpression.setText(expression);
    }

    private void initializeSelectionCurrencyInfoArrayList() {
        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("USD", "United States Dollar US$", R.drawable.ic_united_states_of_america_flag));
        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("EUR", "Euro €", R.drawable.ic_europe_flag));
        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("JPY", "Japanese Yen ¥", R.drawable.ic_japan_flag));
        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("GBP", "Pound Sterling £", R.drawable.ic_united_kingdom_flag));
        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("CAD", "Canadian Dollar C$", R.drawable.ic_canada_flag));
        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("AUD", "Australian Dollar A$", R.drawable.ic_australia_flag));
        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("KRW", "South Korean Won ₩", R.drawable.ic_south_korea_flag));
//        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("TWD", "New Taiwan Dollar NT$", R.drawable.ic_taiwan_flag));
        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("RUB", "Russian Ruble ₽", R.drawable.ic_russia_flag));
        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("NZD", "New Zealand Dollar NZ$", R.drawable.ic_new_zealand_flag));
        SelectionActivity.selectionCurrencyInfoArrayList.add(new CurrencyInfo("THB", "Thai Baht ฿", R.drawable.ic_thailand_flag));
    }

    private void createColumnsOfNumpad() {
        String[] labels = new String[]{"7", "4", "1", ".",
                "8", "5", "2", "0",
                "9", "6", "3", "=",
                "⌫", "/", "*", "-", "+"};
        int[] ids = {R.id.column1, R.id.column2, R.id.column3, R.id.column4};

        for (int i = 0; i < ids.length; i++) {
            LinearLayout column = (LinearLayout)findViewById(ids[i]);
            for (int j = 0; j < 4; j++) {
                Button button = createButton(labels[i*4+j], i*4+j);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
                button.setLayoutParams(param);
                column.addView(button);
            }
            if (i == 3) {
                Button button = createButton(labels[labels.length - 1], labels.length - 1);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
                button.setLayoutParams(param);
                column.addView(button);
            }
        }
    }

    private Button createButton(final String label, int i) {
        Button button = new Button(getApplicationContext());
        button.setText(label);
        button.setId(i);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleInput(label);
            }
        });
        button.setBackgroundColor(getResources().getColor(R.color.primaryText));
        button.setTextColor(getResources().getColor(R.color.iconAndText));
        return button;
    }

    private void handleInput(String label) {
        TextView textView = (TextView)findViewById(R.id.expression);
        String text = textView.getText().toString();
        String expr = "";
        String amount = "";
        if (label.equals("⌫") && text.length() > 0) {
            expr = text.substring(0, text.length() - 1);
            textView.setText(expr);
        }
        if (label.equals("="))
            if (indexOfOperator(text) == text.length() - 1)
                return;
            else {
                amount = setAmounts(text);
                TextView tvBase = (TextView)findViewById(R.id.amountBase);
                tvBase.setText(amount);
                expr = amount;
            }
        else if (!checkValidExpression(label))
            return;
        else {
            expr = text + label;
            textView.setText(expr);
        }
        expression = expr;
        baseAmount = amount;
    }

    private String setAmounts(String text) {
        Calculator cal = new Calculator(text);
        double resultCal = cal.calculate();

        double resultConv = 0.0;
        CurrencyInfo currencyInfo = null;
        for (int i = 0; i < currencyInfoArrayList.size(); i++) {
            currencyInfo = currencyInfoArrayList.get(i);
            CurrencyConverter converter = new CurrencyConverter(resultCal, currencyInfo.currency, getRatesTask.getRate(currencyInfo.currency));
            resultConv = round(converter.convert(), 2);
            currencyInfo.amount = resultConv;
            currencyInfoArrayList.set(i, currencyInfo);
        }
        resultCurrencyAdapter.notifyDataSetChanged();

        return String.valueOf(round(resultCal, 2));
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private boolean checkValidExpression(String label) {
        if ((label.equals("+") || label.equals("-") || label.equals("*") || label.equals("/")) && !checkOperators())
            return false;
        if (label.equals(".") && !checkDot())
            return false;
        if (label.equals("⌫"))
            return false;
        return true;
    }

    private boolean checkDot() {
        TextView textView = (TextView)findViewById(R.id.expression);
        String text = textView.getText().toString();
        int posOperator = indexOfOperator(text);
        int posFirstDot = text.indexOf(".");
        int posLastDot = text.lastIndexOf(".");

        if (posFirstDot == -1)
            return true;
        if (posFirstDot == posLastDot && posOperator > posFirstDot)
            return true;
        else
            return false;
    }

    private int indexOfOperator(String text) {
        if (text.indexOf("+") >= 0)
            return text.indexOf("+");
        else if (text.indexOf("-") >= 0)
            return text.indexOf("-");
        else if (text.indexOf("*") >= 0)
            return text.indexOf("*");
        else if (text.indexOf("/") >= 0)
            return text.indexOf("/");
        return -1;
    }

    private boolean checkOperators() {
        TextView textView = (TextView)findViewById(R.id.expression);
        String text = textView.getText().toString();
        if (text.length() == 0)
            return false;
        return (text.indexOf("+") == -1 && text.indexOf("-") == -1 && text.indexOf("*") == -1 && text.indexOf("/") == -1);
    }

    private void setListView() {
        ListView listView = (ListView)findViewById(R.id.listView);
        setEventListView(listView);
        resultCurrencyAdapter = new ResultCurrencyAdapter(this, 0, currencyInfoArrayList);
        listView.setAdapter(resultCurrencyAdapter);
    }

    private void setEventListView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CurrencyInfo currencyInfo = (CurrencyInfo)adapterView.getAdapter().getItem(position);
                currencyInfoArrayList.remove(currencyInfo);
                resultCurrencyAdapter.notifyDataSetChanged();

                SelectionActivity.selectionCurrencyInfoArrayList.add(currencyInfo);
                SelectionActivity.currencyAdapter.notifyDataSetChanged();
            }
        });
    }

    public void startSelectionActivity(View view) {
        if (SelectionActivity.selectionCurrencyInfoArrayList.size() == 0)
            return;
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtra("expression", expression);
        startActivity(intent);
    }
}