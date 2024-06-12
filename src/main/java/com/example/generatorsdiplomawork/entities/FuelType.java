package com.example.generatorsdiplomawork.entities;

public enum FuelType {
    ДП("ДП"), Бензин("А-80");
    private String name;
    private FuelType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
