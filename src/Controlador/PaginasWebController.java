/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.*;
import static Modelo.Auxiliares.alerta;
import static Modelo.Auxiliares.anadirTab;
import static Modelo.Logica.*;
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
import java.net.URL;
import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author Hacedor
 */
public class PaginasWebController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        actualizarTabPane();
    }

    //Inicializacion Auxiliar         
    //Fin
    void actualizarTabPane()
    {
        ArrayList<String> nombresBD = Logica.obtenerTablas(2);
        for (String nombresAccD : nombresBD)
        {
            try
            {
                Tab tab = anadirTab(nombresAccD, 2);
                tab.setId("tabPanelWeb");
                PanelApp.getTabs().add(tab); // Agregar el nuevo tab
            } catch (Exception e)
            {
                alerta(e.getMessage(), "PaginasWebControlller", "actualizarTabPane");
            }
        }
    }

    @FXML
    private void btnBuscar(ActionEvent event)
    {
        String tabla = buscarAccesoDirecto(TextField.getText(), 2);
        for (int i = 0; i < leerAccesosDirecto(tabla, 2).size(); i++)
        {
            PaneAccesosDirectos.getChildren().add(Boton.inicializarBotonDeLasPestañas(i, tabla, 2));
        }
    }
}
