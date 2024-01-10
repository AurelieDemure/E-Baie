package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.entity.User;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.Page;
import org.codingweek.view.ConnexionView;
import org.codingweek.view.MyOffersView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OfferModifController extends Controller implements Observeur {
    public Label errorFillAll;
    public TextField title;
    public TextField description;
    public TextField price;
    public ChoiceBox type_offer;
    public ChoiceBox frequency;
    public TextField localization;
    public ImageView path_offer;

    @Override
    public void refresh() {

    }

    private void toggleErrorFillAll(boolean visible) {
        errorFillAll.setVisible(visible);
        errorFillAll.setManaged(visible);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        toggleErrorFillAll(false);
        update();
    }

    @Override
    public void update() {
    }

    public void selectImage(ActionEvent event) {
    }

    public void saveModifiedOffer(ActionEvent event) {
    }

    public void showConfirmationAddDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Etes vous sur ?");
        alert.setContentText("Voulez vous vraiment annuler vos modifications ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                ApplicationContext.getInstance().setPageType(Page.OFFER);
                try {
                    ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
