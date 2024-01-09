package org.codingweek.db.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Query")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "user_mail", referencedColumnName = "email")
    private User user;

    @Column(name = "date_query")
    private LocalDateTime date;

    @Column(name = "accepted")
    private boolean accepted;

    @Column(name = "notation")
    private int notation;

    public Query(Offer offer, User user, boolean accepted, int notation) {
        this.date = LocalDateTime.now();
        this.offer = offer;
        this.user = user;
        this.date = date;
        this.accepted = accepted;
        this.notation = notation;
    }

    public Query() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public int getNotation() {
        return notation;
    }

    public void setNotation(int notation) {
        this.notation = notation;
    }
}