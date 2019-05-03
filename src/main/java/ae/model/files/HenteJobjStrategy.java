package ae.model.files;

import ae.model.Kunde;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.util.List;

public class HenteJobjStrategy implements HenteFilStrategy {
    public ObservableList<Kunde> hentKunderFraFil(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream is = null;
        ObservableList<Kunde> kunder = FXCollections.observableArrayList();
        try {

            FileInputStream fis = new FileInputStream(path);
            is = new ObjectInputStream(fis);

            kunder.setAll((List<Kunde>)is.readObject());
        } catch (InvalidClassException e) {
            throw new InvalidClassException("Kunne ikke hente fil. Serialversjon stemmer ikke.");
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
        return kunder;
    }
}
