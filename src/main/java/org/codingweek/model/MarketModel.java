package org.codingweek.model;

import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;

import java.util.List;
import java.util.stream.Collectors;

public class MarketModel {

    private List<Offer> offersAvailable;

    public MarketModel() {
        DatabaseManager dbHandler = DatabaseHandler.getInstance().getDbManager();
        offersAvailable = dbHandler.getAllEntity(Offer.class);
    }


    /** Return all the offer on the database without the one of the user*/
    public List<Offer> getOffersAvailable(String email) {
        if (offersAvailable != null) {
            return offersAvailable.stream()
                    .filter(offer -> !offer.getOwner().getEmail().equals(email))
                    .collect(Collectors.toList());
        }
        DatabaseManager dbHandler = DatabaseHandler.getInstance().getDbManager();
        List<Offer> offers = dbHandler.getAllEntity(Offer.class);
        offersAvailable = offers;
        return offers.stream()
                .filter(offer -> !offer.getOwner().getEmail().equals(email))
                .collect(Collectors.toList());
    }

    /** Return all the offer on the database with filtering*/
    public List<Offer> getOffersAvailableFiltered(String email, OfferType type) {
        List<Offer> offers = getOffersAvailable(email);
        if (type != null)
            offers.removeIf(offer -> !offer.getType().equals(type));

        return offers;
    }







}
