package ae.controller;
import ae.model.*;
import ae.util.AlertHandler;
import ae.util.IdUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ae.HovedApplikasjon;
import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;



public class SkademeldingController {


    // kundenr-tabellen
    @FXML
    public TableView<Kunde> kundeNrTabell;
    @FXML
    public TableColumn<Kunde, Number> kundeNrKolonne;
    @FXML
    public TableColumn<Kunde, String> etternavnKolonne;
    @FXML
    private TextField søkField;

    // skademelding tabellen.
    @FXML
    private TableView<Skademelding> skademeldingTabell;
    @FXML
    private TableColumn<Skademelding, Number> skadeNrKolonne;
    @FXML
    private TableColumn<Skademelding, String> skadeTypeKolonne;
    @FXML
    private TableColumn<Skademelding, Double> belopTakseringKolonne;
    @FXML
    private TableColumn<Skademelding, Double> erstatningUtbetaltKolonne;
    @FXML
    private TableColumn<Skademelding, LocalDate> datoSkadeKolonne;
    @FXML
    private TableColumn<Skademelding, String> statusKolonne;

    // Labels.
    @FXML
    private Label beskrivelseAvSkadeLabel, kontaktinfoVitnerLabel;

    @FXML
    private ChoiceBox typeChoice;

    private ObservableList<String> typeSortering = FXCollections.observableArrayList("Alle", "Båtforsikring",
            "Hus- og innboforsikring", "Fritidsboligforsikring", "Reiseforsikring");

    private Filbehandling fb = new Filbehandling();

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;

    public SkademeldingController() {}

