package org.codingweek.model.filter;

public enum SortOffer {
    PRICEASC("Prix croissant"),
    PRICEDESC("Prix décroissant"),
    TITLEASC("Titre A-Z"),
    TITLEDESC("Titre Z-A");

    private final String value;

    SortOffer(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static SortOffer fromString(String value) {
        for (SortOffer enumVal: SortOffer.values())
            if (enumVal.value.equals(value))
                return enumVal;

        throw new IllegalArgumentException("No constant with the value: " + value);
    }

}
