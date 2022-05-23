package com.jpmc.salesreport.util;

public enum ProductNames {
    apple("apple"),
    apples("apple"),
    orange("orange"),
    oranges("orange"),
    banana("banana"),
    bananas("banana");
    public final String label;

    private ProductNames(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return this.label;
    }
}
