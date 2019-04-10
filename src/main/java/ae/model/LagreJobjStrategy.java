package ae.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LagreJobjStrategy implements LagreFilStrategy {
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
}
