package com.example.generatorsdiplomawork.utils;

public class DoubleUtils {
    public String formatToTwoDecimals(double value) {
        int decimalPlaces = 2;
        return String.format("%." + decimalPlaces + "f", value);
    }
}
