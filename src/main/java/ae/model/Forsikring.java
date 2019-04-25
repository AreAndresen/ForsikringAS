package ae.model;

import javafx.beans.property.*;

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
        this.type.set(type);
    }
    public StringProperty typeProperty() {
        return type;
    }
}
