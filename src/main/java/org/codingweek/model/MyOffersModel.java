package org.codingweek.model;

import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.Query;
import org.codingweek.db.entity.User;
import org.codingweek.model.filter.Frequency;
import org.codingweek.model.filter.OfferType;

import java.util.Date;
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

    public static List<Query> getQueriesByOffer(Offer offer) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        List<Query> queries = db.getAllEntity(Query.class);
        return queries.stream()
                .filter(query -> query.getOffer().getId() == offer.getId())
                .collect(Collectors.toList());
    }
}
