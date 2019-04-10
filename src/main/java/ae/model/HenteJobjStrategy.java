package ae.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class HenteJobjStrategy implements HenteFilStrategy {
    public ObservableList<Kunde> lesKundeFraFil(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream is = null;
        ObservableList<Kunde> kunder = FXCollections.observableArrayList();
        try {

            FileInputStream fis = new FileInputStream(path);
            is = new ObjectInputStream(fis);

            kunder.setAll((List<Kunde>)is.readObject());
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return kunder;
    }
}
