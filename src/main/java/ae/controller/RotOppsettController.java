package ae.controller;

import ae.HovedApplikasjon;
import ae.model.*;
import ae.util.AlertHandler;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller for RotOppsett. Rotoppsettet inneholder menylinjen
 * og plass hvor andre views skal plasserers.
 */
public class RotOppsettController {

    // Referanse til hovedapplikasjonen
    private HovedApplikasjon hovedApplikasjon;
    private ExecutorService service = Executors.newSingleThreadExecutor();

    @FXML
    private Menu filMenu;

    public RotOppsettController() {
    }

    /**
     * Kalles fra hovedapplikasjonen for å gi en referanse til seg
     * selv.
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;
    }

    // <------- KUNDE ------->
    //Går til kundeoversikt ved trykk i meny
    @FXML
    private void gåTilKundeoversikt() {
        Viewbehandling.visKundeOversikt(hovedApplikasjon);
    }


    // <------SKADEMELDING------>
    //Går til skademeldingversikt ved trykk i meny
    @FXML
    private void gåTilSkademeldingoversikt() {
        Viewbehandling.visSkademeldingOversikt(hovedApplikasjon);
    }

    /**
     * <--- FORSIKRING --->
     */
    @FXML
    private void gåTilForsikringoversikt() {
        Viewbehandling.visForsikringOversikt(hovedApplikasjon);
    }

    // <---- FILER ----->
    @FXML
    private void lagreFilTrykket() {
        try {
            Filbehandling.lagreFil(hovedApplikasjon);
        } catch (FileNotFoundException e) {
            AlertHandler.genererWarningAlert("Feilmelding", "Kunne ikke åpne fil",
                    "Filen kan allerede være åpnet. Lukk filen og prøv igjen.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void hentFilTrykket() {
        File fil = Filbehandling.henteFilVelger(hovedApplikasjon.getHovedStage());

        if (fil != null) {
            disableKnapper();
            Task<ObservableList<Kunde>> innlesing = new InnlesingThread(fil, hovedApplikasjon,
                    this::enableKnapper);
            service.execute(innlesing);
        }
    }

    private void disableKnapper() {
        filMenu.setDisable(true);
        Viewbehandling.getKundeController().getNyButton().setDisable(true);
        Viewbehandling.getKundeController().getRedigerButton().setDisable(true);
        Viewbehandling.getKundeController().getSlettButton().setDisable(true);
        Viewbehandling.getSkademeldingController().getNyButton().setDisable(true);
        Viewbehandling.getSkademeldingController().getRedigerButton().setDisable(true);
        Viewbehandling.getSkademeldingController().getSlettButton().setDisable(true);
        Viewbehandling.getForsikringController().getNyMenuButton().setDisable(true);
        Viewbehandling.getForsikringController().getRedigerButton().setDisable(true);
        Viewbehandling.getForsikringController().getSlettButton().setDisable(true);
    }

    private void enableKnapper() {
        filMenu.setDisable(false);
        Viewbehandling.getKundeController().getNyButton().setDisable(false);
        Viewbehandling.getKundeController().getRedigerButton().setDisable(false);
        Viewbehandling.getKundeController().getSlettButton().setDisable(false);
        Viewbehandling.getSkademeldingController().getNyButton().setDisable(false);
        Viewbehandling.getSkademeldingController().getRedigerButton().setDisable(false);
        Viewbehandling.getSkademeldingController().getSlettButton().setDisable(false);
        Viewbehandling.getForsikringController().getNyMenuButton().setDisable(false);
        Viewbehandling.getForsikringController().getRedigerButton().setDisable(false);
        Viewbehandling.getForsikringController().getSlettButton().setDisable(false);
    }

    @FXML
    private void avsluttTrykket() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(hovedApplikasjon.getHovedStage());
        alert.setTitle("Avslutt");
        alert.setHeaderText("Bekreft avlsutning");
        alert.setContentText("Er du sikker på at du ønsker å avslutte?");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Bekreft");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            hovedApplikasjon.getHovedStage().close();
        }
    }

    @FXML
    private void visOmOss() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(hovedApplikasjon.getHovedStage());
        alert.setTitle("Om oss");
        alert.setHeaderText("Semesteroppgave DATA1600 Vår 2019");
        alert.setContentText("Dette er en semesteroppgave av studenter ved OsloMet.");

        alert.showAndWait();
    }
}