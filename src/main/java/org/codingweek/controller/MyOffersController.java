package org.codingweek.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.codingweek.ApplicationContext;
import org.codingweek.model.Page;

import java.net.URL;
import java.util.ResourceBundle;

public class MyOffersController extends Controller implements Observeur {
    @FXML
    private Label welcomeText;

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
}
