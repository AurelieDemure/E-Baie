package org.codingweek;

import javafx.application.Application;
import javafx.stage.Stage;
import org.codingweek.model.Page;
import org.codingweek.view.AccountView;
import org.codingweek.view.ConnexionView;
import org.codingweek.view.MainView;
import java.io.IOException;

public class EntryPoint extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ApplicationSettings.getInstance().setPrimaryStage(stage);
        ApplicationContext.getInstance().setPageType(Page.ACCOUNT);
        stage.setTitle(Configuration.APP_TITLE);
        stage.setScene(new AccountView().loadScene());
        stage.show();
    }

    public static void main(String[] args) {
            launch();
        }
}