package org.codingweek.controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.entity.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import org.codingweek.model.*;
import org.codingweek.model.filter.*;
import org.codingweek.view.OfferMarketView;
import org.codingweek.view.TchatView;

public class MarketController extends Controller implements Observeur{

    @FXML
    public ChoiceBox<String> distanceChoice;
    private int offerId = -1;

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
    private RadioButton serviceButton;

    @FXML
    private RadioButton loanButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseHandler.getInstance().getDbManager().addObserveur(this);

        this.priceChoice.getItems().setAll("Tout type de prix", "Moins de 100 florains", "Entre 100 et 200 florains", "Entre 200 et 300 florains", "Entre 300 et 400 florains", "Entre 400 et 500 florains", "Plus de 500 florains");
        this.priceChoice.getSelectionModel().select(0);
        this.priceChoice.setOnAction( (event -> {
            search();
        }));
        this.frequencyChoice.getItems().setAll("Tout type de frequence", "Unique", "Journalier", "Hebdomadaire", "Mensuelle", "Annuelle");
        this.frequencyChoice.getSelectionModel().select(0);
        this.frequencyChoice.setOnAction( (event -> {
            search();
        }));
        this.sortChoice.getItems().setAll("Prix croissant", "Prix décroissant", "Titre A-Z", "Titre Z-A");
        this.sortChoice.getSelectionModel().select(0);
        this.sortChoice.setOnAction( (event -> {
            search();
        }));
        this.distanceChoice.setDisable(ApplicationContext.getInstance().getUser_authentified().getAddress() == null || ApplicationContext.getInstance().getUser_authentified().getAddress().isEmpty());
        this.distanceChoice.getItems().setAll("Toutes distances", "Moins de 5km", "Moins de 20km", "Moins de 30km", "Moins de 60km", "Moins de 100km", "Moins de 200km", "Toutes distances");
        this.distanceChoice.getSelectionModel().select(0);
        this.distanceChoice.setOnAction( (event -> {
            search();
        }));
        this.offers.clear();
        this.offers = MarketModel.getOffersAvailable(ApplicationContext.getInstance().getUser_authentified().getEmail());
        
        refresh();
    }

    @Override
    public void update() {
        refresh();
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

    void search() {
        String email = ApplicationContext.getInstance().getUser_authentified().getEmail();

        String text = this.reaserchField.getText();
        if(text==""){
            text = null;
        }

        OfferType offerType = null;
        if(this.loanButton.isSelected()){
            offerType = OfferType.LOAN;
        }
        else if(this.serviceButton.isSelected()){
            offerType = OfferType.SERVICE;
        }
        
        Frequency frequency = Frequency.fromString(this.frequencyChoice.getValue());
        if(frequency == Frequency.ALLFREQUENCY){
            frequency = null;
        }

        Price price = Price.fromString(this.priceChoice.getValue());
        if(price == Price.ALLPRICE){
            price = null;
        }
        Distance distance = Distance.fromString(this.distanceChoice.getValue());
        if (distance == Distance.ALLDISTANCE){
            distance = null;
        }

        SortOffer sortOffer = SortOffer.fromString(this.sortChoice.getValue());

        this.offers = MarketModel.getOffersAvailableFiltered(email, text, offerType, frequency, price, sortOffer, distance);
        refresh();
    }

    @FXML
    void clickLoan(ActionEvent event) {
        search();
    }

    @FXML
    void clickService(ActionEvent event) {
        search();
    }

    @FXML
    void clickServiceLoan(ActionEvent event) {
        search();
    }

    @FXML
    void textTyped(KeyEvent event) {
        search();
    }

    public void goToOffer(Offer offer){
        try {
            ApplicationContext.getInstance().setIndex(this.offerId);
            ApplicationSettings.getInstance().setCurrentScene(new OfferMarketView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        Label ownerLabel = new Label("de : " + offer.getOwner().getFirstName() + " " + offer.getOwner().getLastName());

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
        pane.setOnMouseClicked( (event -> {
            this.offerId = offer.getId();
            goToOffer(offer);
        }));
        return pane;
    }
}