package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Notification;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.Query;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.MyOffersModel;
import org.codingweek.model.Page;
import org.codingweek.view.MyOffersView;
import org.codingweek.view.TchatView;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AcceptOfferController extends Controller implements Observeur {

    @FXML
    public GridPane content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //DatabaseHandler.getInstance().getDbManager().addObserveur(this);
        refresh();
    }

    @Override
    public void update() {
        //refresh();
    }

    @Override
    public void refresh() {
        content.getChildren().clear();

        Offer offer = ApplicationContext.getInstance().getAcceptOffer();
        List<Query> queries = MyOffersModel.getNotAcceptedQueriesByOffer(offer);

        if(queries.size() == 0){
            try {
                ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        content.getStyleClass().add("gridpane");

        ColumnConstraints defaultColumnConstraints = new ColumnConstraints();
        defaultColumnConstraints.setPrefWidth(120);
        defaultColumnConstraints.setPrefWidth(120);
        defaultColumnConstraints.setPrefWidth(120);
        defaultColumnConstraints.setPrefWidth(120);

        content.getColumnConstraints().add(defaultColumnConstraints);

        for (Query query : queries) {
            Label firstName = new Label(query.getUser().getFirstName());
            Label lastName = new Label(query.getUser().getLastName());

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            Date beginDate = query.getDateBegin();
            Date endDate = query.getDateEnd();

            String formattedDate = sdf.format(beginDate);
            String formattedDate2 = sdf.format(endDate);

            Label beginDateLbl = new Label("Du " + formattedDate);
            Label endDateLbl = new Label("au " + formattedDate2);

            Button acceptBtn = new Button("Accepter");
            acceptBtn.getStyleClass().add("button_accept");
            acceptBtn.setOnAction(event -> {
                query.acceptQuery();
            });

            Button refuse = new Button("Refuser");
            refuse.getStyleClass().add("button_decline");
            refuse.setOnAction(event -> {
                query.refuseQuery();
            });

            Button contact = new Button("Contact");
            contact.getStyleClass().add("button_contact");
            contact.setOnAction(event -> {
                ApplicationContext.getInstance().setContactUser(query.getUser());
                ApplicationContext.getInstance().setPageType(Page.MESSAGE);
                try {
                    ApplicationSettings.getInstance().setCurrentScene(new TchatView().loadScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


            content.add(firstName, 0, queries.indexOf(query));
            content.add(lastName, 1, queries.indexOf(query));
            content.add(beginDateLbl, 2, queries.indexOf(query));
            content.add(endDateLbl, 3, queries.indexOf(query));
            content.add(acceptBtn, 4, queries.indexOf(query));
            content.add(refuse, 5, queries.indexOf(query));
            content.add(contact, 6, queries.indexOf(query));
        }
    }
    @FXML
    public void goBack(ActionEvent actionEvent) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
