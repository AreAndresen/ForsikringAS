package ae.model;

import ae.model.exceptions.UgyldigKundeFormatException;
import javafx.collections.ObservableList;

import java.io.IOException;

public interface HenteFilStrategy {
    ObservableList<Kunde> lesKundeFraFil(String path) throws IOException, ClassNotFoundException, UgyldigKundeFormatException;
}
