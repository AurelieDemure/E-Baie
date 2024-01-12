package org.codingweek.db.entity;

import com.opencagedata.jopencage.model.JOpenCageLatLng;
import org.codingweek.model.GeoLocalisation;
import org.codingweek.model.filter.Frequency;
import org.codingweek.model.filter.OfferType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 1024)
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "email")
    private User owner;

    @Column(name = "price")
    private double price;

    @Column(name = "type_offer")
    private String type;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "localization")
    private String localization;


    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "path_offer")
    private String path;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Query> queries;


    public Offer(String title, String description, User owner, double price, OfferType type, Frequency frequency, String localization, String path) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.price = price;
        this.type = type.getValue();
        this.frequency = frequency.getValue();
        this.localization = localization;
        JOpenCageLatLng res = GeoLocalisation.getLatLng(localization);
        if (res != null) {
            lat = res.getLat();
            lon = res.getLng();
        }

        this.path = path;
    }

    /** Create a offer for test purpose without calling API for lat lon */
    public static Offer getOfferTest(String title, String description, User owner, double price, OfferType type, Frequency frequency, String localization, String path) {
        Offer offer = new Offer();
        offer.title = title;
        offer.description = description;
        offer.owner = owner;
        offer.price = price;
        offer.type = type.getValue();
        offer.frequency = frequency.getValue();
        offer.localization = localization;
        offer.path = path;
        return offer;
    }

    public Offer() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OfferType getTypeOffer() {
        return OfferType.fromString(type);
    }

    public void setType(OfferType type) {
        this.type = type.getValue();
    }

    public Frequency getFrequency() {
        return Frequency.fromString(frequency);
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency.getValue();
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
        JOpenCageLatLng res = GeoLocalisation.getLatLng(localization);
        if (res != null) {
            lat = res.getLat();
            lon = res.getLng();
        }

    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }



    public void setType(String type) {
        this.type = type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public List<Query> getAllAcceptedQueries() {
        return queries.stream()
                .filter(Query::isAccepted)
                .toList();
    }

    public List<Query> getAllPendingQueries() {
            return queries.stream()
                .filter(query -> !query.isAccepted())
                .toList();
    }

    public Integer getNotation() {
        int c = 0;
        int moyenne = 0;
        boolean getNotation = false ;
        for (Query query : queries) {
            Integer note = query.getNotation();
            if (note != null) {
                getNotation = true;
                c++;
                moyenne += note;
            }
        }
        if (getNotation) {
            return moyenne / c ;
        } else {
            return null ; 
        }
        
    }
}