package org.codingweek.controller;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.codingweek.*;
import org.codingweek.db.*;
import org.codingweek.db.entity.*;
import org.codingweek.model.*;
import org.codingweek.view.MarketView;
import org.codingweek.view.MyOffersView;
import org.codingweek.view.TchatView;

import java.io.IOException;
import java.net.*;
import java.time.LocalDate;
import java.util.*;
import java.time.format.*;

public class OfferMarketController extends Controller implements Observeur{

    public Offer offer;
    @FXML
    private Label noteLabel;
    public ImageView OfferImage;
    public Label OffreTitle;
    public Label OfferDescription;
    public Label OfferPrice;
    public Label OfferTypeServ;
    public Label OfferFrequency;
    public Label OfferLoc;
    @FXML
    public DatePicker dateBegin;
    public DatePicker dateEnd;
    public Label offerAuthor;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
    
    private List<Query> queries;

    @FXML
    private void showConfirmationAddDialog() {

        if (this.dateBegin.getValue() != null && this.dateEnd.getValue() != null) {
            Date dateBegin = Date.from(this.dateBegin.getValue().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
            Date dateEnd = Date.from(this.dateEnd.getValue().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
            Query query = new Query(this.offer, ApplicationContext.getInstance().getUser_authentified(), false, 0, dateBegin, dateEnd);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes vous sur ?");
            alert.setContentText("Voulez vous vraiment réserver cette offre ?");

            alert.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
                    db.saveEntity(query);

                    ApplicationContext.getInstance().setPageType(Page.MARKET);

                    DatabaseHandler.getInstance().getDbManager().saveEntity(
                            new Notification("Demande de réservation", this.offer.getOwner(), false, "Demande de réservation", new Date())
                    );

                    try {
                        ApplicationSettings.getInstance().setCurrentScene(new MarketView().loadScene());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    @Override
    public void refresh() {

        
    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        StringConverter<LocalDate> converter = new LocalDateStringConverter(formatter, formatter);
        dateBegin.setEditable(false);
        dateEnd.setEditable(false);
        dateEnd.setConverter(converter);
        dateBegin.setConverter(converter);
        this.offer = OfferMarketModel.getOffer(ApplicationContext.getInstance().getIndex());
        ApplicationContext.getInstance().setIndex(null);
        this.noteLabel.setText("5/5");
        this.OfferImage.setImage(ImageHandler.getImage(this.offer.getPath())); 
        this.offerAuthor.setText(this.offer.getOwner().getFirstName() + " " + this.offer.getOwner().getLastName());
        this.OffreTitle.setText(this.offer.getTitle());
        this.OfferPrice.setText(this.offer.getPrice() + " florains");
        this.OfferTypeServ.setText(this.offer.getType());
        this.OfferFrequency.setText(this.offer.getFrequency().getValue());
        this.OfferDescription.setText(this.offer.getDescription());
        this.OfferLoc.setText(this.offer.getLocalization());
        this.queries = offer.getQueries();
        this.dateBegin.setDayCellFactory(InputFieldValidator.getDateBeginCellFactory(this.dateEnd.getValue(), this.queries));
        this.dateEnd.setDayCellFactory(InputFieldValidator.getDateBeginCellFactory(this.dateBegin.getValue(), this.queries));
    }

    @FXML
    void selectDateBegin(ActionEvent event) {
        this.dateEnd.setDayCellFactory(InputFieldValidator.getDateEndCellFactory(this.dateBegin.getValue(), this.queries));
    }

    @FXML
    void selectDateEnd(ActionEvent event) {
        this.dateBegin.setDayCellFactory(InputFieldValidator.getDateBeginCellFactory(this.dateEnd.getValue(), this.queries));
    }

    public void contactAuthor(ActionEvent actionEvent) {
        ApplicationContext.getInstance().setContactUser(offer.getOwner());
        ApplicationContext.getInstance().setPageType(Page.MESSAGE);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new TchatView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOffer(Offer offer) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        db.deleteEntity(offer);
    }

    public void setOffer(Offer offer){
        this.offer = offer;
    }

    public void returnMarket(ActionEvent event) {
        ApplicationContext.getInstance().setPageType(Page.MARKET);
        try {
            ApplicationSettings.getInstance().setCurrentScene(new MarketView().loadScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
