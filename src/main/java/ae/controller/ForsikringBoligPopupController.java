package ae.controller;


import ae.HovedApplikasjon;
import ae.util.AlertHandler;
import ae.model.BoligForsikring;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Popup vindu for å opprette og redigere Hus- og innboforsikring
 */
public class ForsikringBoligPopupController {

    // textfields + choicebox
    @FXML
    private TextField kundeNrField, forsikringsNrField, premieField, datoOpprettetField, forsikringsbelopField, betingelserField,
            typeField, adresseBoligField, byggeårField, byggematerialeField, antallKvmField, forsikringsbelopBygningField,
            forsikringsbelopInnboField;
    @FXML
    private ChoiceBox standardChoice;

    private Stage popupStage;
    private BoligForsikring boligForsikringÅRedigere;
    private boolean bekreft = false;
    private HovedApplikasjon hovedApplikasjon;

    // choice box
    private ObservableList<String> typeSortering = FXCollections.observableArrayList("Høy", "Middels", "Lav");

    @FXML
    private void initialize() {
        standardChoice.setValue("Middels");
        standardChoice.setItems(typeSortering);
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setBoligForsikringÅRedigere(BoligForsikring boligforsikring) {
        this.boligForsikringÅRedigere = boligforsikring;
        instansierFelter();
    }

    private void instansierFelter() {
        kundeNrField.setText(Integer.toString(boligForsikringÅRedigere.getKundeNr()));
        forsikringsNrField.setText(Integer.toString(boligForsikringÅRedigere.getForsikringsNr()));
        premieField.setText(Long.toString(boligForsikringÅRedigere.getÅrligPremie()));
        datoOpprettetField.setText(boligForsikringÅRedigere.getDatoOpprettet().toString());
        forsikringsbelopField.setText(Long.toString(boligForsikringÅRedigere.getForsikringsBelop()));
        betingelserField.setText(boligForsikringÅRedigere.getBetingelser());
        typeField.setText(boligForsikringÅRedigere.getType());
        adresseBoligField.setText(boligForsikringÅRedigere.getAdresseBolig());
        byggeårField.setText(Integer.toString(boligForsikringÅRedigere.getByggeår()));
        byggematerialeField.setText(boligForsikringÅRedigere.getByggemateriale());
        antallKvmField.setText(Integer.toString(boligForsikringÅRedigere.getAntallKvm()));
        forsikringsbelopBygningField.setText(Long.toString(boligForsikringÅRedigere.getForsikringsbelopBygning()));
        forsikringsbelopInnboField.setText(Long.toString(boligForsikringÅRedigere.getForsikringsbelopInnbo()));

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

        if (sjekkOgOppdaterBoligforsikring()) {
            bekreft = true;
            popupStage.close();
        }
    }

    private boolean sjekkOgOppdaterBoligforsikring() {
        String msg = "";

        msg += boligForsikringÅRedigere.sjekkOgOppdaterKundeNr(kundeNrField, hovedApplikasjon);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterForsikringsNr(forsikringsNrField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterÅrligPremie(premieField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterDatoOpprettet(datoOpprettetField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterForsikringsbelop(forsikringsbelopField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterBetingelser(betingelserField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterType(typeField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterAdresseBolig(adresseBoligField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterByggeår(byggeårField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterByggemateriale(byggematerialeField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterStandard(standardChoice);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterAntallKvm(antallKvmField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterForsikringsbelopBygning(forsikringsbelopBygningField);
        msg += boligForsikringÅRedigere.sjekkOgOppdaterForsikringsbelopInnbo(forsikringsbelopInnboField);


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
