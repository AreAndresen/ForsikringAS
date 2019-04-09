package ae.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KundeJobjStrategy implements KundeFilStrategy {
    @Override
    public void skrivKundeTilFil(ObservableList<Kunde> kundeTabell, String path) throws IOException {
        ObjectOutputStream out = null;
        try {

            FileOutputStream fos = new FileOutputStream(path);
            out = new ObjectOutputStream(fos);

            out.writeObject(new ArrayList<Kunde>(kundeTabell));
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

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
