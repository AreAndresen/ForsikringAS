package ae.model.files;

import ae.model.Kunde;
import ae.model.exceptions.UgyldigKundeFormatException;
import javafx.collections.ObservableList;

import java.io.IOException;

public interface HenteFilStrategy {
    ObservableList<Kunde> hentKunderFraFil(String path) throws IOException, ClassNotFoundException, UgyldigKundeFormatException;
}
