package org.codingweek.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.codingweek.ApplicationContext;
import org.codingweek.model.Page;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class AccountController extends Controller implements Observeur{
    public TextField firstnameField;
    public TextField lastnameField;
    public TextField emailField;
    public PasswordField passwordField;
    public DatePicker birthDateField;
    public TextField phoneNumberField;
    public TextField addressField;
    public TextField descriptionField;
    @FXML
    private Label welcomeText;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
    private LocalDate date;

    public void DateConvert(LocalDate date) {

    }

    @FXML
    private void showConfirmationAddDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Etes vous sur ?");
        alert.setContentText("Voulez vous vraiment supprimer votre compte ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                // CODE WHEN OK
            }
        });
    }

    @Override
    public void refresh() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        firstnameField.setText("a");
        lastnameField.setText("d");
        emailField.setText("ad@tn.net");
        passwordField.setText("");
        phoneNumberField.setText("");
        addressField.setText("");
        descriptionField.setText("");
    }
}

