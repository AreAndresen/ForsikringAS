package ae.model.exceptions.skademelding;

public class UgyldigKundenrException extends NumberFormatException {
    public UgyldigKundenrException() {
        super ("Kundenummer må være et tall.");
    }
}
