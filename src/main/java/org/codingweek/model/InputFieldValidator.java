package org.codingweek.model;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static Callback<DatePicker, DateCell> getPastDayCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                // Disable future dates
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Optional: Highlight disabled dates
                }
            }
        };
    }

}
