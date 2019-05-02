package ae.model.files;

import ae.model.Kunde;
import javafx.collections.ObservableList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
