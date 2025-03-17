package com.globalexchange.model;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CurrencyConverter {
    private static final Logger LOGGER = Logger.getLogger(CurrencyConverter.class.getName());
    private static final Map<String, Map<String, Double>> conversionRates = new HashMap<>();

    static {
        // Initialize conversion rates
        Map<String, Double> inrRates = new HashMap<>();
        inrRates.put("USD", 0.012);
        inrRates.put("EUR", 0.011);
        inrRates.put("GBP", 0.0095);
        inrRates.put("INR", 1.0);
        conversionRates.put("INR", inrRates);

        Map<String, Double> usdRates = new HashMap<>();
        usdRates.put("INR", 83.33);
        usdRates.put("EUR", 0.92);
        usdRates.put("GBP", 0.79);
        usdRates.put("USD", 1.0);
        conversionRates.put("USD", usdRates);

        Map<String, Double> eurRates = new HashMap<>();
        eurRates.put("INR", 90.91);
        eurRates.put("USD", 1.09);
        eurRates.put("GBP", 0.86);
        eurRates.put("EUR", 1.0);
        conversionRates.put("EUR", eurRates);

        Map<String, Double> gbpRates = new HashMap<>();
        gbpRates.put("INR", 105.26);
        gbpRates.put("USD", 1.27);
        gbpRates.put("EUR", 1.16);
        gbpRates.put("GBP", 1.0);
        conversionRates.put("GBP", gbpRates);
    }

    public static double convert(String fromCurrency, String toCurrency, double amount) {
        LOGGER.info("Converting " + amount + " from " + fromCurrency + " to " + toCurrency);

        if (!conversionRates.containsKey(fromCurrency)) {
            throw new IllegalArgumentException("Unsupported source currency: " + fromCurrency);
        }

        Map<String, Double> rates = conversionRates.get(fromCurrency);
        if (!rates.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Unsupported target currency: " + toCurrency);
        }

        double rate = rates.get(toCurrency);
        double result = amount * rate;

        LOGGER.info("Conversion result: " + result);

        return Math.round(result * 100.0) / 100.0; // Round to 2 decimal places
    }
}