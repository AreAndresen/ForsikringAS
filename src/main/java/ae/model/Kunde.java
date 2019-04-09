package ae.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ae.model.exceptions.UgyldigAdresseFakturaException;
import ae.model.exceptions.UgyldigEtternavnException;
import ae.model.exceptions.UgyldigFornavnException;
import ae.util.IdUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Domenemodell for kunder
 */
public class Kunde implements Serializable, Observable {
    private static final long serialVersionUID = 1;
    private ObservableHjelper observersHandler = new ObservableHjelper();

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
     * Konstruktør for midlertidig kunde i Ny kunde.
     */
    public Kunde(int forsikringsNr) {
        this(forsikringsNr, LocalDate.now(), null, null, null);
    }

    /**
     * Konstruktør for Ny kunde.
     */
    public Kunde(int forsikringsNr, LocalDate datoKundeOpprettet, String etternavn, String fornavn,
                 String adresseFaktura) {

        // Ta imot parametere.
        this.forsikringsNr = new SimpleIntegerProperty(forsikringsNr);
        this.datoKundeOpprettet = new SimpleObjectProperty<LocalDate>(datoKundeOpprettet);
        this.etternavn = new SimpleStringProperty(etternavn);
        this.fornavn = new SimpleStringProperty(fornavn);
        this.adresseFaktura = new SimpleStringProperty(adresseFaktura);

        // Instansiere listene så de er opprettet.
        this.forsikringer = new SimpleObjectProperty<List<Forsikring>>(new ArrayList<>());
        this.skademeldinger = new SimpleObjectProperty<List<Skademelding>>(new ArrayList<>());
        this.erstatningerUbetalte = new SimpleObjectProperty<List<Skademelding>>(new ArrayList<>());
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
     */
    // forsikringsNr
    public int getForsikringsNr() {
        return forsikringsNr.get();
    }
    public void setForsikringsNr(int forsikringsNr) {
        this.forsikringsNr.set(forsikringsNr);
        observersHandler.update();
    }
    public IntegerProperty forsikringsNrProperty() {
        return forsikringsNr;
    }

    // datoKundeOpprettet
    public LocalDate getDatoKundeOpprettet() {
        return datoKundeOpprettet.get();
    }
    public void setDatoKundeOpprettet(LocalDate datoKundeOpprettet) {
        this.datoKundeOpprettet.set(datoKundeOpprettet);
        observersHandler.update();
    }
    public ObjectProperty<LocalDate> datoKundeOpprettetProperty() {
        return datoKundeOpprettet;
    }

    // etternavn
    public String getEtternavn() {
        return etternavn.get();
    }
    //Set med exception
    public void setEtternavn(String etternavn) {
        if(etternavn == null || etternavn.length() == 0){
            throw new UgyldigEtternavnException();
        }
        this.etternavn.set(etternavn);
        observersHandler.update();
    }
    public StringProperty etternavnProperty() {
        return etternavn;
    }

    // fornavn
    public String getFornavn() {
        return fornavn.get();
    }
    //Set med exception
    public void setFornavn(String fornavn) {
        if(fornavn == null || fornavn.length() == 0){
            throw new UgyldigFornavnException();
        }
        this.fornavn.set(fornavn);
        observersHandler.update();
    }
    public StringProperty fornavnProperty() {
        return fornavn;
    }

    // adresseFaktura
    public String getAdresseFaktura() {
        return adresseFaktura.get();
    }
    //Set med exception
    public void setAdresseFaktura(String adresseFaktura) {
        if(adresseFaktura == null || adresseFaktura.length() == 0){
            throw new UgyldigAdresseFakturaException();
        }
        this.adresseFaktura.set(adresseFaktura);
        observersHandler.update();
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
        observersHandler.update();
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
        observersHandler.update();
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
        observersHandler.update();
    }
    public ObjectProperty<List<Skademelding>> erstatningerUbetalteProperty() {
        return erstatningerUbetalte;
    }

    @Override
    public void observe(Observer o){
        observersHandler.add(o);
    }

    @Override
    public String toString() {
        return getForsikringsNr() +","+ getDatoKundeOpprettet() +","+ getEtternavn() +","+ getFornavn() +","+
                getAdresseFaktura() +","+ getForsikringer() +","+ getSkademeldinger() +","+ getErstatningerUbetalte();
    }
}