package ae.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class Kunde implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * Nødvendige datafelt for å kommunisere med TableView.
     * transient brukes for at maskinen ikke skal prøve å serialisere Property feltene
     */
    private transient IntegerProperty forsikringsNr;
    private transient ObjectProperty<LocalDate> datoKundeOpprettet;
    private transient StringProperty etternavn;
    private transient StringProperty fornavn;
    private transient StringProperty adresseFaktura;
    private transient ObjectProperty<List<Forsikring>> forsikringer;
    private transient ObjectProperty<List<Skademelding>> skademeldinger;
    private transient ObjectProperty<List<Skademelding>> erstatningerUbetalte;

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

    /*public void registrerSkademelding(Skademelding skademelding){
        //if(!finnes(personnr)){
            //Pasient nyPasient = new Pasient(navn, personnr);
            this.skademeldinger.set(List<Skademelding> skademelding);
        //}
    }*/

    /*public void slettSkademelding(int skadeNr){
        Skademelding skademelding = null;
        for(Skademelding enSkademelding : skademeldinger){
            if(enPasient.getPersonnr().equals(personnr)){
                pasient = enPasient;
                break;
            }
        }
        if(pasient != null){
            pasienter.remove(pasient);
        }
    }

    public boolean finnes(String personnr){
        boolean funnet = false;
        for(Pasient enPasient : pasienter){
            if(enPasient.getPasientnr().equals(personnr)){
                funnet = true;
            }
        }
        return funnet;
    }*/



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

    /**
     * Tilpasset writeObject-serialisering av Kunde-objektet da ObservableList og
     * Property-felter ikke er serialiserbart.
     */
    private void writeObject(ObjectOutputStream os) throws IOException {
        os.defaultWriteObject();
        os.writeObject(getForsikringsNr());
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
        this.forsikringsNr = new SimpleIntegerProperty((int)is.readObject());
        this.datoKundeOpprettet = new SimpleObjectProperty<LocalDate>((LocalDate)is.readObject());
        this.etternavn = new SimpleStringProperty((String)is.readObject());
        this.fornavn = new SimpleStringProperty((String)is.readObject());
        this.adresseFaktura = new SimpleStringProperty((String)is.readObject());
        this.forsikringer = new SimpleObjectProperty<List<Forsikring>>((List<Forsikring>)is.readObject());
        this.skademeldinger = new SimpleObjectProperty<List<Skademelding>>((List<Skademelding>)is.readObject());
        this.erstatningerUbetalte = new SimpleObjectProperty<List<Skademelding>>((List<Skademelding>)is.readObject());
    }

    @Override
    public String toString() {
        return getForsikringsNr() +","+ getDatoKundeOpprettet() +","+ getEtternavn() +","+ getFornavn() +","+
                getAdresseFaktura() +","+ getForsikringer() +","+ getSkademeldinger() +","+ getErstatningerUbetalte();
    }
}