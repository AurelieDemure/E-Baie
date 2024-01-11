package org.codingweek.model.filter;

public enum Distance {

    LESS5KM("Moins de 5km"),

    LESS20KM("Moins de 20km"),

    LESS30KM("Moins de 30km"),

    LESS60KM("Moins de 60km"),

    LESS100KM("Moins de 100km"),

    LESS200KM("Moins de 200km"),

    ALLDISTANCE("Toutes distances");

    private final String value;

    Distance(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static Distance fromString(String value) {
        for (Distance enumVal: Distance.values())
            if (enumVal.value.equals(value))
                return enumVal;

        throw new IllegalArgumentException("No constant with the value: " + value);
    }

}
