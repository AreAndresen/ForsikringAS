package ae.controller;
import ae.model.Filbehandling;
import ae.model.Kunde;
import ae.model.Viewbehandling;
import ae.util.IdUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ae.HovedApplikasjon;
import java.time.LocalDate;
import java.util.Optional;
import ae.model.Skademelding;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;



public class SkademeldingController {

    // Tabellen.
    @FXML
    private TableView<Skademelding> skademeldingTabell;
    //@FXML
    //private TableColumn<Skademelding, Number> forsikringsNrKolonne; //kunde ID
    @FXML
    private TableColumn<Skademelding, Number> skadeNrKolonne;
    @FXML
    private TableColumn<Skademelding, String> skadeTypeKolonne;
    //@FXML
    //private TableColumn<Skademelding, String> skadeBeskrivelseKolonne;
    @FXML
    private TableColumn<Skademelding, Double> belopTakseringKolonne;
    @FXML
    private TableColumn<Skademelding, Double> erstatningUtbetaltKolonne;
    @FXML
    private TableColumn<Skademelding, LocalDate> datoSkadeKolonne;

    // Labels.
    @FXML
    private Label skadeNrLabel, beskrivelseAvSkadeLabel, vitneInfoLabel, kundeNrLabel;

    private Filbehandling fb = new Filbehandling();

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;

    private Kunde kundeÅRedigere;
    private SkademeldingRedigerPopupController SkadePopup;

    public SkademeldingController() {}

    @FXML
    private void initialize() {
        // Initier skademelding-tabellen med kobling til alle kolonnene
        //forsikringsNrKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeNrProperty());
        skadeNrKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeNrProperty());
        skadeTypeKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeTypeProperty());
        //skadeBeskrivelseKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeBeskrivelseProperty());
        belopTakseringKolonne.setCellValueFactory(celleData -> celleData.getValue().belopTakseringProperty().asObject()); //asObject() på tall
        erstatningUtbetaltKolonne.setCellValueFactory(celleData -> celleData.getValue().erstatningsbelopUtbetaltProperty().asObject());
        datoSkadeKolonne.setCellValueFactory(celleData -> celleData.getValue().datoSkadeProperty());

        // Sender inn null for å tømme feltene.
        visSkademeldingDetaljer(null);

        // ChangeListener som ser etter endringer.
        skademeldingTabell.getSelectionModel().selectedItemProperty().addListener(
                (observable, gammelData, nyData) -> visSkademeldingDetaljer(nyData));
    }

    /*@FXML
    public void gåTilNySkademeldingPopup() {
        Skademelding nySkademelding = new Skademelding(IdUtil.genererLøpenummerSkade(hovedApplikasjon.getSkademeldingData()));
        boolean bekreftTrykket = Viewbehandling.visNySkademeldingPopup(hovedApplikasjon, nySkademelding);

        if (bekreftTrykket) {
            hovedApplikasjon.getSkademeldingData().add(nySkademelding); //legger til ny skademelding i skademelding array

            //TODO MÅ FÅ TIL EN KOBLIG PÅ KUNDENØKKEL TIL SKADEMELDING
            //legger til skademelding til kundearray
            //nt index = SkadePopup.getKundeNrField();
            //int reel = (int)index - 1;
            Kunde kunde = hovedApplikasjon.getKundeData().get(0); //Integer.parseInt(kundeNrKolonne.getText()) -1
            kunde.setSkademeldinger(hovedApplikasjon.getSkademeldingData()); //legger til en skademelding til kunde

            /*
            /TODO MÅ FÅ TIL EN KOBLIG PÅ KUNDENØKKEL TIL SKADEMELDING
            // legger til skademelding til riktig kundearray
            ObservableList<Kunde> kunder =  hovedApplikasjon.getKundeData();
            for(Kunde enKunde : kunder) {
                if (enKunde.getForsikringsNr() == (valgtKunde.getForsikringsNr())) {
                    enKunde.setSkademeldinger(hovedApplikasjon.getSkademeldingData());
                }
            }

        }
    }*/

    @FXML
    public void gåTilRedigerSkademeldingPopup() {
        Skademelding valgtSkademelding = skademeldingTabell.getSelectionModel().getSelectedItem();

        if (valgtSkademelding != null) {
            boolean bekreftTrykket = Viewbehandling.visRedigerSkademeldingPopup(hovedApplikasjon, valgtSkademelding);

            if (bekreftTrykket) {
                visSkademeldingDetaljer(valgtSkademelding);
            }
        }

        // TODO: else: alert boks
    }

    /**
     * Sletter valgt kunde fra listen, med bekreftelse
     */
    @FXML
    public void slettValgtSkademelding() {
        int valgtSkademeldingIndex = skademeldingTabell.getSelectionModel().getSelectedIndex();

        Skademelding valgtSkademelding = skademeldingTabell.getItems().get(valgtSkademeldingIndex);
        String skademeldingInfo = valgtSkademelding.getSkadeNr() +", "+ valgtSkademelding.getSkadeType() +" " +
                ""+ valgtSkademelding.getSkadeBeskrivelse();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(hovedApplikasjon.getHovedStage());
        alert.setTitle("Slett Skademelding");
        alert.setHeaderText("Bekreft sletting av skademelding");
        alert.setContentText("Er du sikker på at du ønsker å slette skademelding " + skademeldingInfo +"?");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Bekreft");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            skademeldingTabell.getItems().remove(valgtSkademeldingIndex);
        }
    }

    //TODO VURDERES OM DENNE TYPEN NAVIGERING SKAL VÆRE MED VIDERE
    //-------KUNDE-------
    //Går til kundeoversikt ved trykk i meny
    @FXML
    private void gåTilKundeoversikt() {
        Viewbehandling.visKundeOversikt(hovedApplikasjon);
        //lagreFilMenuItem.setDisable(false);
    }


    /**
     * Fyller ut info-feltene om hver kunde.
     * Labelen til Forsikringer, Skademeldinger og Ubetalte erstatninger indikerer
     * antall av de ulike typene. Knappene skal trykkes for å vise de.
     */
    public void visSkademeldingDetaljer(Skademelding skademelding) {
        if (skademelding != null) {

            //forsikringsNrLabel.setText(Integer.toString(kunde.));
            skadeNrLabel.setText(Integer.toString(skademelding.getSkadeNr()));
            beskrivelseAvSkadeLabel.setText(skademelding.getSkadeBeskrivelse());
            vitneInfoLabel.setText(skademelding.getKontaktinfoVitner());
            //skadeTypeLabel.setText(skademelding.getSkadeType());
            //utbetaltErstatningLabel.setText(Double.toString(skademelding.getErstatningsbelopUtbetalt()));


        } else {

            // Ingen skademelding valgt, fjerner all tekst.
            skadeNrLabel.setText("");
            beskrivelseAvSkadeLabel.setText("");
            vitneInfoLabel.setText("");
            //skadeTypeLabel.setText("");
            //utbetaltErstatningLabel.setText("");
        }
    }

    /**
     * Kalles fra RotOppsettController for å gi en referanse til
     * hovedapplikasjonen.
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;

        // Legger til data fra ObservableList til tabellen
        //skademeldingTabell.setItems(hovedApplikasjon.getSkademeldingData());
    }
}