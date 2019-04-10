package ae.model;

import javafx.collections.ObservableList;

import java.io.IOException;

public interface HenteFilStrategy {
    ObservableList<Kunde> lesKundeFraFil(String path) throws IOException, ClassNotFoundException;
}
