package org.codingweek.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.entity.Notification;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.Query;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.MyOffersModel;
import org.codingweek.view.MyOffersView;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AcceptOfferController extends Controller implements Observeur {

    @FXML
    public ScrollPane scrollPaneContent;

    @Override
    public void refresh() {

        Offer offer = ApplicationContext.getInstance().getAcceptOffer();
        List<Query> queries = MyOffersModel.getQueriesByOffer(offer);

        if(queries.size() == 0){
            try {
                ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        VBox vbox = new VBox();

        for (Query query : queries) {
            if (!query.isAccepted()) {
                HBox hbox = new HBox();

                Label label = new Label(query.getUser().getFirstName() + " " + query.getUser().getLastName());
                Button accept = new Button("Accepter");
                accept.setOnAction(event -> {
                    query.setAccepted(true);
                    DatabaseHandler.getInstance().getDbManager().updateEntity(query);

                    //Date today

                    Notification notification = new Notification("Demande acceptée", query.getUser(), false, "once", new Date());
                    refresh();
                });

                Button refuse = new Button("Refuser");
                refuse.setOnAction(event -> {
                    DatabaseHandler.getInstance().getDbManager().deleteEntity(query);
                    refresh();
                });

                hbox.getChildren().addAll(label, accept, refuse);
                vbox.getChildren().add(hbox);
            }
        }

        scrollPaneContent.setContent(vbox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }

    @Override
    public void update() {

    }
}
