package ae.controller;

import ae.HovedApplikasjon;
import ae.model.Filbehandling;
import ae.model.Kunde;
import ae.model.KundeJobjStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Controller for RotOppsett. Rotoppsettet inneholder menylinjen
 * og plass hvor andre views skal plasserers.
 */
public class RotOppsettController {

    // Referanse til hovedapplikasjonen
    private HovedApplikasjon hovedApplikasjon;

    @FXML
    private MenuItem lagreFilMenuItem, hentFilMenuItem;

    public RotOppsettController() { }

    @FXML
    private void gåTilKundeoversikt() {
        visKundeOversikt();
    }

    /**
     * Kalles fra hovedapplikasjonen for å gi en referanse til seg
     * selv.
     *
     * @param hovedApplikasjon
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;
    }

    /**
     * Åpner kundeoversikten når bruker trykker på Kunder i menylinjen
     */
    public void visKundeOversikt() {
        try {
            // Last inn kundeoversikten fra fxml-fil.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HovedApplikasjon.class.getResource("/view/KundeView.fxml"));
            AnchorPane kundeOversikt = (AnchorPane) loader.load();

            // Plasser kundeoversikten i senter av rotoppsettet.
            hovedApplikasjon.getRotOppsett().setCenter(kundeOversikt);
            lagreFilMenuItem.setDisable(false);
            hentFilMenuItem.setDisable(false);

            KundeController kundeController = loader.getController();
            kundeController.setHovedApplikasjon(hovedApplikasjon);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void lagreFilTrykket() {
        File filPath = visLagreFilVelger();

        if (filPath.getPath().endsWith(".jobj")) {
            try {
                Filbehandling.lagreKunde(new KundeJobjStrategy(), hovedApplikasjon.getKundeData(), filPath.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * FileChooser lagre fil
     */
    private File visLagreFilVelger() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Hent en fil");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV-fil (.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("JOBJ-fil (.jobj)", "*.jobj")
        );

        File fil = fileChooser.showSaveDialog(hovedApplikasjon.getHovedStage());

        if (fil != null) {
            return fil;
        } else {
            return null;
        }
    }
}
