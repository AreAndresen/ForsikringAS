package ae.controller;

import ae.HovedApplikasjon;
import ae.controller.util.UgyldigInputHandler;
import ae.model.Båtforsikring;
import ae.model.Forsikring;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

        msg += båtforsikringÅRedigere.sjekkKundeNr(kundeNrField, hovedApplikasjon);
        msg += båtforsikringÅRedigere.sjekkForsikringsNr(forsikringsNrField);
        msg += båtforsikringÅRedigere.sjekkDatoOpprettet(datoOpprettetField);
        msg += båtforsikringÅRedigere.sjekkForsikringsbelop(forsikringsbelopField);
        msg += båtforsikringÅRedigere.sjekkBetingelser(betingelserField);
        msg += båtforsikringÅRedigere.sjekkType(typeField);
        msg += båtforsikringÅRedigere.sjekkRegistreringsNr(regnrField);

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
