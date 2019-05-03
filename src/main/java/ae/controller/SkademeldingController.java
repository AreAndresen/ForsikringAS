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
import java.util.Map;
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
    private TableColumn<Skademelding, Number> belopTakseringKolonne;
    @FXML
    private TableColumn<Skademelding, Number> erstatningUtbetaltKolonne;
    @FXML
    private TableColumn<Skademelding, LocalDate> datoSkadeKolonne;
    @FXML
    private TableColumn<Skademelding, String> statusKolonne;

    // Labels.
    @FXML
    private Label beskrivelseAvSkadeLabel, kontaktinfoVitnerLabel;

    @FXML
    private Button nyButton, redigerButton, slettButton;

    public Button getNyButton() {
        return nyButton;
    }
    public Button getRedigerButton() {
        return redigerButton;
    }
    public Button getSlettButton() {
        return slettButton;
    }

    @FXML
    private ChoiceBox typeChoice;

    private ObservableList<String> typeSortering = FXCollections.observableArrayList("Alle", "Båtforsikring",
            "Hus- og innboforsikring", "Fritidsboligforsikring", "Reiseforsikring");

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
        belopTakseringKolonne.setCellValueFactory(celleData -> celleData.getValue().belopTakseringProperty());
        erstatningUtbetaltKolonne.setCellValueFactory(celleData -> celleData.getValue().erstatningsbelopUtbetaltProperty());
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

        // Behandling av søk
        søkField.textProperty().addListener((((observable, gammelVerdi, nyVerdi) -> {
            FilteredList<Kunde> kundeFiltered = new FilteredList<>(hovedApplikasjon.getKundeData(), k -> true);

            kundeFiltered.setPredicate(kunde -> Kunde.søkeordFunnet(kunde, nyVerdi));

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
     * antall av de ulike typene. Knappene kan trykkes for å vise de.*/
    public void visSkademeldingDetaljer(Skademelding skademelding) {
        if (skademelding != null) {
            beskrivelseAvSkadeLabel.setText(skademelding.getSkadeBeskrivelse());

            //finner alle vitner
            String vitner = "";
            for (Map.Entry<String, String> info : skademelding.getKontaktinfoVitner().entrySet()) {
                vitner += "Navn: "+info.getValue()+" Tlf:"+info.getKey()+"\n";
            }
            kontaktinfoVitnerLabel.setText(vitner);


        } else {
            // Ingen skademelding valgt, fjerner all tekst.
            beskrivelseAvSkadeLabel.setText("");
            kontaktinfoVitnerLabel.setText("");
        }
    }


    @FXML
    public void gåTilNySkademeldingPopup() {

        Kunde valgtKunde = kundeNrTabell.getSelectionModel().getSelectedItem();

        if (valgtKunde != null) {
            int skadeNr = IdUtil.genererLøpenummerSkade(hovedApplikasjon.getKundeData());
            Skademelding nySkademelding = new Skademelding(valgtKunde.getKundeNr(), skadeNr);

            boolean bekreftTrykket = Viewbehandling.visNySkademeldingPopup(hovedApplikasjon, nySkademelding);

            if (bekreftTrykket) {
                valgtKunde.getSkademeldinger().add(nySkademelding);
                valgtKunde.setAntallErstatningerUbetalte();
            }
        }
        else {
            AlertHandler.genererWarningAlert("Ny skademelding", "Ingen kunde valgt",
                    "Du må velge en kunde for å kunne registrere skademeldinger!");
        }
    }

    @FXML
    public void gåTilRedigerSkademeldingPopup() {
        Kunde valgtKunde = kundeNrTabell.getSelectionModel().getSelectedItem();
        Skademelding valgtSkademelding = skademeldingTabell.getSelectionModel().getSelectedItem();

        if (valgtSkademelding != null && valgtKunde != null) {
            boolean bekreftTrykket = Viewbehandling.visRedigerSkademeldingPopup(hovedApplikasjon, valgtSkademelding);

            if (bekreftTrykket) {
                visSkademeldingDetaljer(valgtSkademelding);
                valgtKunde.setAntallErstatningerUbetalte();
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
        Kunde valgtkunde = kundeNrTabell.getSelectionModel().getSelectedItem();
        Skademelding valgtSkademelding = skademeldingTabell.getSelectionModel().getSelectedItem();

        if(valgtSkademelding != null && valgtkunde != null) {

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
                valgtkunde.getSkademeldinger().remove(valgtSkademelding);
                //setter antall ubetalte
                valgtkunde.setAntallErstatningerUbetalte();
            }
        }
        else{
            AlertHandler.genererWarningAlert("Slett skademelding", "Ingen skademelding valgt",
                    "Du må velge en skademelding for å kunne slette!");
        }
    }


    //------------------------ VITNER-------------------------------
    @FXML
    public void gåTilNyttVitnePopup() {
        //Kunde valgtKunde = kundeNrTabell.getSelectionModel().getSelectedItem();
        Skademelding valgtSkademelding = skademeldingTabell.getSelectionModel().getSelectedItem();

        if (valgtSkademelding != null) {
            boolean bekreftTrykket = Viewbehandling.visLeggTilVitnePopup(hovedApplikasjon, valgtSkademelding);

            if (bekreftTrykket) {
                visSkademeldingDetaljer(valgtSkademelding);
            }
        }
        else{
            AlertHandler.genererWarningAlert("Legg til vitne - Endre navn på eksisterende tlf", "Ingen skademelding valgt",
                    "Du må velge en skademelding for å kunne legge til vitne!");
        }
    }

    @FXML
    public void slettVitner() {
        Kunde valgtkunde = kundeNrTabell.getSelectionModel().getSelectedItem();
        Skademelding valgtSkademelding = skademeldingTabell.getSelectionModel().getSelectedItem();

        if(valgtSkademelding != null && valgtkunde != null) {
            String skademeldingInfo = Integer.toString(valgtSkademelding.getSkadeNr());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(hovedApplikasjon.getHovedStage());
            alert.setTitle("Slett alle vitner");
            alert.setHeaderText("Bekreft sletting av vitner");
            alert.setContentText("Er du sikker på at du ønsker å slette alle vitner til\n skademeldingnummer: " + skademeldingInfo +"?");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Bekreft");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                //sletter
                valgtSkademelding.getKontaktinfoVitner().clear();
                kontaktinfoVitnerLabel.setText("");
            }
        }
        else{
            AlertHandler.genererWarningAlert("Slett alle vitner", "Ingen skademelding valgt",
                    "Du må velge en skademelding for å kunne slette vitner!");
        }
    }

    /**
     * Kalles fra RotOppsettController for å gi en referanse til
     * hovedapplikasjonen.*/
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {

        this.hovedApplikasjon = hovedApplikasjon;
        kundeNrTabell.setItems(hovedApplikasjon.getKundeData());
    }
}