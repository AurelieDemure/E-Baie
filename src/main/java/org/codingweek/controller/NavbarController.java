package org.codingweek.controller;

import javafx.scene.image.Image;
import org.codingweek.*;
import org.codingweek.db.entity.User;
import org.codingweek.model.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import org.codingweek.view.*;
import javafx.scene.image.ImageView;



//import javax.swing.text.html.ImageView;

public class NavbarController extends Controller implements Observeur{

    public Button deconnexion;
    @FXML
    private Button accountButton;

    @FXML
    private Button marketButton;

    @FXML
    private Button messageButton;

    @FXML
    private Pane navbar;

    @FXML
    private Button offerButton;

    @FXML
    void clickLogo(MouseEvent event) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MarketView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();  
    }


    @FXML
    void clickAccount(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new AccountView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();
    }

    @FXML
    void clickMarket(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MarketView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();
    }

    @FXML
    void clickMessage(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.MESSAGE);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new TchatView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();
    }

    @FXML
    void clickOffer(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView deco = new ImageView(new Image(
                Objects.requireNonNull(ApplicationContext.class
                        .getResourceAsStream("/org/codingweek/img/doorDisconnect.png"))
        ));
        deco.setFitWidth(40);
        deco.setFitHeight(40);
        deconnexion.setGraphic(deco);
        switch(ApplicationContext.getInstance().getPageType()){
            case ACCOUNT:
                this.accountButton.setStyle("-fx-text-fill: -fx-white");
                break;
            case MARKET:
                this.marketButton.setStyle("-fx-text-fill: -fx-white");
                break;
            case MESSAGE:
                this.messageButton.setStyle("-fx-text-fill: -fx-white");
                break;
            case OFFER:
                this.offerButton.setStyle("-fx-text-fill: -fx-white");
                break;
            default:
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void refresh() {
        this.accountButton.setStyle("-fx-text-fill: -fx-dark");
        this.marketButton.setStyle("-fx-text-fill: -fx-dark");
        this.messageButton.setStyle("-fx-text-fill: -fx-dark");
        this.offerButton.setStyle("-fx-text-fill: -fx-dark");
        switch(ApplicationContext.getInstance().getPageType()){
            case ACCOUNT:
                this.accountButton.setStyle("-fx-text-fill: -fx-white");
                break;
            case MARKET:
                this.marketButton.setStyle("-fx-text-fill: -fx-white");
                break;
            case MESSAGE:
                this.messageButton.setStyle("-fx-text-fill: -fx-white");
                break;
            case OFFER:
                this.offerButton.setStyle("-fx-text-fill: -fx-white");
                break;
            default:
        }
    }

    public void Disconnect(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.NONE);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new ConnexionView().loadScene());
            ApplicationContext.getInstance().setUser_authentified(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

