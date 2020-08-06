package com.example.currencyconverter;

public class HistoryInfo {
    public CurrencyInfo[] targetCurrencies = null;
    public String dateAndTime = "";
    public String amount = "";

    public HistoryInfo(String date, String amt) {
        dateAndTime = date;
        amount = amt;
    }
    public HistoryInfo(CurrencyInfo[] targets) {
        targetCurrencies = targets;
    }
}
