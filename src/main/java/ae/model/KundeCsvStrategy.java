package ae.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class KundeCsvStrategy implements KundeFilStrategy {
    @Override
    public void skrivKundeTilFil(ObservableList<Kunde> kundeTabell, String path) throws IOException {
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

    @Override
    public ObservableList<Kunde> lesKundeFraFil(String path) throws IOException, ClassNotFoundException {

        return null;
    }
}
