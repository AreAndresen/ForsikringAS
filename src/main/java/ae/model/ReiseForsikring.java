package ae.model;

import ae.model.exceptions.UgyldigInputException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

public class ReiseForsikring extends Forsikring implements Serializable {
    private transient StringProperty forsikringsOmråde;
    private transient DoubleProperty forsikringsSum;

    // < ------------------------------------ KONSTRUKTØRER ------------------------------------ >

    // tomt objekt konstruktør
    public ReiseForsikring(int kundeNr, int forsikringsNr) {
        this(kundeNr, forsikringsNr, LocalDate.now(), 0, null, "Reiseforsikring", null, 0);
    }

    // default konstruktør
    public ReiseForsikring(int kundeNr, int forsikringsNr, LocalDate datoOpprettet, int forsikringsBelop,
                           String betingelser, String type, String forsikringsOmråde, double forsikringsSum) {
        super(kundeNr, forsikringsNr, datoOpprettet, forsikringsBelop, betingelser, type);
        this.forsikringsOmråde = new SimpleStringProperty(forsikringsOmråde);
        this.forsikringsSum = new SimpleDoubleProperty(forsikringsSum);
    }

    // < ------------------------------------ GET OG SET ------------------------------------ >

    // forsikringsOmråde
    public String getForsikringsOmråde() {
        return forsikringsOmråde.get();
    }
    public void setForsikringsOmråde(String område) {
        if (område == null || !område.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.]{1,30}+")) {
            throw new UgyldigInputException("Forsikringsområde kan ikke overstige 30 tegn og eneste tillate" +
                    " spesialtegn er bindestrek og punktum.");
        }
        this.forsikringsOmråde.set(område);
    }
    public StringProperty forsikringsOmrådeProperty() {
        return forsikringsOmråde;
    }

    // forsikringsSum
    public double getForsikringsSum() {
        return forsikringsSum.get();
    }
    public void setForsikringsSum(double sum) {
        if (sum <= 0) {
            throw new UgyldigInputException("Forsikringssum må være større enn 0.");
        }
        this.forsikringsSum.set(sum);
    }
    public DoubleProperty forsikringsSumProperty() {
        return forsikringsSum;
    }

    // < ------------------------------------ INPUT-VALIDERING ------------------------------------ >

    // forsikringsOmråde
    public String sjekkOgOppdaterForsikringsOmråde(TextField forsikringsOmrådeField) {
        String msg = "";

        if (forsikringsOmrådeField.getText() == null || forsikringsOmrådeField.getText().isEmpty()) {
            msg += "Forsikringsområde kan ikke være tom.\n";
        } else {
            try {
                setForsikringsOmråde(forsikringsOmrådeField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // forsikringsSum
    public String sjekkOgOppdaterForsikringsSum(TextField forsikringsSumField) {
        String msg = "";

        if (forsikringsSumField.getText() == null || forsikringsSumField.getText().isEmpty()) {
            msg += "Forsikringssum kan ikke være tom.\n";
        } else {
            try {
                setForsikringsSum(Double.parseDouble(forsikringsSumField.getText()));
            } catch (NumberFormatException e) {
                msg += "Forsikringssum må være tall.\n";
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
        os.writeObject(getForsikringsOmråde());
        os.writeObject(getForsikringsSum());
    }

    // readObject
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        is.defaultReadObject();
        this.forsikringsOmråde = new SimpleStringProperty((String) is.readObject());
        this.forsikringsSum = new SimpleDoubleProperty((double) is.readObject());
    }

    // < ------------------------------------ toString - CSV ------------------------------------ >

    /*@Override
    public String toString() {
        return super.toString() + "," + getForsikringsOmråde() + "," + getForsikringsSum();
    }*/
}
