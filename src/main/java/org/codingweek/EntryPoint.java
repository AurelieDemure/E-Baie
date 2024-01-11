package org.codingweek;

import javafx.application.Application;
import javafx.stage.Stage;
import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.User;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.Page;
import org.codingweek.view.ConnexionView;
import org.codingweek.view.MarketView;
import org.codingweek.view.OfferCreateView;

import java.io.IOException;

public class EntryPoint extends Application {

    private final static boolean isDev = true;

    private final static String emailDev = "a@a.aa";

    @Override
    public void start(Stage stage) throws IOException {
        ApplicationSettings.getInstance().setPrimaryStage(stage);
        ApplicationContext.getInstance().setPageType(Page.NONE);
        ApplicationContext.getInstance().setUser_authentified(null);
        stage.setTitle(Configuration.APP_TITLE);
        if (!isDev) {
            stage.setScene(new ConnexionView().loadScene());
        } else {
            ApplicationContext.getInstance().setUser_authentified(DatabaseHandler.getInstance().getDbManager().getEntity(User.class, emailDev));
            stage.setScene(new MarketView().loadScene());
        }

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