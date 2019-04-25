package ae.model;

import ae.HovedApplikasjon;
import ae.model.exceptions.UgyldigDatoException;
import ae.model.exceptions.forsikring.UgyldigForsikringsbelopException;
import ae.model.exceptions.UgyldigLopeNrException;
import ae.model.exceptions.forsikring.UgyldigTypeException;
import javafx.beans.property.*;
import javafx.scene.control.TextField;

import java.time.DateTimeException;
import java.time.LocalDate;

public abstract class Forsikring {
    private final transient IntegerProperty kundeNr;
    private final transient IntegerProperty forsikringsNr;
    private final transient ObjectProperty<LocalDate> datoOpprettet;
    private final transient IntegerProperty forsikringsBelop;
    private final transient StringProperty betingelser;
    private final transient StringProperty type;

    // default konstruktør
    public Forsikring(int forsikringsNr) {
        this(0, forsikringsNr, LocalDate.now(), 0, null, null);
    }

    // non-default konstruktør
    public Forsikring(int kundeNr, int forsikringsNr, LocalDate datoOpprettet,
                      int forsikringsBelop, String betingelser, String type) {
        this.kundeNr = new SimpleIntegerProperty(kundeNr);
        this.forsikringsNr = new SimpleIntegerProperty(forsikringsNr);
        this.datoOpprettet = new SimpleObjectProperty<>(datoOpprettet);
        this.forsikringsBelop = new SimpleIntegerProperty(forsikringsBelop);
        this.betingelser = new SimpleStringProperty(betingelser);
        this.type = new SimpleStringProperty(type);
    }

    /**
     * Get- og Set-metoder for datafeltene
     */

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
    public int getForsikringsBelop() {
        return forsikringsBelop.get();
    }
    public void setForsikringsBelop(int forsikringsBelop) {
        if (forsikringsBelop <= 0) {
            throw new UgyldigForsikringsbelopException();
        }
        this.forsikringsBelop.set(forsikringsBelop);
    }
    public IntegerProperty forsikringsBelopProperty() {
        return forsikringsBelop;
    }

    // betingelser
    public String getBetingelser() {
        return betingelser.get();
    }
    public void setBetingelser(String betingelser) {
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
        if (!"Båtforsikring".equals(type) && !"Hus- og innboforsikring".equals(type)
                && !"Fritidsboligforsikring".equals(type) && !"Reiseforsikring".equals(type)) {
            throw new UgyldigTypeException();
        }
        this.type.set(type);
    }
    public StringProperty typeProperty() {
        return type;
    }

    /**
     * STATISKE METODER FOR INPUT-VALIDERING AV FELLES FELTER I FORSIKRING
     */
    public static String sjekkKundeNr(TextField kundeNrField, HovedApplikasjon hovedApplikasjon,
                                      Forsikring forsikringÅRedigere) {
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
                    forsikringÅRedigere.setKundeNr(Integer.parseInt(kundeNrField.getText()));
                }
            } catch (NumberFormatException e) {
                msg += "Kundenummer må være tall.\n";
            } catch (UgyldigLopeNrException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    public static String sjekkForsikringsNr(TextField forsikringsNrField, Forsikring forsikringÅRedigere) {
        String msg = "";

        if (forsikringsNrField.getText() == null || forsikringsNrField.getText().isEmpty()) {
            msg += "Forsikringsnummer kan ikke være tomt.\n";
        } else {
            try {
                forsikringÅRedigere.setForsikringsNr(Integer.parseInt(forsikringsNrField.getText()));
            } catch (NumberFormatException e) {
                msg += "Forsikringsnummer nå være tall.\n";
            } catch (UgyldigLopeNrException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    public static String sjekkDatoOpprettet(TextField datoOpprettetField, Forsikring forsikringÅRedigere) {
        String msg = "";

        if (datoOpprettetField.getText() == null || datoOpprettetField.getText().isEmpty()) {
            msg += "Dato kan ikke være tom.\n";
        } else {
            try {
                forsikringÅRedigere.setDatoOpprettet(LocalDate.parse(datoOpprettetField.getText()));
            } catch (DateTimeException e) {
                msg += "Dato er ikke en gyldig dato.\n";
            } catch (UgyldigDatoException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    public static String sjekkForsikringsbelop(TextField forsikringsbelopField, Forsikring forsikringÅRedigere) {
        String msg = "";

        if (forsikringsbelopField.getText() == null || forsikringsbelopField.getText().isEmpty()) {
            msg += "Forsikringsbeløp kan ikke være tomt.\n";
        } else {
            try {
                forsikringÅRedigere.setForsikringsBelop(Integer.parseInt(forsikringsbelopField.getText()));
            } catch (NumberFormatException e) {
                msg += "Forsikringsbeløp må være tall.\n";
            } catch (UgyldigForsikringsbelopException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    public static String sjekkBetingelser(TextField betingelserField, Forsikring forsikringÅRedigere) {
        String msg = "";

        if (betingelserField.getText() == null || betingelserField.getText().isEmpty()) {
            msg += "Betingelser kan ikke være tom.\n";
        } else {
            forsikringÅRedigere.setBetingelser(betingelserField.getText());
        }
        return msg;
    }

    public static String sjekkType(TextField typeField, Forsikring forsikringÅRedigere) {
        String msg = "";

        if (typeField.getText() == null || typeField.getText().isEmpty()) {
            msg += "Type kan ikke være tom.\n";
        } else {
            try {
                forsikringÅRedigere.setType(typeField.getText());
            } catch (UgyldigTypeException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }
}
