package ae.controller;

import ae.HovedApplikasjon;
import ae.controller.util.UgyldigInputHandler;
import ae.model.Skademelding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VitneRedigerPopupController {
/*
    @FXML
    private TextField navnField, tlfField;

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
     *
    public void setSkademeldingÅRedigere(Skademelding skademelding) {
        this.skademeldingÅRedigere = skademelding;

        instansierFelter();
    }

    /**
     * Metode for å legge inn kundens data i TextFields.
     *
    public void instansierFelter() {
        //navnField.setText(Integer.toString(skademeldingÅRedigere.getKundeNr()));
        //tlfField.setText(Integer.toString(skademeldingÅRedigere.getSkadeNr()));

    }

    /**
     * Returnerer true dersom bruker trykker på Bekreft, hvis ikke
     * så false
     *
    public boolean erBekreftTrykket() {
        return bekreft;
    }

    /**
     * Kalles når bruker trykker Bekreft.

    @FXML
    public void bekreftTrykkes() {

        if(sjekkOgOppdaterSkademelding()){ //implementert en boolean for å lukke om input er riktig/feil
            bekreft = true;
            popupStage.close();
        }
    }

    private boolean sjekkOgOppdaterSkademelding() {
        String msg = "";

        msg += skademeldingÅRedigere.sjekkOgOppdaterNavn(navnField);
        msg += skademeldingÅRedigere.sjekkOgOppdaterTlf(tlfField);

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
    }*/
}
