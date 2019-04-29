package ae.controller;

import ae.HovedApplikasjon;
import ae.util.AlertHandler;
import ae.model.BåtForsikring;
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
    private BåtForsikring båtForsikringÅRedigere;
    private boolean bekreft = false;
    private HovedApplikasjon hovedApplikasjon;

    @FXML
    private void initialize() {}

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setBåtForsikringÅRedigere(BåtForsikring båtforsikring) {
        this.båtForsikringÅRedigere = båtforsikring;
        instansierFelter();
    }

    private void instansierFelter() {
        kundeNrField.setText(Integer.toString(båtForsikringÅRedigere.getKundeNr()));
        forsikringsNrField.setText(Integer.toString(båtForsikringÅRedigere.getForsikringsNr()));
        datoOpprettetField.setText(båtForsikringÅRedigere.getDatoOpprettet().toString());
        forsikringsbelopField.setText(Double.toString(båtForsikringÅRedigere.getForsikringsBelop()));
        betingelserField.setText(båtForsikringÅRedigere.getBetingelser());
        typeField.setText(båtForsikringÅRedigere.getType());
        regnrField.setText(båtForsikringÅRedigere.getRegistreringsNr());
        båttypeField.setText(båtForsikringÅRedigere.getTypeModell());
        lengdeFotField.setText(Integer.toString(båtForsikringÅRedigere.getLengdeFot()));
        årsmodellField.setText(Integer.toString(båtForsikringÅRedigere.getÅrsmodell()));
        motortypeField.setText(båtForsikringÅRedigere.getMotorEgenskaper());

        kundeNrField.setDisable(true);
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

        msg += båtForsikringÅRedigere.sjekkOgOppdaterKundeNr(kundeNrField, hovedApplikasjon);
        msg += båtForsikringÅRedigere.sjekkOgOppdaterForsikringsNr(forsikringsNrField);
        msg += båtForsikringÅRedigere.sjekkOgOppdaterDatoOpprettet(datoOpprettetField);
        msg += båtForsikringÅRedigere.sjekkOgOppdaterForsikringsbelop(forsikringsbelopField);
        msg += båtForsikringÅRedigere.sjekkOgOppdaterBetingelser(betingelserField);
        msg += båtForsikringÅRedigere.sjekkOgOppdaterType(typeField);
        msg += båtForsikringÅRedigere.sjekkOgOppdaterRegistreringsNr(regnrField);
        msg += båtForsikringÅRedigere.sjekkOgOppdaterTypeModell(båttypeField);
        msg += båtForsikringÅRedigere.sjekkOgOppdaterLengdeFot(lengdeFotField);
        msg += båtForsikringÅRedigere.sjekkOgOppdaterÅrsmodell(årsmodellField);
        msg += båtForsikringÅRedigere.sjekkOgOppdaterMotorEgenskaper(motortypeField);

        if (msg.length() != 0) {
            AlertHandler.genererUgyldigInputAlert(msg);
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
