package org.codingweek.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type_notification")
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "seen")
    private boolean seen;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "date_notification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Notification(String type, User user, boolean seen, String frequency, Date date) {
        this.type = type;
        this.user = user;
        this.seen = seen;
        this.frequency = frequency;
        this.date = date;
    }

    public Notification() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
