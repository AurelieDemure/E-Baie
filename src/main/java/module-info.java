module org.codingweek {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.codingweek to javafx.fxml;
    exports org.codingweek;
}