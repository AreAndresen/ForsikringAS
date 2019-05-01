package ae.model;

import ae.HovedApplikasjon;
import ae.model.exceptions.UgyldigKundeFormatException;
import ae.util.AlertHandler;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Filbehandling {

    private static void lagreKunde(LagreFilStrategy lagringsmetode, ObservableList<Kunde> kundeTabell, String path)
            throws IOException, FileNotFoundException {
        lagringsmetode.skrivKundeTilFil(kundeTabell, path);
    }

    private static ObservableList<Kunde> hentKunde(HenteFilStrategy hentemetode, String path) throws IOException,
            ClassNotFoundException, UgyldigKundeFormatException {
        return hentemetode.lesKundeFraFil(path);
    }

    // lagrer fil
    public static void lagreFil(HovedApplikasjon hovedApplikasjon) {
        File filPath = Filbehandling.lagreFilVelger(hovedApplikasjon.getHovedStage());

        if (filPath != null) {
            if (filPath.getPath().endsWith(".jobj")) {
                try {
                    Filbehandling.lagreKunde(new LagreJobjStrategy(), hovedApplikasjon.getKundeData(), filPath.getPath());
                } catch (FileNotFoundException e) {
                    AlertHandler.genererWarningAlert("Feilmelding", "Kunne ikke åpne fil",
                            "Filen kan allerede være åpnet. Lukk filen og prøv igjen.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (filPath.getPath().endsWith(".csv")) {
                try {
                    Filbehandling.lagreKunde(new LagreCsvStrategy(), hovedApplikasjon.getKundeData(), filPath.getPath());
                } catch (FileNotFoundException e) {
                    AlertHandler.genererWarningAlert("Feilmelding", "Kunne ikke åpne fil",
                            "Filen kan allerede være åpnet. Lukk filen og prøv igjen.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // returnerer innholdet i fil
    public static ObservableList<Kunde> henteFil(HovedApplikasjon hovedApplikasjon) {
        File filPath = Filbehandling.henteFilVelger(hovedApplikasjon.getHovedStage());

        if (filPath != null) {
            if (filPath.getPath().endsWith(".jobj")) {
                try {
                    return Filbehandling.hentKunde(new HenteJobjStrategy(), filPath.getPath());
                } catch (UgyldigKundeFormatException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if (filPath.getPath().endsWith(".csv")) {
                try {
                    return Filbehandling.hentKunde(new HenteCsvStrategy(), filPath.getPath());
                } catch (UgyldigKundeFormatException e) {
                    AlertHandler.genererUgyldigInputAlert(e.getMessage());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * FileChooser lagre fil
     */
    private static File lagreFilVelger(Stage hovedStage) {
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
    private static File henteFilVelger(Stage hovedStage) {
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