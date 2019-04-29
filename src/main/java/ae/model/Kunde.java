package ae.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ae.model.exceptions.kunde.UgyldigAdresseException;
import ae.model.exceptions.kunde.UgyldigEtternavnException;
import ae.model.exceptions.kunde.UgyldigFornavnException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

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
    private transient ObjectProperty<ObservableList<Forsikring>> forsikringer;
    private transient ObjectProperty<ObservableList<Skademelding>> skademeldinger;
    private transient IntegerProperty antallErstatningerUbetalte;

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
        this.forsikringer = new SimpleObjectProperty<ObservableList<Forsikring>>(FXCollections.observableArrayList());
        this.skademeldinger = new SimpleObjectProperty<ObservableList<Skademelding>>(FXCollections.observableArrayList());

        this.antallErstatningerUbetalte = new SimpleIntegerProperty(0);
    }

    /**
     * Konstruktør for kunde-opprettelse ved innlesing av fil.
     */
    public Kunde(int kundeNr, LocalDate datoKundeOpprettet, String etternavn, String fornavn,
                 String adresseFaktura, ObservableList<Forsikring> forsikringer, ObservableList<Skademelding> skademeldinger,
                 int antallErstatningerUbetalte) { //List<Skademelding> her
        this.kundeNr = new SimpleIntegerProperty(kundeNr);
        this.datoKundeOpprettet = new SimpleObjectProperty<LocalDate>(datoKundeOpprettet);
        this.etternavn = new SimpleStringProperty(etternavn);
        this.fornavn = new SimpleStringProperty(fornavn);
        this.adresseFaktura = new SimpleStringProperty(adresseFaktura);
        this.forsikringer = new SimpleObjectProperty<ObservableList<Forsikring>>(forsikringer);
        this.skademeldinger = new SimpleObjectProperty<ObservableList<Skademelding>>(skademeldinger);
        this.antallErstatningerUbetalte  = new SimpleIntegerProperty(antallErstatningerUbetalte);
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
        if(fornavn == null || !fornavn.matches("[a-zA-ZæøåÆØÅ]{2,20}+")){
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
            throw new UgyldigAdresseException();
        }
        this.adresseFaktura.set(adresseFaktura);
    }
    public StringProperty adresseFakturaProperty() {
        return adresseFaktura;
    }

    // forsikringer
    public ObservableList<Forsikring> getForsikringer() {
        return forsikringer.get();
    }
    public void setForsikringer(ObservableList<Forsikring> forsikringer) {
        this.forsikringer.set(forsikringer);
    }
    public ObjectProperty<ObservableList<Forsikring>> forsikringerProperty() {
        return forsikringer;
    }


    // skademeldinger
    public ObservableList<Skademelding> getSkademeldinger() {
        return skademeldinger.get();
    }
    public void setSkademeldinger(ObservableList<Skademelding> skademeldinger) {
        this.skademeldinger.set(skademeldinger);
    }
    public ObjectProperty<ObservableList<Skademelding>> skademeldingerProperty() {
        return skademeldinger;
    }


    //antallErstatningerUbetalte
    public int getAntallErstatningerUbetalte() {
        return antallErstatningerUbetalte.get();
    }
    //TODO metoder for å finne antall ubetalte erstatninger
    public void setAntallErstatningerUbetalte(){
        int antall = 0;

        for(Skademelding skade : this.getSkademeldinger()){
            if(skade.getStatus().equals("Ubetalt")){
                antall++;
            }
        }
        this.antallErstatningerUbetalte.set(antall);
    }
    public IntegerProperty antallErstatningerUbetalteProperty() {
        return antallErstatningerUbetalte;
    }

    // Statisk metode som brukes for søk
    public static boolean behandleSøk(Kunde kunde, String input) {
        // viser alt hvis det ikke er noe skrevet inn
        if (input == null || input.isEmpty()) {
            return true;
        } else {
            // må ta til lower case for å sjekke på alt
            String søkeord = input.toLowerCase();

            if (Integer.toString(kunde.getKundeNr()).startsWith(søkeord)) {
                return true;
            }
            if (kunde.getEtternavn().toLowerCase().startsWith(søkeord)) {
                return true;
            }
            if (kunde.getFornavn().toLowerCase().startsWith(søkeord)) {
                return true;
            }
            if (kunde.getAdresseFaktura().toLowerCase().contains(søkeord)) {
                return true;
            }
            if (kunde.getDatoKundeOpprettet().toString().contains(søkeord)) {
                return true;
            }
        }
        return false;
    }


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
        os.writeObject(new ArrayList<>(getForsikringer()));
        os.writeObject(new ArrayList<>(getSkademeldinger()));
        os.writeObject(getAntallErstatningerUbetalte());
    }

    /**
     * Tilpasset readObject-deserialisering av Kunde-objektet.
     */
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        is.defaultReadObject();
        this.kundeNr = new SimpleIntegerProperty((int)is.readObject());
        this.datoKundeOpprettet = new SimpleObjectProperty<>((LocalDate) is.readObject());
        this.etternavn = new SimpleStringProperty((String)is.readObject());
        this.fornavn = new SimpleStringProperty((String)is.readObject());
        this.adresseFaktura = new SimpleStringProperty((String)is.readObject());
        this.forsikringer = new SimpleObjectProperty<>(FXCollections.observableArrayList((List<Forsikring>) is.readObject()));
        this.skademeldinger = new SimpleObjectProperty<>(FXCollections.observableArrayList((List<Skademelding>) is.readObject()));
        this.antallErstatningerUbetalte = new SimpleIntegerProperty((int)is.readObject());

    }

    @Override
    public String toString() {
        return getKundeNr() +","+ getDatoKundeOpprettet() +","+ getEtternavn() +","+ getFornavn() +","+
                getAdresseFaktura() +","+ getForsikringer() +","+ getSkademeldinger();
    }
}