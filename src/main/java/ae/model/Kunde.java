package ae.model;

import ae.HovedApplikasjon;
import ae.model.exceptions.UgyldigDatoException;
import ae.model.exceptions.UgyldigInputException;
import ae.model.exceptions.UgyldigLopeNrException;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class Kunde implements Serializable {
    private static final long serialVersionUID = 1;

    private transient IntegerProperty kundeNr;
    private transient ObjectProperty<LocalDate> datoKundeOpprettet;
    private transient StringProperty etternavn;
    private transient StringProperty fornavn;
    private transient StringProperty adresseFaktura;
    private transient ObjectProperty<ObservableList<Forsikring>> forsikringer;
    private transient ObjectProperty<ObservableList<Skademelding>> skademeldinger;
    private transient IntegerProperty antallErstatningerUbetalte;


    // < ------------------------------------ KONSTRUKTØRER ------------------------------------ >
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


    // < ------------------------------------ GET OG SET ------------------------------------ >

    // kundeNr
    public int getKundeNr() {
        return kundeNr.get();
    }
    public void setKundeNr(int kundeNr) {
        if (kundeNr < 0) {
            throw new UgyldigLopeNrException("Kundenummer må være større enn 0.");
        }
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
        if (datoKundeOpprettet.isAfter(LocalDate.now())) {
            throw new UgyldigDatoException();
        }
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
        if(etternavn == null || !etternavn.matches("[a-zA-ZæøåÆØÅ\\-\\ ]{2,30}+")){
            throw new UgyldigInputException("Etternavn må være mellom 2-30 bokstaver og " +
                    " kan kun inneholde bokstaver og spesialtegnet - ");
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
        if(fornavn == null || !fornavn.matches("[a-zA-ZæøåÆØÅ\\-\\ ]{2,30}+")){
            throw new UgyldigInputException("Fornavn må være mellom 2-30 bokstaver og " +
                    " kan kun inneholde bokstaver og spesialtegnet - ");
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
        if (adresseFaktura == null || !adresseFaktura.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.]{2,50}+")) {
            throw new UgyldigInputException("Adresse kan ikke overstige 50 tegn og eneste tillate\n spesialtegn" +
                    "er bindestrek og punktum.");
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
    public void setAntallErstatningerUbetalte(){
        int antall = 0;
        //teller antall ubetalte
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

    // < ------------------------------------ INPUT-VALIDERING ------------------------------------ >

    //kundeNr - SJEKK AT KUNDEN IKKE FINNES
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

    //kundeNr - SJEKK AT KUNDEN ALLEREDE FINNES
    public String sjekkOgOpprettKundeNr(TextField kundeNrField, HovedApplikasjon hovedApplikasjon) {
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
                if (kundeFinnes) {
                    msg += "Det er allerede en kunde registrert med det\nkundenummeret i systemet.\n";
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

    //oppdaterer fornavn
    public String sjekkOgOppdaterFornavn(TextField fornavnField) {
        String msg = "";

        if (fornavnField.getText() == null || fornavnField.getText().isEmpty()) {
            msg += "Fornavn kan ikke være tom.\n";
        } else {
            try {
                setFornavn(fornavnField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    //oppdaterer etternavn
    public String sjekkOgOppdaterEtternavn(TextField etternavnField) {
        String msg = "";

        if (etternavnField.getText() == null || etternavnField.getText().isEmpty()) {
            msg += "Etternavn kan ikke være tom.\n";
        } else {
            try {
                setEtternavn(etternavnField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    //oppdaterer adresseFaktura
    public String sjekkOgOppdaterAdresseFaktura(TextField adresseField) {
        String msg = "";

        if (adresseField.getText() == null || adresseField.getText().isEmpty()) {
            msg += "Adresse kan ikke være tom.\n";
        } else {
            try {
                setAdresseFaktura(adresseField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // skadedato
    public String sjekkOgOppdaterDatoKundeOpprettet(TextField datoKundeOpprettetField) {
        String msg = "";

        if ( datoKundeOpprettetField.getText() == null || datoKundeOpprettetField.getText().isEmpty()) {
            msg += "Dato kan ikke være tom.\n";
        } else {
            try {
                setDatoKundeOpprettet(LocalDate.parse(datoKundeOpprettetField.getText()));
            } catch (DateTimeException e) {
                msg += "Dato er ikke en gyldig dato.\n";
            } catch (UgyldigDatoException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }


    // < ------------------------------------ SERIALISERING ------------------------------------ >

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