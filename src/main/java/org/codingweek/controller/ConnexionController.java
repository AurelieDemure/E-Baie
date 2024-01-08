package org.codingweek.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.codingweek.ApplicationContext;
import org.codingweek.model.Page;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnexionController extends Controller implements Observeur {
    public TextField emailField;
    public PasswordField passwordField;
    public TextField firstnameField;
    public TextField lastnameField;
    public TextField emailInscrField;
    public PasswordField passwordInscrField;

    @FXML
    private Label welcomeText;

    @Override
    public void refresh() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        emailField.setText("ad@tn.net");
        firstnameField.setText("a");
        lastnameField.setText("d");
        emailInscrField.setText("ad@tn.eu");
    }
}
