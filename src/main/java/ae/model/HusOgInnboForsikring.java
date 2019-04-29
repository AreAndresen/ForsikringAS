package ae.model;

import ae.model.exceptions.UgyldigInputException;
import javafx.beans.property.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

public class HusOgInnboForsikring extends Forsikring implements Serializable {
    private transient StringProperty adresseBolig;
    private transient IntegerProperty byggeår;
    private transient StringProperty byggemateriale;
    private transient StringProperty standard;
    private transient IntegerProperty antallKvm;
    private transient DoubleProperty forsikringsbelopBygning;
    private transient DoubleProperty forsikringsbelopInnbo;

    // < ------------------------------------ KONSTRUKTØRER ------------------------------------ >

    // tomt objekt-konstruktør
    public HusOgInnboForsikring(int kundeNr, int forsikringsNr) {
        this(kundeNr, forsikringsNr, LocalDate.now(), 0, null, "Hus- og innboforsikring",
                null, 0, null, null, 0, 0,
                0);
    }

    // default konstruktør
    public HusOgInnboForsikring(int kundeNr, int forsikringsNr, LocalDate datoOpprettet, int forsikringsBelop,
                                String betingelser, String type, String adresseBolig, int byggeår,
                                String byggemateriale, String standard, int antallKvm, double forsikringsbelopBygning,
                                double forsikringsbelopInnbo) {
        super(kundeNr, forsikringsNr, datoOpprettet, forsikringsBelop, betingelser, type);
        this.adresseBolig = new SimpleStringProperty(adresseBolig);
        this.byggeår = new SimpleIntegerProperty(byggeår);
        this.byggemateriale = new SimpleStringProperty(byggemateriale);
        this.standard = new SimpleStringProperty(standard);
        this.antallKvm = new SimpleIntegerProperty(antallKvm);
        this.forsikringsbelopBygning = new SimpleDoubleProperty(forsikringsbelopBygning);
        this.forsikringsbelopInnbo = new SimpleDoubleProperty(forsikringsbelopInnbo);
    }

    // < ------------------------------------ GET OG SET ------------------------------------ >

