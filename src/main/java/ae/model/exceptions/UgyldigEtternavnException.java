package ae.model.exceptions;

public class UgyldigEtternavnException extends IllegalArgumentException{
    public UgyldigEtternavnException() {
        super("Etternavnet er tomt eller inneholder ugyldig input.");
    }
}

