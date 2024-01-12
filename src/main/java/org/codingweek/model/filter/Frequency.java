package org.codingweek.model.filter;

public enum Frequency {
    ALLFREQUENCY("Tout type de frequence"),
    DAYLY("Journalier"),
    WEEKLY("Hebdomadaire"),
    MONTHLY("Mensuelle");

    private final String value;

    Frequency(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static Frequency fromString(String value) {
        for (Frequency enumVal: Frequency.values())
            if (enumVal.value.equals(value))
                return enumVal;

        throw new IllegalArgumentException("No constant with the value: " + value);
    }

}
