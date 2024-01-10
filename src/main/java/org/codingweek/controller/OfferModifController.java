package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.User;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.ImageHandler;
import org.codingweek.model.OfferModifModel;
import org.codingweek.model.Page;
import org.codingweek.model.filter.Frequency;
import org.codingweek.model.filter.OfferType;
import org.codingweek.view.ConnexionView;
import org.codingweek.view.MyOffersView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OfferModifController extends Controller implements Observeur {
    public Label errorFillAll;
    public TextField title;
    public TextField description;
    public Label errorNotDouble;
    public TextField price;
    public ChoiceBox type_offer;
    public ChoiceBox frequency;
    public TextField localization;
    public ImageView path_offer;
    public String path;

    private Offer offer;

    @Override
    public void refresh() {

    }

    private void toggleErrorFillAll(boolean visible) {
        errorFillAll.setVisible(visible);
        errorFillAll.setManaged(visible);
    }

    private void toggleErrorNotDouble(boolean visible) {
        errorNotDouble.setVisible(visible);
        errorNotDouble.setManaged(visible);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        toggleErrorFillAll(false);
        toggleErrorNotDouble(false);
        type_offer.getItems().addAll("Pret", "Service");
        frequency.getItems().addAll("Tout type de frequence", "Unique", "Journalier", "Hebdomadaire", "Mensuelle", "Annuelle");
        update();
    }

    @Override
    public void update() {
        offer = OfferModifModel.getMyOfferToModify(ApplicationContext.getInstance().getOfferId());
        toggleErrorFillAll(false);
        toggleErrorNotDouble(false);
        title.setText(offer.getTitle());
        description.setText(offer.getDescription());
        price.setText(String.valueOf(offer.getPrice()));
        try {
            Double val = Double.parseDouble(price.getText());
        } catch (Exception e ) {
            toggleErrorNotDouble(true);
        }
        type_offer.setValue(offer.getTypeOffer().getValue());
        frequency.setValue(offer.getFrequency().getValue());
        localization.setText(offer.getLocalization());
        path_offer.setImage(ImageHandler.getImage(offer.getPath()));
        ApplicationContext.getInstance().setOfferId(null);
    }

    public void selectImage(ActionEvent event) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        File file = ImageHandler.openModalFile();
        String path = file.getAbsolutePath();
        this.path = path;
        path_offer.setImage(new Image(new File(path).toURI().toString()));
    }

    public void saveModifiedOffer(ActionEvent event) {
        if (title.getText().isEmpty() || description.getText().isEmpty() || price.getText().isEmpty() || type_offer.getValue() == null || frequency.getValue() == null || localization.getText().isEmpty()) {
            errorFillAll.setText("Veuillez remplir tous les champs");
            toggleErrorFillAll(true);
        } else {
            Double prices;
            try {
                prices = Double.parseDouble(price.getText());
            } catch (Exception e) {
                toggleErrorNotDouble(true);
                return;
            }
                offer.setTitle(title.getText());
                offer.setDescription(description.getText());
                offer.setPrice(prices);
                offer.setType(OfferType.fromString(type_offer.getValue().toString()));
                offer.setFrequency(Frequency.fromString(frequency.getValue().toString()));
                offer.setLocalization(localization.getText());

                DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
                db.updateEntity(offer);
                ApplicationContext.getInstance().setPageType(Page.OFFER);
                try {
                    ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
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
