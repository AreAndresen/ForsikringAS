package ae.controller;

import ae.HovedApplikasjon;
import ae.model.Kunde;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controller for RotOppsett. Rotoppsettet inneholder menylinjen
 * og plass hvor andre views skal plasserers.
 */
public class RotOppsettController {

    // Referanse til hovedapplikasjonen
    private HovedApplikasjon hovedApplikasjon;

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

            KundeController kundeController = loader.getController();
            kundeController.setHovedApplikasjon(hovedApplikasjon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
