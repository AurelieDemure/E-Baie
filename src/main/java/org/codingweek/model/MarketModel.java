package org.codingweek.model;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.codingweek.ApplicationContext;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.User;
import org.codingweek.model.filter.*;

public class MarketModel {


    private static List<Offer> offersAvailable;

    /** Return all the offer on the database without the one of the user
     * Email is the value of the offer to be excluded */
    public static List<Offer> getOffersAvailable(String email) {
        if (offersAvailable == null)
            offersAvailable = DatabaseHandler.getInstance().getDbManager().getAllEntity(Offer.class);
        if (offersAvailable == null) return null;
        return offersAvailable.stream()
                .filter(offer -> !offer.getOwner().getEmail().equals(email))
                .collect(Collectors.toList());
    }

    /** Return all the offer on the database with filtering except the own owned by sleeping people
     * Email is the value of the offer to be excluded */
    public static List<Offer> getOffersAvailableFiltered(String email, String research, OfferType type, Frequency frequency, Price price, SortOffer sortOffer, Distance distance) {
        List<Offer> offers = getOffersAvailable(email);
        if (offers == null) return null;
        offers = offers.stream().filter( offer ->  !offer.getOwner().isSleeping()).collect(Collectors.toList());
        if (research != null)
            offers = offers.stream()
                    .filter(offer -> compareTitle2(offer.getTitle(), research))
                    .collect(Collectors.toList());   
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
       if (distance != null && ApplicationContext.getInstance().getUser_authentified() != null) {
           User user = ApplicationContext.getInstance().getUser_authentified();
           offers = offers.stream()
                    .filter(offer -> switch (distance) {
                                case ALLDISTANCE -> true;
                                case LESS5KM -> GeoLocalisation.distance(offer.getLat(), offer.getLon(), user.getLat(), user.getLon()) < 5;
                                case LESS20KM -> GeoLocalisation.distance(offer.getLat(), offer.getLon(), user.getLat(), user.getLon()) < 20;
                                case LESS30KM -> GeoLocalisation.distance(offer.getLat(), offer.getLon(), user.getLat(), user.getLon()) < 30;
                                case LESS60KM -> GeoLocalisation.distance(offer.getLat(), offer.getLon(), user.getLat(), user.getLon()) < 60;
                                case LESS100KM -> GeoLocalisation.distance(offer.getLat(), offer.getLon(), user.getLat(), user.getLon()) < 100;
                                case LESS200KM -> GeoLocalisation.distance(offer.getLat(), offer.getLon(), user.getLat(), user.getLon()) < 200;
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

    public static boolean compareTitle2(String title, String research) {
        Pattern pattern = Pattern.compile(research, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(title).find();
    }

    public static Boolean compareTitle(String title, String research){
        Boolean bool = true;
        for(int i=0; i < research.length(); i++){
            if (title.charAt(i) != research.charAt(i)){
                bool = false;
            }
        }
        return bool;
    }





}