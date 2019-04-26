package ae.model;

import ae.model.exceptions.UgyldigKundeFormatException;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Filbehandling {

    public static void lagreKunde(LagreFilStrategy lagringsmetode, ObservableList<Kunde> kundeTabell, String path)
            throws IOException {
        lagringsmetode.skrivKundeTilFil(kundeTabell, path);
    }

    public static ObservableList<Kunde> hentKunde(HenteFilStrategy hentemetode, String path) throws IOException,
            ClassNotFoundException, UgyldigKundeFormatException {
        return hentemetode.lesKundeFraFil(path);
    }

    /**
     * FileChooser lagre fil
     */
    public static File lagreFilVelger(Stage hovedStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lagre til fil");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV-fil (.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("JOBJ-fil (.jobj)", "*.jobj")
        );

        File fil = fileChooser.showSaveDialog(hovedStage);

        if (fil != null) {
            return fil;
        } else {
            return null;
        }
    }

    /**
     * FileChooser hente fil
     */
    public static File henteFilVelger(Stage hovedStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Hent en fil");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV-fil (.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("JOBJ-fil (.jobj)", "*.jobj")
        );

        File fil = fileChooser.showOpenDialog(hovedStage);

        if (fil != null) {
            return fil;
        } else {
            return null;
        }
    }
}