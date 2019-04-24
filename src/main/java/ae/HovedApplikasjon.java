package ae;

import ae.model.Båtforsikring;
import ae.model.Forsikring;
import ae.model.Kunde;
import ae.model.Skademelding;
import ae.model.Viewbehandling;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HovedApplikasjon extends Application {

    public static void main(String[] args) { launch(args); }

    private Stage hovedStage;
    private BorderPane rotOppsett;
    private ObservableList<Kunde> kundeData = FXCollections.observableArrayList();

    /**
     * Konstruktør for hovedapplikasjon. Sett inn dummy-data for testing
     */
    public HovedApplikasjon() { }

    /**
     * Returnerer hovedstagen.
     */
    public Stage getHovedStage() {
        return hovedStage;
    }

    /**
     * Get og set for rotOppsett
     */
    public BorderPane getRotOppsett() { return rotOppsett; }
    public void setRotOppsett(BorderPane rotOppsett) { this.rotOppsett = rotOppsett; }

    /** Returnerer kunde data som en ObservableList.*/
    public ObservableList<Kunde> getKundeData() {
        return kundeData;
    }

    /** Returnerer alle skademeldinger som en ObservableList.*/
    public ObservableList<Skademelding> getAlleSkademeldinger() {

        ObservableList<Skademelding> skademeldinger = FXCollections.observableArrayList();

        for(Kunde kunde : kundeData) {
            skademeldinger.addAll(kunde.getSkademeldinger());
        }
        return skademeldinger;
    }




    @Override
    public void start(Stage hovedStage) {
        this.hovedStage = hovedStage;
        this.hovedStage.setTitle("Forsikring AS");

        // Rotoppsettet kjører så lenge applikasjonen kjører.
        Viewbehandling.initierRotOppsett(this);

        Kunde enKunde = new Kunde(1);
        Forsikring enBåtforsikring = new Båtforsikring(enKunde, 29);
        enBåtforsikring.setType("Båtforsikring");
        Forsikring toBåtforsikring = new Båtforsikring(enKunde, 30);
        toBåtforsikring.setType("Båtforsikring");
        enKunde.getForsikringer().add(enBåtforsikring);
        enKunde.getForsikringer().add(toBåtforsikring);

        Kunde toKunde = new Kunde(2);
        Forsikring treBåtforsikring = new Båtforsikring(toKunde, 31);
        treBåtforsikring.setType("Båtforsikring");
        Forsikring fireBåtforsikring = new Båtforsikring(toKunde, 32);
        fireBåtforsikring.setType("Båtforsikring");
        toKunde.getForsikringer().add(treBåtforsikring);
        toKunde.getForsikringer().add(fireBåtforsikring);

        kundeData.add(enKunde);
        kundeData.add(toKunde);

    }
}
