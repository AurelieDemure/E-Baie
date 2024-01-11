package org.codingweek.model;

import org.codingweek.controller.Observeur;
import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.Query;
import org.codingweek.db.entity.User;
import org.codingweek.model.filter.Frequency;
import org.codingweek.model.filter.OfferType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MyOffersModel  {

    public static List<Offer> getMyOffers(String email) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        return db.getAllEntity(Offer.class).stream()
                .filter(offer -> offer.getOwner().getEmail().equals(email))
                .collect(Collectors.toList());
    }

    public static List<Query> getNotAcceptedQueriesByOffer(Offer offer) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        List<Query> queries = db.getAllEntity(Query.class);
        return queries.stream()
                .filter(query -> query.getOffer().getId() == offer.getId())
                .filter(query -> !query.isAccepted())
                .collect(Collectors.toList());
    }
}
