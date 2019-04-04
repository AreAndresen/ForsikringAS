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
 *
 * @author Hans-Erling Eidsvold
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
     * Konstruktør.
     *
     * @param etternavn kundens etternavn
     * @param fornavn kundens fornavn
     * @param adresseFaktura kundens fakturaadresse
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

}
