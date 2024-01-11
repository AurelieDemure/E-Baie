package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.Query;
import org.codingweek.model.ImageHandler;
import org.codingweek.model.OfferModalModel;
import org.codingweek.model.OfferModifModel;
import org.codingweek.model.Page;
import org.codingweek.view.MyOffersView;
import org.codingweek.view.OfferModifView;
import org.codingweek.view.TchatView;

import javax.swing.plaf.basic.BasicButtonUI;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class OfferModalController extends Controller implements Observeur{
    public ImageView OfferImage;
    public Label OffreTitle;
    public Label OfferDescription;
    public Label OfferPrice;
    public Label OfferTypeServ;
    public Label OfferFrequency;
    public Label OfferBook;

    public List<Query> OfferBooks = new ArrayList<>();
    public VBox scrollfield;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);

    private Integer indexModify = -1;

    @Override
    public void refresh() {

    }

    @Override
    public void update() {
        Offer offer = OfferModalModel.getMyOfferToModify(ApplicationContext.getInstance().getOfferId());
        indexModify = offer.getId();
        OffreTitle.setText(offer.getTitle());
        OfferPrice.setText(String.valueOf(offer.getPrice()));
        OfferFrequency.setText(offer.getFrequency().getValue());
        OfferDescription.setText(offer.getDescription());
        OfferTypeServ.setText(offer.getTypeOffer().getValue());
        OfferImage.setImage(ImageHandler.getImage(offer.getPath()));
        this.OfferBooks = offer.getAllAcceptedQueries();
        OfferBooks = OfferBooks.stream().sorted(Comparator
                .comparing(Query::getDateBegin))
                .collect(Collectors.toList());
        this.scrollfield.getChildren().clear();
        Pane pane;

        for (Query query : this.OfferBooks) {
            pane = makePaneQuery(query);
            this.scrollfield.getChildren().add(pane);
        }
    }

    private Pane makePaneQuery(Query query) {


        Label beginDate = new Label(query.getDateBegin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(
                formatter
        ));
        beginDate.setLayoutX(0);
        Label endDate = new Label(query.getDateEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(
                formatter
        ));
        endDate.setLayoutX(100);
        Label user = new Label(query.getUser().getFirstName() + " " + query.getUser().getLastName());
        user.setLayoutX(200);

        Button contactUser = new Button("Contacter l'acheteur");
        contactUser.setLayoutX(300);
        contactUser.getStyleClass().add("buttonDisconnect");
        contactUser.setOnAction(e -> {
            ApplicationContext.getInstance().setContactUser(query.getUser());
            ApplicationContext.getInstance().setPageType(Page.MESSAGE);
            try {
                ApplicationSettings.getInstance().setCurrentScene(new TchatView().loadScene());
            } catch (IOException event ) {
                throw new RuntimeException(event);
            }
        });

        Pane pane = new AnchorPane();
        pane.setMaxHeight(200);
        pane.getChildren().addAll(beginDate, endDate, user, contactUser);
        return  pane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        update();
        ApplicationContext.getInstance().setOfferId(null);
    }

    public void goToModifyOffer(ActionEvent event) {
        ApplicationContext.getInstance().setOfferId(this.indexModify);
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new OfferModifView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnToMyOffers(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void hideExpired(ActionEvent event) {
        Offer offer = OfferModalModel.getMyOfferToModify(ApplicationContext.getInstance().getOfferId());
        this.OfferBooks = offer.getAllAcceptedQueries();
        OfferBooks = OfferBooks.stream().sorted(Comparator
                        .comparing(Query::getDateBegin))
                .collect(Collectors.toList());
        this.scrollfield.getChildren().clear();
        Pane pane;

        for (Query query : this.OfferBooks) {
            Date d = new Date();
            LocalDateTime l = LocalDateTime.now().ofInstant(d.toInstant(), ZoneId.systemDefault());
            Date compare = Date.from(l.atZone(ZoneId.systemDefault()).toInstant());
            if (query.getDateEnd().compareTo(compare) == 1) {
                OfferBooks.remove(query);
            }
        }

        for (Query q : this.OfferBooks) {
            pane = makePaneQuery(q);
            this.scrollfield.getChildren().add(pane);
        }
    }
}
