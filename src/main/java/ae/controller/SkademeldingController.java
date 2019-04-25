package ae.controller;
import ae.controller.util.UgyldigInputHandler;
import ae.model.Filbehandling;
import ae.model.Kunde;
import ae.model.Viewbehandling;
import ae.util.IdUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ae.HovedApplikasjon;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import ae.model.Skademelding;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;



public class SkademeldingController {

    // Tabellen.
    @FXML
    private TableView<Skademelding> skademeldingTabell;
    @FXML
    private TableColumn<Skademelding, Number> forsikringsNrKolonne; //kunde ID
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
    @FXML
    private TableColumn<Skademelding, String> statusKolonne;

    // Labels.
    @FXML
    private Label skadeNrLabel, beskrivelseAvSkadeLabel, vitneInfoLabel, kundeNrLabel;

    private Filbehandling fb = new Filbehandling();

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;

    public SkademeldingController() {}

    @FXML
    private void initialize() {
        // Initier skademelding-tabellen med kobling til alle kolonnene
        forsikringsNrKolonne.setCellValueFactory(celleData -> celleData.getValue().forsikringsNrProperty());
        skadeNrKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeNrProperty());
        skadeTypeKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeTypeProperty());
        //skadeBeskrivelseKolonne.setCellValueFactory(celleData -> celleData.getValue().skadeBeskrivelseProperty());
        belopTakseringKolonne.setCellValueFactory(celleData -> celleData.getValue().belopTakseringProperty().asObject()); //asObject() på tall
        erstatningUtbetaltKolonne.setCellValueFactory(celleData -> celleData.getValue().erstatningsbelopUtbetaltProperty().asObject());
        datoSkadeKolonne.setCellValueFactory(celleData -> celleData.getValue().datoSkadeProperty());
        statusKolonne.setCellValueFactory(celleData -> celleData.getValue().statusProperty());

        // Sender inn null for å tømme feltene.
        visSkademeldingDetaljer(null);

        // ChangeListener som ser etter endringer.
        skademeldingTabell.getSelectionModel().selectedItemProperty().addListener(
                (observable, gammelData, nyData) -> visSkademeldingDetaljer(nyData));
    }

    @FXML
    public void gåTilNySkademeldingPopup() {
        Skademelding nySkademelding = new Skademelding(IdUtil.genererLøpenummerSkade(hovedApplikasjon.getKundeData()));
        boolean bekreftTrykket = Viewbehandling.visNySkademeldingPopup(hovedApplikasjon, nySkademelding);

        if (bekreftTrykket) {
            //henter
            boolean finnes = false;
            for(Kunde enKunde : hovedApplikasjon.getKundeData()) {
                if (enKunde.getKundeNr() == nySkademelding.getForsikringsNr()) {

                    finnes = true; //kontrollerer at kunden finnes
                    //List<Skademelding> skademeldingerArray = enKunde.getSkademeldinger();

                    ObservableList<Skademelding> skademeldingerArray = enKunde.getSkademeldinger();
                    skademeldingerArray.add(nySkademelding); //legger til ny skademelding

                    enKunde.setSkademeldinger(skademeldingerArray); //legger nytt array inn i kunde


                    //todo MÅ FULLFØRE AT ANTALL SKADEMELDINGER OPPDATERER FORTLØPENDE PÅ ALLE
                    // Legger til antallUbetalte
                    //HashMap<Integer, Double> erstatninger = new HashMap<Integer, Double>();
                    ObservableList<Integer> erstatninger = FXCollections.observableArrayList();

                    for(Skademelding skade : enKunde.getSkademeldinger()){
                        if(skade.getStatus().equals("Ubetalt")){
                            erstatninger.add(skade.getForsikringsNr());
                        }
                    }
                    enKunde.setAntallErstatningerUbetalte(erstatninger);

                }
            }
            if(!finnes) { //kundeNr finnes ikke i kundeData
                UgyldigInputHandler.generateAlert("Det er ingen kunde registrert med det\nkundenummeret i systemet"); //alert
            }
        }
    }

    @FXML
    public void gåTilRedigerSkademeldingPopup() {
        Skademelding valgtSkademelding = skademeldingTabell.getSelectionModel().getSelectedItem();

        if (valgtSkademelding != null) {
            boolean bekreftTrykket = Viewbehandling.visRedigerSkademeldingPopup(hovedApplikasjon, valgtSkademelding);

            if (bekreftTrykket) {
                visSkademeldingDetaljer(valgtSkademelding);


            }
        }
        else{
            UgyldigInputHandler.generateAlert("Du må velge en skademelding for å redigere."); //alert
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

                //gammel metode - har ikke != null
                //slette fra kundedata array her
                /*for(Kunde enKunde : hovedApplikasjon.getKundeData()) {
                    if (enKunde.getKundeNr() == valgtSkademelding.getForsikringsNr()) {
                        enKunde.getSkademeldinger().remove(valgtSkademelding);
                    }
                }*/

                //slette fra kundedata array her
                Kunde slettKundeSkademelding = null;
                for(Kunde enKunde : hovedApplikasjon.getKundeData()) {
                    if (enKunde.getKundeNr() == valgtSkademelding.getForsikringsNr()) {
                        slettKundeSkademelding = enKunde;
                    }
                }
                if(slettKundeSkademelding != null){
                    // slettKundeSkademelding.getSkademeldinger().remove(valgtSkademelding);
                }
            }
        }
        else{
            UgyldigInputHandler.generateAlert("Du må velge en skademelding for å kunne slette."); //alert
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

    /** Fyller ut info-feltene om hver kunde.Labelen til Forsikringer, Skademeldinger og Ubetalte erstatninger indikerer
     * antall av de ulike typene. Knappene skal trykkes for å vise de.*/
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
     * hovedapplikasjonen.*/
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;

        // Legger til data fra ObservableList til tabellen
        //skademeldingTabell.setItems(hovedApplikasjon.getKundeData());
        /*ObservableList<Skademelding> skademeldinger = FXCollections.observableArrayList();

        for(Kunde kunde : hovedApplikasjon.getKundeData()){
            skademeldinger.addAll(kunde.getSkademeldinger());
        }*/

        skademeldingTabell.setItems(hovedApplikasjon.getAlleSkademeldinger());
    }
}