package ae.model.exceptions.forsikring;

public class UgyldigTypeException extends IllegalArgumentException {
    public UgyldigTypeException() {
        super("Type må være en gyldig forsikringstype.");
    }
}
