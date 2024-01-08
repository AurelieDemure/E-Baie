package org.codingweek.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Observable;

@DatabaseTable(tableName = "users")
public class User extends Observable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String firstName;

    @DatabaseField
    private String lastName;

    @DatabaseField
    private String password;

    @DatabaseField
    private String email;

    @DatabaseField
    private int balance;
}