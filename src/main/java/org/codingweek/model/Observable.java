package org.codingweek.model;

import org.codingweek.controller.Observeur;

import java.util.ArrayList;

public abstract class Observable {

    private final ArrayList<Observeur> observeurs;

    public Observable() {
        observeurs = new ArrayList<>();
    }

    public void addObserveur(Observeur observeur) {
        observeurs.add(observeur);
    }

    public void removeObserveur(Observeur observeur) {
        observeurs.remove(observeur);
    }

    public void notifyObserveurs() {
        for (Observeur observeur : observeurs) {
            observeur.update();
        }
    }

}
