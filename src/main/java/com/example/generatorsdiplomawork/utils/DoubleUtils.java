package com.example.generatorsdiplomawork.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleUtils {
    public static double roundToDecimals(double value) {
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
