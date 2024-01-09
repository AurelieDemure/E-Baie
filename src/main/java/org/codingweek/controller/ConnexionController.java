package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.entity.User;
import org.codingweek.model.AuthHandler;
import org.codingweek.model.DatabaseHandler;
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

    private void toggleErrorSigninLabel(boolean visible) {
        errorSigninLabel.setVisible(visible);
        errorSigninLabel.setManaged(visible);
    }

    private void toggleErrorSignupLabel(boolean visible) {
        errorSignupLabel.setVisible(visible);
        errorSignupLabel.setManaged(visible);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        toggleErrorSigninLabel(false);
        toggleErrorSignupLabel(false);
    }

    public void signin(ActionEvent actionEvent) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
        toggleErrorSignupLabel(false);
        try {
            User user = AuthHandler.getUser(emailField.getText());
            if (user == null) {
                errorSigninLabel.setText("Utilisateur inconnu");
                toggleErrorSigninLabel(true);
                return;
            }
            if (!AuthHandler.checkPassword(user, passwordField.getText())) {
                    errorSigninLabel.setText("Mot de passe incorrect");
                    toggleErrorSigninLabel(true);
                    return;
            }

            ApplicationContext.getInstance().setUser_authentified(user);
            ApplicationSettings.getInstance().setCurrentScene(new MarketView().loadScene());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void signup(ActionEvent actionEvent) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
        try {
            if (firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || emailInscrField.getText().isEmpty() || passwordInscrField.getText().isEmpty()) {
                errorSignupLabel.setText("Veuillez remplir tous les champs");
                toggleErrorSignupLabel(true);
                return;
            }
            if (AuthHandler.getUser(emailInscrField.getText()) != null) {
                errorSignupLabel.setText("Utilisateur déjà existant");
                toggleErrorSignupLabel(true);
                return;
            }
            User user = new User();
            user.setFirstName(firstnameField.getText());
            user.setLastName(lastnameField.getText());
            user.setEmail(emailInscrField.getText());
            user.setPassword(passwordInscrField.getText());

            DatabaseHandler.getInstance().getDbManager().saveEntity(user);

            ApplicationContext.getInstance().setUser_authentified(user);
            ApplicationSettings.getInstance().setCurrentScene(new MarketView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
