package org.codingweek.model;

import org.codingweek.db.entity.Offer;
import org.codingweek.model.filter.Frequency;
import org.codingweek.model.filter.OfferType;
import org.codingweek.model.filter.Price;
import org.codingweek.model.filter.SortOffer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MarketModel {


    private static List<Offer> offersAvailable;

    /** Return all the offer on the database without the one of the user
     * Email is the value of the offer to be excluded */
    public static List<Offer> getOffersAvailable(String email) {
        if (offersAvailable == null)
            offersAvailable = DatabaseHandler.getInstance().getDbManager().getAllEntity(Offer.class);
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
        if (frequency != null) {
            offers = offers.stream()
                    .filter(offer -> offer.getFrequency().equals(frequency))
                    .collect(Collectors.toList());
        }
        if (price != null) {
            offers = offers.stream()
                    .filter(offer -> switch (price) {
                                case ALLPRICE -> true;
                                case LESS100 -> offer.getPrice() < 100;

                                case FROM100TO200 -> offer.getPrice() >= 100 && offer.getPrice() < 200;
                                case FROM200TO300 -> offer.getPrice() >= 200 && offer.getPrice() < 300;
                                case FROM300TO400 -> offer.getPrice() >= 300 && offer.getPrice() < 400;
                                case FROM400TO500 -> offer.getPrice() >= 400 && offer.getPrice() < 500;
                                case MORE500 -> offer.getPrice() >= 500;
                            }
                    ).collect(Collectors.toList());
        }
        if (sortOffer != null) {
            Stream<Offer> offerStream = offers.stream();
            Comparator comparator = null;
            switch (sortOffer) {
                case PRICEASC -> comparator = (o1, o2) ->
                        (((Offer)o1).getPrice() <= ((Offer)o2).getPrice()) ?
                                ( ((Offer)o1).getPrice() == ((Offer)o2).getPrice() ) ? 0: -1
                                : 1;
                case PRICEDESC -> comparator = (o1, o2) ->
                        (((Offer)o1).getPrice() <= ((Offer)o2).getPrice()) ?
                                ( ((Offer)o1).getPrice() == ((Offer)o2).getPrice() ) ? 0: 1
                                : -1;

                case TITLEASC -> comparator = Comparator.comparing(o -> ((Offer) o).getTitle());

                case TITLEDESC -> comparator = Comparator.comparing(o -> ((Offer) o).getTitle()).reversed() ;
            }
            offers = (List<Offer>) offerStream.sorted(comparator)
                    .collect(Collectors.toList());

        }

        return offers;
    }







}