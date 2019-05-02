package ae.model;

import ae.model.exceptions.UgyldigInputException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

public class BåtForsikring extends Forsikring implements Serializable {
    private transient StringProperty registreringsNr;
    private transient StringProperty typeModell;
    private transient IntegerProperty lengdeFot;
    private transient IntegerProperty årsmodell;
    private transient StringProperty motorEgenskaper;

    // < ------------------------------------ KONSTRUKTØRER ------------------------------------ >

    // tomt objekt-konstruktør
    public BåtForsikring(int kundeNr, int forsikringsNr) {
        this(kundeNr, forsikringsNr, 0.0, LocalDate.now(), 0, null, "Båtforsikring", null,
                null, 0, 0, null);
    }

    // default konstruktør
    public BåtForsikring(int kundeNr, int forsikringsNr, double premie, LocalDate datoOpprettet, int forsikringsBelop,
                         String betingelser, String type, String registreringsNr, String typeModell, int lengdeFot,
                         int årsmodell, String motorEgenskaper) {
        super(kundeNr, forsikringsNr, premie, datoOpprettet, forsikringsBelop, betingelser, type);
        this.registreringsNr = new SimpleStringProperty(registreringsNr);
        this.typeModell = new SimpleStringProperty(typeModell);
        this.lengdeFot = new SimpleIntegerProperty(lengdeFot);
        this.årsmodell = new SimpleIntegerProperty(årsmodell);
        this.motorEgenskaper = new SimpleStringProperty(motorEgenskaper);
    }

    // < ------------------------------------ GET OG SET ------------------------------------ >

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
        if (typeModell == null || !typeModell.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.]{1,30}+")) {
            throw new UgyldigInputException("Båttype kan ikke overstige 30 tegn og eneste tillate spesialtegn" +
                    " er bindestrek og punktum.");
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
        if (årsmodell < 1899 || årsmodell > 2030) {
            throw new UgyldigInputException("Årsmodell må være etter 1899 og før 2030.");
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
        if (motorEgenskaper == null || !motorEgenskaper.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.]{1,30}+")) {
            throw new UgyldigInputException("Motortype og styrke kan ikke overstige 30 tegn og eneste tillate spesialtegn" +
                    " er bindestrek og punktum.");
        }
        this.motorEgenskaper.set(motorEgenskaper);
    }
    public StringProperty motorEgenskaperProperty() {
        return motorEgenskaper;
    }

    // < ------------------------------------ INPUT-VALIDERING ------------------------------------ >

    // registreringsNr
    public String sjekkOgOppdaterRegistreringsNr(TextField registreringsnrField) {
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

    // typeModell
    public String sjekkOgOppdaterTypeModell(TextField typeModellField) {
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

    // lengdeFot
    public String sjekkOgOppdaterLengdeFot(TextField lengdeFotField) {
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

    // årsmodell
    public String sjekkOgOppdaterÅrsmodell(TextField årsmodellField) {
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

    // motorEgenskaper
    public String sjekkOgOppdaterMotorEgenskaper(TextField motorEgenskaperField) {
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

    // < ------------------------------------ SERIALISERING ------------------------------------ >

    // writeObject
    private void writeObject(ObjectOutputStream os) throws IOException {
        os.defaultWriteObject();
        os.writeObject(getRegistreringsNr());
        os.writeObject(getTypeModell());
        os.writeObject(getLengdeFot());
        os.writeObject(getÅrsmodell());
        os.writeObject(getMotorEgenskaper());
    }

    // readObject
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        is.defaultReadObject();
        this.registreringsNr = new SimpleStringProperty((String) is.readObject());
        this.typeModell = new SimpleStringProperty((String) is.readObject());
        this.lengdeFot = new SimpleIntegerProperty((int) is.readObject());
        this.årsmodell = new SimpleIntegerProperty((int) is.readObject());
        this.motorEgenskaper = new SimpleStringProperty((String) is.readObject());
    }

    // < ------------------------------------ toString - CSV ------------------------------------ >

    /*@Override
    public String toString() {
        return super.toString() + "," + getRegistreringsNr() + "," + getTypeModell()  + "," + getLengdeFot()
                + "," + getÅrsmodell()  + "," + getMotorEgenskaper();
    }*/
}