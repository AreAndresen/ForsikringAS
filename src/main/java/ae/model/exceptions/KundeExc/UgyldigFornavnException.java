package ae.model.exceptions.KundeExc;

public class UgyldigFornavnException extends IllegalArgumentException {
    public UgyldigFornavnException() {
        super("Fornavnet er tomt eller inneholder ugyldig input.");
    }
}
