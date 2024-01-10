package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.codingweek.ApplicationContext;
import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.User;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.Page;

import java.net.URL;
import java.util.ResourceBundle;

public class OfferMarketController extends Controller implements Observeur{
    public ImageView OfferImage;
    public Label OffreTitle;
    public Label OfferDescription;
    public Label OfferPrice;
    public Label OfferTypeServ;
    public Label OfferFrequency;
    public Label OfferLoc;
    public Label OfferBook;
    public DatePicker dateBegin;
    public DatePicker dateEnd;
    public Label offerAuthor;
    @FXML
    private Label welcomeText;

    @FXML
    private void showConfirmationAddDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Etes vous sur ?");
        alert.setContentText("Voulez vous vraiment réserver cette offre ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
                //db.getEntity(User.class, this.offerAuthor.getEm)
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
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        //OfferImage.setImage(Image i.png);
        OffreTitle.setText("Titre");
        OfferPrice.setText("Prix");
        OfferFrequency.setText("Frequency");
        OfferDescription.setText("Description");
        OfferLoc.setText("Localisation");
        OfferFrequency.setText("Frequence");
        OfferBook.setText("date prise, date rendu, utilisateur");
    }

    public void contactAuthor(ActionEvent actionEvent) {
    }

    public void deleteOffer(Offer offer) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        db.deleteEntity(offer);
    }
}