package ae.model.exceptions.kunde;

public class UgyldigAdresseFakturaException extends IllegalArgumentException {
    public UgyldigAdresseFakturaException() {
        super("Faktura adresse er tomt eller inneholder ugyldig input.");
    }
}
