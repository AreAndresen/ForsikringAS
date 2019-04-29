package ae.model;

import ae.HovedApplikasjon;
import ae.model.exceptions.UgyldigBelopException;
import ae.model.exceptions.UgyldigDatoException;
import ae.model.exceptions.UgyldigInputException;
import ae.model.exceptions.UgyldigLopeNrException;
import javafx.beans.property.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;


public class Skademelding implements Serializable {
    private static final long serialVersionUID = 1;

    private transient IntegerProperty kundeNr;
    private transient IntegerProperty skadeNr;
    private transient ObjectProperty<LocalDate> datoSkade;
    private transient StringProperty skadeType;
    private transient StringProperty skadeBeskrivelse;
    private transient DoubleProperty belopTaksering;
    private transient DoubleProperty erstatningsbelopUtbetalt;
    private transient StringProperty kontaktinfoVitner;
    private transient StringProperty status;


    // < ------------------------------------ KONSTRUKTØRER ------------------------------------ >

    // tomt objekt-konstruktør
    public Skademelding(int kundeNr, int skadeNr) { this(kundeNr, skadeNr, LocalDate.now(), null, null,
            0.0, 0.0, null, null); }

    // default konstruktør
     public Skademelding(int kundeNr, int skadeNr, LocalDate datoSkade, String skadeType, String skadeBeskrivelse,
                         Double belopTaksering, Double erstatningsbelopUtbetalt, String kontaktinfoVitner, String status) {
         this.kundeNr = new SimpleIntegerProperty(kundeNr);
        this.skadeNr = new SimpleIntegerProperty(skadeNr);
        this.datoSkade = new SimpleObjectProperty<LocalDate>(datoSkade);
        this.skadeType = new SimpleStringProperty(skadeType);
        this.skadeBeskrivelse = new SimpleStringProperty(skadeBeskrivelse);
        this.belopTaksering = new SimpleDoubleProperty(belopTaksering);
        this.erstatningsbelopUtbetalt = new SimpleDoubleProperty(erstatningsbelopUtbetalt);
        this.kontaktinfoVitner = new SimpleStringProperty(kontaktinfoVitner);
        this.status = new SimpleStringProperty(status);
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

    // skadeNr
    public int getSkadeNr() {
        return skadeNr.get();
    }
    public void setSkadeNr(int skadeNr) {
        if (skadeNr < 0) {
            throw new UgyldigLopeNrException("Skadenummer må være større enn 0.");
        }
        this.kundeNr.set(skadeNr);
    }
    public IntegerProperty skadeNrProperty() {
        return skadeNr;
    }

    //datoSkade
    public LocalDate getDatoSkade() {
        return datoSkade.get();
    }
    public void setDatoSkade(LocalDate datoSkade) {
        if (datoSkade.isAfter(LocalDate.now())) {
            throw new UgyldigDatoException();
        }
        this.datoSkade.set(datoSkade);
    }
    public ObjectProperty<LocalDate> datoSkadeProperty() {
        return datoSkade;
    }

    // skadeType
    public String getSkadeType() { return skadeType.get(); }
    public void setSkadeType(String skadeType) {
        if (!"Båtforsikring".equals(skadeType) && !"Hus- og innboforsikring".equals(skadeType)
                && !"Fritidsboligforsikring".equals(skadeType) && !"Reiseforsikring".equals(skadeType)) {
            throw new UgyldigInputException("Type må være en gyldig forsikringstype.");
        }
        this.skadeType.set(skadeType);
    }
    public StringProperty skadeTypeProperty() {
        return skadeType;
    }

    // skadeBeskrivelse
    public String getSkadeBeskrivelse() { return skadeBeskrivelse.get(); }
    public void setSkadeBeskrivelse(String skadeBeskrivelse) {
        if (skadeBeskrivelse == null || !skadeBeskrivelse.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.\\?]{1,100}+")) {
            throw new UgyldigInputException("Skadebeskrivelse kan ikke overstige 100 tegn og eneste tillate\n spesialtegn" +
                    "er bindestrek, punktum og ?");
        }
        this.skadeBeskrivelse.set(skadeBeskrivelse);
    }
    public StringProperty skadeBeskrivelseProperty() {
        return skadeBeskrivelse;
    }

    // belopTaksering
    public Double getBelopTaksering() { return belopTaksering.get(); }
    public void setBelopTaksering(Double belopTaksering) {
        if (belopTaksering <= 0) {
            throw new UgyldigBelopException("Takseringsbeløp kan ikke være mindre enn 0.");
        }
        this.belopTaksering.set(belopTaksering);
    }
    public DoubleProperty belopTakseringProperty() {
        return belopTaksering;
    }

    // erstatningsbelopUtbetalt
    public Double getErstatningsbelopUtbetalt() { return erstatningsbelopUtbetalt.get(); }
    public void setErstatningsbelopUtbetalt(Double erstatningsbelopUtbetalt) {
        if (erstatningsbelopUtbetalt <= 0) {
            throw new UgyldigBelopException("ErstatningsbelopUtbetalt kan ikke være mindre enn 0.");
        }
        this.erstatningsbelopUtbetalt.set(erstatningsbelopUtbetalt);
    }
    public DoubleProperty erstatningsbelopUtbetaltProperty() {
        return erstatningsbelopUtbetalt;
    }

