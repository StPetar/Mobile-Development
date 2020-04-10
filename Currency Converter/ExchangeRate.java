package com.unitest.moad.thecurrencyconverter;

public class ExchangeRate {
    private String currencyName;
    private double ratePerEuro;
    private String capital;

    public ExchangeRate(String currencyName, String capital, double ratePerEuro) {
        this.currencyName = currencyName;
        this.ratePerEuro = ratePerEuro;
        this.capital = capital;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCapital() {
        return capital;
    }

    public double getRatePerEuro() {
        return ratePerEuro;
    }

    public void setRatePerEuro(double rate){
        this.ratePerEuro = rate;
    }
}
