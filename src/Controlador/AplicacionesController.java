package Controlador;

import Modelo.*;
import static Modelo.Auxiliares.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AplicacionesController implements Initializable {

    //Componentes del FXML
    @FXML
    private TabPane PanelApp;
    @FXML
    private AnchorPane PanelTabPanel;
    @FXML
    private Tab tabPanelAppBuscar;
    @FXML
    private Pane PaneBtn;
    @FXML
    private TextField TextField;
    @FXML
    private Button btnBuscar;
    @FXML
    private Pane PaneAccesosDirectos;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        actualizarTabPane();
    }

    void actualizarTabPane()
    {        
        ArrayList<String> nombresBD = Logica.obtenerTablas(1);
        for (String nombresAccD : nombresBD)
        {
            String icono = emparajarBtnIcono(nombresAccD);
            try
            {
                Tab tab = anadirTab(nombresAccD, 1);
                Image img = new Image(Auxiliares.class.getResourceAsStream("/Archivos/" + icono));
                tab.setGraphic(new javafx.scene.image.ImageView(img));
                PanelApp.getTabs().add(tab); // Agregar el nuevo tab
            } catch (Exception e)
            {
                alerta(e.getMessage(), "AplicacionesControlller", "actualizarTabPane");
            }
        }
    }

    //btn para agregar a la bd
    public void btnAnadirBd()
    {
        ArrayList<String> nombresBD = Logica.obtenerTablas(1);
        ObservableList<String> opciones = FXCollections.observableArrayList(nombresBD);
        ChoiceDialog<String> dialogo = new ChoiceDialog<>(opciones.get(0), opciones);
        dialogo.setTitle("Anadir Aplicacion");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Selecciona una opción:");

        Optional<String> resultado = dialogo.showAndWait();
        if (resultado.isPresent())
        {
            obtenerDirectorioArchivo(resultado.get(), "AplicacioneController");
        }
    }

    @FXML
    private void btnBuscar(ActionEvent event)
    {
        String tabla = Logica.buscarAccesoDirecto(TextField.getText(), 1);
        PaneAccesosDirectos.getChildren().clear(); // borrar los botones existentes
        for (int i = 0; i < Logica.leerAccesosDirecto(tabla, 1).size(); i++)
        {
            PaneAccesosDirectos.getChildren().add(Boton.inicializarBotonDeLasPestañas(i, tabla, 1));
        }
    }

}
