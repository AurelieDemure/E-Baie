package org.codingweek.controller;

import java.net.URL;
import java.util.*;

import org.codingweek.ApplicationContext;
import org.codingweek.db.entity.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import org.codingweek.model.*;
import org.codingweek.model.filter.*;

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
    private RadioButton serviceButton;

    @FXML
    private RadioButton serviceLoanButton;

    @FXML
    private RadioButton loanButton;

    @FXML
    private Button researchButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.priceChoice.getItems().setAll("Tout type de prix", "Moins de 100 florains", "Entre 100 et 200 florains", "Entre 200 et 300 florains", "Entre 300 et 400 florains", "Entre 400 et 500 florains", "Plus de 500 florains");
        this.priceChoice.getSelectionModel().select(0);
        this.frequencyChoice.getItems().setAll("Tout type de frequence", "Unique", "Journalier", "Hebdomadaire", "Mensuelle", "Annuelle");
        this.frequencyChoice.getSelectionModel().select(0);
        this.sortChoice.getItems().setAll("Prix croissant", "Prix décroissant", "Titre A-Z", "Titre Z-A");
        this.sortChoice.getSelectionModel().select(0);
        this.offers.clear();
        this.offers = MarketModel.getOffersAvailable(ApplicationContext.getInstance().getUser_authentified().getEmail());
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
    void clickSearch(ActionEvent event) {
        OfferType offerType = null;
        if(this.loanButton.isSelected()){
            offerType = OfferType.LOAN;
        }
        else if(this.serviceButton.isSelected()){
            offerType = OfferType.SERVICE;
        }
        this.offers = MarketModel.getOffersAvailableFiltered(ApplicationContext.getInstance().getUser_authentified().getEmail(), offerType, Frequency.fromString(this.frequencyChoice.getValue()), Price.fromString(this.priceChoice.getValue()), SortOffer.fromString(this.sortChoice.getValue()));
        refresh();
    }

    public Pane makePaneOffre(Offer offer){
        ImageView image = new ImageView();
        image.setImage(ImageHandler.getImage(offer.getPath()));
        image.setTranslateX(20);
        image.setTranslateY(20);
        image.setFitHeight(200);
        image.setFitWidth(200);

        Label titleLabel = new Label(offer.getTitle());
        titleLabel.getStyleClass().add("title");
        titleLabel.setMaxWidth(150);

        Label priceLabel = new Label(offer.getPrice() + " florains");
        priceLabel.getStyleClass().add("price");

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

        Label ownerLabel = new Label("by : " + offer.getOwner().getFirstName() + " " + offer.getOwner().getLastName());

        Label frequencyLabel = new Label(offer.getFrequency().getValue());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(titleLabel, priceLabel, offerTypeLabel, ownerLabel, frequencyLabel);
        vbox.getStyleClass().add("margin");

        HBox hbox = new HBox();
        hbox.getChildren().addAll(image, vbox);

        Pane pane = new Pane();
        pane.getStyleClass().add("offer");
        pane.setMinSize(100, 100);
        pane.getChildren().add(hbox);
        return pane;
    }
}