package org.codingweek.model;

import org.codingweek.ApplicationContext;
import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;

public class OfferModalModel {
    public static Offer getMyOfferToModify(int index) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        return db.getEntity(Offer.class, index);
    }
}
