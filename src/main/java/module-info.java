module org.codingweek {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    opens org.codingweek to javafx.fxml, org.hibernate.orm.core;
    exports org.codingweek;
    exports org.codingweek.controller;
    opens org.codingweek.controller to javafx.fxml;
}
