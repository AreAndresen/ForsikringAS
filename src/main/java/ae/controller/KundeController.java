package ae.controller;

import ae.util.AlertHandler;
import ae.model.*;
import ae.util.IdUtil;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ae.HovedApplikasjon;

import java.time.LocalDate;
import java.util.Optional;


public class KundeController {

    // Tabellen.
    @FXML
    private TableView<Kunde> kundeTabell;
    @FXML
    private TableColumn<Kunde, Number> kundeNrKolonne;
    @FXML
    private TableColumn<Kunde, String> etternavnKolonne;
    @FXML
    private TableColumn<Kunde, String> fornavnKolonne;
    @FXML
    private TableColumn<Kunde, String> adresseFakturaKolonne;
    @FXML
    private TableColumn<Kunde, LocalDate> datoKundeOpprettetKolonne;

    @FXML
    public Button redigerButton, slettButton;

    // Labels.
    @FXML
    private Label kundeNrLabel, etternavnLabel, fornavnLabel, adresseFakturaLabel,
            datoKundeOpprettetLabel, forsikringerLabel, skademeldingerLabel, erstatningerUbetalteLabel;

    // Søke-feltet.
    @FXML
    private TextField søkField;

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;

    public KundeController() {}

    @FXML
    private void initialize() {
        // Initier kunde-tabellen med kobling til alle kolonnene
        kundeNrKolonne.setCellValueFactory(celleData -> celleData.getValue().kundeNrProperty());
        etternavnKolonne.setCellValueFactory(celleData -> celleData.getValue().etternavnProperty());
        fornavnKolonne.setCellValueFactory(celleData -> celleData.getValue().fornavnProperty());
        adresseFakturaKolonne.setCellValueFactory(celleData -> celleData.getValue().adresseFakturaProperty());
        datoKundeOpprettetKolonne.setCellValueFactory(celleData -> celleData.getValue().datoKundeOpprettetProperty());

        // Sender inn null for å tømme feltene.
        visKundensDetaljer(null);

        // ChangeListener som ser etter endringer.
        kundeTabell.getSelectionModel().selectedItemProperty().addListener(
                (observable, gammelData, nyData) -> visKundensDetaljer(nyData));

        // Behandling av søk
        søkField.textProperty().addListener((((observable, gammelVerdi, nyVerdi) -> {
            FilteredList<Kunde> kundeFiltered = new FilteredList<>(hovedApplikasjon.getKundeData(), k -> true);

            kundeFiltered.setPredicate(kunde -> Kunde.behandleSøk(kunde, nyVerdi));

            SortedList<Kunde> kundeSorted = new SortedList<>(kundeFiltered);
            kundeSorted.comparatorProperty().bind(kundeTabell.comparatorProperty());
            kundeTabell.setItems(kundeSorted);
        })));
    }

    @FXML
    public void gåTilNyKundePopup() {
        int index = IdUtil.genererLøpenummerKunde(hovedApplikasjon.getKundeData());
        Kunde nyKunde = new Kunde(index);
        boolean bekreftTrykket = Viewbehandling.visNyKundePopup(hovedApplikasjon, nyKunde);

        if (bekreftTrykket) {
            hovedApplikasjon.getKundeData().add(nyKunde);
        }
    }

    @FXML
    public void gåTilRedigerKundePopup() {
        Kunde valgtKunde = kundeTabell.getSelectionModel().getSelectedItem();

        if (valgtKunde != null) {
            boolean bekreftTrykket = Viewbehandling.visRedigerKundePopup(hovedApplikasjon, valgtKunde);

            if (bekreftTrykket) {
                visKundensDetaljer(valgtKunde);
            }
        }
        else{
            AlertHandler.genererWarningAlert("Rediger kunde", "Ingen kunde valgt",
                    "Du må velge en kunde for å redigere!");
        }
    }

    /**
     * Sletter valgt kunde fra listen, med bekreftelse
     */
    @FXML
    public void slettValgtKunde() {
        Kunde valgtKunde = kundeTabell.getSelectionModel().getSelectedItem();

        if(valgtKunde != null){
            String kundeInfo = Integer.toString(valgtKunde.getKundeNr());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(hovedApplikasjon.getHovedStage());
            alert.setTitle("Slett kunde");
            alert.setHeaderText("Bekreft sletting av kunde");
            alert.setContentText("Er du sikker på at du ønsker å slette kunde nummer: " + kundeInfo +"?");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Bekreft");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                hovedApplikasjon.getKundeData().remove(valgtKunde);
            }
        }
        else{
            AlertHandler.genererWarningAlert("Slett kunde", "Ingen kunde valgt",
                    "Du må velge en kunde for å kunne slette!");
        }
    }

    //-------SKADEMELDING-------
    //Går til skademeldingversikt ved trykk på knapp for skademeldinger
    @FXML
    private void gåTilSkademeldingoversikt() {
        Viewbehandling.visSkademeldingOversikt(hovedApplikasjon);
        //lagreFilMenuItem.setDisable(false);
    }

    //-------Forsikringer-------
    //Går til forsikringsversikt ved trykk på knapp for forsikringer
    @FXML
    private void gåTilForsikringoversikt() {
        Viewbehandling.visSkademeldingOversikt(hovedApplikasjon);
        //lagreFilMenuItem.setDisable(false);
    }


    /**
     * Fyller ut info-feltene om hver kunde.
     * Labelen til Forsikringer, Skademeldinger og Ubetalte erstatninger indikerer
     * antall av de ulike typene. Knappene skal trykkes for å vise de.
     */
    public void visKundensDetaljer(Kunde kunde) {
        if (kunde != null) {

            kundeNrLabel.setText(Integer.toString(kunde.getKundeNr()));
            etternavnLabel.setText(kunde.getEtternavn());
            fornavnLabel.setText(kunde.getFornavn());
            adresseFakturaLabel.setText(kunde.getAdresseFaktura());
            datoKundeOpprettetLabel.setText(kunde.getDatoKundeOpprettet().toString());
            forsikringerLabel.setText(Integer.toString(kunde.getForsikringer().size()));
            skademeldingerLabel.setText(Integer.toString(kunde.getSkademeldinger().size()));
            erstatningerUbetalteLabel.setText(Integer.toString(kunde.getAntallErstatningerUbetalte()));

        } else {

            // Ingen kunde valgt, fjerner all tekst.
            kundeNrLabel.setText("");
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
        kundeTabell.setItems(hovedApplikasjon.getKundeData());
    }
}
