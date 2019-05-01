package ae.controller;

import ae.HovedApplikasjon;
import ae.model.Skademelding;
import ae.util.AlertHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class VitnePopupController {

    @FXML
    private TextField vitne1navnField, vitne1tlfField;


    private Stage popupStage;
    private Skademelding skademeldingÅRedigere;
    private boolean bekreft = false;

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;

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
    public void instansierFelter() {
        //Ny vitner
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
    public void bekreftTrykkes() {

        if(sjekkOgOppdaterSkademelding()){ //implementert en boolean for å lukke om input er riktig/feil
            bekreft = true;
            popupStage.close();
        }
    }

    private boolean sjekkOgOppdaterSkademelding() {
        String msg = "";

        //vitner
        msg += skademeldingÅRedigere.sjekkOgOppdaterKontaktinfoVitne(vitne1navnField, vitne1tlfField);

        //kontrollerer etter aktiverte feilmeldinger
        if(msg.length() != 0){
            AlertHandler.genererUgyldigInputAlert(msg);
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

