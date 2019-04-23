package ae.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Båtforsikring extends Forsikring {
    private final transient StringProperty registreringsNr;
    private final transient StringProperty typeModell;
    private final transient IntegerProperty lengdeFot;
    private final transient IntegerProperty årsmodell;
    private final transient StringProperty motorEgenskaper;

    // default konstruktør
    public Båtforsikring(Kunde kunde, int forsikringsNr) {
        this(kunde, forsikringsNr, LocalDate.now(), 0, null, null,
                null, 0, 0, null);
    }

    // non-default konstruktør
    public Båtforsikring(Kunde kunde, int forsikringsNr, LocalDate datoOpprettet, int forsikringsBelop,
                         String betingelser, String registreringsNr,  String typeModell, int lengdeFot,
                         int årsmodell, String motorEgenskaper) {
        super(kunde, forsikringsNr, datoOpprettet, forsikringsBelop, betingelser);
        this.registreringsNr = new SimpleStringProperty(registreringsNr);
        this.typeModell = new SimpleStringProperty(typeModell);
        this.lengdeFot = new SimpleIntegerProperty(lengdeFot);
        this.årsmodell = new SimpleIntegerProperty(årsmodell);
        this.motorEgenskaper = new SimpleStringProperty(motorEgenskaper);
    }
}
