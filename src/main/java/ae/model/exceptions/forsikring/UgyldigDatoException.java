package ae.model.exceptions.forsikring;

public class UgyldigDatoException extends IllegalArgumentException {
    public UgyldigDatoException() {
        super ("Dato må være dagens dato eller tidligere.");
    }
}
