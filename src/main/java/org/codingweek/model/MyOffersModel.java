package org.codingweek.model;

import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;

import java.util.List;
import java.util.stream.Collectors;

public class MyOffersModel {
    private static List<Offer> myOffers;

    public static List<Offer> getMyOffers(String email) {
        if (myOffers == null) {
            DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
            myOffers = db.getAllEntity(Offer.class);
        }
        return myOffers.stream()
                .filter(offer -> offer.getOwner().getEmail().equals(email))
                .collect(Collectors.toList());
    }
}
