package org.codingweek.model;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.codingweek.ApplicationContext;

import java.io.File;
import java.util.Objects;

public class ImageHandler {

    /** Load a static path image */
    public static Image getImage(String name) {
        try {
            if (!new File(name).exists())
                throw new Exception();
            return new Image(new File(name).toURI().toString());
        } catch (Exception e) {
            return new Image(
                    Objects.requireNonNull(ApplicationContext.class
                            .getResourceAsStream("/org/codingweek/img/notfoundimg.jpg")));
        }
    }

    public static File openModalFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir l'image");

        // Add filters to restrict the file types
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        // Show the FileChooser and get the selected file
        return fileChooser.showOpenDialog(null);
    }
}
