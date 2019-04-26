package ae.controller;

import ae.HovedApplikasjon;
import ae.controller.util.UgyldigInputHandler;
import ae.model.BåtForsikring;
import ae.model.Forsikring;
import ae.model.Kunde;
import ae.model.Viewbehandling;
import ae.util.IdUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Optional;

public class ForsikringController {

    // tabellen
    @FXML
    public TableView<Forsikring> forsikringTabell;
    @FXML
    public TableColumn<Forsikring, Number> kundeKolonne;
    @FXML
    public TableColumn<Forsikring, Number> forsikringsnrKolonne;
    @FXML
    public TableColumn<Forsikring, LocalDate> datoOpprettetKolonne;
    @FXML
    public TableColumn<Forsikring, Number> forsikringsbelopKolonne;
    @FXML
    public TableColumn<Forsikring, String> betingelserKolonne;
    @FXML
    public TableColumn<Forsikring, String> typeKolonne;

    // labels
    @FXML
    public Label metaEnLabel, metaToLabel, metaTreLabel, metaFireLabel, metaFemLabel,
            metaSeksLabel, metaSjuLabel, metaÅtteLabel;
    @FXML
    public Label resultatEnLabel, resultatToLabel, resultatTreLabel, resultatFireLabel,
            resultatFemLabel, resultatSeksLabel, resultatSjuLabel, resultatÅtteLabel;

    // referanse til hovedapplikasjonen
    private HovedApplikasjon hovedApplikasjon;

    // tom konstruktør
    public ForsikringController() { }

    @FXML
    private void initialize() {
        // koble kolonnene med datafeltene
        kundeKolonne.setCellValueFactory(celleData -> celleData.getValue().kundeNrProperty());
        forsikringsnrKolonne.setCellValueFactory(celleData -> celleData.getValue().forsikringsNrProperty());
        datoOpprettetKolonne.setCellValueFactory(celleData -> celleData.getValue().datoOpprettetProperty());
        forsikringsbelopKolonne.setCellValueFactory(celleData -> celleData.getValue().forsikringsBelopProperty());
        betingelserKolonne.setCellValueFactory(celleData -> celleData.getValue().betingelserProperty());
        typeKolonne.setCellValueFactory(celleData -> celleData.getValue().typeProperty());

        visForsikringDetaljer(null);

        forsikringTabell.getSelectionModel().selectedItemProperty().addListener(
                ((observable, gammelData, nyData) -> visForsikringDetaljer(nyData)));
    }

    private void visForsikringDetaljer(Forsikring forsikring) {

        // det er forskjellig data for hver type forsikring
        if (forsikring != null) {

            // båtforsikring
            if ("BåtForsikring".equals(forsikring.getType())) {
                BåtForsikring båtForsikring = (BåtForsikring) forsikring;

                // setter inn metadata
                metaEnLabel.setText("Registreringsnummer");
                metaToLabel.setText("Båttype og modell");
                metaTreLabel.setText("Lengde i fot");
                metaFireLabel.setText("Årsmodell");
                metaFemLabel.setText("Motortype og styrke");

                // setter inn resultatdata
                resultatEnLabel.setText(båtForsikring.getRegistreringsNr());
                resultatToLabel.setText(båtForsikring.getTypeModell());
                resultatTreLabel.setText(Integer.toString(båtForsikring.getLengdeFot()));
                resultatFireLabel.setText(Integer.toString(båtForsikring.getÅrsmodell()));
                resultatFemLabel.setText(båtForsikring.getMotorEgenskaper());
            }
            // TODO: fullføre for resterende forsikringer
        } else {
            // tømmer metadata
            metaEnLabel.setText("");
            metaToLabel.setText("");
            metaTreLabel.setText("");
            metaFireLabel.setText("");
            metaFemLabel.setText("");
            metaSeksLabel.setText("");
            metaSjuLabel.setText("");
            metaÅtteLabel.setText("");

            // tømmer resultatdataen
            resultatEnLabel.setText("");
            resultatToLabel.setText("");
            resultatTreLabel.setText("");
            resultatFireLabel.setText("");
            resultatFemLabel.setText("");
            resultatSeksLabel.setText("");
            resultatSjuLabel.setText("");
            resultatÅtteLabel.setText("");
        }
    }

    @FXML
    public void gåTilNyBåtForsikringPopup() {
        int forsikringsNr = IdUtil.genererLøpenummerForsikring(hovedApplikasjon.getKundeData());
        Forsikring nyBåtForsikring = new BåtForsikring(forsikringsNr);
        boolean bekreftTrykket = Viewbehandling.visNyBåtforsikringPopup(hovedApplikasjon, (BåtForsikring) nyBåtForsikring);

        if (bekreftTrykket) {
            for (Kunde kunde : hovedApplikasjon.getKundeData()) {
                if (kunde.getKundeNr() == nyBåtForsikring.getKundeNr()) {
                    kunde.getForsikringer().add(nyBåtForsikring);
                }
            }
        }
    }

    @FXML
    public void gåTilRedigerForsikringPopup() {
        Forsikring valgtForsikring = forsikringTabell.getSelectionModel().getSelectedItem();

        if (valgtForsikring != null) {
            if ("Båtforsikring".equals(valgtForsikring.getType())) {
                boolean bekreftTrykket = Viewbehandling.visRedigerBåtforsikringPopup(
                        hovedApplikasjon, (BåtForsikring) valgtForsikring);

                if (bekreftTrykket) {
                    visForsikringDetaljer(valgtForsikring);
                }
            }
        } else {
            UgyldigInputHandler.generateAlert("Du må velge en kunde for å redigere.");
        }
    }

    // TODO: FIKSE SLETT ORDENTLIG (DYNAMISK)
    @FXML
    public void slettValgtForsikring() {
        Forsikring valgtForsikring = forsikringTabell.getSelectionModel().getSelectedItem();

        if (valgtForsikring != null) {
            String forsikringInfo = Integer.toString(valgtForsikring.getForsikringsNr());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(hovedApplikasjon.getHovedStage());
            alert.setTitle("Slett forsikring");
            alert.setHeaderText("Bekreft sletting av forsikring");
            alert.setContentText("Er du sikker på at du ønsker å slette forsikring nummer: " + forsikringInfo +"?");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Bekreft");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Forsikring forsikringTilSletting = null;
                for (Kunde kunde : hovedApplikasjon.getKundeData()) {
                    if (valgtForsikring.getKundeNr() == kunde.getKundeNr()) {
                        for (Forsikring forsikring : kunde.getForsikringer()) {
                            if (valgtForsikring.equals(forsikring)) {
                                forsikringTilSletting = forsikring;
                            }
                        }
                    }
                    kunde.getForsikringer().remove(forsikringTilSletting);
                }
            }
        }
    }

    /**
     * Kalles fra RotOppsettController for å gi en referanse til
     * hovedapplikasjonen.
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;
        oppdaterTabell();
    }

    // TODO: MÅ BLI DYNAMISK
    public void oppdaterTabell() {
        this.forsikringTabell.getItems().clear();
        ObservableList<Forsikring> forsikringer = FXCollections.observableArrayList();
        // Legger til data fra ObservableList til tabellen
        for (Kunde kunde : hovedApplikasjon.getKundeData()) {
            forsikringer.addAll(kunde.getForsikringer());
        }
        forsikringTabell.setItems(forsikringer);
    }
}
