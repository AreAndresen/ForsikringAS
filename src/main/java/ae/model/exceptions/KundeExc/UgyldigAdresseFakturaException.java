package ae.model.exceptions.KundeExc;

public class UgyldigAdresseFakturaException extends IllegalArgumentException {
    public UgyldigAdresseFakturaException() {
        super("Faktura adresse er tomt eller inneholder ugyldig input.");
    }
}
