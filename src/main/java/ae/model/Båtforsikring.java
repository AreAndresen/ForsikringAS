package ae.model;

import ae.model.exceptions.UgyldigInputException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class Båtforsikring extends Forsikring {
    private final transient StringProperty registreringsNr;
    private final transient StringProperty typeModell;
    private final transient IntegerProperty lengdeFot;
    private final transient IntegerProperty årsmodell;
    private final transient StringProperty motorEgenskaper;

    // default konstruktør
    public Båtforsikring(int forsikringsNr) {
        this(0, forsikringsNr, LocalDate.now(), 0, null, "Båtforsikring", null,
                null, 0, 0, null);
    }

    // non-default konstruktør
    public Båtforsikring(int kundeNr, int forsikringsNr, LocalDate datoOpprettet, int forsikringsBelop,
                         String betingelser, String type, String registreringsNr,  String typeModell, int lengdeFot,
                         int årsmodell, String motorEgenskaper) {
        super(kundeNr, forsikringsNr, datoOpprettet, forsikringsBelop, betingelser, type);
        this.registreringsNr = new SimpleStringProperty(registreringsNr);
        this.typeModell = new SimpleStringProperty(typeModell);
        this.lengdeFot = new SimpleIntegerProperty(lengdeFot);
        this.årsmodell = new SimpleIntegerProperty(årsmodell);
        this.motorEgenskaper = new SimpleStringProperty(motorEgenskaper);
    }

    /**
     * Get- og Set-metoder for datafeltene
     */

    // registreringsNr
    public String getRegistreringsNr() {
        return registreringsNr.get();
    }
    public void setRegistreringsNr(String registreringsNr) {
        if (registreringsNr == null || !registreringsNr.matches("[a-zA-ZæøåÆØÅ0-9]{4,10}+")) {
            throw new UgyldigInputException("Registreringsnummer må være på gyldig format.");
        }
        this.registreringsNr.set(registreringsNr);
    }
    public StringProperty registreringsNrProperty() {
        return registreringsNr;
    }

    // typeModell
    public String getTypeModell() {
        return typeModell.get();
    }
    public void setTypeModell(String typeModell) {
        this.typeModell.set(typeModell);
    }
    public StringProperty typeModellProperty() {
        return typeModell;
    }

    // lengdeFot
    public int getLengdeFot() {
        return lengdeFot.get();
    }
    public void setLengdeFot(int lengdeFot) {
        this.lengdeFot.set(lengdeFot);
    }
    public IntegerProperty lengdeFotProperty() {
        return lengdeFot;
    }

    // årsmodell
    public int getÅrsmodell() {
        return årsmodell.get();
    }
    public void setÅrsmodell(int årsmodell) {
        this.årsmodell.set(årsmodell);
    }
    public IntegerProperty årsmodellPropert() {
        return årsmodell;
    }

    // motorEgenskaper
    public String getMotorEgenskaper() {
        return motorEgenskaper.get();
    }
    public void setMotorEgenskaper(String motorEgenskaper) {
        this.motorEgenskaper.set(motorEgenskaper);
    }
    public StringProperty motorEgenskaperProperty() {
        return motorEgenskaper;
    }

    /**
     * --- METODER FOR INPUT-VALIDERING
     */
    public String sjekkRegistreringsNr(TextField registreringsnrField) {
        String msg = "";

        if (registreringsnrField.getText() == null || registreringsnrField.getText().isEmpty()) {
            msg += "Registreringsnummer kan ikke være tom.\n";
        } else {
            try {
                setRegistreringsNr(registreringsnrField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }
}
