package com.example.currencyconverter;

public class CurrencyConverter {
    private double amount = 0;
    private String country = "";
    private double rate = 0.0;

    public CurrencyConverter(double amt, String ct) {
        amount = amt;
        country = ct;
    }

    public CurrencyConverter(double amt, String ct, double r) {
        amount = amt;
        country = ct;
        rate = r;
    }

    public double convert() {
        if (rate != 0.0)
            return amount * rate;
        else
            return defaultConvert();
    }

    private double defaultConvert() {
        if (country.equals("USD"))
            return amount * 1.1854;
        else if (country.equals("JPY"))
            return amount * 125.37;
        else if (country.equals("GBP"))
            return amount * 0.90265;
        else if (country.equals("CAD"))
            return amount * 1.5703;
        else if (country.equals("AUD"))
            return amount * 1.6415;
        else if (country.equals("KRW"))
            return amount * 1405.74;
        else if (country.equals("RUB"))
            return amount * 86.3692;
        else if (country.equals("NZD"))
            return amount * 1.7809;
        else if (country.equals("THB"))
            return amount * 36.759;
        else
            return 0;
    }
}
