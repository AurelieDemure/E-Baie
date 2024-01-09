package org.codingweek.model;

import javax.persistence.*;
import java.util.Set;

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
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Column(name = "price")
    private double price;

    @Column(name = "type_offer")
    private String type;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "localization")
    private String localization;

    @Column(name = "path_offer")
    private String path;

    public Offer(String title, String description, User owner, double price, String type, String frequency, String localization, String path) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.price = price;
        this.type = type;
        this.frequency = frequency;
        this.localization = localization;
        this.path = path;
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
        if (type.equals("LOAN")) {
            return OfferType.LOAN;
        } else {
            return OfferType.SERVICE;
        }
    }

    public void setType(OfferType type) {
        if (type.equals(OfferType.LOAN)) {
            this.type = "LOAN";
        } else {
            this.type = "SERVICE";
        }
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}