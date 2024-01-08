package org.codingweek;

import javafx.scene.Scene;
import javafx.stage.Stage;

/** singleton class to store application settings
 * in link with javaFX implementation */
public class ApplicationSettings {

    private static ApplicationSettings instance;

    private Scene currentScene;

    private Stage primaryStage;


    private ApplicationSettings() {

    }

    public static ApplicationSettings getInstance() {
        if (instance == null) {
            instance = new ApplicationSettings();
        }
        return instance;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
        primaryStage.setScene(currentScene);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

}
