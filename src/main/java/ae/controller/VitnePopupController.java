package ae.controller;

import ae.model.Skademelding;
import ae.util.AlertHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class VitnePopupController {

    @FXML
    private TextField vitne1navnField, vitne1tlfField;


    private Stage popupStage;
    private Skademelding skademeldingÅRedigere;
    private boolean bekreft = false;

    @FXML
    private void initialize() {
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
        //Setter info fra siste vitne lagt til
        for (Map.Entry<String, String> info : skademeldingÅRedigere.getKontaktinfoVitner().entrySet()) {
            vitne1navnField.setText(info.getValue());
            vitne1tlfField.setText(info.getKey());
        }
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

        if (sjekkOgOppdaterSkademelding()) { //implementert en boolean for å lukke om input er riktig/feil
            bekreft = true;
            popupStage.close();
        }
    }

    private boolean sjekkOgOppdaterSkademelding() {
        String msg = "";

        //vitner
        msg += skademeldingÅRedigere.sjekkOgOppdaterKontaktinfoVitne(vitne1navnField, vitne1tlfField);

        //kontrollerer etter aktiverte feilmeldinger
        if (msg.length() != 0) {
            AlertHandler.genererUgyldigInputAlert(msg);
            return false;
        } else {
            return true; //riktig input
        }
    }

    @FXML
    private void avbrytTrykkes() {
        popupStage.close();
    }
}

