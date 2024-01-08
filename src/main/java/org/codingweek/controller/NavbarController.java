package org.codingweek.controller;

import org.codingweek.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    void clickAccount(ActionEvent event) {

    }

    @FXML
    void clickMarket(ActionEvent event) {

    }

    @FXML
    void clickMessage(ActionEvent event) {

    }

    @FXML
    void clickOffer(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        switch(ApplicationContext.getInstance().getPageType()){
            case ACCOUNT:
                this.accountButton.setUnderline(true);
                break;
            case MARKET:
                this.marketButton.setUnderline(true);
                break;
            case MESSAGE:
                this.messageButton.setUnderline(true);
                break;
            case OFFER:
                this.offerButton.setUnderline(true);
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refresh'");
    }

}

