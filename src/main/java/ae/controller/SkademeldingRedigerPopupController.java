package ae.controller;

import ae.HovedApplikasjon;
import ae.controller.util.UgyldigInputHandler;
import ae.model.Skademelding;
import ae.model.exceptions.UgyldigBelopException;
import ae.model.exceptions.UgyldigInputException;
import ae.model.exceptions.UgyldigLopeNrException;
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
    private boolean inputOK = false;

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

        oppdaterFelter();
    }

    /**
     * Metode for å legge inn kundens data i TextFields.
     */
    public void oppdaterFelter() {
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
        // TODO: input-validering med exceptions venter
        oppdaterSkademelding();


        if(inputOK){ //implementert en boolean for å lukke om input er riktig/feil
            bekreft = true;
            popupStage.close();
        }
    }

    public void oppdaterSkademelding() {
        String msg = "";

        //skademeldingÅRedigere.setSkadeNr(Integer.parseInt(skadeNrField.getText()));

        //Bytter set her ut med metoder (se under)

        msg += skademeldingÅRedigere.sjekkOgOppdaterSkadeNr(skadeNrField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterKundeNr(kundeNrField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterDatoSkade(datoSkademeldingOpprettetField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterSkadetype(skadeTypeField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterSkadebeskrivelse(skadebeskrivelseField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterTakseringsbeløp(belopTakseringField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterErstatningsbelopUtbetalt(erstatningsbelopUtbetaltField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterKontaktinfoVitner(vitneInfoField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterStatus(statusField);

        //kundeÅRedigere.setDatoKundeOpprettet(LocalDate.datoKundeOpprettetField.getText());
        // TODO: må parse LocalDate så riktig format lagres

        //kontrollerer etter aktiverte feilmeldinger
        if(msg.length() != 0){
            UgyldigInputHandler.generateAlert(msg); //alert
        }
        else{
            inputOK = true; //riktig input
        }
    }


    @FXML
    public void avbrytTrykkes() {
        popupStage.close();
    }
}
