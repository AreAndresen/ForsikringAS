package ae;

import java.io.IOException;

import ae.controller.RotOppsettController;
import ae.model.Kunde;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HovedApplikasjon extends Application {

    public static void main(String[] args) { launch(args); }

    private Stage hovedStage;
    private BorderPane rotOppsett;
    private ObservableList<Kunde> kundeData = FXCollections.observableArrayList();

    /**
     * Konstruktør for hovedapplikasjon med dummy-data for å teste.
     * TODO: Det må lages en metode for å opprette unikt Forsikringsnr
     */
    public HovedApplikasjon() {
        kundeData.add(new Kunde("Eidsvold", "Hans-Erling", "Oslo"));
        kundeData.add(new Kunde("Andresen", "Are", "Drøbak"));
    }

    @Override
    public void start(Stage hovedStage) {
        this.hovedStage = hovedStage;
        this.hovedStage.setTitle("Forsikring AS");

        // Rotoppsettet kjører så lenge applikasjonen kjører.
        initierRotOppsett();

    }

    /**
     * Initierer rotoppsettet som skal kjøres ved oppstart.
     */
    public void initierRotOppsett() {
        try {
            // Last inn fxml-fil.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HovedApplikasjon.class.getResource("/view/RotOppsett.fxml"));
            rotOppsett = (BorderPane) loader.load();

            // Vis scenen som inneholder rotoppsettet.
            Scene scene = new Scene(rotOppsett);
            hovedStage.setScene(scene);
            hovedStage.show();

            // Overfør hovedapplikasjonen til rot-controlleren.
            RotOppsettController rotOppsettController = loader.getController();
            rotOppsettController.setHovedApplikasjon(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returnerer hovedstagen.
     * @return Stage
     */
    public Stage getHovedStage() {
        return hovedStage;
    }

    /**
     * Returnerer rotoppsettet.
     * @return BorderPane
     */
    public BorderPane getRotOppsett() { return rotOppsett; }

    /**
     * Returnerer kunde data som en ObservableList.
     * @return
     */
    public ObservableList<Kunde> getKundeData() {
        return kundeData;
    }
}