    // adresseBolig
    public String getAdresseBolig() {
        return adresseBolig.get();
    }
    public void setAdresseBolig(String adresseBolig) {
        if (adresseBolig == null || !adresseBolig.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.]{2,50}+")) {
            throw new UgyldigInputException("Boligadresse kan ikke overstige 50 tegn og eneste tillate spesialtegn" +
                    " er bindestrek og punktum.");
        }
        this.adresseBolig.set(adresseBolig);
    }
    public StringProperty adresseBoligProperty() {
        return adresseBolig;
    }

    // byggeår
    public int getByggeår() {
        return byggeår.get();
    }
    public void setByggeår(int byggeår) {
        if (byggeår < 1000 || byggeår > 2020) {
            throw new UgyldigInputException("Byggeår må være etter 1000 og før 2030.");
        }
        this.byggeår.set(byggeår);
    }
    public IntegerProperty byggeårProperty() {
        return byggeår;
    }

    // byggemateriale
    public String getByggemateriale() {
        return byggemateriale.get();
    }
    public void setByggemateriale(String byggemateriale) {
        if (byggemateriale == null || !byggemateriale.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.]{1,30}+")) {
            throw new UgyldigInputException("Byggemateriale kan ikke overstige 30 tegn og eneste tillate spesialtegn" +
                    " er bindestrek og punktum.");
        }
        this.byggemateriale.set(byggemateriale);
    }
    public StringProperty byggematerialeProperty() {
        return byggemateriale;
    }

    // standard
    public String getStandard() {
        return standard.get();
    }
    public void setStandard(String standard) {
        if (!"Høy".equals(standard) && !"Middels".equals(standard) && !"Lav".equals(standard)) {
            throw new UgyldigInputException("Standard må være av en bestemt standard.");
        }
        this.standard.set(standard);
    }
    public StringProperty standardProperty() {
        return standard;
    }

    // antallKvm
    public int getAntallKvm() {
        return antallKvm.get();
    }
    public void setAntallKvm(int antallKvm) {
        if (antallKvm <= 0 || antallKvm > 999) {
            throw new UgyldigInputException("Antall kvadratmeter må være mellom 1 og 999");
        }
        this.antallKvm.set(antallKvm);
    }
    public IntegerProperty antallKvmProperty() {
        return antallKvm;
    }

    // forsikringsbelopBygning
    public double getForsikringsbelopBygning() {
        return forsikringsbelopBygning.get();
    }
    public void setForsikringsbelopBygning(double forsikringsbelopBygning) {
        if (forsikringsbelopBygning <= 0) {
            throw new UgyldigInputException("Forsikringsbeløp for bygning må være større enn 0.");
        }
        this.forsikringsbelopBygning.set(forsikringsbelopBygning);
    }
    public DoubleProperty forsikringsbelopBygningProperty() {
        return forsikringsbelopBygning;
    }

    // forsikringsbelopInnbo
    public double getForsikringsbelopInnbo() {
        return forsikringsbelopInnbo.get();
    }
    public void setForsikringsbelopInnbo(double forsikringsbelopInnbo) {
        if (forsikringsbelopInnbo <= 0) {
            throw new UgyldigInputException("Forsikringsbeløp for innbo må være større enn 0.");
        }
        this.forsikringsbelopInnbo.set(forsikringsbelopInnbo);
    }
    public DoubleProperty forsikringsbelopInnboProperty() {
        return forsikringsbelopInnbo;
    }

    // < ------------------------------------ INPUT-VALIDERING ------------------------------------ >

    // adresseBolig
    public String sjekkOgOppdaterAdresseBolig(TextField adresseBoligField) {
        String msg = "";

        if (adresseBoligField.getText() == null || adresseBoligField.getText().isEmpty()) {
            msg += "Boligadresse kan ikke være tom.\n";
        } else {
            try {
                setAdresseBolig(adresseBoligField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // byggeår
    public String sjekkOgOppdaterByggeår(TextField byggeårField) {
        String msg = "";

        if (byggeårField.getText() == null || byggeårField.getText().isEmpty()) {
            msg += "Byggeår kan ikke være tomt.\n";
        } else {
            try {
                setByggeår(Integer.parseInt(byggeårField.getText()));
            } catch (NumberFormatException e) {
                msg += "Byggeår må være tall.\n";
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // byggemateriale
    public String sjekkOgOppdaterByggemateriale(TextField byggematerialeField) {
        String msg = "";

        if (byggematerialeField.getText() == null || byggematerialeField.getText().isEmpty()) {
            msg += "Byggemateriale kan ikke være tom.\n";
        } else {
            try {
                setByggemateriale(byggematerialeField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // standard
    public String sjekkOgOppdaterStandard(ChoiceBox standardField) {
        String msg = "";

        if (standardField.getValue() == null || standardField.getItems().isEmpty()) {
            msg += "Standard kan ikke være tom.\n";
        } else {
            try {
                setStandard(standardField.getValue().toString());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // antallKvm
    public String sjekkOgOppdaterAntallKvm(TextField antallKvmField) {
        String msg = "";

        if (antallKvmField.getText() == null || antallKvmField.getText().isEmpty()) {
            msg += "Antall kvadratmeter kan ikke være tom.\n";
        } else {
            try {
                setAntallKvm(Integer.parseInt(antallKvmField.getText()));
            } catch (NumberFormatException e) {
                msg += "Antall kvadrat meter må være tall.\n";
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // forsikringsbelopBygning
    public String sjekkOgOppdaterForsikringsbelopBygning(TextField forsikringsbelopBygningField) {
        String msg = "";

        if (forsikringsbelopBygningField.getText() == null || forsikringsbelopBygningField.getText().isEmpty()) {
            msg += "Forsikringsbeløp for bygning kan ikke være tom.\n";
        } else {
            try {
                setForsikringsbelopBygning(Double.parseDouble(forsikringsbelopBygningField.getText()));
            } catch (NumberFormatException e) {
                msg += "Forsikringsbeløp for bygning må være tall.\n";
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // forsikringsbelopInnbo
    public String sjekkOgOppdaterForsikringsbelopInnbo(TextField forsikringsbelopInnboField) {
        String msg = "";

        if (forsikringsbelopInnboField.getText() == null ||forsikringsbelopInnboField.getText().isEmpty()) {
            msg += "Forsikringsbeløp for innbo kan ikke være tom.\n";
        } else {
            try {
                setForsikringsbelopInnbo(Double.parseDouble(forsikringsbelopInnboField.getText()));
            } catch (NumberFormatException e) {
                msg += "Forsikringsbeløp for innbo må være tall.\n";
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
        os.writeObject(getAdresseBolig());
        os.writeObject(getByggeår());
        os.writeObject(getByggemateriale());
        os.writeObject(getStandard());
        os.writeObject(getAntallKvm());
        os.writeObject(getForsikringsbelopBygning());
        os.writeObject(getForsikringsbelopInnbo());
    }

    // readObject
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        is.defaultReadObject();
        this.adresseBolig = new SimpleStringProperty((String) is.readObject());
        this.byggeår = new SimpleIntegerProperty((int) is.readObject());
        this.byggemateriale = new SimpleStringProperty((String) is.readObject());
        this.standard = new SimpleStringProperty((String) is.readObject());
        this.antallKvm = new SimpleIntegerProperty((int) is.readObject());
        this.forsikringsbelopBygning = new SimpleDoubleProperty((double) is.readObject());
        this.forsikringsbelopInnbo = new SimpleDoubleProperty((double) is.readObject());
    }

    // < ------------------------------------ toString - CSV ------------------------------------ >

    /*@Override
    public String toString() {
        return super.toString() + "," + getAdresseBolig()  + "," + getByggeår()  + "," + getByggemateriale()  + "," +
                getStandard() + "," + getAntallKvm() + "," + getForsikringsbelopBygning() + "," +
                getForsikringsbelopInnbo();
    }*/
}