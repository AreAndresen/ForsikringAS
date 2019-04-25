package ae.controller;

import ae.controller.util.UgyldigInputHandler;
import ae.model.Kunde;
import ae.model.exceptions.kunde.UgyldigAdresseFakturaException;
import ae.model.exceptions.kunde.UgyldigEtternavnException;
import ae.model.exceptions.kunde.UgyldigFornavnException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Popup-vindu for å redigere kunde
 */
public class KundeRedigerPopupController {

    @FXML
    private TextField kundeNrField, etternavnField, fornavnField,
    adresseFakturaField, datoKundeOpprettetField;

    private Stage popupStage;
    private Kunde kundeÅRedigere;
    private boolean bekreft = false;
    private boolean inputOK = false;

    @FXML
    private void initialize() { }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    /**
     * Plasserer kunden som skal redigeres i popup-vinduet.
     */
    public void setKundeÅRedigere(Kunde kunde) {
        this.kundeÅRedigere = kunde;
        oppdaterFelter();
    }

    /**
     * Metode for å legge inn kundens data i TextFields.
     */
    public void oppdaterFelter() {
        kundeNrField.setText(Integer.toString(kundeÅRedigere.getKundeNr()));
        etternavnField.setText(kundeÅRedigere.getEtternavn());
        fornavnField.setText(kundeÅRedigere.getFornavn());
        adresseFakturaField.setText(kundeÅRedigere.getAdresseFaktura());
        datoKundeOpprettetField.setText(kundeÅRedigere.getDatoKundeOpprettet().toString());

        kundeNrField.setDisable(true);
        datoKundeOpprettetField.setDisable(true);
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
        // TODO: input-validering med exceptions venter

        sjekkKunde(); //genererer feilmeldinger om aktivert
        if(inputOK){ //implementert en boolean for å lukke om input er riktig/feil

            bekreft = true; //true her legger til kunde
            popupStage.close();
        }
    }

    public void sjekkKunde() {
        String msg = "";

        kundeÅRedigere.setKundeNr(Integer.parseInt(kundeNrField.getText()));

        //Bytter set her ut med metoder (se under)
        msg += sjekkFornavn();
        msg += redigerEtternavn();
        msg += redigerAdresseFaktura();
        //legger inn antall ubetalte
        //redigerAntallUbetalteErstatninger();

        //kundeÅRedigere.setDatoKundeOpprettet(LocalDate.datoKundeOpprettetField.getText());
        // TODO: må parse LocalDate så riktig format lagres

        //kontrollerer etter aktiverte feilmeldinger
        if(msg.length() != 0){
            UgyldigInputHandler.generateAlert(msg); //alert
        }
        else{
            inputOK = true; //riktig input
        }
    }

    //TODO: METODER FOR ENDRING AV KUNDE
    //oppdaterer fornavn
    private String sjekkFornavn() {
        String msg = "";

        if (fornavnField.getText() == null || fornavnField.getText().isEmpty()) {
            msg += "Fornavn kan ikke være tomt.\n";
        } else {
            try {
                kundeÅRedigere.setFornavn(fornavnField.getText());
            } catch (UgyldigFornavnException e) {
                msg += e.getMessage()+"\n";
            }
        }
        return msg;
    }

    //oppdaterer etternavn
    private String redigerEtternavn() {
        String msg = "";
        try {
            kundeÅRedigere.setEtternavn(etternavnField.getText());
        }
        catch (UgyldigEtternavnException e) {
            msg +=e.getMessage()+"\n";
        }
        return msg;
    }

    //oppdaterer adresseFaktura
    private String redigerAdresseFaktura() {
        String msg = "";
        try {
            kundeÅRedigere.setAdresseFaktura(adresseFakturaField.getText());
        }
        catch (UgyldigAdresseFakturaException e) {
            msg +=e.getMessage()+"\n";
        }
        return msg;
    }

    /*//oppdaterer adresseFaktura
    private void redigerAntallUbetalteErstatninger() {
        //oppdaterer antallUbetalte
        //HashMap<Integer, Double> erstatninger = new HashMap<Integer, Double>();
        ObservableList<Integer> erstatninger = FXCollections.observableArrayList();

        for(Skademelding skade : kundeÅRedigere.getSkademeldinger()){
            if(skade.getStatus().equals("Ubetalt")){
                erstatninger.add(skade.getForsikringsNr());
            }
        }
        kundeÅRedigere.setAntallErstatningerUbetalte(erstatninger);

        //kundeÅRedigere.setAntallErstatningerUbetalte(erstatninger);
    }*/




    @FXML
    public void avbrytTrykkes() {
        popupStage.close();
    }
}
