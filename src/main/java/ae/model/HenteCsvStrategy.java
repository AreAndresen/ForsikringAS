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
        String[] linjeSplit = linje.split(",\\[");

        if (linjeSplit.length != 3) {
            throw new UgyldigKundeFormatException("Fil er ikke skrevet på riktig format. 1");
        }

        String[] kundeData = linjeSplit[0].split(",");
        String[] forsikringData = linjeSplit[1].split(",");
        String[] skademeldingData = linjeSplit[2].split(",");

        System.out.println(linjeSplit[1]);
        for (int i = 0; i < forsikringData.length; i++) {
            System.out.print(forsikringData[i] + " -- ");
        }

        if (kundeData.length != 5) {
            throw new UgyldigKundeFormatException("Fil er ikke skrevet på riktig format. 2");
        }

        if (forsikringData.length != 11) {
            throw new UgyldigKundeFormatException("Fil er ikke skrevet på riktig format. 3");
        }

        int kundeNr = parseTall(kundeData[0], "Kundenummer er ikke et tall.");
        LocalDate datoKundeOpprettet = LocalDate.parse(kundeData[1]);
        String etternavn = kundeData[2];
        String fornavn = kundeData[3];
        String adresseFaktura = kundeData[4];

        return new Kunde(kundeNr, datoKundeOpprettet, etternavn, fornavn, adresseFaktura);
    }

    private static int parseTall(String string, String errorMelding) throws UgyldigKundeFormatException {
        int tall;

        try {
            tall = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new UgyldigKundeFormatException(errorMelding);
        }
        return tall;
    }
}
