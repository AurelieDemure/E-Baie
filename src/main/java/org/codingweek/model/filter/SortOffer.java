package org.codingweek.model.filter;

public enum SortOffer {
    PRICEASC("Prix croissant"),
    PRICEDESC("Prix décroissant"),
    TITLEASC("Titre A-Z"),
    TITLEDESC("Titre Z-A"),
    AUTORASC("Auteur A-Z"),
    AUTORDESC("Auteur Z-A");

    private final String value;

    SortOffer(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
