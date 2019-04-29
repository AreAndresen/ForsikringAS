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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ForsikringController {

    // kundenr-tabellen
    @FXML
    public TableView<Kunde> kundeNrTabell;
    @FXML
    public TableColumn<Kunde, Number> kundeNrKolonne;
    @FXML
    public TableColumn<Kunde, String> etternavnKolonne;

    // forsikring-tabellen
    @FXML
    public TableView<Forsikring> forsikringTabell;
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

    @FXML
    public ChoiceBox typeChoice;

    private ObservableList<String> typeSortering = FXCollections.observableArrayList("Alle", "Båtforsikring",
            "Hus- og innboforsikring", "Fritidsboligforsikring", "Reiseforsikring");

    // referanse til hovedapplikasjonen
    private HovedApplikasjon hovedApplikasjon;

    // tom konstruktør
    public ForsikringController() { }

    @FXML
    private void initialize() {
        // koble kolonnene med datafeltene

        kundeNrKolonne.setCellValueFactory(celleData -> celleData.getValue().kundeNrProperty());
        etternavnKolonne.setCellValueFactory(celleData -> celleData.getValue().etternavnProperty());

        forsikringsnrKolonne.setCellValueFactory(celleData -> celleData.getValue().forsikringsNrProperty());
        datoOpprettetKolonne.setCellValueFactory(celleData -> celleData.getValue().datoOpprettetProperty());
        forsikringsbelopKolonne.setCellValueFactory(celleData -> celleData.getValue().forsikringsBelopProperty());
        betingelserKolonne.setCellValueFactory(celleData -> celleData.getValue().betingelserProperty());
        typeKolonne.setCellValueFactory(celleData -> celleData.getValue().typeProperty());

        typeChoice.setValue("Alle");
        typeChoice.setItems(typeSortering);

        visForsikringDetaljer(null);
        visForsikringer(null);

        forsikringTabell.getSelectionModel().selectedItemProperty().addListener(
                ((observable, gammelData, nyData) -> visForsikringDetaljer(nyData)));

        kundeNrTabell.getSelectionModel().selectedItemProperty().addListener(
                (((observable, gammelData, nyData) -> visForsikringer(nyData))));
    }

    private void visForsikringer(Kunde kunde) {

        if (kunde != null) {
            forsikringTabell.setItems(kunde.getForsikringer());
        } else {
            forsikringTabell.getItems().clear();
        }
    }

    private void visForsikringDetaljer(Forsikring forsikring) {

        // det er forskjellig data for hver type forsikring
        if (forsikring != null) {

            // båtforsikring
            if ("Båtforsikring".equals(forsikring.getType())) {
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
        if (kundeNrTabell.getSelectionModel().getSelectedItem() != null) {
            int forsikringsNr = IdUtil.genererLøpenummerForsikring(hovedApplikasjon.getKundeData());

            Forsikring nyBåtForsikring = new BåtForsikring(
                    kundeNrTabell.getSelectionModel().getSelectedItem().getKundeNr(), forsikringsNr);

            boolean bekreftTrykket = Viewbehandling.visNyBåtforsikringPopup(hovedApplikasjon, (BåtForsikring) nyBåtForsikring);

            if (bekreftTrykket) {
                for (Kunde kunde : hovedApplikasjon.getKundeData()) {
                    if (kunde.getKundeNr() == nyBåtForsikring.getKundeNr()) {
                        kunde.getForsikringer().add(nyBåtForsikring);
                    }
                }
            }
        } else {
            UgyldigInputHandler.generateAlert("Du må velge en kunde for å kunne registrere en forsikring!");
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
            UgyldigInputHandler.generateAlert("Du må velge en forsikring for å kunne redigere!");
        }
    }

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
                Kunde kunde = kundeNrTabell.getSelectionModel().getSelectedItem();
                kunde.getForsikringer().remove(valgtForsikring);
            }
        } else {
            UgyldigInputHandler.generateAlert("Du må velge en forsikring for å kunne slette!");
        }
    }

    /**
     * Kalles fra RotOppsettController for å gi en referanse til
     * hovedapplikasjonen.
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;

        kundeNrTabell.setItems(hovedApplikasjon.getKundeData());
    }
}
