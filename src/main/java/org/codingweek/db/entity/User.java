package org.codingweek.db.entity;

import com.opencagedata.jopencage.model.JOpenCageLatLng;
import org.codingweek.model.GeoLocalisation;
import org.codingweek.model.PasswordUtility;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "balance")
    private int balance;

    @Column(name = "date_birth")
    private Date date_birth;


    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "sleeping")
    private Boolean sleeping;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Offer> offers;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Notification> notifications;


    public User(String email,String firstName, String lastName, String password, String phone, String address, String description, int balance, Date date_birth) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = PasswordUtility.hashPassword(password);
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.balance = balance;
        this.sleeping = false;
        JOpenCageLatLng res = GeoLocalisation.getLatLng(address);
        if (res != null) {
            lat = res.getLat();
            lon = res.getLng();
        }
        this.date_birth = date_birth;

    }

    /** Usage on test create a User without calling API for lat lon */
    public static User getUserTest(String email,String firstName, String lastName, String password, String phone, String address, String description, int balance, Date date_birth) {
        User user = new User();
        user.email = email;
        user.firstName = firstName;
        user.lastName = lastName;
        user.password = PasswordUtility.hashPassword(password);
        user.phone = phone;
        user.address = address;
        user.description = description;
        user.balance = balance;
        user.date_birth = date_birth;
        return user;
    }

    public User() {}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    /** Get the latitude of the user
     * @return the latitude of the user
     * NULL if the user has not a valid address
     */
    public Double getLat() {
        return lat;
    }

    /** Get the longitude of the user
     * @return the latitude of the user
     * NULL if the user has not a valid address
     */
    public Double getLon() {
        return lon;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordUtility.hashPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getBalance() {
        return balance;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean isSleeping() {
        return sleeping;
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        JOpenCageLatLng res = GeoLocalisation.getLatLng(address);
        if (res != null) {
            lat = res.getLat();
            lon = res.getLng();
        }

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public Date getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(Date date_birth) {
        this.date_birth = date_birth;
    }

    public List<Notification> getNotifications() {
        if (this.notifications == null) {
            return null;
        }
        return this.notifications.stream().distinct().collect(Collectors.toList());
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}