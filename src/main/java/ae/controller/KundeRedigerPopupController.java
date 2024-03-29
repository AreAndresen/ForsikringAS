package ae.controller;

import ae.HovedApplikasjon;
import ae.util.AlertHandler;
import ae.model.Kunde;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Popup-vindu for å redigere kunde
 */
public class KundeRedigerPopupController {

    @FXML
    private TextField kundeNrField, etternavnField, fornavnField,
    adresseFakturaField, datoKundeOpprettetField;

    private Stage popupStage;
    private Kunde kundeÅRedigere;
    private boolean bekreft = false;

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;

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
        instansierFelter();
    }

    /**
     * Metode for å legge inn kundens data i TextFields.
     */
    private void instansierFelter() {
        kundeNrField.setText(Integer.toString(kundeÅRedigere.getKundeNr()));
        etternavnField.setText(kundeÅRedigere.getEtternavn());
        fornavnField.setText(kundeÅRedigere.getFornavn());
        adresseFakturaField.setText(kundeÅRedigere.getAdresseFaktura());
        datoKundeOpprettetField.setText(kundeÅRedigere.getDatoKundeOpprettet().toString());

        kundeNrField.setDisable(true);
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
    private void bekreftTrykkes() {
        if(sjekkOgOppdaterKunde()) { //implementert en boolean for å lukke om input er riktig/feil
            bekreft = true;
            popupStage.close();
        }
    }

    private boolean sjekkOgOppdaterKunde() {
        String msg = "";

        //oppdaterer kundeNr
        msg += kundeÅRedigere.sjekkOgOppdaterKundeNr(kundeNrField, hovedApplikasjon);
        //Oppretter om det ikke finnes
        if (msg.equals("Det er ingen kunde registrert med det\nkundenummeret i systemet.\n")) {
            msg = "";
            msg += kundeÅRedigere.sjekkOgOpprettKundeNr(kundeNrField, hovedApplikasjon);
        }

        msg += kundeÅRedigere.sjekkOgOppdaterFornavn(fornavnField);
        msg += kundeÅRedigere.sjekkOgOppdaterEtternavn(etternavnField);
        msg += kundeÅRedigere.sjekkOgOppdaterAdresseFaktura(adresseFakturaField);
        msg += kundeÅRedigere.sjekkOgOppdaterDatoKundeOpprettet(datoKundeOpprettetField);


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
