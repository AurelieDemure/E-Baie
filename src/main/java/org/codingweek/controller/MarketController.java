package org.codingweek.controller;

import java.net.URL;
import java.util.*;

import org.codingweek.db.entity.Offer;
import org.codingweek.model.OfferType;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MarketController extends Controller implements Observeur{

    private List<Offer> offers = new ArrayList<Offer>();

    @FXML
    private ChoiceBox<String> priceChoice;

    @FXML
    private ChoiceBox<String> frequencyChoice;

    @FXML
    private ChoiceBox<String> sortChoice;

    @FXML
    private TextField reaserchField;

    @FXML
    private VBox scrollField;

    @FXML
    private ToggleGroup type;

    @FXML
    void reaserchText(KeyEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.priceChoice.getItems().setAll("Tout type de prix", "Moins de 100 florains", "Entre 100 et 200 florains", "Entre 200 et 300 florains", "Entre 300 et 400 florains", "Entre 400 et 500 florains", "Plus de 500 florains");
        this.priceChoice.getSelectionModel().select(0);
        this.frequencyChoice.getItems().setAll("Tout type de fréquence", "Unique", "Journalier", "Hebdomadaire", "Mensuelle", "Annuelle");
        this.frequencyChoice.getSelectionModel().select(0);
        this.sortChoice.getItems().setAll("Prix croissant", "Prix décroissant", "Titre A-Z", "Titre Z-A", "Auteur A-Z", "Auteur Z-A");
        this.sortChoice.getSelectionModel().select(0);
        Pane pane;
        searchOffers();
        for(Offer offer : this.offers){
            pane = makePaneOffre(offer);
            this.scrollField.getChildren().add(pane);
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refresh'");
    }

    public void searchOffers(){
        Offer offer1 = new Offer();
        offer1.setOfferType(OfferType.LOAN);
        offer1.setTitle("Un truc");
        offer1.setPrice(100);
        Offer offer2 = new Offer();
        offer2.setOfferType(OfferType.SERVICE);
        offer2.setTitle("Un autre truc");
        offer2.setPrice(10);
        offers.add(offer1);
        offers.add(offer2);
    }

    public Pane makePaneOffre(Offer offer){
        Pane pane = new Pane();
        HBox hbox = new HBox();
        VBox vbox = new VBox();
        ImageView image = new ImageView();
        Label titleLabel = new Label(offer.getTitle());
        Label priceLabel = new Label(offer.getPrice() + " florains");
        String offerType;
        switch(offer.getOfferType()){
            case LOAN:
                offerType = "Prêt";
                break;
            case SERVICE:
                offerType = "Service";
                break;
            default:
            offerType = "";
        }
        Label offerTypeLabel = new Label(offerType);
        vbox.getChildren().addAll(titleLabel, priceLabel, offerTypeLabel);
        hbox.getChildren().addAll(image, vbox);
        pane.getChildren().add(hbox);
        pane.getStyleClass().add("offer");
        return pane;
    }
}
