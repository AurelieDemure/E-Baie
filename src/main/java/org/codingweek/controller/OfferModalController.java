package org.codingweek.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.entity.Offer;
import org.codingweek.model.ImageHandler;
import org.codingweek.model.OfferModalModel;
import org.codingweek.model.OfferModifModel;
import org.codingweek.model.Page;
import org.codingweek.view.OfferModifView;

import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OfferModalController extends Controller implements Observeur{
    public ImageView OfferImage;
    public Label OffreTitle;
    public Label OfferDescription;
    public Label OfferPrice;
    public Label OfferTypeServ;
    public Label OfferFrequency;
    public Label OfferLoc;
    public Label OfferBook;

    @FXML
    private void showConfirmationAddDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Etes vous sur ?");
        alert.setContentText("Voulez vous vraiment modifier cette offre ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                ApplicationContext.getInstance().setPageType(Page.OFFER);
                try {
                    ApplicationSettings.getInstance().setCurrentScene(new OfferModifView().loadScene());
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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        Offer offer = OfferModalModel.getMyOfferToModify(ApplicationContext.getInstance().getOfferId());
        OffreTitle.setText(offer.getTitle());
        OfferPrice.setText(String.valueOf(offer.getPrice()));
        OfferFrequency.setText(offer.getFrequency().getValue());
        OfferDescription.setText(offer.getDescription());
        OfferLoc.setText(offer.getLocalization());
        OfferTypeServ.setText(offer.getTypeOffer().getValue());
        OfferImage.setImage(ImageHandler.getImage(offer.getPath()));
        ApplicationContext.getInstance().setOfferId(null);
    }

}
