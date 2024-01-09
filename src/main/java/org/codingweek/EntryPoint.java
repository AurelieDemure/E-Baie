package org.codingweek;

import javafx.application.Application;
import javafx.stage.Stage;
import org.codingweek.db.DatabaseManager;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.Page;
import org.codingweek.view.ConnexionView;
import org.codingweek.view.OfferCreateView;

import java.io.IOException;

public class EntryPoint extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ApplicationSettings.getInstance().setPrimaryStage(stage);
        ApplicationContext.getInstance().setPageType(Page.NONE);
        ApplicationContext.getInstance().setUser_authentified(null);
        stage.setTitle(Configuration.APP_TITLE);
        stage.setScene(new ConnexionView().loadScene());
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(e -> {
            System.out.println("Fermeture de l'application");
            DatabaseHandler.getInstance().getDbManager().tearDown();
            System.exit(0);
        });
    }

    public static void main(String[] args) {

        DatabaseManager dbManager = new DatabaseManager();
        dbManager.setup();

        launch();
        }
}