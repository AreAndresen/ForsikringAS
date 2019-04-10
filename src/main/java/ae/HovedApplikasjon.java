package ae;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import ae.controller.KundeRedigerPopupController;
import ae.controller.RotOppsettController;
import ae.model.Forsikring;
import ae.model.Kunde;
import ae.model.Skademelding;
import ae.model.Viewbehandling;
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
     * Konstruktør for hovedapplikasjon. Sett inn dummy-data for testing
     */
    public HovedApplikasjon() { }

    /**
     * Returnerer hovedstagen.
     */
    public Stage getHovedStage() {
        return hovedStage;
    }

    /**
     * Get og set for rotOppsett
     */
    public BorderPane getRotOppsett() { return rotOppsett; }
    public void setRotOppsett(BorderPane rotOppsett) { this.rotOppsett = rotOppsett; }

    /**
     * Returnerer kunde data som en ObservableList.
     */
    public ObservableList<Kunde> getKundeData() {
        return kundeData;
    }

    @Override
    public void start(Stage hovedStage) {
        this.hovedStage = hovedStage;
        this.hovedStage.setTitle("Forsikring AS");

        // Rotoppsettet kjører så lenge applikasjonen kjører.
        Viewbehandling.initierRotOppsett(this);

    }
}
