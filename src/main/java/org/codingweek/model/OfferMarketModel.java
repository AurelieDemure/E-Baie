package org.codingweek.model;

import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;

public class OfferMarketModel {

    public static Offer getOffer(int index) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        return db.getEntity(Offer.class, index);
    }

}