package com.example.currencyconverter;

public class CurrencyInfo {
    public String currency;
    public String fullCurrency;
    public int flagPath;

    public CurrencyInfo(String curr, String fCurr, int path) {
        currency = curr;
        fullCurrency = fCurr;
        flagPath = path;
    }

}
