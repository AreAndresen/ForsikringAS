package ae.model;

import ae.model.exceptions.UgyldigInputException;
import ae.model.exceptions.UgyldigKundeFormatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class HenteCsvStrategy implements HenteFilStrategy {
    @Override
    public ObservableList<Kunde> lesKundeFraFil(String path) throws IOException, UgyldigKundeFormatException {
        ObservableList<Kunde> kunder = FXCollections.observableArrayList();
        BufferedReader reader = null;

        try {
            reader = Files.newBufferedReader(Paths.get(path));
            String linje = null;

            while ((linje = reader.readLine()) != null) {
                kunder.add(parseKunde(linje));
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return kunder;
    }

    private static Kunde parseKunde(String linje) throws UgyldigKundeFormatException {
        // først sjekke om det er kunde, forsikring og skademelding formattert
        String[] linjeSplit = linje.split(",\\[");
        if (linjeSplit.length != 3) {
            throw new UgyldigKundeFormatException("Fil er ikke skrevet på riktig format. 1");
        }

        String[] kundeData = linjeSplit[0].split(",");

        // fjerner ] som kommer etter siste verdi i listen
        String forsikringString = linjeSplit[1].replace("]", "");
        String skademeldingString = linjeSplit[2].replace("]", "");

        // fjerner whitespace etter kommaet som separerer objektene i listen
        forsikringString = forsikringString.replaceAll(",\\s*", ",");
        skademeldingString = skademeldingString.replaceAll(",\\s*", ",");

        String[] forsikringData = null ;
        String[] skademeldingData = null;
        if (forsikringString != "") {
            forsikringData = forsikringString.split(",");
        }
        if (skademeldingString != "") {
            skademeldingData = skademeldingString.split(",");
        }

        if (kundeData.length != 5) {
            throw new UgyldigKundeFormatException("Fil er ikke skrevet på riktig format. 2");
        }

        int kundeNr = parseInt(kundeData[0], "Kundenummer er ikke et tall.");
        LocalDate datoKundeOpprettet = LocalDate.parse(kundeData[1]);
        String etternavn = kundeData[2];
        String fornavn = kundeData[3];
        String adresseFaktura = kundeData[4];

        Kunde tmpKunde = new Kunde(kundeNr, datoKundeOpprettet, etternavn, fornavn, adresseFaktura);

        if (forsikringData != null && forsikringData.length >= 6) {
            for (int i = 0; i < forsikringData.length; i+=6)  {
                int kundeNrForsikring = parseInt(forsikringData[i], "Kundenummer er ikke et heltall.");
                int forsikringsNr = parseInt(forsikringData[i+1], "Forsikringsnummber er ikke et heltall.");
                LocalDate datoOpprettet = LocalDate.parse(forsikringData[i+2]);
                double forsikringsbelop = parseDouble(forsikringData[i+3], "Forsikringsbeløp er ikke et" +
                        "desimaltall.");
                String betingelser = forsikringData[i+4];
                String type = forsikringData[i+5];

                if ("Båtforsikring".equals(type)) {
                    Forsikring tmpBåtforsikring = new BåtForsikring(kundeNrForsikring, forsikringsNr);
                    tmpBåtforsikring.setDatoOpprettet(datoOpprettet);
                    tmpBåtforsikring.setForsikringsBelop(forsikringsbelop);
                    tmpBåtforsikring.setBetingelser(betingelser);
                    tmpBåtforsikring.setType(type);

                    tmpKunde.getForsikringer().add(tmpBåtforsikring);
                }
            }

        }
        return tmpKunde;
    }

    private static int parseInt(String string, String errorMelding) throws UgyldigKundeFormatException {
        int tall;

        try {
            tall = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new UgyldigKundeFormatException(errorMelding);
        }
        return tall;
    }

    private static double parseDouble(String string, String errorMelding) throws UgyldigKundeFormatException {
        double tall;

        try {
            tall = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            throw new UgyldigKundeFormatException(errorMelding);
        }
        return tall;
    }
}