    @FXML
    private void initialize() {
        // Initier skademelding-tabellen med kobling til alle kolonnene
        kundeNrKolonne.setCellValueFactory(celleData -> celleData.getValue().kundeNrProperty());
        etternavnKolonne.setCellValueFactory(celleData -> celleData.getValue().etternavnProperty());

        skadeNrKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeNrProperty());
        skadeTypeKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeTypeProperty());
        belopTakseringKolonne.setCellValueFactory(celleData -> celleData.getValue().belopTakseringProperty().asObject()); //asObject() på tall
        erstatningUtbetaltKolonne.setCellValueFactory(celleData -> celleData.getValue().erstatningsbelopUtbetaltProperty().asObject());
        datoSkadeKolonne.setCellValueFactory(celleData -> celleData.getValue().datoSkadeProperty());
        statusKolonne.setCellValueFactory(celleData -> celleData.getValue().statusProperty());

        //typeChoice.setValue("Alle");
        typeChoice.setItems(typeSortering);

        // Sender inn null for å tømme feltene.
        visSkademeldingDetaljer(null);
        visSkademeldinger(null);

        // ChangeListener som ser etter endringer.
        skademeldingTabell.getSelectionModel().selectedItemProperty().addListener(
                (observable, gammelData, nyData) -> visSkademeldingDetaljer(nyData));

        kundeNrTabell.getSelectionModel().selectedItemProperty().addListener(
                (((observable, gammelData, nyData) -> visSkademeldinger(nyData))));


        søkField.textProperty().addListener((((observable, gammelVerdi, nyVerdi) -> {
            FilteredList<Kunde> kundeFiltered = new FilteredList<>(hovedApplikasjon.getKundeData(), k -> true);

            kundeFiltered.setPredicate(kunde -> Kunde.behandleSøk(kunde, nyVerdi));

            SortedList<Kunde> kundeSorted = new SortedList<>(kundeFiltered);
            kundeSorted.comparatorProperty().bind(kundeNrTabell.comparatorProperty());
            kundeNrTabell.setItems(kundeSorted);
        })));
    }

    private void visSkademeldinger(Kunde kunde) {
        typeChoice.setValue("Alle");
        typeChoice.setDisable(true);
        if (kunde != null) {

            typeChoice.setDisable(false);
            FilteredList<Skademelding> skademeldingerFiltered = new FilteredList<>(kunde.getSkademeldinger());

            typeChoice.valueProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String gammelVerdi, String nyVerdi) {
                    skademeldingerFiltered.setPredicate("Alle".equals(nyVerdi) ? null : (Skademelding f) -> nyVerdi.equals(f.getSkadeType()));
                }
            });
            skademeldingTabell.setItems(skademeldingerFiltered);
        }
    }

    /** Fyller ut info-feltene om hver kunde.Labelen til Forsikringer, Skademeldinger og Ubetalte erstatninger indikerer
     * antall av de ulike typene. Knappene skal trykkes for å vise de.*/
    public void visSkademeldingDetaljer(Skademelding skademelding) {
        if (skademelding != null) {
            beskrivelseAvSkadeLabel.setText(skademelding.getSkadeBeskrivelse());
            kontaktinfoVitnerLabel.setText(skademelding.getKontaktinfoVitner());

        } else {
            // Ingen skademelding valgt, fjerner all tekst.
            beskrivelseAvSkadeLabel.setText("");
            kontaktinfoVitnerLabel.setText("");
        }
    }


    @FXML
    public void gåTilNySkademeldingPopup() {
        if (kundeNrTabell.getSelectionModel().getSelectedItem() != null) {
            int skadeNr = IdUtil.genererLøpenummerSkade(hovedApplikasjon.getKundeData());

            Skademelding nySkademelding = new Skademelding(
                    kundeNrTabell.getSelectionModel().getSelectedItem().getKundeNr(), skadeNr);

            boolean bekreftTrykket = Viewbehandling.visNySkademeldingPopup(hovedApplikasjon, nySkademelding);

            if (bekreftTrykket) {
                for(Kunde enKunde : hovedApplikasjon.getKundeData()) {
                    if (enKunde.getKundeNr() == nySkademelding.getKundeNr()) {
                        //legger til ny skademelding
                        enKunde.getSkademeldinger().add(nySkademelding);

                        //setter antall ubetalte
                        enKunde.setAntallErstatningerUbetalte();
                    }
                }
            }
        }
        else {
            AlertHandler.genererWarningAlert("Ny skademelding", "Ingen kunde valgt",
                    "Du må velge en kunde for å kunne registrere skademeldinger!");
        }

    }

    @FXML
    public void gåTilRedigerSkademeldingPopup() {
        Skademelding valgtSkademelding = skademeldingTabell.getSelectionModel().getSelectedItem();

        if (valgtSkademelding != null) {
            boolean bekreftTrykket = Viewbehandling.visRedigerSkademeldingPopup(hovedApplikasjon, valgtSkademelding);

            if (bekreftTrykket) {
                visSkademeldingDetaljer(valgtSkademelding);

                //finner kunden for å oppdatere skademeldingerubetalte
                for(Kunde enKunde : hovedApplikasjon.getKundeData()) {
                    if (enKunde.getKundeNr() == valgtSkademelding.getKundeNr()) {
                        //setter antall ubetalte
                        enKunde.setAntallErstatningerUbetalte();
                    }
                }
            }
        }
        else{
            AlertHandler.genererWarningAlert("Rediger skademelding", "Ingen skademelding valgt",
                    "Du må velge en skademelding for å kunne redigere!");
        }
    }

    /**
     * Sletter valgt kunde fra listen, med bekreftelse
     */
    @FXML
    public void slettValgtSkademelding() {
        //int valgtSkademeldingIndex = skademeldingTabell.getSelectionModel().getSelectedIndex();
        Skademelding valgtSkademelding = skademeldingTabell.getSelectionModel().getSelectedItem();

        if(valgtSkademelding != null) {
        //if (valgtSkademeldingIndex >= 0) {
            //Skademelding valgtSkademelding = skademeldingTabell.getItems().get(valgtSkademeldingIndex);

            String skademeldingInfo = Integer.toString(valgtSkademelding.getSkadeNr());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(hovedApplikasjon.getHovedStage());
            alert.setTitle("Slett Skademelding");
            alert.setHeaderText("Bekreft sletting av skademelding");
            alert.setContentText("Er du sikker på at du ønsker å slette skademelding nummer: " + skademeldingInfo +"?");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Bekreft");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                //sletter fra tabell her
                skademeldingTabell.getItems().remove(valgtSkademelding);

                Skademelding skademeldingTilSletting = null;
                for (Kunde enKunde : hovedApplikasjon.getKundeData()) {
                    if (valgtSkademelding.getKundeNr() == enKunde.getKundeNr()) {

                        for (Skademelding skademelding : enKunde.getSkademeldinger()) {
                            if (valgtSkademelding.equals(skademelding)) {
                                skademeldingTilSletting = skademelding;
                            }
                        }
                    }
                    //sletter skademelding
                    enKunde.getSkademeldinger().remove(skademeldingTilSletting);
                    //setter antall ubetalte
                    enKunde.setAntallErstatningerUbetalte();
                }
            }
        }
        else{
            AlertHandler.genererWarningAlert("Slett skademelding", "Ingen skademelding valgt",
                    "Du må velge en skademelding for å kunne slette!");
        }

    }


    /**
     * Kalles fra RotOppsettController for å gi en referanse til
     * hovedapplikasjonen.*/
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {

        this.hovedApplikasjon = hovedApplikasjon;

        //skademeldingTabell.setItems(hovedApplikasjon.getAlleSkademeldinger());

        kundeNrTabell.setItems(hovedApplikasjon.getKundeData());
    }
}