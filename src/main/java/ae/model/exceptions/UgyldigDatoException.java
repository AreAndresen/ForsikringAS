package ae.model.exceptions;

public class UgyldigDatoException extends IllegalArgumentException {
    public UgyldigDatoException() {
        super ("Dato må være dagens dato eller tidligere.");
    }
}
