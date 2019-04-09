package ae.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class ObservableHjelper implements Serializable {
    transient ArrayList<Observer> listeners = new ArrayList<Observer>();

    public void update() {
        for (Observer o : listeners) {
            o.update();
        }
    }

    public void add(Observer o) {
        listeners.add(o);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        listeners = new ArrayList<>();
    }
}
