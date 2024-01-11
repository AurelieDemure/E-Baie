package org.codingweek.model;

import javafx.scene.control.*;
import javafx.util.*;
import java.util.*;
import java.time.*;
import java.util.regex.*;
import java.text.*;
import org.codingweek.db.entity.*;

public class InputFieldValidator {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);


    /** Validate email with regular expression
     * @param email email for validation
     * @return true valid email, otherwise false
     */
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /** Validate password
     * @param password password for validation
     * @return true valid password, otherwise false
     */
    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        return password.length() >= 8;
    }

    /** Validate phone
     * @param phone phone for validation
     * @return true valid phone, otherwise false
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        return phone.length() == 10 && phone.matches("\\d+");
    }

    public static boolean validAdress(String adress) {
        return GeoLocalisation.getLatLng(adress) != null;
    }

    public static Callback<DatePicker, DateCell> getPastDayCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                // Disable past dates
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Optional: Highlight disabled dates
                }
            }
        };
    }

    public static Callback<DatePicker, DateCell> getDateBeginCellFactory(LocalDate dateEnd, List<Query> queries) {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }

                if (dateEnd != null && item.isAfter(dateEnd)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }

                for (Query query : queries) {
                    LocalDate localDateBegin = query.getDateBegin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate localDateEnd = query.getDateEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if ((item.isEqual(localDateBegin) || item.isEqual(localDateEnd) ||
                            (item.isAfter(localDateBegin) && item.isBefore(localDateEnd)))) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;");
                    }
                }
            }
        };
    }


    public static Callback<DatePicker, DateCell> getDateEndCellFactory(LocalDate dateBegin, List<Query> queries){
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                // Disable future dates
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Optional: Highlight disabled dates
                }
                //Disable dates before the beginning of the offer if selected
                if (dateBegin != null && item.isBefore(dateBegin)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Optional: Highlight disabled dates
                }
                //Disable dates that are already reserved
                for(Query query : queries){
                    LocalDate localDateBegin = query.getDateBegin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate localDateEnd = query.getDateEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if(item.isBefore(localDateEnd) && item.isAfter(localDateBegin)){
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;"); // Optional: Highlight disabled dates
                    }
                }
            }
        };
    }
}
