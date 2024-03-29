package ae.model.files;

import ae.HovedApplikasjon;
import ae.model.Kunde;
import ae.util.AlertHandler;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.File;

public class InnlesingThread extends Task<ObservableList<Kunde>> {
    private File filPath;
    private HovedApplikasjon hovedApplikasjon;
    private Runnable kjørNårFerdig;

    public InnlesingThread(File filPath, HovedApplikasjon hovedApplikasjon,
                           Runnable kjørNårFerdig) {
        this.filPath = filPath;
        this.hovedApplikasjon = hovedApplikasjon;
        this.kjørNårFerdig = kjørNårFerdig;
    }

    @Override
    protected ObservableList<Kunde> call() throws Exception {
        Thread.sleep(3000); // emulerer en litt større jobb
        ObservableList<Kunde> hentetData = Filbehandling.hentFil(filPath);
        return hentetData;
    }

    @Override
    protected void failed() {
        AlertHandler.genererUgyldigInputAlert(this.getException().getMessage());
        kjørNårFerdig.run();
    }

    @Override
    protected void succeeded() {
        hovedApplikasjon.getKundeData().setAll(this.getValue());
        kjørNårFerdig.run();
    }
}
