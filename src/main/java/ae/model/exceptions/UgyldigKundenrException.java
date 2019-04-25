package ae.model.exceptions;

public class UgyldigKundenrException extends IllegalArgumentException {
    public UgyldigKundenrException() {
        super ("Kundenummer må være større enn 0.");
    }
}
