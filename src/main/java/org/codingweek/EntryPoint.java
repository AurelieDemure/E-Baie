package org.codingweek;

import javafx.application.Application;
import javafx.stage.Stage;

import org.codingweek.model.Page;
import org.codingweek.view.*;

import java.io.IOException;

public class EntryPoint extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ApplicationSettings.getInstance().setPrimaryStage(stage);
        ApplicationContext.getInstance().setPageType(Page.NONE);
        stage.setTitle(Configuration.APP_TITLE);
        stage.setScene(new AccountView().loadScene());
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
            launch();
        }
}