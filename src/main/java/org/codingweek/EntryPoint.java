package org.codingweek;

import javafx.application.Application;
import javafx.stage.Stage;
import org.codingweek.db.DatabaseManager;
import org.codingweek.model.Offer;
import org.codingweek.model.User;
import org.codingweek.model.Page;
import org.codingweek.view.*;

import java.io.IOException;

public class EntryPoint extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ApplicationSettings.getInstance().setPrimaryStage(stage);
        ApplicationContext.getInstance().setPageType(Page.NONE);
        ApplicationContext.getInstance().setUser_authentified(null);
        stage.setTitle(Configuration.APP_TITLE);
        stage.setScene(new AccountView().loadScene());
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

        DatabaseManager dbManager = new DatabaseManager();
        dbManager.setup();

        launch();
        }
}