package ae.controller.util;

import javafx.scene.control.Alert;


public class UgyldigInputHandler {
    public static void generateAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Feil i kundedata");
        alert.setHeaderText("Feil i kundedata");
        alert.setContentText(msg);

        alert.showAndWait();
    }
}

