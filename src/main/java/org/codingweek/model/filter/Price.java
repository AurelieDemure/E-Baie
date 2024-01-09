package org.codingweek.model.filter;

public enum Price {
    ALLPRICE("Tout type de prix"),
    LESS100("Moins de 100 florains"),
    FROM100TO200("Entre 100 et 200 florains"),
    FROM200TO300("Entre 200 et 300 florains"),
    FROM300TO400("Entre 300 et 400 florains"),
    FROM400TO500("Entre 400 et 500 florains"),
    MORE500("Plus de 500 florains");

    private final String value;

    Price(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static Price fromString(String value) {
        for (Price enumVal: Price.values())
            if (enumVal.value.equals(value))
                return enumVal;

        throw new IllegalArgumentException("No constant with the value: " + value);
    }

}
