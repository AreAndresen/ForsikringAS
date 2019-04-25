package ae.model.exceptions.kunde;

public class UgyldigFornavnException extends IllegalArgumentException {
    public UgyldigFornavnException() {
        super("Fornavn må være mellom 2 og 20 norske bokstaver.");
    }
}
