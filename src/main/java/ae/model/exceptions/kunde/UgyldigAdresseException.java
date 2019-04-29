package ae.model.exceptions.kunde;

public class UgyldigAdresseException extends IllegalArgumentException {
    public UgyldigAdresseException() {
        super("Faktura adresse er tomt eller inneholder ugyldig input.");
    }
}
