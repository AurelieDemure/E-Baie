package org.codingweek.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import org.codingweek.ApplicationContext;
import org.codingweek.model.Page;

public class MainController extends Controller implements Observeur {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
    }
}