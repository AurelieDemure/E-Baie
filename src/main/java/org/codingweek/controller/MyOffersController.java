package org.codingweek.controller;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import org.codingweek.*;
import org.codingweek.db.*;
import org.codingweek.db.entity.*;
import org.codingweek.model.*;
import org.codingweek.view.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MyOffersController extends Controller implements Observeur {

    private List<Offer> myOffers = new ArrayList<>();

    private Integer indexModify = -1;

    @FXML
    public VBox scrollField;



    @Override
    public void refresh() {
        this.scrollField.getChildren().clear();
        Pane pane;
        int x = 0;
        HBox hBox = new HBox();

        for (Offer offer : this.myOffers) {
            pane = makePaneOffer(offer);
            hBox.getChildren().add(pane);
            x++;

            if (x == 2) {
                this.scrollField.getChildren().add(hBox);
                hBox = new HBox();
                x = 0;
            }
        }
        this.scrollField.getChildren().add(hBox);
    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        this.myOffers.clear();
        User user = ApplicationContext.getInstance().getUser_authentified();
        this.myOffers = MyOffersModel.getMyOffers(user.getEmail());
        refresh();
    }

    public void sendToCreateOffer(ActionEvent actionEvent) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new OfferCreateView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void sendToMyQueries(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MyQueriesView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pane makePaneOffer(Offer offer) {
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

        Label frequencyLabel = new Label(offer.getFrequency().getValue());

        Button consulter = new Button("Consulter");
        consulter.getStyleClass().add("buttonConsult");
        consulter.setOnAction(e -> {
            this.indexModify = offer.getId();
            showOfferModal(e);
        });

        Button modifier = new Button("Modifier");
        modifier.getStyleClass().add("buttonModOffer");
        modifier.setOnAction(e -> {
            this.indexModify = offer.getId();
            showOfferModif(e);
        });

        Button supprimer = new Button("Supprimer");
        supprimer.getStyleClass().add("buttonSupOffer");
        supprimer.setOnAction(e -> {
            showConfirmationAddDialog(e, offer);
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(titleLabel, priceLabel, offerTypeLabel, frequencyLabel, consulter, modifier, supprimer);
        vbox.getStyleClass().add("margin");

        HBox hbox = new HBox();
        hbox.getChildren().addAll(image, vbox);

        Pane pane = new Pane();
        pane.getStyleClass().add("offer");
        pane.setMinSize(100, 100);
        pane.getChildren().add(hbox);
        return pane;
    }

    private void showOfferModif(ActionEvent event) {
        try {
            ApplicationContext.getInstance().setOfferId(this.indexModify);
            ApplicationSettings.getInstance().setCurrentScene(new OfferModifView().loadScene());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void showOfferModal(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        try {
            ApplicationContext.getInstance().setOfferId(this.indexModify);
            ApplicationSettings.getInstance().setCurrentScene(new OfferModalView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void showConfirmationAddDialog(ActionEvent actionEvent, Offer offer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Etes vous sur ?");
        alert.setContentText("Voulez vous vraiment supprimer cette offre ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                ApplicationContext.getInstance().setPageType(Page.OFFER);
                DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
                db.deleteEntity(offer);
                try {
                    ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
