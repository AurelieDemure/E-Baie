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
    @FXML
    private Label noteLabel;
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
    
    private List<Query> queries;

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
        this.offer = OfferMarketModel.getOffer(ApplicationContext.getInstance().getIndex());
        this.noteLabel.setText("5/5");
        this.OfferImage.setImage(ImageHandler.getImage(this.offer.getPath())); 
        this.offerAuthor.setText("by : " + this.offer.getOwner().getFirstName() + " " + this.offer.getOwner().getLastName());
        this.OffreTitle.setText(this.offer.getTitle());
        this.OfferPrice.setText(this.offer.getPrice() + " florains");
        this.OfferTypeServ.setText(this.offer.getType());
        this.OfferFrequency.setText(this.offer.getFrequency().getValue());
        this.OfferDescription.setText(this.offer.getDescription());
        this.OfferLoc.setText(this.offer.getLocalization());

        this.queries = offer.getQueries();
        this.OfferBook.setText("date prise, date rendu, utilisateur");
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
