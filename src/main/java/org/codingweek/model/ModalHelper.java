package org.codingweek.model;

import javafx.scene.control.Alert;

public class ModalHelper {

    /** Create a information modal with the given message
     * @param message message to display
     */
    public static void showInformationModal(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Show and wait for the user to close the modal
        alert.showAndWait();
    }
}
