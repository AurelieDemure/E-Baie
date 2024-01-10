package org.codingweek.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.codingweek.Configuration;

import java.io.IOException;
import java.sql.Connection;

public class OfferModifView implements LoaderFXML{
    public Scene loadScene() throws IOException {
        return new Scene(new FXMLLoader(OfferModifView.class.getResource("/org/codingweek/fxml/offerModif.fxml")).load(),
                Configuration.APP_WIDTH,
                Configuration.APP_HEIGHT);
    }
}
