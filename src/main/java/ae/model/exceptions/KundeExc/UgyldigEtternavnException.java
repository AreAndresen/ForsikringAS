package ae.model.exceptions.KundeExc;

public class UgyldigEtternavnException extends IllegalArgumentException{
    public UgyldigEtternavnException() {
        super("Etternavnet er tomt eller inneholder ugyldig input.");
    }
}

