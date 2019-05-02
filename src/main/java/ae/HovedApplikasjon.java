package ae;

import ae.model.*;
import ae.model.BåtForsikring;
import ae.util.IdUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;

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


    @Override
    public void start(Stage hovedStage) {
        this.hovedStage = hovedStage;
        this.hovedStage.setTitle("Forsikring AS");

        // Rotoppsettet kjører så lenge applikasjonen kjører.
        Viewbehandling.initierRotOppsett(this);
        hovedStage.getScene().getStylesheets().add(this.getClass().getResource("/view/style.css").toExternalForm());


        // <-------------------- TEST DUMMY DATA -------------------->
        Kunde testKunde1 = new Kunde(1001, LocalDate.parse("2015-10-05"), "Eidsvold", "Hans-Erling",
                "Pilestredet 31 0166 Oslo");
        kundeData.add(testKunde1);

        Kunde testKunde2 = new Kunde(IdUtil.genererLøpenummerKunde(kundeData), LocalDate.parse("2017-07-14"), "Andresen",
                "Are", "Parkveien 3 0170 Oslo");
        kundeData.add(testKunde2);

        Kunde testKunde3 = new Kunde(IdUtil.genererLøpenummerKunde(kundeData), LocalDate.now(), "Johansen",
                "Frida", "Vaklyriegata 3 0260 Oslo");
        kundeData.add(testKunde3);


        Forsikring testForsikring1 = new BåtForsikring(testKunde1.getKundeNr(), IdUtil.genererLøpenummerForsikring(kundeData),
                50000000, LocalDate.now(), 2000000000, "Hel-kasko", "Båtforsikring", "ABC123",
                "Yacht", 152, 2017, "DX500 2000HK");
        testKunde1.getForsikringer().add(testForsikring1);

        Forsikring testForsikring2 = new BoligForsikring(testKunde1.getKundeNr(), IdUtil.genererLøpenummerForsikring(kundeData),
                900000000, LocalDate.now(), 500000000, "All inventar", "Hus- og innboforsikring", "Osloveien 2 2050 Jessheim",
                2015, "Treverk", "Høy", 280, 350000, 1250000);
        testKunde1.getForsikringer().add(testForsikring2);

        Skademelding testSkademelding1 = new Skademelding(testKunde1.getKundeNr(), IdUtil.genererLøpenummerSkade(kundeData),
                LocalDate.now(), "Reiseforsikring", "Ulykke", 500,
                250, "Ubetalt");
        testSkademelding1.getKontaktinfoVitner().put("12345678", "Ola Nordmann");
        testSkademelding1.getKontaktinfoVitner().put("87654321", "Kari Olsen");
        testKunde1.getSkademeldinger().add(testSkademelding1);
        testKunde1.setAntallErstatningerUbetalte();
    }
}
