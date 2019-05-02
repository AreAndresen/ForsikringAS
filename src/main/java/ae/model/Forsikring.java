package ae.model;

import ae.HovedApplikasjon;
import ae.model.exceptions.UgyldigBelopException;
import ae.model.exceptions.UgyldigDatoException;
import ae.model.exceptions.UgyldigInputException;
import ae.model.exceptions.UgyldigLopeNrException;
import javafx.beans.property.*;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;

public abstract class Forsikring implements Serializable {
    private static final long serialVersionUID = 1;

    private transient IntegerProperty kundeNr;
    private transient IntegerProperty forsikringsNr;
    private transient LongProperty årligPremie;
    private transient ObjectProperty<LocalDate> datoOpprettet;
    private transient LongProperty forsikringsBelop;
    private transient StringProperty betingelser;
    private transient StringProperty type;

    // < ------------------------------------ KONSTRUKTØRER ------------------------------------ >

    // tomt objekt-konstruktør
    public Forsikring(int kundeNr, int forsikringsNr) {
        this(kundeNr, forsikringsNr, 0, LocalDate.now(), 0, null, null);
    }

    // default konstruktør
    public Forsikring(int kundeNr, int forsikringsNr, long premie, LocalDate datoOpprettet,
                      long forsikringsBelop, String betingelser, String type) {
        this.kundeNr = new SimpleIntegerProperty(kundeNr);
        this.forsikringsNr = new SimpleIntegerProperty(forsikringsNr);
        this.årligPremie = new SimpleLongProperty(premie);
        this.datoOpprettet = new SimpleObjectProperty<>(datoOpprettet);
        this.forsikringsBelop = new SimpleLongProperty(forsikringsBelop);
        this.betingelser = new SimpleStringProperty(betingelser);
        this.type = new SimpleStringProperty(type);
    }

    // < ------------------------------------ GET OG SET ------------------------------------ >

    // kunde
    public int getKundeNr() {
        return kundeNr.get();
    }
    public void setKundeNr(int kundeNr) {
        if (kundeNr <= 0) {
            throw new UgyldigLopeNrException("Kundenummer må være større enn 0.");
        }
        this.kundeNr.set(kundeNr);
    }
    public IntegerProperty kundeNrProperty() {
        return kundeNr;
    }

    // forsikringsNr
    public int getForsikringsNr() {
        return forsikringsNr.get();
    }
    public void setForsikringsNr(int forsikringsNr) {
        if (forsikringsNr <= 0) {
            throw new UgyldigLopeNrException("Forsikringsnummer må være større enn 0.");
        }
        this.forsikringsNr.set(forsikringsNr);
    }
    public IntegerProperty forsikringsNrProperty() {
        return forsikringsNr;
    }

    // årligPremie
    public long getÅrligPremie() {
        return årligPremie.get();
    }
    public void setÅrligPremie(long premie) {
        if (premie <= 0) {
            throw new UgyldigBelopException("Årlig forsikringspremie må være større enn 0.");
        }
        this.årligPremie.set(premie);
    }
    public LongProperty årligPremieProperty() {
        return årligPremie;
    }

    // datoOpprettet
    public LocalDate getDatoOpprettet() {
        return datoOpprettet.get();
    }
    public void setDatoOpprettet(LocalDate datoOpprettet) {
        if (datoOpprettet.isAfter(LocalDate.now())) {
            throw new UgyldigDatoException();
        }
        this.datoOpprettet.set(datoOpprettet);
    }
    public ObjectProperty<LocalDate> datoOpprettetProperty() {
        return datoOpprettet;
    }

    // forsikringsBelop
    public long getForsikringsBelop() {
        return forsikringsBelop.get();
    }
    public void setForsikringsBelop(long forsikringsBelop) {
        if (forsikringsBelop <= 0) {
            throw new UgyldigBelopException("Forsikringsbeløp må være større enn 0.");
        }
        this.forsikringsBelop.set(forsikringsBelop);
    }
    public LongProperty forsikringsBelopProperty() {
        return forsikringsBelop;
    }

