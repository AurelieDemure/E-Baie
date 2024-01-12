package org.codingweek.db.entity;

import org.codingweek.model.DatabaseHandler;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.LocalDate;

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

    @Column(name = "date_begin")
    private Date dateBegin;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "date_repeated")
    private Date dateRepeated;

    @Column(name = "accepted")
    private boolean accepted;

    @Column(name = "notation")
    private Integer notation;

    public Query(Offer offer, User user, boolean accepted, Integer notation, Date dateBegin, Date dateEnd) {
        this.date = LocalDateTime.now();
        this.offer = offer;
        this.user = user;
        this.date = date;
        this.dateRepeated = null;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
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

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateBegin() {
        return this.dateBegin;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateEnd() {
        return this.dateEnd;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Integer getNotation() {
        return notation;
    }

    public void setNotation(Integer notation) {
        this.notation = notation;
    }

    /** Refuse the query and send a notification to the user */
    public void refuseQuery() {
        DatabaseHandler.getInstance().getDbManager().deleteEntity(this);
        DatabaseHandler.getInstance().getDbManager().saveEntity(new Notification("Votre offre pour " + offer.getTitle() + " a été refusée", offer.getOwner(), false, "once", new Date()));
    }

    /** Accept the query and send a notification to the user */
    public void acceptQuery() {
        accepted = true;
        DatabaseHandler.getInstance().getDbManager().updateEntity(this);
        DatabaseHandler.getInstance().getDbManager().saveEntity(new Notification("Votre offre pour " + offer.getTitle() + " a été acceptée", offer.getOwner(), false, "once", new Date()));
    }
}