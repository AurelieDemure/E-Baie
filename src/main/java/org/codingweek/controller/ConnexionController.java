package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.model.Page;
import org.codingweek.view.MarketView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnexionController extends Controller implements Observeur {
    public TextField emailField;
    public PasswordField passwordField;
    public TextField firstnameField;
    public TextField lastnameField;
    public TextField emailInscrField;
    public PasswordField passwordInscrField;
    public Label errorSigninLabel;
    public Label errorSignupLabel;

    @Override
    public void refresh() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        errorSigninLabel.setVisible(false);
        errorSignupLabel.setVisible(false);
        errorSignupLabel.setManaged(false);
        errorSigninLabel.setManaged(false);
    }

    public void signin(ActionEvent actionEvent) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
        try {
            ApplicationContext.getInstance().setUser_authentified(null);
            ApplicationSettings.getInstance().setCurrentScene(new MarketView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void signup(ActionEvent actionEvent) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
        try {
            ApplicationContext.getInstance().setUser_authentified(null);
            ApplicationSettings.getInstance().setCurrentScene(new MarketView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
