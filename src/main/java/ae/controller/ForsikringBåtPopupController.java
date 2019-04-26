package ae.controller;

import ae.HovedApplikasjon;
import ae.controller.util.UgyldigInputHandler;
import ae.model.Båtforsikring;
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
    private HovedApplikasjon hovedApplikasjon;

    @FXML
    private void initialize() {}

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setBåtforsikringÅRedigere(Båtforsikring båtforsikring) {
        this.båtforsikringÅRedigere = båtforsikring;
        instansierFelter();
    }

    private void instansierFelter() {
        kundeNrField.setText(Integer.toString(båtforsikringÅRedigere.getKundeNr()));
        forsikringsNrField.setText(Integer.toString(båtforsikringÅRedigere.getForsikringsNr()));
        datoOpprettetField.setText(båtforsikringÅRedigere.getDatoOpprettet().toString());
        forsikringsbelopField.setText(Double.toString(båtforsikringÅRedigere.getForsikringsBelop()));
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

        if (sjekkOgOppdaterBåtforsikring()) {
            bekreft = true;
            popupStage.close();
        }
    }

    private boolean sjekkOgOppdaterBåtforsikring() {
        String msg = "";

        msg += båtforsikringÅRedigere.sjekkOgOppdaterKundeNr(kundeNrField, hovedApplikasjon);
        msg += båtforsikringÅRedigere.sjekkOgOppdaterForsikringsNr(forsikringsNrField);
        msg += båtforsikringÅRedigere.sjekkOgOppdaterDatoOpprettet(datoOpprettetField);
        msg += båtforsikringÅRedigere.sjekkOgOppdaterForsikringsbelop(forsikringsbelopField);
        msg += båtforsikringÅRedigere.sjekkOgOppdaterBetingelser(betingelserField);
        msg += båtforsikringÅRedigere.sjekkOgOppdaterType(typeField);
        msg += båtforsikringÅRedigere.sjekkOgOppdaterRegistreringsNr(regnrField);
        msg += båtforsikringÅRedigere.sjekkOgOppdaterTypeModell(båttypeField);
        msg += båtforsikringÅRedigere.sjekkOgOppdaterLengdeFot(lengdeFotField);
        msg += båtforsikringÅRedigere.sjekkOgOppdaterÅrsmodell(årsmodellField);
        msg += båtforsikringÅRedigere.sjekkOgOppdaterMotorEgenskaper(motortypeField);

        if (msg.length() != 0) {
            UgyldigInputHandler.generateAlert(msg);
            return false;
        } else {
            return true;
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
