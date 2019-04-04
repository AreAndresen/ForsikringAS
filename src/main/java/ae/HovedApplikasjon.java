package ae;

import java.io.IOException;

import ae.controller.RotOppsettController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HovedApplikasjon extends Application {

    public static void main(String[] args) { launch(args); }

    private Stage hovedStage;
    private BorderPane rotOppsett;

    @Override
    public void start(Stage hovedStage) {
        this.hovedStage = hovedStage;
        this.hovedStage.setTitle("Forsikring AS");

        initierRotOppsett();

    }

    /**
     * Initierer rotoppsettet som skal kjøres ved oppstart.
     */
    public void initierRotOppsett() {
        try {
            // Last inn rotoppsettet fra fxml-fil.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HovedApplikasjon.class.getResource("/view/RotOppsett.fxml"));
            rotOppsett = (BorderPane) loader.load();

            // Vis scenen som inneholder rotoppsettet.
            Scene scene = new Scene(rotOppsett);
            hovedStage.setScene(scene);
            hovedStage.show();

            // Overfør controllere
            RotOppsettController rotOppsettController = loader.getController();
            rotOppsettController.setHovedApplikasjon(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returnerer hovedstagen.
     * @return
     */
    public Stage getHovedStage() {
        return hovedStage;
    }

    public BorderPane getRotOppsett() { return rotOppsett; }
}
