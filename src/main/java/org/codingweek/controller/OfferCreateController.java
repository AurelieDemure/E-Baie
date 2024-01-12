package org.codingweek.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.codingweek.ApplicationContext;
import org.codingweek.ApplicationSettings;
import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.Query;
import org.codingweek.db.entity.User;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.ImageHandler;
import org.codingweek.model.InputFieldValidator;
import org.codingweek.model.Page;
import org.codingweek.model.filter.Frequency;
import org.codingweek.model.filter.OfferType;
import org.codingweek.view.MarketView;
import org.codingweek.view.MyOffersView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OfferCreateController extends Controller implements Observeur {

    public TextField title;
    public TextArea description;
    public Label errorNotDouble;
    public TextField price;
    public ChoiceBox type_offer;
    public ChoiceBox frequency;
    public TextField localization;

    public String path;
    public Label errorFillAll;
    public ImageView path_offer;

    @Override
    public void refresh() {
        ApplicationContext.getInstance().setPageType(Page.OFFER);
        toggleErrorFillAll(false);
        toggleErrorNotDouble(false);

        type_offer.getItems().clear();
        frequency.getItems().clear();


        type_offer.getItems().addAll(OfferType.LOAN.getValue(), OfferType.SERVICE.getValue());
        frequency.getItems().addAll("Tout type de frequence", "Unique", "Journalier", "Hebdomadaire", "Mensuelle", "Annuelle");
    }


    private void toggleErrorFillAll(boolean visible) {
        errorFillAll.setVisible(visible);
        errorFillAll.setManaged(visible);
    }

    private void toggleErrorNotDouble(boolean visible) {
        errorNotDouble.setVisible(visible);
        errorNotDouble.setManaged(visible);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseHandler.getInstance().getDbManager().addObserveur(this);
        refresh();
    }

    @Override
    public void update() {
        refresh();
    }

    public void saveModifiedOffer(ActionEvent actionEvent) {
        if (title.getText().isEmpty() || description.getText().isEmpty() || price.getText().isEmpty() || type_offer.getValue() == null || frequency.getValue() == null || localization.getText().isEmpty()) {
                errorFillAll.setText("Veuillez remplir tous les champs");
                toggleErrorFillAll(true);
                return;
        }
        if (!InputFieldValidator.validAdress(localization.getText()) && localization.getText() != null) {
            if (!localization.getText().isEmpty()) {
                errorFillAll.setText("Adresse non reconnue");
                toggleErrorFillAll(true);
                return;
            }
        } else {
            Double prices;
            try {
                prices = Double.parseDouble(price.getText());
            } catch (Exception e) {
                toggleErrorNotDouble(true);
                return;
            }
            DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
            User user = ApplicationContext.getInstance().getUser_authentified();
            Offer offer = new Offer(title.getText(), description.getText(), user, prices, OfferType.fromString(type_offer.getValue().toString()), Frequency.fromString(frequency.getValue().toString()), localization.getText(), path);
            db.saveEntity(offer);
            assert db.getEntity(Offer.class, offer.getId()) != null;

            ApplicationContext.getInstance().setPageType(Page.OFFER);
            try {
                ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showConfirmationAddDialog(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Etes vous sur ?");
        alert.setContentText("Voulez vous vraiment annuler cette offre ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                ApplicationContext.getInstance().setPageType(Page.OFFER);
                try {
                    ApplicationSettings.getInstance().setCurrentScene(new MyOffersView().loadScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void selectImage(ActionEvent actionEvent) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        File file = ImageHandler.openModalFile();
        String path = file.getAbsolutePath();
        this.path = path;
        path_offer.setImage(new Image(new File(path).toURI().toString()));
    }

}
