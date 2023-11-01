/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.AccesoDirecto;
import Modelo.Auxiliares;
import static Modelo.Auxiliares.*;
import Modelo.Boton;
import Modelo.Logica;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;

/**
 * FXML Controller class
 *
 * @author Hacedor
 */
public class CarpetasController implements Initializable {

    @FXML
    private TabPane PanelApp;
    @FXML
    private Tab tabPanelAppBuscar;
    @FXML
    private AnchorPane PanelTabPanel;
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

    void actualizarTabPane()
    {
        ArrayList<String> nombresBD = Logica.obtenerTablas(4);
        for (String nombresAccD : nombresBD)
        {
            String icono = emparajarBtnIcono(nombresAccD);
            try
            {
                Tab tab = anadirTab(nombresAccD, 4);
                Image img = new Image(Auxiliares.class.getResourceAsStream("/Archivos/" + icono));
                tab.setGraphic(new javafx.scene.image.ImageView(img));
                PanelApp.getTabs().add(tab); // Agregar el nuevo tab
            } catch (Exception e)
            {
                alerta(e.getMessage(), "CarpetasController", "actualizarTabPane");
            }
        }
    }

    //btn para agregar a la bd
    /**
     * Prueba
     */
    public static void btnAnadirBd()
    {
        obtenerDirectorioCarpeta("carpeta", "CarpetasController");
    }

    @FXML
    private void btnBuscar(ActionEvent event)
    {
        String tabla = Logica.buscarAccesoDirecto(TextField.getText(), 4);
        PaneAccesosDirectos.getChildren().clear(); // borrar los botones existentes
        for (int i = 0; i < Logica.leerAccesosDirecto(tabla).size(); i++)
        {
            PaneAccesosDirectos.getChildren().add(Boton.inicializarBotonDeLasPestañas(i, tabla, 1));
        }
    }

    public static void obtenerDirectorioCarpeta(String titulo, String tituloClase)
    {
        ArrayList<String> nombresApp = Logica.obtenerTablas(4);

        // Crea un FileChooser
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // Abre la ventana de selección de carpeta
        File selectedDirectory = directoryChooser.showDialog(null);

        // Si se selecciona una carpeta
        if (selectedDirectory != null)
        {
            String name = selectedDirectory.getName();
            String path = selectedDirectory.getAbsolutePath();

            AccesoDirecto ad = new AccesoDirecto();
            ad.setNombre(name);
            ad.setDireccion(path);

            // Obtener el icono de la carpeta seleccionada
            Image imagen = getFileIcon(path);
            if (imagen != null)
            {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imagen, null);
                ad.setIcon(bufferedImage);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Logrado");
                alert.showAndWait();

                boolean encontrado = false;
                for (String nombreBD : nombresApp)
                {
                    if (titulo.equals(nombreBD))
                    {
                        try
                        {
                            boolean registrarAccesoDirecto = Logica.registrarAccesoDirecto(ad, titulo, 1);
                            if (!registrarAccesoDirecto)
                            {
                                alerta("No se registro el acceso directo", "", "");
                            }
                            encontrado = true;
                            break;
                        } catch (Exception ex)
                        {
                            alerta(ex.getMessage(), tituloClase, "obtenerDirectorioArchivo");
                        }
                    }
                }
                if (!encontrado)
                {
                    boolean registrarAccesoDirecto = Logica.registrarAccesoDirecto(ad, "otros", 1);
                    if (!registrarAccesoDirecto)
                    {
                        alerta("No se registro el acceso directo", "", "");
                    }
                }
            } else
            {
                alerta("No se pudo obtener el icono de la carpeta", tituloClase, "obtenerDirectorioArchivo");
            }
        }
    }

}
