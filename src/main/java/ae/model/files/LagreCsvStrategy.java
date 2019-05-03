package ae.model.files;

import ae.model.Kunde;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class LagreCsvStrategy implements LagreFilStrategy {
    @Override
    public void skrivKundeTilFil(ObservableList<Kunde> kundeTabell, String path)
            throws IOException {
        PrintWriter skriver = null;
        try {
            skriver = new PrintWriter(path, StandardCharsets.UTF_8);
            for (Kunde kunde : kundeTabell) {
                skriver.println(kunde);
            }
        } finally {
            if (skriver != null) {
                skriver.close();
            }
        }
    }
}
