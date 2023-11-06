package Controlador;

import Modelo.*;
import static Modelo.Auxiliares.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
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
        int indicacion = 0;
        ArrayList<String> listaIconos = new ArrayList<>();
        listaIconos.add("tabPanelApilacionJuegos");
        listaIconos.add("tabPanelApilacionOtros");
        listaIconos.add("tabPanelApilacionProgramas");
        for (String nombresAccD : nombresBD)
        {
            try
            {
                Tab tab = anadirTab(nombresAccD, 1);
                tab.setId(listaIconos.get(indicacion++));
                PanelApp.getTabs().add(tab); // Agregar el nuevo tab
            } catch (Exception e)
            {
                alerta(e.getMessage(), "AplicacionesControlller", "actualizarTabPane");
            }
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
