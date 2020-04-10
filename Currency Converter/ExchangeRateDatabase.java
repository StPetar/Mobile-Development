package com.unitest.moad.thecurrencyconverter;

import com.unitest.moad.thecurrencyconverter.ExchangeRate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateDatabase {
    // Exchange rates to EURO - price for 1 Euro
    private final static ExchangeRate[] RATES = {
            new ExchangeRate("EUR", "Bruxelles", 1.0),
            new ExchangeRate("USD", "Washington", 1.1035),
            new ExchangeRate("JPY", "Tokyo", 120.96),
            new ExchangeRate("BGN", "Sofia", 1.9558),
            new ExchangeRate("CZK", "Prague", 25.160),
            new ExchangeRate("DKK", "Copenhagen", 7.4730),
            new ExchangeRate("GBP", "London", 0.84313),
            new ExchangeRate("HUF", "Budapest", 336.01),
            new ExchangeRate("PLN", "Warsaw", 4.2565),
            new ExchangeRate("RON", "Bucharest", 4.7799),
            new ExchangeRate("SEK", "Stockholm", 10.5363),
            new ExchangeRate("CHF", "Bern", 1.0712),
            new ExchangeRate("NOK", "Oslo", 9.9375),
            new ExchangeRate("HRK", "Zagreb", 7.4415),
            new ExchangeRate("RUB", "Moscow", 68.1692),
            new ExchangeRate("TRY", "Ankara", 6.5539),
            new ExchangeRate("AUD", "Canberra", 1.6127),
            new ExchangeRate("BRL", "Brasilia", 4.6084),
            new ExchangeRate("CAD", "Ottawa", 1.4491),
            new ExchangeRate("CNY", "Beijing", 7.6509),
            new ExchangeRate("HKD", "Hong Kong", 8.5770),
            new ExchangeRate("IDR", "Jakarta", 15002.91),
            new ExchangeRate("ILS", "Jerusalem", 3.8100),
            new ExchangeRate("INR", "New Delhi", 78.6700),
            new ExchangeRate("KRW", "Seoul", 1289.12),
            new ExchangeRate("MXN", "Mexico City", 20.7170),
            new ExchangeRate("MYR", "Kuala Lumpur", 4.4857),
            new ExchangeRate("NZD", "Wellington", 1.6684),
            new ExchangeRate("PHP", "Manila", 56.092),
            new ExchangeRate("SGD", "Singapore", 1.4910),
            new ExchangeRate("THB", "Bangkok", 33.723),
            new ExchangeRate("ZAR", "Cape Town", 15.8585)
    };

    private final static Map<String, ExchangeRate> CURRENCIES_MAP = new HashMap<>();

    private final static String[] CURRENCIES_LIST;

    static {
        for (ExchangeRate r : RATES) {
            CURRENCIES_MAP.put(r.getCurrencyName(), r);
        }
        CURRENCIES_LIST = new String[CURRENCIES_MAP.size()];

        CURRENCIES_MAP.keySet().toArray(CURRENCIES_LIST);
        Arrays.sort(CURRENCIES_LIST);

    }

    /**
     * Returns list of currency names
     */

    public String[] getCurrencies() {
        return CURRENCIES_LIST;
    }

    /**
     * Gets exchange rate for currency (equivalent for one Euro)
     */

    public double getExchangeRate(String currency) {
        return CURRENCIES_MAP.get(currency).getRatePerEuro();
    }

    public void setExchangeRate(String currency, double rate){

        CURRENCIES_MAP.get(currency).setRatePerEuro(rate);

    }

    public String getCapital(String currency) {
        return CURRENCIES_MAP.get(currency).getCapital();
    }


    /**
     * Converts a value from a currency to another one
     * @return converted value
     */
    public double convert(double value, String currencyFrom, String currencyTo) {
        return value / getExchangeRate(currencyFrom) * getExchangeRate(currencyTo);
    }
}
