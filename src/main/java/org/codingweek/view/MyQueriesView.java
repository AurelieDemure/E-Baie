package org.codingweek.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.codingweek.Configuration;

import java.io.IOException;

public class MyQueriesView implements LoaderFXML{
    public Scene loadScene() throws IOException {
        return new Scene(new FXMLLoader(MyOffersView.class.getResource("/org/codingweek/fxml/myqueries.fxml")).load(),
                Configuration.APP_WIDTH,
                Configuration.APP_HEIGHT);
    }
}
