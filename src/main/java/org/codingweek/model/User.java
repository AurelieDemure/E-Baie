package org.codingweek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="User")
public class User extends Observable {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="firstName")
    private String firstName;

}