package ae.controller;

import ae.HovedApplikasjon;
import ae.util.AlertHandler;
import ae.model.*;
import ae.util.IdUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Optional;

public class ForsikringController {

    // kundenr-tabellen
    @FXML
    private TableView<Kunde> kundeNrTabell;
    @FXML
    private TableColumn<Kunde, Number> kundeNrKolonne;
    @FXML
    private TableColumn<Kunde, String> etternavnKolonne;
    @FXML
    private TextField søkField;

    // forsikring-tabellen
    @FXML
    private TableView<Forsikring> forsikringTabell;
    @FXML
    private TableColumn<Forsikring, Number> forsikringsnrKolonne;
    @FXML
    private TableColumn<Forsikring, Number> premieKolonne;
    @FXML
    private TableColumn<Forsikring, LocalDate> datoOpprettetKolonne;
    @FXML
    private TableColumn<Forsikring, Number> forsikringsbelopKolonne;
    @FXML
    private TableColumn<Forsikring, String> betingelserKolonne;
    @FXML
    private TableColumn<Forsikring, String> typeKolonne;

    // labels
    @FXML
    private Label metaEnLabel, metaToLabel, metaTreLabel, metaFireLabel, metaFemLabel,
            metaSeksLabel, metaSjuLabel, metaÅtteLabel;
    @FXML
    private Label resultatEnLabel, resultatToLabel, resultatTreLabel, resultatFireLabel,
            resultatFemLabel, resultatSeksLabel, resultatSjuLabel, resultatÅtteLabel;

    @FXML
    private ChoiceBox typeChoice;

    // Buttons, for å disable/enable ved threading
    @FXML
    private MenuButton nyMenuButton;
    @FXML
    private Button redigerButton, slettButton;

    public MenuButton getNyMenuButton() {
        return nyMenuButton;
    }
    public Button getRedigerButton() {
        return redigerButton;
    }
    public Button getSlettButton() {
        return slettButton;
    }

    private ObservableList<String> typeSortering = FXCollections.observableArrayList("Alle", "Båtforsikring",
            "Hus- og innboforsikring", "Fritidsboligforsikring", "Reiseforsikring");

    // referanse til hovedapplikasjonen
    private HovedApplikasjon hovedApplikasjon;

    // tom konstruktør
    public ForsikringController() { }

    @FXML
    private void initialize() {
        // koble kolonnene med datafeltene
        kundeNrKolonne.setCellValueFactory(celleData -> celleData.getValue().kundeNrProperty());
        etternavnKolonne.setCellValueFactory(celleData -> celleData.getValue().etternavnProperty());

        forsikringsnrKolonne.setCellValueFactory(celleData -> celleData.getValue().forsikringsNrProperty());
        premieKolonne.setCellValueFactory(celleData -> celleData.getValue().årligPremieProperty());
        datoOpprettetKolonne.setCellValueFactory(celleData -> celleData.getValue().datoOpprettetProperty());
        forsikringsbelopKolonne.setCellValueFactory(celleData -> celleData.getValue().forsikringsBelopProperty());
        betingelserKolonne.setCellValueFactory(celleData -> celleData.getValue().betingelserProperty());
        typeKolonne.setCellValueFactory(celleData -> celleData.getValue().typeProperty());

        //typeChoice.setValue("Alle");
        typeChoice.setItems(typeSortering);

        visForsikringDetaljer(null);
        visForsikringer(null);

        forsikringTabell.getSelectionModel().selectedItemProperty().addListener(
                ((observable, gammelData, nyData) -> visForsikringDetaljer(nyData)));

        kundeNrTabell.getSelectionModel().selectedItemProperty().addListener(
                (((observable, gammelData, nyData) -> visForsikringer(nyData))));

        // Behandling av søk
        søkField.textProperty().addListener((((observable, gammelVerdi, nyVerdi) -> {
            FilteredList<Kunde> kundeFiltered = new FilteredList<>(hovedApplikasjon.getKundeData(), k -> true);

            kundeFiltered.setPredicate(kunde -> Kunde.behandleSøk(kunde, nyVerdi));

            SortedList<Kunde> kundeSorted = new SortedList<>(kundeFiltered);
            kundeSorted.comparatorProperty().bind(kundeNrTabell.comparatorProperty());
            kundeNrTabell.setItems(kundeSorted);
        })));
    }

