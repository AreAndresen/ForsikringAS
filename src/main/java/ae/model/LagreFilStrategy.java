package ae.model;

import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface LagreFilStrategy {
    void skrivKundeTilFil(ObservableList<Kunde> kundeTabell, String path) throws IOException, FileNotFoundException;
}