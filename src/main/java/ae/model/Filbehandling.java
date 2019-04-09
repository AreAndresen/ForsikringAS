package ae.model;

import ae.model.Kunde;
import ae.model.KundeFilStrategy;
import javafx.collections.ObservableList;

import java.io.IOException;

public class Filbehandling {

    public void lagreKunde(KundeFilStrategy lagringsmetode, ObservableList<Kunde> kundeTabell, String path) throws IOException {
        lagringsmetode.skrivKundeTilFil(kundeTabell, path);
    }
}
