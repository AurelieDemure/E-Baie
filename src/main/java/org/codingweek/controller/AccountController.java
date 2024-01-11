package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.entity.User;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.InputFieldValidator;
import org.codingweek.model.ModalHelper;
import org.codingweek.model.Page;
import org.codingweek.view.ConnexionView;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class AccountController extends Controller implements Observeur{
    public TextField firstnameField;
    public TextField lastnameField;
    public TextField emailField;
    public PasswordField passwordField;
    public DatePicker birthDateField;
    public TextField phoneNumberField;
    public TextField addressField;
    public TextArea descriptionField;
    public Label credit;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
    public Label errordisplay;

    private void toggleErro(boolean visible) {
        errordisplay.setVisible(visible);
        errordisplay.setManaged(visible);
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
                    User user = ApplicationContext.getInstance().getUser_authentified();
                    DatabaseHandler.getInstance().getDbManager().deleteEntity(user);
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
        passwordField.setText("");
        if (birthDateField.getValue() != null) {
            try {
                user.setDate_birth(new SimpleDateFormat("yyyy-MM-dd").parse(birthDateField.getValue().toString()));

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
        if (user.getDate_birth() != null) {
            Instant instant = user.getDate_birth().toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();

            birthDateField.setValue(localDate);
        }

        phoneNumberField.setText(user.getPhone());
        addressField.setText(user.getAddress());
        descriptionField.setText(user.getDescription());
        credit.setText(String.valueOf(user.getBalance()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        StringConverter<LocalDate> converter = new LocalDateStringConverter(formatter, formatter);
        birthDateField.setEditable(false);
        birthDateField.setConverter(converter);
        emailField.setEditable(false);
        toggleErro(false);
        birthDateField.setDayCellFactory(InputFieldValidator.getPastDayCellFactory());
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

        if (!passwordField.getText().isEmpty() && passwordField != null) {
            if (!InputFieldValidator.isValidPassword(passwordField.getText())) {
                errordisplay.setText("Mot de passe invalide");
                toggleErro(true);
                return;
            }
        }
        if (!InputFieldValidator.isValidPhone(phoneNumberField.getText()) && phoneNumberField.getText() != null) {
            errordisplay.setText("Numéro de téléphone invalide");
            toggleErro(true);
            return;
        }
        if (!InputFieldValidator.validAdress(addressField.getText()) && addressField.getText() != null) {
            if (!addressField.getText().isEmpty()) {
                errordisplay.setText("Adresse non reconnue");
                toggleErro(true);
                return;
            }
        }
        toggleErro(false);

        User user = ApplicationContext.getInstance().getUser_authentified();
        user.setFirstName(firstnameField.getText());
        user.setLastName(lastnameField.getText());
        if (!Objects.equals(passwordField.getText(), "")) {
            user.setPassword(passwordField.getText());
        }

        if (birthDateField.getValue() != null) {
            try {
                user.setDate_birth(new SimpleDateFormat("yyyy-MM-dd").parse(birthDateField.getValue().toString()));

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }

        user.setPhone(phoneNumberField.getText());
        if (addressField.getText() != user.getAddress()) {
            user.setAddress(addressField.getText());
        }
        user.setDescription(descriptionField.getText());
        DatabaseHandler.getInstance().getDbManager().updateEntity(user);
        update();
        ModalHelper.showInformationModal("Sauvegarde", "Vos modifications ont bien été sauvegardées");
    }
}
