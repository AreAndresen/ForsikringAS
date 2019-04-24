package ae.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ae.model.exceptions.KundeExc.UgyldigAdresseFakturaException;
import ae.model.exceptions.KundeExc.UgyldigEtternavnException;
import ae.model.exceptions.KundeExc.UgyldigFornavnException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Domenemodell for kunder
 */
public class Kunde implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * Nødvendige datafelt for å kommunisere med TableView.
     * transient brukes for at maskinen ikke skal prøve å serialisere Property feltene
     */
    private transient IntegerProperty kundeNr;
    private transient ObjectProperty<LocalDate> datoKundeOpprettet;
    private transient StringProperty etternavn;
    private transient StringProperty fornavn;
    private transient StringProperty adresseFaktura;
    private transient ObjectProperty<List<Forsikring>> forsikringer;
    //private transient ObjectProperty<List<Skademelding>> skademeldinger;
    private transient ObjectProperty<ObservableList<Skademelding>> skademeldinger;

    //private ObservableList<Kunde> kundeData = FXCollections.observableArrayList();

    //private transient ObjectProperty<List<Skademelding>> erstatningerUbetalte;
    private transient ObjectProperty<HashMap<Integer, Double>> erstatningerUbetalte;


    /**
     * Konstruktør for midlertidig kunde i Ny kunde.
     */
    public Kunde(int kundeNr) {
        this(kundeNr, LocalDate.now(), null, null, null);
    }

    /**
     * Konstruktør for Ny kunde.
     */
    public Kunde(int kundeNr, LocalDate datoKundeOpprettet, String etternavn, String fornavn,
                 String adresseFaktura) {

        // Ta imot parametere.
        this.kundeNr = new SimpleIntegerProperty(kundeNr);
        this.datoKundeOpprettet = new SimpleObjectProperty<LocalDate>(datoKundeOpprettet);
        this.etternavn = new SimpleStringProperty(etternavn);
        this.fornavn = new SimpleStringProperty(fornavn);
        this.adresseFaktura = new SimpleStringProperty(adresseFaktura);

        // Instansiere listene så de er opprettet.
        this.forsikringer = new SimpleObjectProperty<List<Forsikring>>(new ArrayList<>());
        //this.skademeldinger = new SimpleObjectProperty<List<Skademelding>>(new ArrayList<>());
        this.skademeldinger = new SimpleObjectProperty<ObservableList<Skademelding>>(FXCollections.observableArrayList());

        //this.erstatningerUbetalte = new SimpleObjectProperty<List<Skademelding>>(new ArrayList<>());
        this.erstatningerUbetalte = new SimpleObjectProperty<HashMap<Integer, Double>>(new HashMap<>());
    }

    /**
     * Konstruktør for kunde-opprettelse ved innlesing av fil.
     */
    public Kunde(int kundeNr, LocalDate datoKundeOpprettet, String etternavn, String fornavn,
                 String adresseFaktura, List<Forsikring> forsikringer, ObservableList<Skademelding> skademeldinger,
                 HashMap<Integer, Double> erstatningerUbetalte) { //List<Skademelding> her
        this.kundeNr = new SimpleIntegerProperty(kundeNr);
        this.datoKundeOpprettet = new SimpleObjectProperty<LocalDate>(datoKundeOpprettet);
        this.etternavn = new SimpleStringProperty(etternavn);
        this.fornavn = new SimpleStringProperty(fornavn);
        this.adresseFaktura = new SimpleStringProperty(adresseFaktura);
        this.forsikringer = new SimpleObjectProperty<List<Forsikring>>(forsikringer);
        //this.skademeldinger = new SimpleObjectProperty<List<Skademelding>>(skademeldinger);
        this.skademeldinger = new SimpleObjectProperty<ObservableList<Skademelding>>(skademeldinger);

        //this.erstatningerUbetalte = new SimpleObjectProperty<List<Skademelding>>(erstatningerUbetalte);
        this.erstatningerUbetalte = new SimpleObjectProperty<HashMap<Integer, Double>>(erstatningerUbetalte);
    }

    /**
     * Getter og settere pluss get-metoder for Property-feltene.
     */
    // kundeNr
    public int getKundeNr() {
        return kundeNr.get();
    }
    public void setKundeNr(int kundeNr) {
        this.kundeNr.set(kundeNr);
    }
    public IntegerProperty kundeNrProperty() {
        return kundeNr;
    }

    // datoKundeOpprettet
    public LocalDate getDatoKundeOpprettet() {
        return datoKundeOpprettet.get();
    }
    public void setDatoKundeOpprettet(LocalDate datoKundeOpprettet) {
        this.datoKundeOpprettet.set(datoKundeOpprettet);
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


    /* skademeldinger
    public List<Skademelding> getSkademeldinger() {
        return skademeldinger.get();
    }
    public void setSkademeldinger(List<Skademelding> skademeldinger) {
        this.skademeldinger.set(skademeldinger);
    }
    public ObjectProperty<List<Skademelding>> skademeldingerProperty() {
        return skademeldinger;
    }*/
    public ObservableList<Skademelding> getSkademeldinger() {
        return skademeldinger.get();
    }
    public void setSkademeldinger(ObservableList<Skademelding> skademeldinger) {
        this.skademeldinger.set(skademeldinger);
    }
    public ObjectProperty<ObservableList<Skademelding>> skademeldingerProperty() {
        return skademeldinger;
    }


    // erstatningerUbetalte
    public HashMap<Integer, Double> getErstatningerUbetalte() {
        return erstatningerUbetalte.get();
    }
    public void setErstatningerUbetalte(HashMap<Integer, Double> erstatningerUbetalte) {
        this.erstatningerUbetalte.set(erstatningerUbetalte);
    }

    /*public void addErstatningUtbetalt(Integer skadeNr, Double erstatningUbetalt){
        this.erstatningerUbetalte.put(skadeNr, erstatningUbetalt);
    }*/

    public ObjectProperty<HashMap<Integer, Double>> erstatningerUbetalteProperty() {
        return erstatningerUbetalte;
    }


    // erstatningerUbetalte
    /*public List<Skademelding> getErstatningerUbetalte() {
        return erstatningerUbetalte.get();
    }
    public void setErstatningerUbetalte(List<Skademelding> skademeldinger) {
        this.skademeldinger.set(skademeldinger);
    }
    public ObjectProperty<List<Skademelding>> erstatningerUbetalteProperty() {
        return erstatningerUbetalte;
    }*/
    /**
     * Tilpasset writeObject-serialisering av Kunde-objektet da ObservableList og
     * Property-felter ikke er serialiserbart.
     */
    private void writeObject(ObjectOutputStream os) throws IOException {
        os.defaultWriteObject();
        os.writeObject(getKundeNr());
        os.writeObject(getDatoKundeOpprettet());
        os.writeObject(getEtternavn());
        os.writeObject(getFornavn());
        os.writeObject(getAdresseFaktura());
        os.writeObject(getForsikringer());
        os.writeObject(getSkademeldinger());
        os.writeObject(getErstatningerUbetalte());
    }

    /**
     * Tilpasset readObject-deserialisering av Kunde-objektet.
     */
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        is.defaultReadObject();
        this.kundeNr = new SimpleIntegerProperty((int)is.readObject());
        this.datoKundeOpprettet = new SimpleObjectProperty<LocalDate>((LocalDate)is.readObject());
        this.etternavn = new SimpleStringProperty((String)is.readObject());
        this.fornavn = new SimpleStringProperty((String)is.readObject());
        this.adresseFaktura = new SimpleStringProperty((String)is.readObject());
        this.forsikringer = new SimpleObjectProperty<List<Forsikring>>((List<Forsikring>)is.readObject());
        //this.skademeldinger = new SimpleObjectProperty<List<Skademelding>>((List<Skademelding>)is.readObject());
        this.skademeldinger = new SimpleObjectProperty<ObservableList<Skademelding>>((ObservableList<Skademelding>)is.readObject());

        //this.erstatningerUbetalte = new SimpleObjectProperty<List<Skademelding>>((List<Skademelding>)is.readObject());
        this.erstatningerUbetalte = new SimpleObjectProperty<HashMap<Integer, Double>>((HashMap<Integer, Double>)is.readObject());
    }

    @Override
    public String toString() {
        return getKundeNr() +","+ getDatoKundeOpprettet() +","+ getEtternavn() +","+ getFornavn() +","+
                getAdresseFaktura() +","+ getForsikringer() +","+ getSkademeldinger() +","+ getErstatningerUbetalte();
    }
}