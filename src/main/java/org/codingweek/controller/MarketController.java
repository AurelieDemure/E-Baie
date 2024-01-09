package org.codingweek.controller;

import java.net.URL;
import java.util.*;

import org.codingweek.model.Offer;
import org.codingweek.model.User;

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

    private List<Offer> listTest = new ArrayList<Offer>();

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
        this.frequencyChoice.getItems().setAll("Tout type de frequence", "Unique", "Journalier", "Hebdomadaire", "Mensuelle", "Annuelle");
        this.frequencyChoice.getSelectionModel().select(0);
        this.sortChoice.getItems().setAll("Prix croissant", "Prix décroissant", "Titre A-Z", "Titre Z-A", "Auteur A-Z", "Auteur Z-A");
        this.sortChoice.getSelectionModel().select(0);
        Pane pane;
        Pane spacePane;
        searchOffers();
        int x = 0;
        HBox hbox = new HBox();
        for(Offer offer : this.offers){
            pane = makePaneOffre(offer);
            hbox.getChildren().add(pane);
            spacePane = new Pane();
            spacePane.getStyleClass().add("space");
            hbox.getChildren().add(spacePane);
            x++;

            if(x == 3){
                this.scrollField.getChildren().add(hbox);
                spacePane = new Pane();
                spacePane.getStyleClass().add("space");
                this.scrollField.getChildren().add(spacePane);
                hbox = new HBox();
                x = 0;
            }
        }
        this.scrollField.getChildren().add(hbox);

        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
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
       for (Offer offer : listTest){
            if(offer.getType()==this.type.toString()){
                offers.add(offer);
            }
       }
    }

    public Pane makePaneOffre(Offer offer){
        Pane pane = new Pane();
        HBox hbox = new HBox();
        VBox vbox = new VBox();
        ImageView image = new ImageView();
        Label titleLabel = new Label(offer.getTitle());
        Label priceLabel = new Label(offer.getPrice() + " florains");
        String offerType;
        switch(offer.getType()){
            case "LOAN":
                offerType = "Pret";
                break;
            case "SERVICE":
                offerType = "Service";
                break;
            default:
            offerType = "";
        }
        Label offerTypeLabel = new Label(offerType);
        Label ownerLabel = new Label("by : " + offer.getOwner());
        Label frequencyLabel = new Label(offer.getFrequency());
        vbox.getChildren().addAll(titleLabel, priceLabel, offerTypeLabel, ownerLabel, frequencyLabel);
        hbox.getChildren().addAll(image, vbox);
        pane.getChildren().add(hbox);
        pane.getStyleClass().add("offer");
        titleLabel.getStyleClass().add("title");
        priceLabel.getStyleClass().add("price");
        return pane;
    }
}
