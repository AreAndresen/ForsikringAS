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

import ae.HovedApplikasjon;
import ae.model.Filbehandling;
import ae.model.Kunde;
import ae.model.Skademelding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
/*
public class SkademeldingController {

    // Tabellen.
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
        belopTakseringKolonne.setCellValueFactory(celleData -> celleData.getValue().belopTakseringProperty());
        datoKundeOpprettetKolonne.setCellValueFactory(celleData -> celleData.getValue().datoKundeOpprettetProperty());

        // Sender inn null for å tømme feltene.
        visKundensDetaljer(null);

        // ChangeListener som ser etter endringer.
        kundeTabell.getSelectionModel().selectedItemProperty().addListener(
                (observable, gammelData, nyData) -> visKundensDetaljer(nyData));
    }
}*/
