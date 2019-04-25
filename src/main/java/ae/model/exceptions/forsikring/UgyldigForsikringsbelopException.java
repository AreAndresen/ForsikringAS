package ae.model.exceptions.forsikring;

public class UgyldigForsikringsbelopException extends IllegalArgumentException {
    public UgyldigForsikringsbelopException() {
        super("Forsikringsbeløp kan ikke være mindre enn 0.");
    }
}
