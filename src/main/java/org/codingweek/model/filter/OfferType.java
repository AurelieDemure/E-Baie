package org.codingweek.model.filter;

public enum OfferType {
    LOAN("Pret"),
    SERVICE("Service");

    private final String value;

    OfferType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OfferType fromString(String value) {
        for (OfferType offerType: OfferType.values())
            if (offerType.value.equals(value))
                return offerType;

        throw new IllegalArgumentException("No constant with the value: " + value);
    }
}
