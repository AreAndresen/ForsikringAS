package ae.util;

import javafx.scene.control.Alert;

public class AlertHandler {
    public static void genererWarningAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public static void genererUgyldigInputAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Feilmelding");
        alert.setHeaderText("Vennligst rett opp felter");
        alert.setContentText(content);

        alert.showAndWait();
    }
}
