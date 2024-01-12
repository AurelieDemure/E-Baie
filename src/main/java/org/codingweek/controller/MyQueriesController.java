package org.codingweek.controller;

import java.io.*;
import java.net.*;
import java.util.*;
import org.codingweek.*;
import org.codingweek.db.entity.*;
import org.codingweek.model.*;
import org.codingweek.view.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import java.time.format.*;
import java.time.*;

public class MyQueriesController extends Controller implements Observeur {

    private List<Query> myQueries = new ArrayList<>();

    @FXML
    private VBox scrollField;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);

    private final StringConverter<LocalDate> converter = new LocalDateStringConverter(formatter, formatter);

    @FXML
    void sendToMyOffers(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        this.myQueries.clear();
        User user = ApplicationContext.getInstance().getUser_authentified();
        this.myQueries = MyQueriesModel.getMyQueries(user.getEmail());
        refresh();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void refresh() {
        this.scrollField.getChildren().clear();
        Pane pane;
        int x = 0;
        HBox hBox = new HBox();

        for (Query query : this.myQueries) {
            pane = makePaneQuery(query);
            hBox.getChildren().add(pane);
            x++;

            if (x == 3) {
                this.scrollField.getChildren().add(hBox);
                hBox = new HBox();
                x = 0;
            }
        }
        this.scrollField.getChildren().add(hBox);
    }

    public Pane makePaneQuery(Query query) {
        HBox notation = new HBox();
        ImageView starImage = new ImageView(new Image(Objects.requireNonNull(ApplicationContext.class.getResourceAsStream("/org/codingweek/img/star.png"))));
        starImage.setFitHeight(30);
        starImage.setFitWidth(30);
        starImage.setTranslateX(5);
        starImage.setTranslateY(-5);
        Label noteLabel = new Label();
        Integer note = query.getNotation() ;
        if (note != null) {
            noteLabel.setText(note + "/5");
        } else {
            noteLabel.setText("Ajouter une note");
            notation.getStyleClass().add("note");
            notation.setOnMouseClicked( (event -> notePopUp(event, query)));
        }
        notation.getChildren().addAll(noteLabel, starImage);
        notation.getStyleClass().add("margin");
        

        ImageView image = new ImageView();
        image.setImage(ImageHandler.getImage(query.getOffer().getPath()));
        image.setTranslateX(5);
        image.setTranslateY(5);
        image.setFitHeight(100);
        image.setFitWidth(100);

        Label titleLabel = new Label(query.getOffer().getTitle());
        titleLabel.getStyleClass().add("title");

        Label priceLabel = new Label(query.getOffer().getPrice() + " florains");
        priceLabel.getStyleClass().add("price");

        LocalDate date = query.getDate().toLocalDate();
        Label dateLabel = new Label(converter.toString(date));

        Label acceptedLabel;
        if (query.isAccepted()) {
            acceptedLabel = new Label("Acceptée");
        } else {
            acceptedLabel = new Label("Refusée");
        }
        acceptedLabel.getStyleClass().add("accepted");

        LocalDate dateBegin = query.getDateBegin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateEnd = query.getDateEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Label dateQueryLabel = new Label("de " + converter.toString(dateBegin) + " à " + converter.toString(dateEnd));

        Label ownerLabel = new Label("de : " + query.getOffer().getOwner().getFirstName() + " " + query.getOffer().getOwner().getLastName());

        Button contact = new Button("Contacter l'auteur");
        contact.getStyleClass().add("buttonDisconnect");
        contact.setOnAction(e -> contactAuthor(e, query));

        VBox vbox1 = new VBox(notation, image);
        vbox1.getStyleClass().add("margin");

        VBox vbox2 = new VBox(titleLabel, priceLabel, dateLabel, acceptedLabel, dateQueryLabel, ownerLabel, contact);
        vbox2.getStyleClass().add("margin");

        HBox hbox = new HBox(vbox1, vbox2);

        Pane pane = new Pane(hbox);
        pane.getStyleClass().add("query");
        pane.setMinSize(10, 10);
        pane.setOnMouseClicked(e -> {
            goToOffer(query.getOffer());
        });
        return pane;
    }
    
    public void contactAuthor(ActionEvent actionEvent, Query query) {
        ApplicationContext.getInstance().setContactUser(query.getOffer().getOwner());
        ApplicationContext.getInstance().setPageType(Page.MESSAGE);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new TchatView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToOffer(Offer offer){
        try {
            ApplicationContext.getInstance().setIndex(offer.getId());
            ApplicationSettings.getInstance().setCurrentScene(new OfferMarketView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void notePopUp(MouseEvent event, Query query) {
        Integer oldNote = query.getNotation();
        ToggleGroup note = new ToggleGroup();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().applyCss();
        Node graphic = alert.getDialogPane().getGraphic();
        alert.setDialogPane(new DialogPane() {
            @Override
            protected Node createDetailsButton() {
                VBox vbox = new VBox();
                RadioButton note0 = new RadioButton();
                note0.setText("0");
                note0.setToggleGroup(note);
                note0.setOnAction(e -> query.setNotation(0));
                RadioButton note1 = new RadioButton();
                note1.setText("1");
                note1.setToggleGroup(note);
                note1.setOnAction(e -> query.setNotation(1));
                RadioButton note2 = new RadioButton();
                note2.setText("2");
                note2.setToggleGroup(note);
                note2.setOnAction(e -> query.setNotation(2));
                RadioButton note3 = new RadioButton();
                note3.setText("3");
                note3.setToggleGroup(note);
                note3.setOnAction(e -> query.setNotation(3));
                RadioButton note4 = new RadioButton();
                note4.setText("4");
                note4.setToggleGroup(note);
                note4.setOnAction(e -> query.setNotation(4));
                RadioButton note5 = new RadioButton();
                note5.setText("5");
                note5.setToggleGroup(note);
                note5.setOnAction(e -> query.setNotation(5));
                vbox.getChildren().addAll(note0, note1, note2, note3, note4, note5);
                return vbox;
            }
        });
        alert.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        alert.getDialogPane().setExpandableContent(new Group());
        alert.getDialogPane().setExpanded(true);
        alert.getDialogPane().setGraphic(graphic);
        alert.setTitle("Notation");
        alert.setHeaderText("Veuilliez attribuer une note à votre demande");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.APPLY) {
            DatabaseHandler.getInstance().getDbManager().updateEntity(query);
        } else {
            query.setNotation(oldNote);
        }        
    }
}
