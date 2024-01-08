package org.codingweek.controller;

import org.codingweek.*;
import org.codingweek.model.Page;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class NavbarController extends Controller implements Observeur{

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
        refresh();  
    }


    @FXML
    void clickAccount(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        refresh();
    }

    @FXML
    void clickMarket(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
        refresh();
    }

    @FXML
    void clickMessage(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.MESSAGE);
        refresh();
    }

    @FXML
    void clickOffer(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

}

