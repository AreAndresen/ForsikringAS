package ae.controller;

import ae.HovedApplikasjon;
import ae.util.AlertHandler;
import ae.model.Skademelding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SkademeldingRedigerPopupController {

    @FXML
    private TextField kundeNrField, skadeNrField,
            belopTakseringField, erstatningsbelopUtbetaltField;
    @FXML
    private ChoiceBox statusField, skadeTypeField;
    @FXML
    private DatePicker datoSkademeldingOpprettetField;
    @FXML
    private TextArea skadebeskrivelseField;

    private ObservableList<String> statusListe =
            FXCollections.observableArrayList("Betalt", "Ubetalt");
    private ObservableList<String> skadeTypeListe =
            FXCollections.observableArrayList("Båtforsikring",
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
    private void instansierFelter() {
        kundeNrField.setText(Integer.toString(skademeldingÅRedigere.getKundeNr()));
        skadeNrField.setText(Integer.toString(skademeldingÅRedigere.getSkadeNr()));
        skadebeskrivelseField.setText(skademeldingÅRedigere.getSkadeBeskrivelse());
        belopTakseringField.setText(Long.toString(skademeldingÅRedigere.getBelopTaksering()));
        erstatningsbelopUtbetaltField.setText(Long.toString(skademeldingÅRedigere.getErstatningsbelopUtbetalt()));
        datoSkademeldingOpprettetField.setValue(skademeldingÅRedigere.getDatoSkade());

        kundeNrField.setDisable(true);
        skadeNrField.setDisable(true);
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
    private void bekreftTrykkes() {

        if (sjekkOgOppdaterSkademelding()) {
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
        msg += skademeldingÅRedigere.sjekkOgOppdaterStatus(statusField);

        //kontrollerer etter aktiverte feilmeldinger
        if (msg.length() != 0) {
            AlertHandler.genererUgyldigInputAlert(msg);
            return false;
        } else {
            return true; //riktig input
        }
    }

    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;
    }

    @FXML
    private void avbrytTrykkes() {
        popupStage.close();
    }
}
