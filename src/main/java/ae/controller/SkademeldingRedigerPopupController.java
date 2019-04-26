package ae.controller;

import ae.HovedApplikasjon;
import ae.controller.util.UgyldigInputHandler;
import ae.model.Skademelding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SkademeldingRedigerPopupController {

    @FXML
    private TextField kundeNrField, skadeNrField, skadebeskrivelseField,
            belopTakseringField, erstatningsbelopUtbetaltField, datoSkademeldingOpprettetField;
    @FXML
    private ChoiceBox statusField, skadeTypeField;
    @FXML
    private TextArea vitneInfoField;

    private ObservableList<String> statusListe = FXCollections.observableArrayList("Betalt", "Ubetalt");
    private ObservableList<String> skadeTypeListe = FXCollections.observableArrayList("Båtforsikring",
            "Hus- og innboforsikring", "Fritidsboligforsikring", "Reiseforsikring");

    private Stage popupStage;
    private Skademelding skademeldingÅRedigere;
    private boolean bekreft = false;

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;


    @FXML
    private void initialize() {
        statusField.setValue("Betalt");
        statusField.setItems(statusListe);

        skadeTypeField.setValue("Båtforsikring");
        skadeTypeField.setItems(skadeTypeListe);
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    /**
     * Plasserer skademeldingen som skal redigeres i popup-vinduet.
     */
    public void setSkademeldingÅRedigere(Skademelding skademelding) {
        this.skademeldingÅRedigere = skademelding;

        instansierFelter();
    }

    /**
     * Metode for å legge inn kundens data i TextFields.
     */
    public void instansierFelter() {
        kundeNrField.setText(Integer.toString(skademeldingÅRedigere.getKundeNr()));
        skadeNrField.setText(Integer.toString(skademeldingÅRedigere.getSkadeNr()));
        //skadeTypeField.setText(skademeldingÅRedigere.getSkadeType());
        skadebeskrivelseField.setText(skademeldingÅRedigere.getSkadeBeskrivelse());
        belopTakseringField.setText(Double.toString(skademeldingÅRedigere.getBelopTaksering()));
        erstatningsbelopUtbetaltField.setText(Double.toString(skademeldingÅRedigere.getErstatningsbelopUtbetalt()));
        datoSkademeldingOpprettetField.setText(skademeldingÅRedigere.getDatoSkade().toString());
        vitneInfoField.setText(skademeldingÅRedigere.getKontaktinfoVitner());
        //statusField.setValue(skademeldingÅRedigere.get);

        //forsikringsNrField.setDisable(true);
        skadeNrField.setDisable(true);
        datoSkademeldingOpprettetField.setDisable(true);
    }

    /**
     * Returnerer true dersom bruker trykker på Bekreft, hvis ikke
     * så false
     */
    public boolean erBekreftTrykket() {
        return bekreft;
    }

    /**
     * Kalles når bruker trykker Bekreft.
     */
    @FXML
    public void bekreftTrykkes() {

        if(sjekkOgOppdaterSkademelding()){ //implementert en boolean for å lukke om input er riktig/feil
            bekreft = true;
            popupStage.close();
        }
    }

    private boolean sjekkOgOppdaterSkademelding() {
        String msg = "";

        msg += skademeldingÅRedigere.sjekkOgOppdaterSkadeNr(skadeNrField);

        msg += skademeldingÅRedigere.sjekkOgOppdaterKundeNr(kundeNrField, hovedApplikasjon);

        msg += skademeldingÅRedigere.sjekkOgOppdaterDatoSkade(datoSkademeldingOpprettetField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterSkadetype(skadeTypeField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterSkadebeskrivelse(skadebeskrivelseField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterTakseringsbeløp(belopTakseringField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterErstatningsbelopUtbetalt(erstatningsbelopUtbetaltField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterKontaktinfoVitner(vitneInfoField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterStatus(statusField);


        //kontrollerer etter aktiverte feilmeldinger
        if(msg.length() != 0){
            UgyldigInputHandler.generateAlert(msg); //alert
            return false;
        }
        else{
            return true; //riktig input
        }
    }

    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;
    }

    @FXML
    public void avbrytTrykkes() {
        popupStage.close();
    }
}
