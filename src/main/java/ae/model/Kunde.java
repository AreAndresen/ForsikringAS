package ae.model;

import java.time.LocalDate;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Domenemodell for kunder
 */
public class Kunde {

    /**
     * Nødvendige datafelt for å kommunisere med TableView.
     */
    private final IntegerProperty forsikringsNr;
    private final ObjectProperty<LocalDate> datoKundeOpprettet;
    private final StringProperty etternavn;
    private final StringProperty fornavn;
    private final StringProperty adresseFaktura;
    private final ObjectProperty<List<Forsikring>> forsikringer;
    private final ObjectProperty<List<Skademelding>> skademeldinger;
    private final ObjectProperty<List<Skademelding>> erstatningerUbetalte;

    /**
     * Konstruktør for Ny kunde.
     */
    public Kunde(String etternavn, String fornavn, String adresseFaktura) {

        // Instansiere unikt forsikringsnr og dato for kundens opprettelse.
        this.forsikringsNr = new SimpleIntegerProperty();
        this.datoKundeOpprettet = new SimpleObjectProperty<LocalDate>(LocalDate.now());

        // Ta imot parametere.
        this.etternavn = new SimpleStringProperty(etternavn);
        this.fornavn = new SimpleStringProperty(fornavn);
        this.adresseFaktura = new SimpleStringProperty(adresseFaktura);

        // Instansiere listene så de er opprettet.
        this.forsikringer = new SimpleObjectProperty<List<Forsikring>>();
        this.skademeldinger = new SimpleObjectProperty<List<Skademelding>>();
        this.erstatningerUbetalte = new SimpleObjectProperty<List<Skademelding>>();
    }

    /**
     * Konstruktør for kunde-opprettelse ved innlesing av fil.
     */
    public Kunde(int forsikringsNr, LocalDate datoKundeOpprettet, String etternavn, String fornavn,
                 String adresseFaktura, List<Forsikring> forsikringer, List<Skademelding> skademeldinger,
                 List<Skademelding> erstatningerUbetalte) {
        this.forsikringsNr = new SimpleIntegerProperty(forsikringsNr);
        this.datoKundeOpprettet = new SimpleObjectProperty<LocalDate>(datoKundeOpprettet);
        this.etternavn = new SimpleStringProperty(etternavn);
        this.fornavn = new SimpleStringProperty(fornavn);
        this.adresseFaktura = new SimpleStringProperty(adresseFaktura);
        this.forsikringer = new SimpleObjectProperty<List<Forsikring>>(forsikringer);
        this.skademeldinger = new SimpleObjectProperty<List<Skademelding>>(skademeldinger);
        this.erstatningerUbetalte = new SimpleObjectProperty<List<Skademelding>>(erstatningerUbetalte);
    }

    /**
     * Getter og settere pluss get-metoder for Property-feltene.
     *
     * Felter som forsikringsNr og datoKundeOpprettet skal ikke endres,
     * derfor har de ikke settere.
     */
    // forsikringsNr
    public int getForsikringsNr() {
        return forsikringsNr.get();
    }
    public IntegerProperty forsikringsNrProperty() {
        return forsikringsNr;
    }

    // datoKundeOpprettet
    public LocalDate getDatoKundeOpprettet() {
        return datoKundeOpprettet.get();
    }
    public ObjectProperty<LocalDate> datoKundeOpprettetProperty() {
        return datoKundeOpprettet;
    }

    // etternavn
    public String getEtternavn() {
        return etternavn.get();
    }
    public void setEtternavn(String etternavn) {
        this.etternavn.set(etternavn);
    }
    public StringProperty etternavnProperty() {
        return etternavn;
    }

    // fornavn
    public String getFornavn() {
        return fornavn.get();
    }
    public void setFornavn(String fornavn) {
        this.fornavn.set(fornavn);
    }
    public StringProperty fornavnProperty() {
        return fornavn;
    }

    // adresseFaktura
    public String getAdresseFaktura() {
        return adresseFaktura.get();
    }
    public void setAdresseFaktura(String adresseFaktura) {
        this.adresseFaktura.set(adresseFaktura);
    }
    public StringProperty adresseFakturaProperty() {
        return adresseFaktura;
    }

    // forsikringer
    public List<Forsikring> getForsikringer() {
        return forsikringer.get();
    }
    public void setForsikringer(List<Forsikring> forsikringer) {
        this.forsikringer.set(forsikringer);
    }
    public ObjectProperty<List<Forsikring>> forsikringerProperty() {
        return forsikringer;
    }

    // skademeldinger
    public List<Skademelding> getSkademeldinger() {
        return skademeldinger.get();
    }
    public void setSkademeldinger(List<Skademelding> skademeldinger) {
        this.skademeldinger.set(skademeldinger);
    }
    public ObjectProperty<List<Skademelding>> skademeldingerProperty() {
        return skademeldinger;
    }

    // erstatningerUbetalte
    public List<Skademelding> getErstatningerUbetalte() {
        return erstatningerUbetalte.get();
    }
    public void setErstatningerUbetalte(List<Skademelding> skademeldinger) {
        this.skademeldinger.set(skademeldinger);
    }
    public ObjectProperty<List<Skademelding>> erstatningerUbetalteProperty() {
        return erstatningerUbetalte;
    }
}