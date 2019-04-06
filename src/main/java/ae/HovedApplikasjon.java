package ae;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import ae.controller.KundeRedigerPopupController;
import ae.controller.RotOppsettController;
import ae.model.Forsikring;
import ae.model.Kunde;
import ae.model.Skademelding;
import ae.util.IdUtil;
import com.sun.javafx.iio.ios.IosDescriptor;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HovedApplikasjon extends Application {

    public static void main(String[] args) { launch(args); }

    private Stage hovedStage;
    private BorderPane rotOppsett;
    private ObservableList<Kunde> kundeData = FXCollections.observableArrayList();

    /**
     * Konstruktør for hovedapplikasjon med dummy-data for å teste.
     */
    public HovedApplikasjon() {
        kundeData.add(new Kunde(0, LocalDate.now(), "Hansen", "Jonas", "Oslo", new ArrayList<Forsikring>(),
                new ArrayList<Skademelding>(), new ArrayList<Skademelding>()));
        kundeData.add(new Kunde(IdUtil.genererLøpenummer(kundeData), LocalDate.now(), "Hansen", "Jonas", "Oslo", new ArrayList<Forsikring>(),
                new ArrayList<Skademelding>(), new ArrayList<Skademelding>()));
        kundeData.add(new Kunde(IdUtil.genererLøpenummer(kundeData), LocalDate.now(), "Hansen", "Jonas", "Oslo", new ArrayList<Forsikring>(),
                new ArrayList<Skademelding>(), new ArrayList<Skademelding>()));
        kundeData.add(new Kunde(IdUtil.genererLøpenummer(kundeData), LocalDate.now(), "Hansen", "Jonas", "Oslo", new ArrayList<Forsikring>(),
                new ArrayList<Skademelding>(), new ArrayList<Skademelding>()));
        kundeData.add(new Kunde(IdUtil.genererLøpenummer(kundeData), LocalDate.now(), "Hansen", "Jonas", "Oslo", new ArrayList<Forsikring>(),
                new ArrayList<Skademelding>(), new ArrayList<Skademelding>()));
    }

    @Override
    public void start(Stage hovedStage) {
        this.hovedStage = hovedStage;
        this.hovedStage.setTitle("Forsikring AS");

        // Rotoppsettet kjører så lenge applikasjonen kjører.
        initierRotOppsett();

    }

    /**
     * Initierer rotoppsettet som skal kjøres ved oppstart.
     */
    public void initierRotOppsett() {
        try {
            // Last inn fxml-fil.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HovedApplikasjon.class.getResource("/view/RotOppsett.fxml"));
            rotOppsett = (BorderPane) loader.load();

            // Vis scenen som inneholder rotoppsettet.
            Scene scene = new Scene(rotOppsett);
            hovedStage.setScene(scene);
            hovedStage.show();

            // Overfør hovedapplikasjonen til rot-controlleren.
            RotOppsettController rotOppsettController = loader.getController();
            rotOppsettController.setHovedApplikasjon(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returnerer hovedstagen.
     * @return Stage
     */
    public Stage getHovedStage() {
        return hovedStage;
    }

    /**
     * Returnerer rotoppsettet.
     * @return BorderPane
     */
    public BorderPane getRotOppsett() { return rotOppsett; }

    /**
     * Returnerer kunde data som en ObservableList.
     * @return
     */
    public ObservableList<Kunde> getKundeData() {
        return kundeData;
    }

    /**
     * Åpne Ny kunde og Endre kunde popupen.
     *
     * @param kunde kunden som skal redigeres
     * @return true dersom bruker trykker Bekreft
     */
    public boolean visNyKundePopup(Kunde kunde) {
        try {
            FXMLLoader loader = hentKundeRedigerPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Registrer ny kunde");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedStage);
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

    public boolean visRedigerKundePopup(Kunde kunde) {
        try {
            FXMLLoader loader = hentKundeRedigerPopup();
            AnchorPane side = (AnchorPane) loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Rediger eksisterende kunde");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(hovedStage);
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

    public FXMLLoader hentKundeRedigerPopup() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HovedApplikasjon.class.getResource("/view/KundeRedigerPopupView.fxml"));
        return loader;
    }
}
