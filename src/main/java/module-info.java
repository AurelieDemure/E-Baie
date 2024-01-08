module org.codingweek.codingweek {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.codingweek.codingweek to javafx.fxml;
    exports org.codingweek.codingweek;
}