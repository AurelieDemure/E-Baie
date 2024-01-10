package org.codingweek.controller;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import org.codingweek.*;
import org.codingweek.db.*;
import org.codingweek.db.entity.*;
import org.codingweek.model.*;
import java.net.*;
import java.util.*;

public class OfferMarketController extends Controller implements Observeur{

    public Offer offer;

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
        this.offer = OfferMarketModel.getOffersAvailable(ApplicationContext.getInstance().getIndex());
        //OfferImage.setImage(Image i.png);
        OffreTitle.setText(this.offer.getTitle());
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

    public void setOffer(Offer offer){
        this.offer = offer;
    }
}
