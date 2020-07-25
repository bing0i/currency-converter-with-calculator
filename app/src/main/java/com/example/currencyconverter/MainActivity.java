package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListView();
        createNumberPad();
    }

    private void createNumberPad() {
//        RelativeLayout numpad = (RelativeLayout)findViewById(R.id.numpad);
//        numpad.setVisibility(LinearLayout.GONE);
        createNumbers();
//        createOperators();
//        hideNumpad();
    }

//    private LinearLayout createOperators() {
//        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.operators);
//        String[] labels = new String[]{"⌫", "+", "-", "*", "/"};
//        for (int i = 0; i < labels.length; i++) {
//            Button button = createButton(labels[i], i + 13);
//            linearLayout.addView(button);
//        }
//        return linearLayout;
//    }

    private GridLayout createNumbers() {
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        String[] labels = new String[]{"7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                ".", "0", "=", "/"};
        for (int i = 0; i < labels.length; i++) {
            Button button = createButton(labels[i], i);
            gridLayout.addView(button);
        }
        return  gridLayout;
    }

    private Button createButton(final String label, int i) {
        Button button = new Button(getApplicationContext());
        button.setText(label);
        button.setId(i);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView)findViewById(R.id.expression);
                String text = textView.getText().toString();
//                if (label.equals("⌫") && text.length() > 0)
//                    textView.setText(text.substring(0, text.length() - 1));
                if (label.equals("="))
                    setAmounts(text);
                else if (!checkValidExpression(label))
                    return;
                else
                    textView.setText(text + label);
            }
        });
        return button;
    }

    public void backspaceEvent(View view) {
        TextView textView = (TextView)findViewById(R.id.expression);
        String text = textView.getText().toString();
        if (text.length() > 0)
            textView.setText(text.substring(0, text.length() - 1));
    }

    private void setAmounts(String text) {
        Calculator cal = new Calculator(text);
        TextView tvBase = (TextView)findViewById(R.id.amountBase);
        double result = cal.calculate();
        tvBase.setText(String.valueOf(round(result, 2)));

        TextView tvCurrencyTarget = (TextView)findViewById(R.id.currencyTarget);
        CurrencyConverter converter = new CurrencyConverter(result, tvCurrencyTarget.getText().toString());
        TextView tvAmountTarget = (TextView)findViewById(R.id.amountTarget);
        tvAmountTarget.setText(String.valueOf(round(converter.convert(), 2)));
    }

    private double round(double value, int places) {
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
//        if (!checkOperators())
//            return true;
//        if (text.indexOf(".") > new Calculator(text).indexOfOperator())
//            return false;
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

//    private void hideNumpad() {
//        TextView textView = (TextView)findViewById(R.id.expression);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RelativeLayout numpad = (RelativeLayout)findViewById(R.id.numpad);
//                numpad.setVisibility(LinearLayout.VISIBLE);
//            }
//        });
//
//        RelativeLayout relativeLayoutBase = (RelativeLayout)findViewById(R.id.baseCurrency);
//        relativeLayoutBase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RelativeLayout numpad = (RelativeLayout)findViewById(R.id.numpad);
//                numpad.setVisibility(LinearLayout.GONE);
//            }
//        });
//
//        RelativeLayout relativeLayoutTarget = (RelativeLayout)findViewById(R.id.targetCurrency);
//        relativeLayoutTarget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RelativeLayout numpad = (RelativeLayout)findViewById(R.id.numpad);
//                numpad.setVisibility(LinearLayout.GONE);
//            }
//        });
//    }

    private void setListView() {
        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayList<CurrencyInfo> currencyInfoArrayList = getCurrencyInfos();

        setEventListView(listView);

        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, 0, currencyInfoArrayList);
        listView.setAdapter(currencyAdapter);
    }

    private void setEventListView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                RelativeLayout numpad = (RelativeLayout)findViewById(R.id.numpad);
//                numpad.setVisibility(LinearLayout.GONE);

                TextView targetTV = (TextView)findViewById(R.id.currencyTarget);
                TextView textView = (TextView)view.findViewById(R.id.currency);
                String text = textView.getText().toString();
                targetTV.setText(text);

                textView = (TextView)view.findViewById(R.id.fullCurrency);
                text = textView.getText().toString();
                targetTV = (TextView)findViewById(R.id.fullCurrencyTarget);
                targetTV.setText(text);

                ImageView targetIV = (ImageView)findViewById(R.id.flagTarget);
                ImageView imageView = (ImageView)view.findViewById(R.id.flag);
                int resource = (int)imageView.getTag();
                targetIV.setImageResource(resource);

                TextView tvExpression = (TextView)findViewById(R.id.expression);
                setAmounts(tvExpression.getText().toString());
            }
        });
    }

    private ArrayList<CurrencyInfo> getCurrencyInfos() {
        ArrayList<CurrencyInfo> currencyInfoArrayList = new ArrayList<>();
        currencyInfoArrayList.add(new CurrencyInfo("USD", "United States Dollar US$", R.drawable.ic_united_states_of_america_flag));
        currencyInfoArrayList.add(new CurrencyInfo("EUR", "Euro €", R.drawable.ic_europe_flag));
        currencyInfoArrayList.add(new CurrencyInfo("JPY", "Japanese Yen ¥", R.drawable.ic_japan_flag));
        currencyInfoArrayList.add(new CurrencyInfo("GBP", "Pound Sterling £", R.drawable.ic_united_kingdom_flag));
        currencyInfoArrayList.add(new CurrencyInfo("CAD", "Canadian Dollar C$", R.drawable.ic_canada_flag));
        currencyInfoArrayList.add(new CurrencyInfo("AUD", "Australian Dollar A$", R.drawable.ic_australia_flag));
        currencyInfoArrayList.add(new CurrencyInfo("KRW", "South Korean Won ₩", R.drawable.ic_south_korea_flag));
        currencyInfoArrayList.add(new CurrencyInfo("TWD", "New Taiwan Dollar NT$", R.drawable.ic_taiwan_flag));
        currencyInfoArrayList.add(new CurrencyInfo("RUB", "Russian Ruble ₽", R.drawable.ic_russia_flag));
        currencyInfoArrayList.add(new CurrencyInfo("NZD", "New Zealand Dollar NZ$", R.drawable.ic_new_zealand_flag));
        currencyInfoArrayList.add(new CurrencyInfo("THB", "Thai Baht ฿", R.drawable.ic_thailand_flag));
        return currencyInfoArrayList;
    }
}