    //kontaktinfo
    public String getKontaktinfoVitner() { return kontaktinfoVitner.get(); }
    public void setKontaktinfoVitner(String kontaktinfoVitner) {
        if (!kontaktinfoVitner.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.\\?]{1,200}+")) {
            throw new UgyldigInputException("Vitner kan ikke overstige 200 tegn og eneste tillate\n spesialtegn" +
                    "er bindestrek, punktum og ?");
        }
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


    // < ------------------------------------ INPUT-VALIDERING ------------------------------------ >

    //skadenr
    public String sjekkOgOppdaterSkadeNr(TextField skadeNrField) {
        String msg = "";

        if (skadeNrField.getText() == null || skadeNrField.getText().isEmpty()) {
            msg += "Skadenummer kan ikke være tomt.\n";
        } else {
            try {
                setSkadeNr(Integer.parseInt(skadeNrField.getText()));
            } catch (NumberFormatException e) {
                msg += "Skadenummer nå være tall.\n";
            } catch (UgyldigLopeNrException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

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

    // skadedato
    public String sjekkOgOppdaterDatoSkade(TextField datoSkademeldingOpprettetField) {
        String msg = "";

        if (datoSkademeldingOpprettetField.getText() == null || datoSkademeldingOpprettetField.getText().isEmpty()) {
            msg += "Dato kan ikke være tom.\n";
        } else {
            try {
                setDatoSkade(LocalDate.parse(datoSkademeldingOpprettetField.getText()));
            } catch (DateTimeException e) {
                msg += "Dato er ikke en gyldig dato.\n";
            } catch (UgyldigDatoException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // skadetype
    public String sjekkOgOppdaterSkadetype(ChoiceBox skadeTypeField) {
        String msg = "";

        if (skadeTypeField.getValue() == null || skadeTypeField.getItems().isEmpty()) {
            msg += "Skadetype kan ikke være tom.\n";
        } else {
            try {
                setSkadeType(skadeTypeField.getValue().toString());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // skadebeskrivelse
    public String sjekkOgOppdaterSkadebeskrivelse(TextArea skadebeskrivelseField) {
        String msg = "";

        if (skadebeskrivelseField.getText() == null || skadebeskrivelseField.getText().isEmpty()) {
            msg += "Skadebeskrivelse kan ikke være tom.\n";
        } else {
            try {
                setSkadeBeskrivelse(skadebeskrivelseField.getText());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // Takseringsbeløp
    public String sjekkOgOppdaterTakseringsbeløp(TextField belopTakseringField) {
        String msg = "";

        if (belopTakseringField.getText() == null || belopTakseringField.getText().isEmpty()) {
            msg += "Takseringssbeløp kan ikke være tomt.\n";
        } else {
            try {
                setBelopTaksering(Double.parseDouble(belopTakseringField.getText()));
            } catch (NumberFormatException e) {
                msg += "Takseringssbeløp må være tall.\n";
            } catch (UgyldigBelopException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // erstatningsbelop Utbetalt
    public String sjekkOgOppdaterErstatningsbelopUtbetalt(TextField erstatningsbelopUtbetaltField) {
        String msg = "";

        if (erstatningsbelopUtbetaltField.getText() == null || erstatningsbelopUtbetaltField.getText().isEmpty()) {
            msg += "Utbetalt erstatningssbeløp kan ikke være tomt.\n";
        } else {
            try {
                setErstatningsbelopUtbetalt(Double.parseDouble(erstatningsbelopUtbetaltField.getText()));
            } catch (NumberFormatException e) {
                msg += "Utbetalt erstatningssbeløp må være tall.\n";
            } catch (UgyldigBelopException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    // skadebeskrivelse
    public String sjekkOgOppdaterKontaktinfoVitner(TextArea kontaktinfoVitnerField) {
        String msg = "";
         try {
             setKontaktinfoVitner(kontaktinfoVitnerField.getText());
         } catch (UgyldigInputException e) {
             msg += e.getMessage() + "\n";
         }

        return msg;
    }

    // status
    public String sjekkOgOppdaterStatus(ChoiceBox statusField) {
        String msg = "";

        if (statusField.getValue() == null || statusField.getItems().isEmpty()) {
            msg += "Status kan ikke være tom.\n";
        } else {
            try {
                setStatus(statusField.getValue().toString());
            } catch (UgyldigInputException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }


    // < ------------------------------------ SERIALISERING ------------------------------------ >

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
        this.kontaktinfoVitner= new SimpleStringProperty((String)is.readObject());
        this.status = new SimpleStringProperty((String)is.readObject());
    }

    // < ------------------------------------ toString - CSV ------------------------------------ >

    @Override
    public String toString() {
        return getSkadeNr() +"," +getKundeNr() +","+ getDatoSkade() +","+ getSkadeType() +","+ getSkadeBeskrivelse() +","+
                getBelopTaksering() +","+ getErstatningsbelopUtbetalt()+","+ getKontaktinfoVitner() +","+getStatus();
    }
}


