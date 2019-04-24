package ae.controller;

import ae.HovedApplikasjon;
import ae.model.*;
import ae.util.IdUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for RotOppsett. Rotoppsettet inneholder menylinjen
 * og plass hvor andre views skal plasserers.
 */
public class RotOppsettController {

    // Referanse til hovedapplikasjonen
    private HovedApplikasjon hovedApplikasjon;

    @FXML
    private MenuItem lagreFilMenuItem;

    public RotOppsettController() { }

    /**
     * Kalles fra hovedapplikasjonen for å gi en referanse til seg
     * selv.
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;
    }

    //-------KUNDE-------
    //Går til kundeoversikt ved trykk i meny
    @FXML
    private void gåTilKundeoversikt() {
        Viewbehandling.visKundeOversikt(hovedApplikasjon);
        lagreFilMenuItem.setDisable(false);
    }

    //Ny skademelding popup
    @FXML
    public void gåTilNyKundePopup() {
        Kunde nyKunde = new Kunde(IdUtil.genererLøpenummer(hovedApplikasjon.getKundeData()));
        boolean bekreftTrykket = Viewbehandling.visNyKundePopup(hovedApplikasjon, nyKunde);

        if (bekreftTrykket) {
            hovedApplikasjon.getKundeData().add(nyKunde);
        }
    }


    //-------SKADEMELDING-------
    //Går til skademeldingversikt ved trykk i meny
    @FXML
    private void gåTilSkademeldingoversikt() {
        Viewbehandling.visSkademeldingOversikt(hovedApplikasjon);
        lagreFilMenuItem.setDisable(false);
    }

    //Ny skademelding popup
    @FXML
    public void gåTilNySkademeldingPopup() {
        Skademelding nySkademelding = new Skademelding(IdUtil.genererLøpenummerSkade(hovedApplikasjon.getKundeData()));
        boolean bekreftTrykket = Viewbehandling.visNySkademeldingPopup(hovedApplikasjon, nySkademelding);

        if (bekreftTrykket) {

            //TODO MÅ FÅ TIL EN KOBLIG PÅ KUNDENØKKEL TIL SKADEMELDING
            // legger til skademelding til riktig kundearray

            //henter
            ObservableList<Kunde> kunder =  hovedApplikasjon.getKundeData(); //kundeData
            for(Kunde enKunde : kunder) {
                if (enKunde.getKundeNr() == (nySkademelding.getForsikringsNr())) {
                    //List<Skademelding> skademeldingerArray = enKunde.getSkademeldinger();
                    ObservableList<Skademelding> skademeldingerArray = enKunde.getSkademeldinger();
                    skademeldingerArray.add(nySkademelding); //legger til ny skademelding
                    enKunde.setSkademeldinger(skademeldingerArray);

                }
            }
        }
    }


    ////-------KUNDE------- - FILER
    @FXML
    public void lagreFilTrykket() {
        File filPath = Filbehandling.lagreFilVelger(hovedApplikasjon.getHovedStage());

        if (filPath.getPath().endsWith(".jobj")) {
            try {
                Filbehandling.lagreKunde(new LagreJobjStrategy(), hovedApplikasjon.getKundeData(), filPath.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (filPath.getPath().endsWith(".csv")) {
            try {
                Filbehandling.lagreKunde(new LagreCsvStrategy(), hovedApplikasjon.getKundeData(), filPath.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void hentFilTrykket() {
        File filPath = Filbehandling.henteFilVelger(hovedApplikasjon.getHovedStage());

        if (filPath.getPath().endsWith(".jobj")) {
            try {
                hovedApplikasjon.getKundeData().setAll(Filbehandling.hentKunde(new HenteJobjStrategy(), filPath.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <--- FORSIKRING --->
     */
    @FXML
    public void gåTilForsikringoversikt() {
        Viewbehandling.visForsikringOversikt(hovedApplikasjon);
        lagreFilMenuItem.setDisable(false);
    }

    public void gåTilNyBåtforsikringPopup(ActionEvent actionEvent) {
    }
}