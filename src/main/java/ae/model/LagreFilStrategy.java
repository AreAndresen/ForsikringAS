package ae.model;

import javafx.collections.ObservableList;

import java.io.IOException;

public interface LagreFilStrategy {
    void skrivKundeTilFil(ObservableList<Kunde> kundeTabell, String path) throws IOException;
}