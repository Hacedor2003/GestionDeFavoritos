package Controlador;

import Modelo.AccesoDirecto;
import static Modelo.Auxiliares.*;
import Modelo.Boton;
import Modelo.Hablable;
import Modelo.Logica;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
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
public class CarpetasController implements Initializable, Hablable {

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

    private static String textLogrado = resourceBundle.getString("ventana_alerta_logrado");
    private static String textErrorIcono = resourceBundle.getString("error_icono");
    private static String textCancelarAdd = resourceBundle.getString("alerta_cancelar_add_web");

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
                tab.setId("tabPanelCarpeta");
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
    public static void btnAnadirCarpeta()
    {

        obtenerDirectorioCarpeta("carpeta", "CarpetasController");
    }

    @FXML
    private void btnBuscar(ActionEvent event)
    {
        String tabla = Logica.buscarAccesoDirecto(TextField.getText(), 4);
        PaneAccesosDirectos.getChildren().clear(); // borrar los botones existentes
        for (int i = 0; i < Logica.leerAccesosDirecto(tabla, 1).size(); i++)
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
                alert.setContentText(textLogrado);
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
                                alerta(resourceBundle.getString("alerta_cancelar_add_web"), "", "");
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
                        alerta(textCancelarAdd, "", "");
                    }
                }
            } else
            {
                alerta(textErrorIcono, tituloClase, "obtenerDirectorioArchivo");
            }
        }
    }

    @Override
    public void cambiarIdioma(String idioma)
    {
        ResourceBundle font = ResourceBundle.getBundle("labels", new Locale(idioma));

        // Recorrer todos los nodos de la escena y actualizar las cadenas de texto
        updateNodeLanguage(PanelApp, font);
    }

    @Override
    public void updateNodeLanguage(Node node, ResourceBundle bundle)
    {
        if (node instanceof Parent)
        {
            for (Node child : ((Parent) node).getChildrenUnmodifiable())
            {
                updateNodeLanguage(child, bundle);
            }
        }

        if (node instanceof Labeled)
        {
            Labeled labeled = (Labeled) node;
            String key = labeled.getText();
            if (key != null && !key.isEmpty())
            {
                textLogrado = resourceBundle.getString("ventana_alerta_logrado");
                textErrorIcono = resourceBundle.getString("error_icono");
                textCancelarAdd = resourceBundle.getString("alerta_cancelar_add_web");
            }
        }

    }

}
