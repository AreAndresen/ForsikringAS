package ae.model;

import ae.model.exceptions.skademelding.UgyldigLopeNrException;
import javafx.beans.property.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

/**
     * Domenemodell for skademelding
     */

public class Skademelding implements Serializable {
    private static final long serialVersionUID = 2;

    /**
     * * Nødvendige datafelt for å kommunisere med TableView.
     * * transient brukes for at maskinen ikke skal prøve å serialisere Property feltene
     */
    private transient IntegerProperty skadeNr;
    private transient IntegerProperty kundeNr; //kunde ID
    private transient ObjectProperty<LocalDate> datoSkade;
    private transient StringProperty skadeType;
    private transient StringProperty skadeBeskrivelse;
    private transient DoubleProperty belopTaksering;
    private transient DoubleProperty erstatningsbelopUtbetalt;
    private transient StringProperty kontaktinfoVitner;
    private transient StringProperty status;
    //private transient ObjectProperty<HashMap<Integer, String>> kontaktinfoVitner;

    /**
     * Konstruktør for midlertidig skademelding i Ny skademelding.
     */
    public Skademelding(int skadeNr) { this(skadeNr, 0, LocalDate.now(), null, null,
            0.0, 0.0, null, null); }

    /**
     * Konstruktør for Ny skademelding.
     */
     public Skademelding(int skadeNr, int kundeNr, LocalDate datoSkade, String skadeType, String skadeBeskrivelse,
                         Double belopTaksering, Double erstatningsbelopUtbetalt, String status) {

        // Ta imot parametere
        this.skadeNr = new SimpleIntegerProperty(skadeNr);
        this.kundeNr = new SimpleIntegerProperty(kundeNr);
        this.datoSkade = new SimpleObjectProperty<LocalDate>(datoSkade);
        this.skadeType = new SimpleStringProperty(skadeType);
        this.skadeBeskrivelse = new SimpleStringProperty(skadeBeskrivelse);
        this.belopTaksering = new SimpleDoubleProperty(belopTaksering);
        this.erstatningsbelopUtbetalt = new SimpleDoubleProperty(erstatningsbelopUtbetalt);
        this.status = new SimpleStringProperty(status);

        //Instansiere listene så de er opprettet.
        //this.kontaktinfoVitner = new SimpleStringProperty(kontaktinfoVitner);
    }

    /**
     * Konstruktør for kunde-opprettelse ved innlesing av fil.
     */
    public Skademelding(int skadeNr, int kundeNr, LocalDate datoSkade, String skadeType, String skadeBeskrivelse,
                        Double belopTaksering, Double erstatningsbelopUtbetalt,
                        String kontaktinfoVitner, String status) {
        this.skadeNr = new SimpleIntegerProperty(skadeNr);
        this.kundeNr = new SimpleIntegerProperty(kundeNr);
        this.datoSkade = new SimpleObjectProperty<LocalDate>(datoSkade);
        this.skadeType = new SimpleStringProperty(skadeType);
        this.skadeBeskrivelse = new SimpleStringProperty(skadeBeskrivelse);
        this.belopTaksering = new SimpleDoubleProperty(belopTaksering);
        this.erstatningsbelopUtbetalt = new SimpleDoubleProperty(erstatningsbelopUtbetalt);
        this.kontaktinfoVitner = new SimpleStringProperty(kontaktinfoVitner);
        this.status = new SimpleStringProperty(status);
    }

    /**
     * Getter og settere pluss get-metoder for Property-feltene.
     */
    // skadeNr
    public int getSkadeNr() {
        return skadeNr.get();
    }
    public void setSkadeNr(int skadeNr) {
        if (skadeNr < 0) {
            throw new UgyldigLopeNrException("Forsikringsnummer må være større enn 0.");
        }
        this.kundeNr.set(skadeNr);
    }
    public IntegerProperty skadeNrProperty() {
        return skadeNr;
    }

    // forsikringsNr
    public int getKundeNr() {
        return kundeNr.get();
    }
    public void setKundeNr(int kundeNr) {
        this.kundeNr.set(kundeNr);
    }
    public IntegerProperty kundeNrProperty() {
        return kundeNr;
    }

    //datoSkade
    public LocalDate getDatoSkade() {
        return datoSkade.get();
    }
    public void setDatoSkade(LocalDate datoSkade) {
        this.datoSkade.set(datoSkade);
    }
    public ObjectProperty<LocalDate> datoSkadeProperty() {
        return datoSkade;
    }

