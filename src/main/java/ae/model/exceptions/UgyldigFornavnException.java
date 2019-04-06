package ae.model.exceptions;

public class UgyldigFornavnException extends IllegalArgumentException {
    public UgyldigFornavnException() {
        super("Fornavnet er tomt eller inneholder ugyldig input.");
    }
}
