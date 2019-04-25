package ae.model.exceptions.skademelding;

public class UgyldigKundenrException extends IllegalArgumentException {
    public UgyldigKundenrException() {
        super ("Kundenummer må være større enn 0.");
    }
}
