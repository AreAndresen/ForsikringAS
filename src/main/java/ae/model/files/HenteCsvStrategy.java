package ae.model.files;

import ae.model.*;
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
                kunder.add(parseKunde(linje, kunder));
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return kunder;
    }

    private static Kunde parseKunde(String linje, ObservableList<Kunde> kunder) throws UgyldigKundeFormatException {
        // først sjekke om det er kunde, forsikring og skademelding formattert
        String[] linjeSplit = linje.split(";\\[");
        if (linjeSplit.length != 3) {
            throw new UgyldigKundeFormatException("Fil er ikke skrevet på riktig format.");
        }

        String[] kundeData = linjeSplit[0].split(";");

        // fjerner ] som kommer etter siste verdi i listen
        String forsikringString = linjeSplit[1].replace("]", "");
        String skademeldingString = linjeSplit[2].replace("]", "");

        // fjerner whitespace etter kommaet/semicolon som separerer objektene i listen
        forsikringString = forsikringString.replaceAll(";\\s*|,\\s*", ";");
        skademeldingString = skademeldingString.replaceAll("}\\,\\s*", ";");

        String[] forsikringData = null ;
        String[] skademeldingData = null;
        if (forsikringString != "") {
            forsikringData = forsikringString.split(";");
        }
        if (skademeldingString != "") {
            skademeldingData = skademeldingString.split(";");
        }

        if (kundeData.length != 5) {
            throw new UgyldigKundeFormatException("Kunde er ikke formatert korrekt.");
        }

        // oppretter kunden
        int kundeNr = parseInt(kundeData[0], "Kundenummer er ikke et tall.");
        for (Kunde kunde : kunder) {
            if (kundeNr == kunde.getKundeNr()) {
                throw new UgyldigKundeFormatException("Det er oppdaget flere kunder med samme kundenummer.");
            }
        }
        LocalDate datoKundeOpprettet = LocalDate.parse(kundeData[1]);
        String etternavn = kundeData[2];
        String fornavn = kundeData[3];
        String adresseFaktura = kundeData[4];

        Kunde tmpKunde = new Kunde(kundeNr, datoKundeOpprettet, etternavn, fornavn, adresseFaktura);

        // legger til forsikringene til kunden
        if (forsikringData != null && forsikringData.length >= 7) {
            leggTilForsikringer(forsikringData, tmpKunde);
        }

        // legger til skademeldingene til kunden
        if (skademeldingData != null && skademeldingData.length >= 9) {
            leggTilSkademeldinger(skademeldingData, tmpKunde);
        }

        tmpKunde.setAntallErstatningerUbetalte();
        return tmpKunde;
    }

    private static void leggTilForsikringer(String[] forsikringer, Kunde kunde) throws UgyldigKundeFormatException {
        for (int i = 0; i < forsikringer.length && forsikringer != null; i += 7) {
            int kundeNr = parseInt(forsikringer[i], "Kundenummer i forsikring er ikke et heltall.");
            int forsikringsNr = parseInt(forsikringer[i + 1], "Forsikringsnummber i forsikring er ikke et heltall.");
            for (Forsikring forsikring : kunde.getForsikringer()) {
                if (forsikringsNr == forsikring.getForsikringsNr()) {
                    throw new UgyldigKundeFormatException("Det er oppdaget flere forsikringer med samme forsikringsnummer.");
                }
            }
            double premie = parseDouble(forsikringer[i + 2], "Årlig premie er ikke et tall.");
            LocalDate datoOpprettet = LocalDate.parse(forsikringer[i + 3]);
            double forsikringsbelop = parseDouble(forsikringer[i + 4], "Forsikringsbeløp er ikke et" +
                    "desimaltall.");
            String betingelser = forsikringer[i + 5];
            String type = forsikringer[i + 6];

            Forsikring tmpForsikring = null;
            if ("Båtforsikring".equals(type)) {
                tmpForsikring = new BåtForsikring(kundeNr, forsikringsNr);
            }
            if ("Hus- og innboforsikring".equals(type)) {
                tmpForsikring = new BoligForsikring(kundeNr, forsikringsNr,
                        "Hus- og innboforsikring");
            }
            if ("Fritidsboligforsikring".equals(type)) {
                tmpForsikring = new BoligForsikring(kundeNr, forsikringsNr,
                        "Fritidsboligforsikring");
            }
            if ("Reiseforsikring".equals(type)) {
                tmpForsikring = new ReiseForsikring(kundeNr, forsikringsNr);
            }
            tmpForsikring.setÅrligPremie(premie);
            tmpForsikring.setDatoOpprettet(datoOpprettet);
            tmpForsikring.setForsikringsBelop(forsikringsbelop);
            tmpForsikring.setBetingelser(betingelser);
            tmpForsikring.setType(type);

            kunde.getForsikringer().add(tmpForsikring);
        }
    }

    private static void leggTilSkademeldinger(String[] skademeldinger, Kunde kunde)
            throws UgyldigKundeFormatException {
        for (int i = 0; i < skademeldinger.length && skademeldinger != null; i += 9) {
            int skadeNr = parseInt(skademeldinger[i], "Skadenummer i skademelding er ikke et heltall.??");
            for (Skademelding skade : kunde.getSkademeldinger()) {
                if (skadeNr == skade.getSkadeNr()) {
                    throw new UgyldigKundeFormatException("Det er oppdaget flere skademeldinger med samme" +
                            " skadenummer.");
                }
            }
            int kundeNr = parseInt(skademeldinger[i + 1], "Kundenummer i skademelding er ikke et heltall.");
            LocalDate datoSkade = LocalDate.parse(skademeldinger[i + 2]);
            String skadeType = skademeldinger[i + 3];
            String skadeBeskrivelse = skademeldinger[i + 4];
            double belopTaksering = parseDouble(skademeldinger[i + 5], "Takseringsbeløp er ikke et tall.");
            double erstatningsBelopUtbetalt = parseDouble(skademeldinger[i + 6], "Utbetalt erstatningsbeløp" +
                    " er ikke et tall.");
            String status = skademeldinger[i + 7];

            // omformaterer vitner-strengen, sånn at det blir hendig å legge til
            String removeBracket = skademeldinger[i + 8].replace("{", "");
            String replace = removeBracket.replaceAll("[=|}]",",");
            String[] kontaktinfoVitner = replace.split(",");

            Skademelding tmpSkademelding = new Skademelding(kundeNr, skadeNr);
            tmpSkademelding.setDatoSkade(datoSkade);
            tmpSkademelding.setSkadeType(skadeType);
            tmpSkademelding.setSkadeBeskrivelse(skadeBeskrivelse);
            tmpSkademelding.setBelopTaksering(belopTaksering);
            tmpSkademelding.setErstatningsbelopUtbetalt(erstatningsBelopUtbetalt);
            tmpSkademelding.setStatus(status);

            // legger til vitner
            for (int y = 0; y < kontaktinfoVitner.length; y+=2) {
                String tlfnr = kontaktinfoVitner[y];
                String navn = kontaktinfoVitner[y + 1];
                tmpSkademelding.getKontaktinfoVitner().put(tlfnr, navn);
            }
            kunde.getSkademeldinger().add(tmpSkademelding);
        }
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
