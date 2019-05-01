package ae.model;

import ae.HovedApplikasjon;
import ae.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Viewbehandling {

    // <---------------- DATAFELTER FOR Å AKSESSERE BUTTONS SOM SKAL DISABLES UNDER THREAD ----------->
    private static KundeController kundeController;
    private static AnchorPane kundeOversikt;

    private static SkademeldingController skademeldingController;
    private static AnchorPane skademeldingOversikt;

    private static ForsikringController forsikringController;
    private static AnchorPane forsikringOversikt;

    public static KundeController getKundeController() {
        return kundeController;
    }
    public static SkademeldingController getSkademeldingController() {
        return skademeldingController;
    }
    public static ForsikringController getForsikringController() {
        return forsikringController;
    }

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

            setPaneOgControllere(hovedApplikasjon);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setPaneOgControllere(HovedApplikasjon hovedApplikasjon) {
        try {
            // kundeoversikten
            FXMLLoader kundeLoader = new FXMLLoader();
            kundeLoader.setLocation(HovedApplikasjon.class.getResource("/view/KundeView.fxml"));
            kundeOversikt = (AnchorPane) kundeLoader.load();
            kundeController = kundeLoader.getController();
            kundeController.setHovedApplikasjon(hovedApplikasjon);

            // skademeldingoversikten
            FXMLLoader skadeLoader = new FXMLLoader();
            skadeLoader.setLocation(HovedApplikasjon.class.getResource("/view/SkademeldingView.fxml"));
            skademeldingOversikt = (AnchorPane) skadeLoader.load();
            skademeldingController = skadeLoader.getController();
            skademeldingController.setHovedApplikasjon(hovedApplikasjon);

            // forsikringoversikten
            FXMLLoader forsikringLoader = new FXMLLoader();
            forsikringLoader.setLocation(HovedApplikasjon.class.getResource("/view/ForsikringView.fxml"));
            forsikringOversikt = (AnchorPane) forsikringLoader.load();
            forsikringController = forsikringLoader.getController();
            forsikringController.setHovedApplikasjon(hovedApplikasjon);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // < ------------------------------------ KUNDER ------------------------------------ >
    /**
     * Åpner kundeoversikten når bruker trykker på Kunder i menylinjen
     */
    public static void visKundeOversikt(HovedApplikasjon hovedApplikasjon) {
        hovedApplikasjon.getRotOppsett().setCenter(kundeOversikt);
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
            kundeRedigerPopupController.setHovedApplikasjon(hovedApplikasjon);

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
            kundeRedigerPopupController.setHovedApplikasjon(hovedApplikasjon);

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
        // Plasser kundeoversikten i senter av rotoppsettet.
        hovedApplikasjon.getRotOppsett().setCenter(skademeldingOversikt);
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
            skademeldingRedigerPopupController.setHovedApplikasjon(hovedApplikasjon);

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
            skademeldingRedigerPopupController.setHovedApplikasjon(hovedApplikasjon);

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
        // Plasser kundeoversikten i senter av rotoppsettet.
        hovedApplikasjon.getRotOppsett().setCenter(forsikringOversikt);
    }

    // Åpner BåtForsikring-popup når bruker trykker på Ny båtforsikring-knappen
    public static boolean visNyBåtforsikringPopup(HovedApplikasjon hovedApplikasjon, BåtForsikring båtforsikring) {
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
            forsikringBåtPopupController.setBåtForsikringÅRedigere(båtforsikring);
            forsikringBåtPopupController.setHovedApplikasjon(hovedApplikasjon);

            popupStage.showAndWait();

            return forsikringBåtPopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Åpner BåtForsikring-popup når bruker trykker på Rediger-knappen og valgt Forsikring er av type BåtForsikring
    public static boolean visRedigerBåtforsikringPopup(HovedApplikasjon hovedApplikasjon, BåtForsikring båtforsikring) {
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
            forsikringBåtPopupController.setBåtForsikringÅRedigere(båtforsikring);
            forsikringBåtPopupController.setHovedApplikasjon(hovedApplikasjon);

            popupStage.showAndWait();

            return forsikringBåtPopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Åpner BoligForsikring-popup når bruker trykker på Ny innboforsikring-knappen
    public static boolean visNyBoligforsikringPopup(HovedApplikasjon hovedApplikasjon, BoligForsikring boligForsikring) {
        try {
            FXMLLoader loader = hentBoligforsikringPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            if ("Hus- og innboforsikring".equals(boligForsikring.getType())) {
                popupStage.setTitle("Registrer ny hus- og innboforsikring");
            }
            if ("Fritidsboligforsikring".equals(boligForsikring.getType())) {
                popupStage.setTitle("Registrer ny fritidsboligforsikring");
            }
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedApplikasjon.getHovedStage());
            Scene scene = new Scene(side);
            popupStage.setScene(scene);

            ForsikringBoligPopupController forsikringBoligPopupController = loader.getController();
            forsikringBoligPopupController.setPopupStage(popupStage);
            forsikringBoligPopupController.setBoligForsikringÅRedigere(boligForsikring);
            forsikringBoligPopupController.setHovedApplikasjon(hovedApplikasjon);

            popupStage.showAndWait();

            return forsikringBoligPopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Åpner BoligForsikring-popup når bruker trykker på Rediger-knappen og valgt Forsikring er av type Hus- og innbo
    public static boolean visRedigerBoligforsikringPopup(HovedApplikasjon hovedApplikasjon,
                                                         BoligForsikring boligForsikring) {
        try {
            FXMLLoader loader = hentBoligforsikringPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            if ("Hus- og innboforsikring".equals(boligForsikring.getType())) {
                popupStage.setTitle("Rediger eksisterende hus- og innboforsikring");
            }
            if ("Fritidsboligforsikring".equals(boligForsikring.getType())) {
                popupStage.setTitle("Rediger eksisterende fritidsboligforsikring");
            }
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedApplikasjon.getHovedStage());
            Scene scene = new Scene(side);
            popupStage.setScene(scene);

            ForsikringBoligPopupController forsikringBoligPopupController = loader.getController();
            forsikringBoligPopupController.setPopupStage(popupStage);
            forsikringBoligPopupController.setBoligForsikringÅRedigere(boligForsikring);
            forsikringBoligPopupController.setHovedApplikasjon(hovedApplikasjon);

            popupStage.showAndWait();

            return forsikringBoligPopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Åpner ReiseForsikring-popup når bruker trykker på Ny reiseforsikring-knappen
    public static boolean visNyReiseforsikringPopup(HovedApplikasjon hovedApplikasjon, ReiseForsikring reiseforsikring) {
        try {
            FXMLLoader loader = hentReiseforsikringPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Registrer ny reiseforsikring");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedApplikasjon.getHovedStage());
            Scene scene = new Scene(side);
            popupStage.setScene(scene);

            ForsikringReisePopupController forsikringReisePopupController = loader.getController();
            forsikringReisePopupController.setPopupStage(popupStage);
            forsikringReisePopupController.setReiseForsikringÅRedigere(reiseforsikring);
            forsikringReisePopupController.setHovedApplikasjon(hovedApplikasjon);

            popupStage.showAndWait();

            return forsikringReisePopupController.erBekreftTrykket();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Åpner ReiseForsikring-popup når bruker trykker på Rediger-knappen og valgt Forsikring er av type ReiseForsikring
    public static boolean visRedigerReiseforsikringPopup(HovedApplikasjon hovedApplikasjon, ReiseForsikring reiseforsikring) {
        try {
            FXMLLoader loader = hentReiseforsikringPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Rediger eksisterende reiseforsikring");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedApplikasjon.getHovedStage());
            Scene scene = new Scene(side);
            popupStage.setScene(scene);

            ForsikringReisePopupController forsikringReisePopupController = loader.getController();
            forsikringReisePopupController.setPopupStage(popupStage);
            forsikringReisePopupController.setReiseForsikringÅRedigere(reiseforsikring);
            forsikringReisePopupController.setHovedApplikasjon(hovedApplikasjon);

            popupStage.showAndWait();

            return forsikringReisePopupController.erBekreftTrykket();
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
        loader.setLocation(HovedApplikasjon.class.getResource("/view/ForsikringBåtPopupView.fxml"));
        return loader;
    }
    private static FXMLLoader hentBoligforsikringPopup() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HovedApplikasjon.class.getResource("/view/ForsikringBoligPopupView.fxml"));
        return loader;
    }
    private static FXMLLoader hentReiseforsikringPopup() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HovedApplikasjon.class.getResource("/view/ForsikringReisePopupView.fxml"));
        return loader;
    }
}
