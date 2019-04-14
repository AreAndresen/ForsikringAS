package ae.controller;
import ae.model.Filbehandling;
import ae.model.Viewbehandling;
import ae.util.IdUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ae.HovedApplikasjon;
import ae.model.Kunde;

import java.time.LocalDate;
import java.util.Optional;

import ae.model.Filbehandling;
import ae.model.Viewbehandling;
import ae.util.IdUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ae.HovedApplikasjon;
import ae.model.Kunde;

import java.time.LocalDate;
import java.util.Optional;

import ae.HovedApplikasjon;
import ae.model.Filbehandling;
import ae.model.Kunde;
import ae.model.Skademelding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class SkademeldingController {

    // Tabellen.
    @FXML
    private TableView<Kunde> skademeldingTabell;
    @FXML
    private TableColumn<Kunde, Number> skadeNrKolonne;
    @FXML
    private TableColumn<Kunde, String> skadeTypeKolonne;
    @FXML
    private TableColumn<Kunde, String> skadeBeskrivelseKolonne;
    @FXML
    private TableColumn<Kunde, String> belopTakseringKolonne;
    @FXML
    private TableColumn<Kunde, LocalDate> datoSkadeKolonne;

    // Labels.
    @FXML
    private Label forsikringsNrLabel, etternavnLabel, fornavnLabel, adresseFakturaLabel,
            datoKundeOpprettetLabel, forsikringerLabel, skademeldingerLabel, erstatningerUbetalteLabel;

    private Filbehandling fb = new Filbehandling();

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;

    public SkademeldingController() {}

    @FXML
    private void initialize() {
        // Initier kunde-tabellen med kobling til alle kolonnene
        skadeNrKolonne.setCellValueFactory(celleData -> celleData.getValue().forsikringsNrProperty());
        skadeTypeKolonne.setCellValueFactory(celleData -> celleData.getValue().etternavnProperty());
        skadeBeskrivelseKolonne.setCellValueFactory(celleData -> celleData.getValue().fornavnProperty());
        belopTakseringKolonne.setCellValueFactory(celleData -> celleData.getValue().adresseFakturaProperty());
        datoSkadeKolonne.setCellValueFactory(celleData -> celleData.getValue().datoKundeOpprettetProperty());

        // Sender inn null for å tømme feltene.
        // TODO: Vis skademeldingsdetaljer
        //visKundensDetaljer(null);

        // ChangeListener som ser etter endringer.
        skademeldingTabell.getSelectionModel().selectedItemProperty().addListener(
                (observable, gammelData, nyData) -> visKundensDetaljer(nyData));
    }

    //TODO: flytte denne til rotoppsettet
    @FXML
    public void gåTilNyKundePopup() {
        Kunde nyKunde = new Kunde(IdUtil.genererLøpenummer(hovedApplikasjon.getKundeData()));
        boolean bekreftTrykket = Viewbehandling.visNyKundePopup(hovedApplikasjon, nyKunde);

        if (bekreftTrykket) {
            hovedApplikasjon.getKundeData().add(nyKunde);
        }
    }

    @FXML
    public void gåTilRedigerKundePopup() {
        Kunde valgtKunde = skademeldingTabell.getSelectionModel().getSelectedItem();

        if (valgtKunde != null) {
            boolean bekreftTrykket = Viewbehandling.visRedigerKundePopup(hovedApplikasjon, valgtKunde);

            if (bekreftTrykket) {
                visKundensDetaljer(valgtKunde);
            }
        }

        // TODO: else: alert boks
    }

    /**
     * Sletter valgt kunde fra listen, med bekreftelse
     */
    @FXML
    public void slettValgtKunde() {
        int valgtKundeIndex = skademeldingTabell.getSelectionModel().getSelectedIndex();

        Kunde valgtKunde = skademeldingTabell.getItems().get(valgtKundeIndex);
        String kundeInfo = valgtKunde.getForsikringsNr() +", "+ valgtKunde.getFornavn() +" "+ valgtKunde.getEtternavn();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(hovedApplikasjon.getHovedStage());
        alert.setTitle("Slett kunde");
        alert.setHeaderText("Bekreft sletting av kunde");
        alert.setContentText("Er du sikker på at du ønsker å slette kunde " + kundeInfo +"?");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Bekreft");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            skademeldingTabell.getItems().remove(valgtKundeIndex);
        }
    }

    /**
     * Fyller ut info-feltene om hver kunde.
     * Labelen til Forsikringer, Skademeldinger og Ubetalte erstatninger indikerer
     * antall av de ulike typene. Knappene skal trykkes for å vise de.
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
            // TODO: må tilpasses nye labels
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
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;

        // Legger til data fra ObservableList til tabellen
        skademeldingTabell.setItems(hovedApplikasjon.getKundeData());
    }
}





    /*// Tabellen.
    @FXML
    private TableView<Skademelding> skademeldingTabell;
    @FXML
    private TableColumn<Skademelding, Number> skadeNrKolonne;
    @FXML
    private TableColumn<Skademelding, String> skadeTypeKolonne;
    @FXML
    private TableColumn<Skademelding, String> skadebeskrivelseKolonne;
    @FXML
    private TableColumn<Skademelding, Double> belopTakseringKolonne;
    @FXML
    private TableColumn<Skademelding, Double> utbetaltBelopKolonne;
    @FXML
    private TableColumn<Skademelding, String> kontaktinfoKolonne;
    @FXML
    private TableColumn<Skademelding, LocalDate> datoSkademeldingOpprettetKolonne;

    // Labels.
    @FXML
    private Label forsikringsNrLabel, etternavnLabel, fornavnLabel, adresseFakturaLabel,
            datoKundeOpprettetLabel, forsikringerLabel, skademeldingerLabel, erstatningerUbetalteLabel;

    private Filbehandling fb = new Filbehandling();


    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;

    public SkademeldingController() {}

    @FXML
    private void initialize() {
        // Initier kunde-tabellen med kobling til alle kolonnene
        skadeNrKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeNrProperty());
        skadeTypeKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeTypeProperty());
        skadebeskrivelseKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeBeskrivelseProperty());
        //belopTakseringKolonne.setCellValueFactory(celleData -> celleData.getValue().belopTakseringProperty());
        //utbetaltBelopKolonne.setCellValueFactory(celleData -> celleData.getValue().erstatningsbelopUtbetaltProperty());
        //kontaktinfoKolonne.setCellValueFactory(celleData -> celleData.getValue().kontaktinfoVitnerProperty());

        // Sender inn null for å tømme feltene.
        visKundensDetaljer(null);

        // ChangeListener som ser etter endringer.
        skademeldingTabell.getSelectionModel().selectedItemProperty().addListener(
                (observable, gammelData, nyData) -> visKundensDetaljer(nyData));
    }


    public void visKundensDetaljer(Skademelding skademelding) {
        if (skademelding != null) {

            /*forsikringsNrLabel.setText(Integer.toString(kunde.getForsikringsNr()));
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
     *//*
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;

        // Legger til data fra ObservableList til tabellen
        //skademeldingTabell.setItems(hovedApplikasjon.getKundeData());
    }*/

