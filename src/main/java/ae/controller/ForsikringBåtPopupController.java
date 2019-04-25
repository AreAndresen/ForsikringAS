package ae.controller;

import ae.HovedApplikasjon;
import ae.controller.util.UgyldigInputHandler;
import ae.model.Båtforsikring;
import ae.model.Forsikring;
import ae.model.Kunde;
import ae.model.exceptions.UgyldigDatoException;
import ae.model.exceptions.forsikring.UgyldigForsikringsbelopException;
import ae.model.exceptions.forsikring.UgyldigForsikringsnrException;
import ae.model.exceptions.UgyldigKundenrException;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Popup-vindu for å opprette og redigere båtforsikring.
 */

public class ForsikringBåtPopupController {

    // textfields
    @FXML
    private TextField kundeNrField, forsikringsNrField, datoOpprettetField, forsikringsbelopField, betingelserField,
            typeField, regnrField, båttypeField, lengdeFotField, årsmodellField, motortypeField;

    private Stage popupStage;
    private Båtforsikring båtforsikringÅRedigere;
    private boolean bekreft = false;
    private boolean inputOK = false;
    private HovedApplikasjon hovedApplikasjon;

    @FXML
    private void initialize() {}

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setBåtforsikringÅRedigere(Båtforsikring båtforsikring) {
        this.båtforsikringÅRedigere = båtforsikring;
        oppdaterFelter();
    }

    private void oppdaterFelter() {
        kundeNrField.setText(Integer.toString(båtforsikringÅRedigere.getKundeNr()));
        forsikringsNrField.setText(Integer.toString(båtforsikringÅRedigere.getForsikringsNr()));
        datoOpprettetField.setText(båtforsikringÅRedigere.getDatoOpprettet().toString());
        forsikringsbelopField.setText(Integer.toString(båtforsikringÅRedigere.getForsikringsBelop()));
        betingelserField.setText(båtforsikringÅRedigere.getBetingelser());
        typeField.setText(båtforsikringÅRedigere.getType());
        regnrField.setText(båtforsikringÅRedigere.getRegistreringsNr());
        båttypeField.setText(båtforsikringÅRedigere.getTypeModell());
        lengdeFotField.setText(Integer.toString(båtforsikringÅRedigere.getLengdeFot()));
        årsmodellField.setText(Integer.toString(båtforsikringÅRedigere.getÅrsmodell()));
        motortypeField.setText(båtforsikringÅRedigere.getMotorEgenskaper());

        typeField.setPromptText("Båtforsikring");
        forsikringsNrField.setDisable(true);
        datoOpprettetField.setDisable(true);
        typeField.setDisable(true);
    }

    public boolean erBekreftTrykket() {
        return bekreft;
    }

    @FXML
    public void bekreftTrykkes() {
        sjekkBåtforsikring();

        if (inputOK) {
            bekreft = true;
            popupStage.close();
        }
    }

    private void sjekkBåtforsikring() {
        String msg = "";

        msg += Forsikring.sjekkKundeNr(kundeNrField, hovedApplikasjon, (Båtforsikring) båtforsikringÅRedigere);
        msg += Forsikring.sjekkForsikringsNr(forsikringsNrField, (Båtforsikring) båtforsikringÅRedigere);
        msg += Forsikring.sjekkDatoOpprettet(datoOpprettetField, (Båtforsikring) båtforsikringÅRedigere);
        msg += Forsikring.sjekkForsikringsbelop(forsikringsbelopField, (Båtforsikring) båtforsikringÅRedigere);

        if (msg.length() != 0) {
            UgyldigInputHandler.generateAlert(msg);
        } else {
            inputOK = true;
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