    // betingelser
    public String getBetingelser() {
        return betingelser.get();
    }
    public void setBetingelser(String betingelser) {
        if (betingelser == null || !betingelser.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.]{1,30}+")) {
            throw new UgyldigInputException("Betingelser kan ikke overstige 30 tegn og eneste tillate\n spesialtegn" +
                    "er bindestrek og punktum.");
        }
        this.betingelser.set(betingelser);
    }
    public StringProperty betingelserProperty() {
        return betingelser;
    }

    // type
    public String getType() {
        return type.get();
    }
    public void setType(String type) {
        if (type == null || (!"Båtforsikring".equals(type) && !"Hus- og innboforsikring".equals(type)
                && !"Fritidsboligforsikring".equals(type) && !"Reiseforsikring".equals(type))) {
            throw new UgyldigInputException("Type må være en gyldig forsikringstype.");
        }
        this.type.set(type);
    }
    public StringProperty typeProperty() {
        return type;
    }

    // < ------------------------------------ INPUT-VALIDERING ------------------------------------ >

    // kundeNr
    public String sjekkOgOppdaterKundeNr(TextField kundeNrField, HovedApplikasjon hovedApplikasjon) {
        String msg = "";

        if (kundeNrField.getText() == null || kundeNrField.getText().isEmpty()) {
            msg += "Kundenummer kan ikke være tomt.\n";
        } else {
            try {
                boolean kundeFinnes = false;
                for (Kunde kunde : hovedApplikasjon.getKundeData()) {
                    if (kunde.getKundeNr() == Integer.parseInt(kundeNrField.getText())) {
                        kundeFinnes = true;
                    }
                }
                if (!kundeFinnes) {
                    msg += "Det er ingen kunde registrert med det\nkundenummeret i systemet.\n";
                } else {
                    setKundeNr(Integer.parseInt(kundeNrField.getText()));
                }
            } catch (NumberFormatException e) {
                msg += "Kundenummer må være tall.\n";
            } catch (UgyldigLopeNrException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // forsikringsNr
    public String sjekkOgOppdaterForsikringsNr(TextField forsikringsNrField) {
        String msg = "";

        if (forsikringsNrField.getText() == null || forsikringsNrField.getText().isEmpty()) {
            msg += "Forsikringsnummer kan ikke være tomt.\n";
        } else {
            try {
                setForsikringsNr(Integer.parseInt(forsikringsNrField.getText()));
            } catch (NumberFormatException e) {
                msg += "Forsikringsnummer nå være tall.\n";
            } catch (UgyldigLopeNrException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // årligPremie
    public String sjekkOgOppdaterÅrligPremie(TextField premieField) {
        String msg = "";

        if (premieField.getText() == null || premieField.getText().isEmpty()) {
            msg += "Årlig forsikringspremie kan ikke være tom.\n";
        } else {
            try {
                setÅrligPremie(Long.parseLong(premieField.getText()));
            } catch (NumberFormatException e) {
                msg += "Årlig forsikringspremie må være tall.\n";
            } catch (UgyldigBelopException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // datoOpprettet
    public String sjekkOgOppdaterDatoOpprettet(TextField datoOpprettetField) {
        String msg = "";

        if (datoOpprettetField.getText() == null || datoOpprettetField.getText().isEmpty()) {
            msg += "Dato kan ikke være tom.\n";
        } else {
            try {
                setDatoOpprettet(LocalDate.parse(datoOpprettetField.getText()));
            } catch (DateTimeException e) {
                msg += "Dato er ikke en gyldig dato.\n";
            } catch (UgyldigDatoException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // forsikringsbelop
    public String sjekkOgOppdaterForsikringsbelop(TextField forsikringsbelopField) {
        String msg = "";

        if (forsikringsbelopField.getText() == null || forsikringsbelopField.getText().isEmpty()) {
            msg += "Forsikringsbeløp kan ikke være tomt.\n";
        } else {
            try {
                setForsikringsBelop(Long.parseLong(forsikringsbelopField.getText()));
            } catch (NumberFormatException e) {
                msg += "Forsikringsbeløp må være tall.\n";
            } catch (UgyldigBelopException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // betingelser
    public String sjekkOgOppdaterBetingelser(TextField betingelserField) {
        String msg = "";

        if (betingelserField.getText() == null || betingelserField.getText().isEmpty()) {
            msg += "Betingelser kan ikke være tom.\n";
        } else {
            try {
                setBetingelser(betingelserField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // type
    public String sjekkOgOppdaterType(TextField typeField) {
        String msg = "";

        if (typeField.getText() == null || typeField.getText().isEmpty()) {
            msg += "Type kan ikke være tom.\n";
        } else {
            try {
                setType(typeField.getText());
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
        os.writeObject(getKundeNr());
        os.writeObject(getForsikringsNr());
        os.writeObject(getÅrligPremie());
        os.writeObject(getDatoOpprettet());
        os.writeObject(getForsikringsBelop());
        os.writeObject(getBetingelser());
        os.writeObject(getType());
    }

    // readObject
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        is.defaultReadObject();
        this.kundeNr = new SimpleIntegerProperty((int) is.readObject());
        this.forsikringsNr = new SimpleIntegerProperty((int) is.readObject());
        this.årligPremie = new SimpleLongProperty((long) is.readObject());
        this.datoOpprettet = new SimpleObjectProperty<>((LocalDate) is.readObject());
        this.forsikringsBelop = new SimpleLongProperty((long) is.readObject());
        this.betingelser = new SimpleStringProperty((String) is.readObject());
        this.type = new SimpleStringProperty((String) is.readObject());
    }

    // < ------------------------------------ toString - CSV ------------------------------------ >

    @Override
    public String toString() {
        return getKundeNr() + ";" + getForsikringsNr() + ";" + getÅrligPremie() + ";" + getDatoOpprettet()  + ";"
                + getForsikringsBelop() + ";" + getBetingelser() + ";" + getType();
    }
}
