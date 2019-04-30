package ae.controller;

import ae.HovedApplikasjon;
import ae.model.BåtForsikring;
import ae.model.ReiseForsikring;
import ae.util.AlertHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ForsikringReisePopupController {

    // TextFields
    @FXML
    private TextField kundeNrField, forsikringsNrField, datoOpprettetField, forsikringsbelopField, betingelserField,
            typeField, områdeField, sumField;

    private Stage popupStage;
    private ReiseForsikring reiseForsikringÅRedigere;
    private boolean bekreft = false;
    private HovedApplikasjon hovedApplikasjon;

    @FXML
    private void initialize() {}

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setReiseForsikringÅRedigere(ReiseForsikring reiseforsikring) {
        this.reiseForsikringÅRedigere = reiseforsikring;
        instansierFelter();
    }

    private void instansierFelter() {
        kundeNrField.setText(Integer.toString(reiseForsikringÅRedigere.getKundeNr()));
        forsikringsNrField.setText(Integer.toString(reiseForsikringÅRedigere.getForsikringsNr()));
        datoOpprettetField.setText(reiseForsikringÅRedigere.getDatoOpprettet().toString());
        forsikringsbelopField.setText(Double.toString(reiseForsikringÅRedigere.getForsikringsBelop()));
        betingelserField.setText(reiseForsikringÅRedigere.getBetingelser());
        typeField.setText(reiseForsikringÅRedigere.getType());
        områdeField.setText(reiseForsikringÅRedigere.getForsikringsOmråde());
        sumField.setText(Double.toString(reiseForsikringÅRedigere.getForsikringsSum()));

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
        if (sjekkOgOppdaterReiseforsikring()) {
            bekreft = true;
            popupStage.close();
        }
    }

    private boolean sjekkOgOppdaterReiseforsikring() {
        String msg = "";

        msg += reiseForsikringÅRedigere.sjekkOgOppdaterKundeNr(kundeNrField, hovedApplikasjon);
        msg += reiseForsikringÅRedigere.sjekkOgOppdaterForsikringsNr(forsikringsNrField);
        msg += reiseForsikringÅRedigere.sjekkOgOppdaterDatoOpprettet(datoOpprettetField);
        msg += reiseForsikringÅRedigere.sjekkOgOppdaterForsikringsbelop(forsikringsbelopField);
        msg += reiseForsikringÅRedigere.sjekkOgOppdaterBetingelser(betingelserField);
        msg += reiseForsikringÅRedigere.sjekkOgOppdaterType(typeField);
        msg += reiseForsikringÅRedigere.sjekkOgOppdaterForsikringsOmråde(områdeField);
        msg += reiseForsikringÅRedigere.sjekkOgOppdaterForsikringsSum(sumField);

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
