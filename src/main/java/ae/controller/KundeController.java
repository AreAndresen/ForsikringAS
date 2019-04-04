package ae.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ae.HovedApplikasjon;
import ae.model.Kunde;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller for KundeView. KundeView inneholder visning av kunder, legge til
 * ny kunde, endre kunde og slette kunde.
 */
public class KundeController {

    // Tabellen.
    @FXML
    private TableView<Kunde> kundeTabell;
    @FXML
    private TableColumn<Kunde, Number> forsikringsNrKolonne;
    @FXML
    private TableColumn<Kunde, String> etternavnKolonne;
    @FXML
    private TableColumn<Kunde, String> fornavnKolonne;
    @FXML
    private TableColumn<Kunde, String> adresseFakturaKolonne;
    @FXML
    private TableColumn<Kunde, LocalDate> datoKundeOpprettetKolonne;

    // Labels.
    @FXML
    private Label forsikringsNrLabel;
    @FXML
    private Label etternavnLabel;
    @FXML
    private Label fornavnLabel;
    @FXML
    private Label adresseFakturaLabel;
    @FXML
    private Label datoKundeOpprettetLabel;
    @FXML
    private Label forsikringerLabel;
    @FXML
    private Label skademeldingerLabel;
    @FXML
    private Label erstatningerUbetalteLabel;

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;

    public KundeController() {}

    @FXML
    private void initialize() {
        // Initier kunde-tabellen med alle kolonnene
        forsikringsNrKolonne.setCellValueFactory(celleData -> celleData.getValue().forsikringsNrProperty());
        etternavnKolonne.setCellValueFactory(celleData -> celleData.getValue().etternavnProperty());
        fornavnKolonne.setCellValueFactory(celleData -> celleData.getValue().fornavnProperty());
        adresseFakturaKolonne.setCellValueFactory(celleData -> celleData.getValue().adresseFakturaProperty());
        datoKundeOpprettetKolonne.setCellValueFactory(celleData -> celleData.getValue().datoKundeOpprettetProperty());

        // Sender inn null for å tømme feltene.
        visKundensDetaljer(null);

        // ChangeListener som ser etter endringer.
        kundeTabell.getSelectionModel().selectedItemProperty().addListener(
                (observable, gammelData, nyData) -> visKundensDetaljer(nyData));
    }

    @FXML
    public void gåTilNyKundePopup() {
    }

    /**
     * Fyller ut info-feltene om hver kunde.
     * Labelen til Forsikringer, Skademeldinger og Ubetalte erstatninger indikerer
     * antall av de ulike typene. Knappene skal trykkes for å vise de.
     *
     * @param kunde kunden som er valgt, hvis ikke valgt så null
     */
    public void visKundensDetaljer(Kunde kunde) {
        if (kunde != null) {

            forsikringsNrLabel.setText(Integer.toString(kunde.getForsikringsNr()));
            etternavnLabel.setText(kunde.getEtternavn());
            fornavnLabel.setText(kunde.getFornavn());
            adresseFakturaLabel.setText(kunde.getAdresseFaktura());
            datoKundeOpprettetLabel.setText(kunde.getDatoKundeOpprettet().toString());
            forsikringerLabel.setText(Integer.toString(kunde.getForsikringer().size()));
            skademeldingerLabel.setText(Integer.toString(kunde.getSkademeldinger().size()));
            erstatningerUbetalteLabel.setText(Integer.toString(kunde.getErstatningerUbetalte().size()));

        } else {

            // Ingen kunde valgt, fjerner all tekst.
            forsikringsNrLabel.setText("");
            etternavnLabel.setText("");
            fornavnLabel.setText("");
            adresseFakturaLabel.setText("");
            datoKundeOpprettetLabel.setText("");
            forsikringerLabel.setText("");
            skademeldingerLabel.setText("");
            erstatningerUbetalteLabel.setText("");

        }
    }

    /**
     * Kalles fra RotOppsettController for å gi en referanse til
     * hovedapplikasjonen.
     *
     * @param hovedApplikasjon
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;

        // Legger til data fra ObservableList til tabellen
        kundeTabell.setItems(hovedApplikasjon.getKundeData());
    }
}
