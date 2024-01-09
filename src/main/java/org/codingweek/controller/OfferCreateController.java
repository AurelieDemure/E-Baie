package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.Query;
import org.codingweek.db.entity.User;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.Page;
import org.codingweek.view.MarketView;
import org.codingweek.view.MyOffersView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OfferCreateController extends Controller implements Observeur {

    public TextField title;
    public TextField description;
    public TextField owner;
    public TextField price;
    public TextField type_offer;
    public TextField frequency;
    public TextField localization;
    public ImageView path_offer;
    public Label errorFillAll;

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
    }

    @Override
    public void update() {
    }

    public void saveModifiedOffer(ActionEvent actionEvent) {
        if (title.getText() == null | description.getText() == null | owner.getText() == null | price.getText() == null | type_offer.getText() == null | frequency.getText() == null | localization.getText() == null | path_offer.getImage() == null) {
                errorFillAll.setText("Veuillez remplir tous les champs");
                toggleErrorFillAll(true);
        } else {
                DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
                String mail = owner.getText();
                User user = db.getEntity(User.class, mail);
                Double prices = Double.parseDouble(price.getText());
                Offer offer = new Offer(title.getText(), description.getText(), user, prices, type_offer.getText(), frequency.getText(), localization.getText(), path_offer.getAccessibleText());
                db.saveEntity(offer);
        }
    }

    public void showConfirmationAddDialog(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Etes vous sur ?");
        alert.setContentText("Voulez vous vraiment annuler cette offre ?");

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
