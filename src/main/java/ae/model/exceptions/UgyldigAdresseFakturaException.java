package ae.model.exceptions;

public class UgyldigAdresseFakturaException extends IllegalArgumentException {
    public UgyldigAdresseFakturaException() {
        super("Faktura adresse er tomt eller inneholder ugyldig input.");
    }
}
