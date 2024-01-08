module org.codingweek {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires ormlite.core;
    requires ormlite.jdbc;

    opens org.codingweek to javafx.fxml;
    exports org.codingweek;
    exports org.codingweek.controller;
    opens org.codingweek.controller to javafx.fxml;
}