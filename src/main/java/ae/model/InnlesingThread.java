package ae.model;

import javafx.concurrent.Task;

public class InnlesingThread extends Task<Void> {
    private Runnable kjørNårFerdig;

    public InnlesingThread(Runnable kjørNårFerdig) {
        this.kjørNårFerdig = kjørNårFerdig;
    }

    @Override
    protected Void call() throws Exception {
        // emulerer en "stor" jobb på 3 sekunder...
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // gjør ikke noe her, men hvis hvis du er i en løkke
            // burde løkkes stoppes med break hvis isCancelled() er true...
        }

        return null;
    }

    @Override
    protected void succeeded() {
        kjørNårFerdig.run();
    }
}
