package org.codingweek.controller;

import java.net.URL;
import java.util.*;

import org.codingweek.ApplicationContext;
import org.codingweek.db.entity.*;
import org.codingweek.model.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

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
    private RadioButton serviceButton;

    @FXML
    private RadioButton loanButton;

    @FXML
    private RadioButton serviceLoanButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "SERVICE", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "SERVICE", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "SERVICE", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "SERVICE", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "SERVICE", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "LOAN", "Hebdomadaire", "Ugine", "ljhsdgljf"));
        listTest.add(new Offer("Un truc", "blablabla", new User("Un", "gens", "blupblup", "hgkvhg", "khgsvdk", "kjhsvdfkjv", "ljhsdf", 4), 100, "SERVICE", "Hebdomadaire", "Ugine", "ljhsdgljf"));

        this.priceChoice.getItems().setAll("Tout type de prix", "Moins de 100 florains", "Entre 100 et 200 florains", "Entre 200 et 300 florains", "Entre 300 et 400 florains", "Entre 400 et 500 florains", "Plus de 500 florains");
        this.priceChoice.getSelectionModel().select(0);
        this.frequencyChoice.getItems().setAll("Tout type de frequence", "Unique", "Journalier", "Hebdomadaire", "Mensuelle", "Annuelle");
        this.frequencyChoice.getSelectionModel().select(0);
        this.sortChoice.getItems().setAll("Prix croissant", "Prix décroissant", "Titre A-Z", "Titre Z-A", "Auteur A-Z", "Auteur Z-A");
        this.sortChoice.getSelectionModel().select(0);
        this.offers.clear();
        for(Offer offer : listTest){
            this.offers.add(offer);
        }
        refresh();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void refresh() {
        this.scrollField.getChildren().clear();
        Pane pane;
        Pane spacePane;
        int x = 0;
        HBox hbox = new HBox();
        for(Offer offer : this.offers){
            pane = makePaneOffre(offer);
            hbox.getChildren().add(pane);
            x++;

            if(x == 2){
                this.scrollField.getChildren().add(hbox);
                hbox = new HBox();
                x = 0;
            }
        }
        this.scrollField.getChildren().add(hbox);
    }

    @FXML
    void loanSelected(ActionEvent event) {
        this.offers.clear();
        for(Offer offer : listTest){
            if(offer.getTypeOffer()==OfferType.LOAN){
                this.offers.add(offer);
            }
            else{
                this.offers.remove(offer);
            }
        }
        refresh();
    }

    @FXML
    void serviceLoanSelected(ActionEvent event) {
        this.offers.clear();
        for(Offer offer : listTest){
            this.offers.add(offer);
        }
        refresh();
    }

    @FXML
    void serviceSelected(ActionEvent event) {
        this.offers.clear();
        for(Offer offer : listTest){
            if(offer.getTypeOffer()==OfferType.SERVICE){
                this.offers.add(offer);
            }
            else{
                this.offers.remove(offer);
            }
        }
        refresh();
    }

    public Pane makePaneOffre(Offer offer){
        Pane pane = new Pane();
        HBox hbox = new HBox();
        VBox vbox = new VBox();
        ImageView image = new ImageView();
        image.setImage(ImageHandler.getImage(offer.getPath()));
        Label titleLabel = new Label(offer.getTitle());
        Label priceLabel = new Label(offer.getPrice() + " florains");
        String offerType;
        switch(offer.getTypeOffer()){
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
