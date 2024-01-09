package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.model.Page;
import org.codingweek.db.entity.User;
import org.codingweek.view.ConnexionView;

import java.io.IOException;
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
    public Label credit;

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
                ApplicationContext.getInstance().setPageType(Page.NONE);
                try {
                    ApplicationSettings.getInstance().setCurrentScene(new ConnexionView().loadScene());
                    ApplicationContext.getInstance().setUser_authentified(null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void refresh() {

    }

    @Override
    public void update() {
        User user = ApplicationContext.getInstance().getUser_authentified();
        firstnameField.setText(user.getFirstName());
        lastnameField.setText(user.getLastName());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        if (user.getDate_birth() != null) birthDateField.setValue(LocalDate.parse(user.getDate_birth().toString(), formatter));
        phoneNumberField.setText(user.getPhone());
        addressField.setText(user.getAddress());
        descriptionField.setText(user.getDescription());
        credit.setText(String.valueOf(user.getBalance()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        update();
    }

    public void disconnect(ActionEvent actionEvent) {
        ApplicationContext.getInstance().setPageType(Page.NONE);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new ConnexionView().loadScene());
            ApplicationContext.getInstance().setUser_authentified(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void saveModifiedAccount(ActionEvent actionEvent) {
        User user = ApplicationContext.getInstance().getUser_authentified();
        user.setFirstName(firstnameField.getText());
        user.setLastName(lastnameField.getText());
        user.setEmail(emailField.getText());
        user.setPassword(passwordField.getText());
        user.setPhone(phoneNumberField.getText());
        user.setAddress(addressField.getText());
        user.setDescription(descriptionField.getText());
        update();
    }
}

