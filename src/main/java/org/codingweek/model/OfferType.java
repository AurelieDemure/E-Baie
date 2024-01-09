package org.codingweek.model;

public enum OfferType {
    LOAN("Prêt"),
    SERVICE("Service");

    private final String value;

    OfferType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
