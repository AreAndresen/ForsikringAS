package ae.controller;


import ae.HovedApplikasjon;
import ae.controller.util.UgyldigInputHandler;
import ae.model.BåtForsikring;
import ae.model.HusOgInnboForsikring;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Popup vindu for å opprette og redigere Hus- og innboforsikring
 */
public class ForsikringHusOgInnboPopupController {

    // textfields + choicebox
    @FXML
    private TextField kundeNrField, forsikringsNrField, datoOpprettetField, forsikringsbelopField, betingelserField,
            typeField, adresseBoligField, byggeårField, byggematerialeField, antallKvmField, forsikringsbelopBygningField,
            forsikringsbelopInnboField;
    @FXML
    private ChoiceBox standardChoice;

    private Stage popupStage;
    private HusOgInnboForsikring innboForsikringÅRedigere;
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

    public void setInnboForsikringÅRedigere(HusOgInnboForsikring innboforsikring) {
        this.innboForsikringÅRedigere = innboforsikring;
        instansierFelter();
    }

    private void instansierFelter() {
        kundeNrField.setText(Integer.toString(innboForsikringÅRedigere.getKundeNr()));
        forsikringsNrField.setText(Integer.toString(innboForsikringÅRedigere.getForsikringsNr()));
        datoOpprettetField.setText(innboForsikringÅRedigere.getDatoOpprettet().toString());
        forsikringsbelopField.setText(Double.toString(innboForsikringÅRedigere.getForsikringsBelop()));
        betingelserField.setText(innboForsikringÅRedigere.getBetingelser());
        typeField.setText(innboForsikringÅRedigere.getType());
        adresseBoligField.setText(innboForsikringÅRedigere.getAdresseBolig());
        byggeårField.setText(Integer.toString(innboForsikringÅRedigere.getByggeår()));
        byggematerialeField.setText(innboForsikringÅRedigere.getByggemateriale());
        antallKvmField.setText(Integer.toString(innboForsikringÅRedigere.getAntallKvm()));
        forsikringsbelopBygningField.setText(Double.toString(innboForsikringÅRedigere.getForsikringsbelopBygning()));
        forsikringsbelopInnboField.setText(Double.toString(innboForsikringÅRedigere.getForsikringsbelopInnbo()));

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

        if (sjekkOgOppdaterInnboforsikring()) {
            bekreft = true;
            popupStage.close();
        }
    }

    private boolean sjekkOgOppdaterInnboforsikring() {
        String msg = "";

        msg += innboForsikringÅRedigere.sjekkOgOppdaterKundeNr(kundeNrField, hovedApplikasjon);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterForsikringsNr(forsikringsNrField);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterDatoOpprettet(datoOpprettetField);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterForsikringsbelop(forsikringsbelopField);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterBetingelser(betingelserField);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterType(typeField);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterAdresseBolig(adresseBoligField);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterByggeår(byggeårField);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterByggemateriale(byggematerialeField);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterStandard(standardChoice);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterAntallKvm(antallKvmField);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterForsikringsbelopBygning(forsikringsbelopBygningField);
        msg += innboForsikringÅRedigere.sjekkOgOppdaterForsikringsbelopInnbo(forsikringsbelopInnboField);


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
