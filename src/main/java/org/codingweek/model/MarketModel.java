package org.codingweek.model;

import org.codingweek.db.entity.Offer;
import org.codingweek.model.filter.Frequency;
import org.codingweek.model.filter.OfferType;
import org.codingweek.model.filter.Price;
import org.codingweek.model.filter.SortOffer;

import java.util.List;
import java.util.stream.Collectors;

public class MarketModel {


    /** Return all the offer on the database without the one of the user
     * Email is the value of the offer to be excluded */
    public static List<Offer> getOffersAvailable(String email) {
        List<Offer> offersAvailable = DatabaseHandler.getInstance().getDbManager().getAllEntity(Offer.class);
        System.out.println(offersAvailable);
        return offersAvailable.stream()
                .filter(offer -> !offer.getOwner().getEmail().equals(email))
                .collect(Collectors.toList());
    }

    /** Return all the offer on the database with filtering
     * Email is the value of the offer to be excluded */
    public static List<Offer> getOffersAvailableFiltered(String email, OfferType type, Frequency frequency, Price price, SortOffer sortOffer) {
        List<Offer> offers = getOffersAvailable(email);
        if (type != null)
            offers = offers.stream()
                    .filter(offer -> offer.getTypeOffer().equals(type))
                    .collect(Collectors.toList());

        return offers;
    }







}
