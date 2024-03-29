package ae.model.files;

import ae.HovedApplikasjon;
import ae.model.Kunde;
import ae.model.exceptions.UgyldigKundeFormatException;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class Filbehandling {

    // lagrer fil
    public static void lagreFil(HovedApplikasjon hovedApplikasjon) throws IOException {
        File filPath = Filbehandling.lagreFilVelger(hovedApplikasjon.getHovedStage());

        if (filPath != null) {
            if (filPath.getPath().endsWith(".jobj")) {
                Filbehandling.lagreKunder(new LagreJobjStrategy(),
                        hovedApplikasjon.getKundeData(), filPath.getPath());
            }

            if (filPath.getPath().endsWith(".csv")) {
                Filbehandling.lagreKunder(new LagreCsvStrategy(),
                        hovedApplikasjon.getKundeData(), filPath.getPath());
            }
        }
    }

    // returnerer innholdet i fil
    public static ObservableList<Kunde> hentFil(File filPath) throws IOException,
            ClassNotFoundException, UgyldigKundeFormatException {

        if (filPath != null) {
            if (filPath.getPath().endsWith(".jobj")) {
                return Filbehandling.hentKunder(new HenteJobjStrategy(),
                        filPath.getPath());
            }

            if (filPath.getPath().endsWith(".csv")) {
                return Filbehandling.hentKunder(new HenteCsvStrategy(),
                        filPath.getPath());
            }
        }
        return null;
    }

    private static void lagreKunder(LagreFilStrategy lagringsmetode,
                                    ObservableList<Kunde> kundeTabell, String path)
            throws IOException {
        lagringsmetode.skrivKundeTilFil(kundeTabell, path);
    }

    private static ObservableList<Kunde> hentKunder(HenteFilStrategy hentemetode, String path)
            throws IOException,
            ClassNotFoundException, UgyldigKundeFormatException {
        return hentemetode.hentKunderFraFil(path);
    }

    /**
     * FileChooser lagre fil
     */
    private static File lagreFilVelger(Stage hovedStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lagre til fil");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "CSV-fil (.csv)", "*.csv"),
                new FileChooser.ExtensionFilter(
                        "JOBJ-fil (.jobj)", "*.jobj")
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
                new FileChooser.ExtensionFilter(
                        "CSV-fil (.csv)", "*.csv"),
                new FileChooser.ExtensionFilter(
                        "JOBJ-fil (.jobj)", "*.jobj")
        );

        File fil = fileChooser.showOpenDialog(hovedStage);

        if (fil != null) {
            return fil;
        } else {
            return null;
        }
    }
}