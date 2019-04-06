package ae.controller;

import ae.controller.util.UgyldigInputHandler;
import ae.model.Kunde;
import ae.model.exceptions.UgyldigEtternavnException;
import ae.model.exceptions.UgyldigFornavnException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Popup-vindu for å redigere kunde
 */
public class KundeRedigerPopupController {

    @FXML
    private TextField forsikringsNrField, etternavnField, fornavnField,
    adresseFakturaField, datoKundeOpprettetField;

    private Stage popupStage;
    private Kunde kundeÅRedigere;
    private boolean bekreft = false;
    private boolean inputOK = false;

    @FXML
    private void initialize() { }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    /**
     * Plasserer kunden som skal redigeres i popup-vinduet.
     */
    public void setKundeÅRedigere(Kunde kunde) {
        this.kundeÅRedigere = kunde;
        oppdaterFelter();
    }

    /**
     * Metode for å legge inn kundens data i TextFields.
     */
    public void oppdaterFelter() {
        forsikringsNrField.setText(Integer.toString(kundeÅRedigere.getForsikringsNr()));
        etternavnField.setText(kundeÅRedigere.getEtternavn());
        fornavnField.setText(kundeÅRedigere.getFornavn());
        adresseFakturaField.setText(kundeÅRedigere.getAdresseFaktura());
        datoKundeOpprettetField.setText(kundeÅRedigere.getDatoKundeOpprettet().toString());

        forsikringsNrField.setDisable(true);
        datoKundeOpprettetField.setDisable(true);
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
        oppdaterKunde();
        bekreft = true;

        if(inputOK){ //implementert en boolean for å lukke om input er riktig/feil
            popupStage.close();
        }
    }

    public void oppdaterKunde() {
        String msg = "";

        kundeÅRedigere.setForsikringsNr(Integer.parseInt(forsikringsNrField.getText()));

        //Bytter set her ut med metoder (se under)
        msg += redigerFornavn();
        msg += redigerEtternavn();

        kundeÅRedigere.setAdresseFaktura(adresseFakturaField.getText());
        //kundeÅRedigere.setDatoKundeOpprettet(LocalDate.datoKundeOpprettetField.getText());
        // TODO: må parse LocalDate så riktig format lagres

        if(msg.length() != 0){ //kontrollerer etter aktivert feilmeldinger
            UgyldigInputHandler.generateAlert(msg); //alert
        }
        else{
            inputOK = true; //riktig input
        }
    }

    //TODO: METODER FOR ENDRING AV KUNDE
    //oppdaterer fornavn
    private String redigerFornavn() {
        String msg = "";
        try {
            kundeÅRedigere.setFornavn(fornavnField.getText());
        }
        catch (UgyldigFornavnException e) {
            msg +=e.getMessage()+"\n";
        }
        return msg;
    }

    //oppdaterer etternavn
    private String redigerEtternavn() {
        String msg = "";
        try {
            kundeÅRedigere.setEtternavn(etternavnField.getText());
        }
        catch (UgyldigEtternavnException e) {
            msg +=e.getMessage()+"\n";
        }
        return msg;
    }

    @FXML
    public void avbrytTrykkes() {
        popupStage.close();
    }
}