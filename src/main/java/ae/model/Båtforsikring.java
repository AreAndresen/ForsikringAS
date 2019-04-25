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
            throw new UgyldigInputException("Registreringsnummer må være mellom 4 og 10 tegn.");
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
        if (typeModell == null || !typeModell.matches("[a-zA-ZæøåÆØÅ0-9]{1,30}+")) {
            throw new UgyldigInputException("Båttype og modell kan ikke overstige 30 tegn.");
        }
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
        if (lengdeFot <= 0 || lengdeFot > 512) {
            throw new UgyldigInputException("Lengde må være mellom 1 og 512 fot.");
        }
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
        if (årsmodell < 1899 || årsmodell > 2020) {
            throw new UgyldigInputException("Årsmodell må være etter 1899 og før 2020.");
        }
        this.årsmodell.set(årsmodell);
    }
    public IntegerProperty årsmodellProperty() {
        return årsmodell;
    }

    // motorEgenskaper
    public String getMotorEgenskaper() {
        return motorEgenskaper.get();
    }
    public void setMotorEgenskaper(String motorEgenskaper) {
        if (motorEgenskaper == null || !motorEgenskaper.matches("[a-zA-ZæøåÆØÅ0-9]{1,30}+")) {
            throw new UgyldigInputException("Motortype og styrke kan ikke overstige 30 tegn.");
        }
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

    public String sjekkTypeModell(TextField typeModellField) {
        String msg = "";

        if (typeModellField.getText() == null || typeModellField.getText().isEmpty()) {
            msg += "Båttype og modell kan ikke være tom.\n";
        } else {
            try {
                setTypeModell(typeModellField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    public String sjekkLengdeFot(TextField lengdeFotField) {
        String msg = "";

        if (lengdeFotField.getText() == null || lengdeFotField.getText().isEmpty()) {
            msg += "Lengde kan ikke være tom.\n";
        } else {
            try {
                setLengdeFot(Integer.parseInt(lengdeFotField.getText()));
            } catch (NumberFormatException e) {
                msg += "Lengde må være tall.\n";
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    public String sjekkÅrsmodell(TextField årsmodellField) {
        String msg = "";

        if (årsmodellField.getText() == null || årsmodellField.getText().isEmpty()) {
            msg += "Årsmodell kan ikke være tom.\n";
        } else {
            try {
                setÅrsmodell(Integer.parseInt(årsmodellField.getText()));
            } catch (NumberFormatException e) {
                msg += "Årsmodell må være tall.\n";
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    public String sjekkMotorEgenskaper(TextField motorEgenskaperField) {
        String msg = "";

        if (motorEgenskaperField.getText() == null || motorEgenskaperField.getText().isEmpty()) {
            msg += "Motortype og styrke kan ikke være tom.\n";
        } else {
            try {
                setMotorEgenskaper(motorEgenskaperField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }
}
