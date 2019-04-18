package ae.controller;

import ae.model.*;
import ae.util.IdUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ae.HovedApplikasjon;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private Label forsikringsNrLabel, etternavnLabel, fornavnLabel, adresseFakturaLabel,
            datoKundeOpprettetLabel, forsikringerLabel, skademeldingerLabel, erstatningerUbetalteLabel;

    private Filbehandling fb = new Filbehandling();

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;

    public KundeController() {}

    @FXML
    private void initialize() {
        // Initier kunde-tabellen med kobling til alle kolonnene
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

    // TODO: flytte denne til rotoppsettet
    @FXML
    public void gåTilNyKundePopup() {
        int index = IdUtil.genererLøpenummer(hovedApplikasjon.getKundeData());
        Kunde nyKunde = new Kunde(index);
        boolean bekreftTrykket = Viewbehandling.visNyKundePopup(hovedApplikasjon, nyKunde);

        if (bekreftTrykket) {
            hovedApplikasjon.getKundeData().add(nyKunde);
        }
    }

    /*TODO MÅ FIKSES - NY METODE FOR DIREKTE SKADEMEDLING ---------
    @FXML
    public void gåTilNySkademeldingPopupKunde() {
        Kunde valgtKunde = kundeTabell.getSelectionModel().getSelectedItem();

        if (valgtKunde != null) {
            Skademelding nySkademelding = new Skademelding(IdUtil.genererLøpenummerSkade(hovedApplikasjon.getSkademeldingData()));
            boolean bekreftTrykket = Viewbehandling.visNySkademeldingPopup(hovedApplikasjon, nySkademelding);

            if (bekreftTrykket) {
                hovedApplikasjon.getSkademeldingData().add(nySkademelding); //legger til ny skademelding i skademelding array


                //TODO MÅ FÅ TIL EN KOBLIG PÅ KUNDENØKKEL TIL SKADEMELDING
                // legger til skademelding til riktig kundearray

                List<Skademelding> skademeldingerArray = new ArrayList<>(); //array som skal fylles hver gang

                //henter
                ObservableList<Kunde> kunder =  hovedApplikasjon.getKundeData(); //kundeData
                for(Kunde enKunde : kunder) {
                    if (enKunde.getForsikringsNr() == (valgtKunde.getForsikringsNr())) {
                        //enKunde.setSkademeldinger(hovedApplikasjon.getSkademeldingData());

                        //FORTSETT HER - GI SKADEMELDING forsikringsNr (kundeId)
                        for(Skademelding melding : hovedApplikasjon.getSkademeldingData()){
                                if(melding.getForsikringsNr() == enKunde.getForsikringsNr()){
                                    skademeldingerArray.add(melding);
                                }
                        }
                        enKunde.setSkademeldinger(skademeldingerArray);

                    }
                }
            }
        }
    }*/

    @FXML
    public void gåTilRedigerKundePopup() {
        Kunde valgtKunde = kundeTabell.getSelectionModel().getSelectedItem();

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
        int valgtKundeIndex = kundeTabell.getSelectionModel().getSelectedIndex();

        try{
            if(valgtKundeIndex != 0){
                Kunde valgtKunde = kundeTabell.getItems().get(valgtKundeIndex);
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
                    kundeTabell.getItems().remove(valgtKundeIndex);
                }
            }
        }
        catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    //TODO VURDERES OM DENNE TYPEN NAVIGERING SKAL VÆRE MED VIDERE
    //-------SKADEMELDING-------
    //Går til skademeldingversikt ved trykk i meny
    @FXML
    private void gåTilSkademeldingoversikt() {
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
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;

        // Legger til data fra ObservableList til tabellen
        kundeTabell.setItems(hovedApplikasjon.getKundeData());
    }
}
