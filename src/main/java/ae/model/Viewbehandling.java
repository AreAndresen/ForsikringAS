package ae.model;

import ae.HovedApplikasjon;
import ae.controller.KundeController;
import ae.controller.KundeRedigerPopupController;
import ae.controller.RotOppsettController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Viewbehandling {

    /**
     * Initierer rotoppsettet, kjøres i start-metoden.
     */
    public static void initierRotOppsett(HovedApplikasjon hovedApplikasjon) {
        try {
            // Last inn fxml-fil.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HovedApplikasjon.class.getResource("/view/RotOppsett.fxml"));
            hovedApplikasjon.setRotOppsett((BorderPane) loader.load());

            // Vis scenen som inneholder rotoppsettet.
            Scene scene = new Scene(hovedApplikasjon.getRotOppsett());
            hovedApplikasjon.getHovedStage().setScene(scene);
            hovedApplikasjon.getHovedStage().show();

            // Overfør hovedapplikasjonen til rot-controlleren.
            RotOppsettController rotOppsettController = loader.getController();
            rotOppsettController.setHovedApplikasjon(hovedApplikasjon);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Åpner kundeoversikten når bruker trykker på Kunder i menylinjen
     */
    public static void visKundeOversikt(HovedApplikasjon hovedApplikasjon) {
        try {
            // Last inn kundeoversikten fra fxml-fil.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HovedApplikasjon.class.getResource("/view/KundeView.fxml"));
            AnchorPane kundeOversikt = (AnchorPane) loader.load();

            // Plasser kundeoversikten i senter av rotoppsettet.
            hovedApplikasjon.getRotOppsett().setCenter(kundeOversikt);

            KundeController kundeController = loader.getController();
            kundeController.setHovedApplikasjon(hovedApplikasjon);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Åpne Ny kunde popup-vinduet.
     */
    public static boolean visNyKundePopup(HovedApplikasjon hovedApplikasjon, Kunde kunde) {
        try {
            FXMLLoader loader = hentKundeRedigerPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Registrer ny kunde");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedApplikasjon.getHovedStage());
            Scene scene = new Scene(side);
            popupStage.setScene(scene);

            KundeRedigerPopupController kundeRedigerPopupController = loader.getController();
            kundeRedigerPopupController.setPopupStage(popupStage);
            kundeRedigerPopupController.setKundeÅRedigere(kunde);

            popupStage.showAndWait();

            return kundeRedigerPopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Rediger eksisterende kunde popup-vinduet.
     */
    public static boolean visRedigerKundePopup(HovedApplikasjon hovedApplikasjon, Kunde kunde) {
        try {
            FXMLLoader loader = hentKundeRedigerPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Rediger eksisterende kunde");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedApplikasjon.getHovedStage());
            Scene scene = new Scene(side);
            popupStage.setScene(scene);

            KundeRedigerPopupController kundeRedigerPopupController = loader.getController();
            kundeRedigerPopupController.setPopupStage(popupStage);
            kundeRedigerPopupController.setKundeÅRedigere(kunde);

            popupStage.showAndWait();

            return kundeRedigerPopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Brukes i Ny og Rediger popup for å hente det samme vinduet.
     */
    private static FXMLLoader hentKundeRedigerPopup() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HovedApplikasjon.class.getResource("/view/KundeRedigerPopupView.fxml"));
        return loader;
    }
}