    // skadeType
    public String getSkadeType() { return skadeType.get(); }
    public void setSkadeType(String skadeType) {
        this.skadeType.set(skadeType);
    }
    public StringProperty skadeTypeProperty() {
        return skadeType;
    }

    // skadeBeskrivelse
    public String getSkadeBeskrivelse() { return skadeBeskrivelse.get(); }
    public void setSkadeBeskrivelse(String skadeBeskrivelse) {
        this.skadeBeskrivelse.set(skadeBeskrivelse);
    }
    public StringProperty skadeBeskrivelseProperty() {
        return skadeType;
    }

    // belopTaksering
    public Double getBelopTaksering() { return belopTaksering.get(); }
    public void setBelopTaksering(Double belopTaksering) {
        this.belopTaksering.set(belopTaksering);
    }
    public DoubleProperty belopTakseringProperty() {
        return belopTaksering;
    }

    // erstatningsbelopUtbetalt
    public Double getErstatningsbelopUtbetalt() { return erstatningsbelopUtbetalt.get(); }
    public void setErstatningsbelopUtbetalt(Double erstatningsbelopUtbetalt) {
        this.erstatningsbelopUtbetalt.set(erstatningsbelopUtbetalt);
    }
    public DoubleProperty erstatningsbelopUtbetaltProperty() {
        return erstatningsbelopUtbetalt;
    }

    /* kontaktinfoVitner
    public HashMap<Integer, String> getKontaktinfoVitner() {
        return kontaktinfoVitner.get();
    }
    public void setKontaktinfoVitner(HashMap<Integer, String> kontaktinfoVitner) {
        this.kontaktinfoVitner.set(kontaktinfoVitner);
    }
    public ObjectProperty<HashMap<Integer, String>> kontaktinfoVitnerProperty() {
        return kontaktinfoVitner;
    }*/

    //kontaktinfo
    public String getKontaktinfoVitner() {
        return kontaktinfoVitner.get();
    }
    public void setKontaktinfoVitner(String kontaktinfoVitner) {
        this.kontaktinfoVitner.set(kontaktinfoVitner);
    }
    public StringProperty kontaktinfoVitnerProperty() {
        return kontaktinfoVitner;
    }

    //status
    public String getStatus() {
        return status.get();
    }
    public void setStatus(String status) {
        this.status.set(status);
    }
    public StringProperty statusProperty() {
        return status;
    }


    /**
     * Tilpasset writeObject-serialisering av Skademelding-objektet da ObservableList og
     * Property-felter ikke er serialiserbart.*/

    private void writeObject(ObjectOutputStream os) throws IOException {
        os.defaultWriteObject();
        os.writeObject(getSkadeNr());
        os.writeObject(getKundeNr());
        os.writeObject(getDatoSkade());
        os.writeObject(getSkadeType());
        os.writeObject(getSkadeBeskrivelse());
        os.writeObject(getBelopTaksering());
        os.writeObject(getErstatningsbelopUtbetalt());
        os.writeObject(getKontaktinfoVitner());
        os.writeObject(getStatus());
    }

    /**
     * Tilpasset readObject-deserialisering av skademelding-objektet.*/

    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        is.defaultReadObject();
        this.skadeNr = new SimpleIntegerProperty((int)is.readObject());
        this.kundeNr = new SimpleIntegerProperty((int)is.readObject());
        this.datoSkade = new SimpleObjectProperty<LocalDate>((LocalDate)is.readObject());
        this.skadeType = new SimpleStringProperty((String)is.readObject());
        this.skadeBeskrivelse = new SimpleStringProperty((String)is.readObject());
        this.belopTaksering = new SimpleDoubleProperty((double)is.readObject());
        this.erstatningsbelopUtbetalt = new SimpleDoubleProperty((double)is.readObject());
        //this.kontaktinfoVitner = new SimpleObjectProperty<HashMap<Integer, String>>((HashMap<Integer, String>)is.readObject());
        this.kontaktinfoVitner = new SimpleStringProperty((String)is.readObject());
        this.status = new SimpleStringProperty((String)is.readObject());
    }

    @Override
    public String toString() {
        return getSkadeNr() +"," +getKundeNr() +","+ getDatoSkade() +","+ getSkadeType() +","+ getSkadeBeskrivelse() +","+
                getBelopTaksering() +","+ getErstatningsbelopUtbetalt()+","+ getKontaktinfoVitner() +","+getStatus();
    }
}


