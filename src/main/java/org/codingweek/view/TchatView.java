package org.codingweek.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.codingweek.Configuration;

import java.io.IOException;

public class TchatView implements LoaderFXML {

    @Override
    public Scene loadScene() throws IOException {
        return new Scene(new FXMLLoader(TchatView.class.getResource("/org/codingweek/fxml/tchat.fxml")).load(),
                Configuration.APP_WIDTH,
                Configuration.APP_HEIGHT);
    }

}