    private void visForsikringer(Kunde kunde) {
        typeChoice.setValue("Alle");
        typeChoice.setDisable(true);
        if (kunde != null) {
            typeChoice.setDisable(false);
            FilteredList<Forsikring> forsikringerFiltered = new FilteredList<>(kunde.getForsikringer());

            typeChoice.valueProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String gammelVerdi, String nyVerdi) {
                    forsikringerFiltered.setPredicate("Alle".equals(nyVerdi) ? null : (Forsikring f) -> nyVerdi.equals(f.getType()));
                }
            });

            forsikringTabell.setItems(forsikringerFiltered);
        }
    }

    private void visForsikringDetaljer(Forsikring forsikring) {

        // det er forskjellig data for hver type forsikring
        if (forsikring != null) {

            // båtforsikring
            if ("Båtforsikring".equals(forsikring.getType())) {
                BåtForsikring båtForsikring = (BåtForsikring) forsikring;

                // setter inn metadata
                metaEnLabel.setText("Registreringsnummer");
                metaToLabel.setText("Båttype og modell");
                metaTreLabel.setText("Lengde i fot");
                metaFireLabel.setText("Årsmodell");
                metaFemLabel.setText("Motortype og styrke");

                // setter inn resultatdata
                resultatEnLabel.setText(båtForsikring.getRegistreringsNr());
                resultatToLabel.setText(båtForsikring.getTypeModell());
                resultatTreLabel.setText(Integer.toString(båtForsikring.getLengdeFot()));
                resultatFireLabel.setText(Integer.toString(båtForsikring.getÅrsmodell()));
                resultatFemLabel.setText(båtForsikring.getMotorEgenskaper());

                // tømmer de andre
                metaSeksLabel.setText("");
                metaSjuLabel.setText("");
                metaÅtteLabel.setText("");
                resultatSeksLabel.setText("");
                resultatSjuLabel.setText("");
                resultatÅtteLabel.setText("");
            }

            if ("Hus- og innboforsikring".equals(forsikring.getType()) ||
                    "Fritidsboligforsikring".equals(forsikring.getType())) {
                BoligForsikring boligForsikring = (BoligForsikring) forsikring;

                // setter inn metadata
                metaEnLabel.setText("Adresse bolig");
                metaToLabel.setText("Byggeår");
                metaTreLabel.setText("Byggemateriale");
                metaFireLabel.setText("Standard");
                metaFemLabel.setText("Antall kvadratmeter");
                metaSeksLabel.setText("Forsikringsbeløp bygning");
                metaSjuLabel.setText("Forsikringsbeløp innbo");

                // setter inn resultatdata
                resultatEnLabel.setText(boligForsikring.getAdresseBolig());
                resultatToLabel.setText(Integer.toString(boligForsikring.getByggeår()));
                resultatTreLabel.setText(boligForsikring.getByggemateriale());
                resultatFireLabel.setText(boligForsikring.getStandard());
                resultatFemLabel.setText(Integer.toString(boligForsikring.getAntallKvm()));
                resultatSeksLabel.setText(Long.toString(boligForsikring.getForsikringsbelopBygning()));
                resultatSjuLabel.setText(Long.toString(boligForsikring.getForsikringsbelopInnbo()));

                // tømmer de andre
                metaÅtteLabel.setText("");
                resultatÅtteLabel.setText("");
            }

            if ("Reiseforsikring".equals(forsikring.getType())) {
                ReiseForsikring reiseForsikring = (ReiseForsikring) forsikring;

                // setter inn metadata
                metaEnLabel.setText("Forsikringsområde");
                metaToLabel.setText("Forsikringssum");

                // setter inn resultatdata
                resultatEnLabel.setText(reiseForsikring.getForsikringsOmråde());
                resultatToLabel.setText(Long.toString(reiseForsikring.getForsikringsSum()));

                // tømmer de andre
                metaTreLabel.setText("");
                metaFireLabel.setText("");
                metaFemLabel.setText("");
                metaSeksLabel.setText("");
                metaSjuLabel.setText("");
                metaÅtteLabel.setText("");
                resultatTreLabel.setText("");
                resultatFireLabel.setText("");
                resultatFemLabel.setText("");
                resultatSeksLabel.setText("");
                resultatSjuLabel.setText("");
                resultatÅtteLabel.setText("");
            }
        } else {
            // tømmer metadata
            metaEnLabel.setText("");
            metaToLabel.setText("");
            metaTreLabel.setText("");
            metaFireLabel.setText("");
            metaFemLabel.setText("");
            metaSeksLabel.setText("");
            metaSjuLabel.setText("");
            metaÅtteLabel.setText("");

            // tømmer resultatdataen
            resultatEnLabel.setText("");
            resultatToLabel.setText("");
            resultatTreLabel.setText("");
            resultatFireLabel.setText("");
            resultatFemLabel.setText("");
            resultatSeksLabel.setText("");
            resultatSjuLabel.setText("");
            resultatÅtteLabel.setText("");
        }
    }

    @FXML
    public void gåTilNyBåtForsikringPopup() {
        Kunde valgtKunde = kundeNrTabell.getSelectionModel().getSelectedItem();

        if (valgtKunde != null) {
            int forsikringsNr = IdUtil.genererLøpenummerForsikring(hovedApplikasjon.getKundeData());

            Forsikring nyBåtForsikring = new BåtForsikring(valgtKunde.getKundeNr(), forsikringsNr);

            boolean bekreftTrykket = Viewbehandling.visNyBåtforsikringPopup(hovedApplikasjon, (BåtForsikring) nyBåtForsikring);

            if (bekreftTrykket) {
                valgtKunde.getForsikringer().add(nyBåtForsikring);
            }
        } else {
            AlertHandler.genererWarningAlert("Ny forsikring", "Ingen kunde valgt",
                    "Du må velge en kunde for å kunne registrere forsikringer!");
        }
    }

    @FXML
    public void gåTilNyHusOgInnboForsikringPopup() {
        Kunde valgtKunde = kundeNrTabell.getSelectionModel().getSelectedItem();

        if (valgtKunde != null) {
            int forsikringsNr = IdUtil.genererLøpenummerForsikring(hovedApplikasjon.getKundeData());

            Forsikring nyHusOgInnboForsikring = new BoligForsikring(valgtKunde.getKundeNr(), forsikringsNr,
                    "Hus- og innboforsikring");

            boolean bekreftTrykket = Viewbehandling.visNyBoligforsikringPopup(hovedApplikasjon,
                    (BoligForsikring) nyHusOgInnboForsikring);

            if (bekreftTrykket) {
                valgtKunde.getForsikringer().add(nyHusOgInnboForsikring);
            }
        } else {
            AlertHandler.genererWarningAlert("Ny forsikring", "Ingen kunde valgt",
                    "Du må velge en kunde for å kunne registrere forsikringer!");
        }
    }

    @FXML
    public void gåTilNyFritidsboligForsikringPopup() {
        Kunde valgtKunde = kundeNrTabell.getSelectionModel().getSelectedItem();

        if (valgtKunde != null) {
            int forsikringsNr = IdUtil.genererLøpenummerForsikring(hovedApplikasjon.getKundeData());

            Forsikring nyFritidsboligForsikring = new BoligForsikring(valgtKunde.getKundeNr(), forsikringsNr,
                    "Fritidsboligforsikring");

            boolean bekreftTrykket = Viewbehandling.visNyBoligforsikringPopup(hovedApplikasjon,
                    (BoligForsikring) nyFritidsboligForsikring);

            if (bekreftTrykket) {
                valgtKunde.getForsikringer().add(nyFritidsboligForsikring);
            }
        } else {
            AlertHandler.genererWarningAlert("Ny forsikring", "Ingen kunde valgt",
                    "Du må velge en kunde for å kunne registrere forsikringer!");
        }
    }

    @FXML
    public void gåTilNyReiseForsikringPopup() {

        Kunde valgtKunde = kundeNrTabell.getSelectionModel().getSelectedItem();

        if (valgtKunde != null) {
            int forsikringsNr = IdUtil.genererLøpenummerForsikring(hovedApplikasjon.getKundeData());

            Forsikring nyReiseForsikring = new ReiseForsikring(valgtKunde.getKundeNr(), forsikringsNr);

            boolean bekreftTrykket = Viewbehandling.visNyReiseforsikringPopup(hovedApplikasjon,
                    (ReiseForsikring) nyReiseForsikring);

            if (bekreftTrykket) {
                valgtKunde.getForsikringer().add(nyReiseForsikring);
            }
        } else {
            AlertHandler.genererWarningAlert("Ny forsikring", "Ingen kunde valgt",
                    "Du må velge en kunde for å kunne registrere forsikringer!");
        }
    }

    @FXML
    public void gåTilRedigerForsikringPopup() {
        Forsikring valgtForsikring = forsikringTabell.getSelectionModel().getSelectedItem();

        if (valgtForsikring != null) {
            if ("Båtforsikring".equals(valgtForsikring.getType())) {
                boolean bekreftTrykket = Viewbehandling.visRedigerBåtforsikringPopup(
                        hovedApplikasjon, (BåtForsikring) valgtForsikring);

                if (bekreftTrykket) {
                    visForsikringDetaljer(valgtForsikring);
                }
            }

            if ("Hus- og innboforsikring".equals(valgtForsikring.getType()) ||
                    "Fritidsboligforsikring".equals(valgtForsikring.getType())) {
                boolean bekreftTrykket = Viewbehandling.visRedigerBoligforsikringPopup(
                        hovedApplikasjon, (BoligForsikring) valgtForsikring);

                if (bekreftTrykket) {
                    visForsikringDetaljer(valgtForsikring);
                }
            }

            if ("Reiseforsikring".equals(valgtForsikring.getType())) {
                boolean bekreftTrykket = Viewbehandling.visRedigerReiseforsikringPopup(hovedApplikasjon,
                        (ReiseForsikring) valgtForsikring);

                if (bekreftTrykket) {
                    visForsikringDetaljer(valgtForsikring);
                }
            }

        } else {
            AlertHandler.genererWarningAlert("Rediger forsikring", "Ingen forsikring valgt",
                    "Du må velge en forsikring for å kunne redigere!");
        }
    }

    @FXML
    public void slettValgtForsikring() {
        Kunde valgtkunde = kundeNrTabell.getSelectionModel().getSelectedItem();
        Forsikring valgtForsikring = forsikringTabell.getSelectionModel().getSelectedItem();

        if (valgtForsikring != null && valgtkunde != null) {
            String forsikringInfo = Integer.toString(valgtForsikring.getForsikringsNr());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(hovedApplikasjon.getHovedStage());
            alert.setTitle("Slett forsikring");
            alert.setHeaderText("Bekreft sletting av forsikring");
            alert.setContentText("Er du sikker på at du ønsker å slette forsikring nummer: " + forsikringInfo +"?");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Bekreft");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                valgtkunde.getForsikringer().remove(valgtForsikring);
            }
        } else {
            AlertHandler.genererWarningAlert("Slett forsikring", "Ingen forsikring valgt",
                    "Du må velge en forsikring for å kunne slette!");
        }
    }

    /**
     * Kalles fra RotOppsettController for å gi en referanse til
     * hovedapplikasjonen.
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;

        kundeNrTabell.setItems(hovedApplikasjon.getKundeData());
    }
}
