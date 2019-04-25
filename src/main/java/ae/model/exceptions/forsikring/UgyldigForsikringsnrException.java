package ae.model.exceptions.forsikring;

public class UgyldigForsikringsnrException extends IllegalArgumentException {
    public UgyldigForsikringsnrException() {
        super ("Forsikringsnummer må være større enn 0.");
    }
}
