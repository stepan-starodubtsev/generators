package com.example.generatorsdiplomawork.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatUtils {
    public String formatToTwoDecimals(double value) {
        return String.format("%.2f", value);
    }

    public String formatDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
