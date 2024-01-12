package org.codingweek.controller;

import javafx.scene.image.*;
import org.codingweek.*;
import org.codingweek.db.entity.*;
import org.codingweek.model.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import java.time.format.*;
import java.time.*;
import org.codingweek.view.*;



//import javax.swing.text.html.ImageView;

public class NavbarController extends Controller implements Observeur{

    @FXML
    private VBox notifList;

    public Button deconnexion;
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
    private Label notifLabel;

    @FXML
    private ScrollPane notifBox;

    @FXML
    private Pane notifFond;

    private List<Notification> notifications;

    private int count = 0;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);

    private final StringConverter<LocalDate> converter = new LocalDateStringConverter(formatter, formatter);

    @FXML
    void clickLogo(MouseEvent event) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MarketView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();  
    }


    @FXML
    void clickAccount(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new AccountView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();
    }

    @FXML
    void clickMarket(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MarketView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();
    }

    @FXML
    void clickMessage(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.MESSAGE);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new TchatView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();
    }

    @FXML
    void clickOffer(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseHandler.getInstance().getDbManager().addObserveur(this);
        notifExited();
        setNotifs();
        ImageView deco = new ImageView(new Image(
                Objects.requireNonNull(ApplicationContext.class
                        .getResourceAsStream("/org/codingweek/img/doorDisconnect.png"))
        ));
        deco.setFitWidth(40);
        deco.setFitHeight(40);
        deconnexion.setGraphic(deco);
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
    }

    @Override
    public void refresh() {
        setNotifs();
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

    public void Disconnect(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.NONE);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new ConnexionView().loadScene());
            ApplicationContext.getInstance().setUser_authentified(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void setNotifs(){
        this.notifications = ApplicationContext.getInstance().getUser_authentified().getNotifications();
        if (this.notifications == null) {
            this.notifications = new ArrayList<>();
        }
        this.count = 0;        
        for(Notification notif : this.notifications) {
            if (!notif.getSeen()) {
                this.count++;
            }
        }
        if (this.count > 99) {
            this.notifLabel.setText("99+");
        } else if (this.count == 0) {
            this.notifLabel.setVisible(false);
        } else {
            this.notifLabel.setText("" + this.count);
        }

        this.notifList.getChildren().clear();
        if (count == 0) {
            Label noNotif = new Label("Pas de notification");
            noNotif.getStyleClass().add("noNotif");
            this.notifList.getChildren().add(noNotif);
        }
        else {
            for (Notification notif : this.notifications) {
                if (!notif.getSeen()) {
                    this.notifList.getChildren().add(makeNotif(notif));
                }
            }
            
        }
    }

    @FXML
    void clickNotifEnter(MouseEvent event) {
        notifEnter();
    }

    @FXML
    void clickNotifExited(MouseEvent event) {
        notifExited();
    }

    void notifEnter() {
        this.notifBox.setVisible(true);
        this.notifFond.setVisible(true);
        setNotifs();
    }

    void notifExited() {
        this.notifBox.setVisible(false);
        this.notifFond.setVisible(false);
    }

    public Pane makeNotif(Notification notif){

        Label typeLabel = new Label(notif.getType());
        typeLabel.getStyleClass().add("title");

        LocalDate date = notif.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Label dateLabel = new Label(date.toString());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(typeLabel, dateLabel);

        Pane pane = new Pane();
        pane.setMinSize(200, 60);
        pane.getStyleClass().add("notif");
        pane.getChildren().add(vbox);
        pane.setOnMouseClicked(event -> clickNotif(event, notif));
        return pane;
    }

    @FXML
    void clickNotif(MouseEvent event, Notification notif) {
        notif.setSeen(true);
        DatabaseHandler.getInstance().getDbManager().updateEntity(notif);
        setNotifs();
    }
}
