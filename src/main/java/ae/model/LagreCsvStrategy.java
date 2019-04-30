package ae.model;

import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class LagreCsvStrategy implements LagreFilStrategy {
    @Override
    public void skrivKundeTilFil(ObservableList<Kunde> kundeTabell, String path) throws IOException, FileNotFoundException {
        PrintWriter skriver = null;
        try {
            skriver = new PrintWriter(path, "UTF-8");
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
