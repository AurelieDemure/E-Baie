package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.model.Page;
import org.codingweek.view.OfferCreateView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyOffersController extends Controller implements Observeur {

    @Override
    public void refresh() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
    }

    public void sendToCreateOffer(ActionEvent actionEvent) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new OfferCreateView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
