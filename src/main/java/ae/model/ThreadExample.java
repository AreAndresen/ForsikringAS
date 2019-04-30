package ae.model;

import javafx.concurrent.Task;

public class ThreadExample extends Task<Void> {
    private Runnable runMeWhenDone;

    public ThreadExample(Runnable doneFunc) {
        this.runMeWhenDone = doneFunc;
    }

    @Override
    protected Void call() throws Exception {
        // emulerer en "stor" jobb på 3 sekunder...
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // gjør ikke noe her, men hvis hvis du er i en løkke
            // burde løkkes stoppes med break hvis isCancelled() er true...
        }

        return null;
    }

    // succeeded kjører i main-UI tråden, og UI elementer kan manipuleres her
    @Override
    protected void succeeded() {
        runMeWhenDone.run();
    }
}
