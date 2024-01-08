package org.codingweek.controller;

import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.codingweek.model.ImageHandler;

public class MarketController extends Controller implements Observeur{

    private List<String> list = Arrays.asList("kjcvkj", "ljh<svj<h","ljbljhv<jhsdvjh","kjh<vkjhv<jkh");

    @FXML
    private TextField reaserchField;

    @FXML
    private VBox scrollField;

    @FXML
    void reaserchText(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Label label;
        for(String text : list){
            label = new Label(text);
            label.setStyle("-fx-width: 200px;  -fx-background-color: -fx-light-green; -fx-padding: 20px; -fx-border-radius: 10px;");
            this.scrollField.getChildren().add(label);
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
