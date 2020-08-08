package com.example.currencyconverter;

import java.util.ArrayList;

public class HistoryInfo {
    public ArrayList<CurrencyInfo> targetCurrencies = new ArrayList<>();
    public String dateAndTime = "";
    public String amount = "";

    public HistoryInfo(String date, String amt) {
        dateAndTime = date;
        amount = amt;
    }

    public HistoryInfo(ArrayList<CurrencyInfo> targets, String date, String amt) {
        dateAndTime = date;
        amount = amt;
        targetCurrencies = targets;
    }

}
