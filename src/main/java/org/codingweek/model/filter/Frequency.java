package org.codingweek.model.filter;

public enum Frequency {
    ALLFREQUENCY("Tout type de frequence"),
    UNIQUE("Unique"),
    DAYLY("Journalier"),
    WEEKLY("Hebdomadaire"),
    MONTHLY("Mensuelle"),
    YEARLY("Annuelle");

    private final String value;

    Frequency(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
