package ae.model;

import ae.HovedApplikasjon;
import ae.controller.*;
import javafx.fxml.FXML;
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


    // < ------------------------------------ KUNDER ------------------------------------ >
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

    // < ------------------------------------ SKADEMELDING ------------------------------------ >
    /**
     * Åpner skademeldingoversikten når bruker trykker på skademelding i menylinjen
     */
    public static void visSkademeldingOversikt(HovedApplikasjon hovedApplikasjon) {
        try {
            // Last inn kundeoversikten fra fxml-fil.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HovedApplikasjon.class.getResource("/view/SkademeldingView.fxml"));
            AnchorPane skademeldingOversikt = (AnchorPane) loader.load();

            // Plasser kundeoversikten i senter av rotoppsettet.
            hovedApplikasjon.getRotOppsett().setCenter(skademeldingOversikt);

            SkademeldingController skademeldingController = loader.getController();
            skademeldingController.setHovedApplikasjon(hovedApplikasjon);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Åpne Ny skademelding popup-vinduet.
     */
    public static boolean visNySkademeldingPopup(HovedApplikasjon hovedApplikasjon, Skademelding skademelding) {
        try {
            FXMLLoader loader = hentSkademeldingRedigerPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Registrer ny skademelding");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedApplikasjon.getHovedStage());
            Scene scene = new Scene(side);
            popupStage.setScene(scene);

            SkademeldingRedigerPopupController skademeldingRedigerPopupController = loader.getController();
            skademeldingRedigerPopupController.setPopupStage(popupStage);
            skademeldingRedigerPopupController.setSkademeldingÅRedigere(skademelding);

            popupStage.showAndWait();

            return skademeldingRedigerPopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Rediger eksisterende skademelding popup-vinduet.
     */
    public static boolean visRedigerSkademeldingPopup(HovedApplikasjon hovedApplikasjon, Skademelding skademelding) {
        try {
            FXMLLoader loader = hentSkademeldingRedigerPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Rediger eksisterende skademelding");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedApplikasjon.getHovedStage());
            Scene scene = new Scene(side);
            popupStage.setScene(scene);

            SkademeldingRedigerPopupController skademeldingRedigerPopupController = loader.getController();
            skademeldingRedigerPopupController.setPopupStage(popupStage);
            skademeldingRedigerPopupController.setSkademeldingÅRedigere(skademelding);

            popupStage.showAndWait();

            return skademeldingRedigerPopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // < ------------------------------------ FORSIKRING ------------------------------------ >

    // Åpner Oversikt - Forsikring når bruker trykker på Forsikring i menylinjen
    public static void visForsikringOversikt(HovedApplikasjon hovedApplikasjon) {
        try {
            // Last inn kundeoversikten fra fxml-fil.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HovedApplikasjon.class.getResource("/view/ForsikringView.fxml"));
            AnchorPane forsikringOversikt = (AnchorPane) loader.load();

            // Plasser kundeoversikten i senter av rotoppsettet.
            hovedApplikasjon.getRotOppsett().setCenter(forsikringOversikt);

            ForsikringController forsikringController = loader.getController();
            forsikringController.setHovedApplikasjon(hovedApplikasjon);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Åpner Båtforsikring-popup når bruker trykker på Ny båtforsikring-knappen
    public static boolean visNyBåtforsikringPopup(HovedApplikasjon hovedApplikasjon, Båtforsikring båtforsikring) {
        try {
            FXMLLoader loader = hentBåtforsikringPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Registrer ny båtforsikring");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedApplikasjon.getHovedStage());
            Scene scene = new Scene(side);
            popupStage.setScene(scene);

            ForsikringBåtPopupController forsikringBåtPopupController = loader.getController();
            forsikringBåtPopupController.setPopupStage(popupStage);
            forsikringBåtPopupController.setBåtforsikringÅRedigere(båtforsikring);
            forsikringBåtPopupController.setHovedApplikasjon(hovedApplikasjon);

            popupStage.showAndWait();

            return forsikringBåtPopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Åpner Båtforsikring-popup når bruker trykker på Rediger-knappen og valgt Forsikring er av type Båtforsikring
    public static boolean visRedigerBåtforsikringPopup(HovedApplikasjon hovedApplikasjon, Båtforsikring båtforsikring) {
        try {
            FXMLLoader loader = hentBåtforsikringPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Rediger eksisterende båtforsikring");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedApplikasjon.getHovedStage());
            Scene scene = new Scene(side);
            popupStage.setScene(scene);

            ForsikringBåtPopupController forsikringBåtPopupController = loader.getController();
            forsikringBåtPopupController.setPopupStage(popupStage);
            forsikringBåtPopupController.setBåtforsikringÅRedigere(båtforsikring);
            forsikringBåtPopupController.setHovedApplikasjon(hovedApplikasjon);

            popupStage.showAndWait();

            return forsikringBåtPopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // < ------------------------------------ FELLES LOADERE ------------------------------------ >
    // for KUNDE
    private static FXMLLoader hentKundeRedigerPopup() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HovedApplikasjon.class.getResource("/view/KundeRedigerPopupView.fxml"));
        return loader;
    }
    // for SKADEMELDING
    private static FXMLLoader hentSkademeldingRedigerPopup() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HovedApplikasjon.class.getResource("/view/SkademeldingRedigerPopupView.fxml"));
        return loader;
    }

    // for FORSIKRING
    private static FXMLLoader hentBåtforsikringPopup() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HovedApplikasjon.class.getResource("/view/ForsikringBåtPopup.fxml"));
        return loader;
    }
}
