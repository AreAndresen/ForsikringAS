package ae.controller;

import ae.model.Forsikring;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

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

    @FXML
    public void gåTilNyBåtforsikringPopup(ActionEvent actionEvent) {
    }
}
