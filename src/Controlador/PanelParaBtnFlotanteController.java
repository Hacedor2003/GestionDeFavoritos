package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Hacedor
 */
public class PanelParaBtnFlotanteController implements Initializable {

    @FXML
    private VBox PanelDondeEstaTodo;
    @FXML
    private Accordion acordeonDondeEsta;
    @FXML
    private TitledPane PestanaDelAcordeon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        acordeonDondeEsta.expandedPaneProperty().addListener((observable, oldPane, newPane) -> {
            if (newPane != null) {
                // Se ha abierto una nueva pestaña, ajusta el VBox al contenido
                PanelDondeEstaTodo.requestLayout();
            }
        });
    }    
}