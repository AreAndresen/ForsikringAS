package ae.model;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public interface KundeFilStrategy {
    void skrivKundeTilFil(ObservableList<Kunde> kundeTabell, String path) throws IOException;
    ObservableList<Kunde> lesKundeFraFil(String path) throws IOException, ClassNotFoundException;
}