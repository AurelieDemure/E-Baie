package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.Query;
import org.codingweek.db.entity.User;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.ImageHandler;
import org.codingweek.model.Page;
import org.codingweek.model.filter.Frequency;
import org.codingweek.model.filter.OfferType;
import org.codingweek.view.MarketView;
import org.codingweek.view.MyOffersView;

import java.io.File;
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

    public String path;
    public Label errorFillAll;
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
    }

    @Override
    public void update() {

    }

    public void saveModifiedOffer(ActionEvent actionEvent) {
        if (title.getText().isEmpty() || description.getText().isEmpty() || owner.getText().isEmpty() || price.getText().isEmpty() || type_offer.getText().isEmpty() || frequency.getText().isEmpty() || localization.getText().isEmpty()) {
                errorFillAll.setText("Veuillez remplir tous les champs");
                toggleErrorFillAll(true);
        } else {
            DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
            User user = ApplicationContext.getInstance().getUser_authentified();
            Double prices = Double.parseDouble(price.getText());
            Offer offer = new Offer(title.getText(), description.getText(), user, prices, OfferType.fromString(type_offer.getText()), Frequency.fromString(frequency.getText()), localization.getText(), path);
            db.saveEntity(offer);
            assert db.getEntity(Offer.class, offer.getId()) != null;

            ApplicationContext.getInstance().setPageType(Page.OFFER);
            try {
                ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    public void selectImage(ActionEvent actionEvent) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        File file = ImageHandler.openModalFile();
        String path = file.getAbsolutePath();
        this.path = path;
        path_offer.setImage(new Image(new File(path).toURI().toString()));
    }
}
