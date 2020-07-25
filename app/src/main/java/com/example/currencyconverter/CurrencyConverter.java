package com.example.currencyconverter;

public class CurrencyConverter {
    double amount = 0;
    String country = "";

    public CurrencyConverter(double amt, String ct) {
        amount = amt;
        country = ct;
    }

    public double convert() {
        if (country.equals("USD"))
            return amount / 23175;
        else if (country.equals("EUR"))
            return amount / 25500;
        else if (country.equals("JPY"))
            return amount / 211;
        else if (country.equals("GBP"))
                return amount / 28493;
        else if (country.equals("CAD"))
            return amount / 16561;
        else if (country.equals("AUD"))
            return amount / 15748;
        else if (country.equals("KRW"))
            return amount / 19.42;
        else if (country.equals("TWD"))
            return amount / 788.57;
        else if (country.equals("RUB"))
            return amount / 326.39;
        else if (country.equals("NZD"))
            return amount / 15203.15;
        else if (country.equals("THB"))
            return amount / 743.21;
        else
            return 0;
    }
}
