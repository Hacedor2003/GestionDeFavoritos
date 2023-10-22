/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;

/**
 * FXML Controller class
 *
 * @author Hacedor
 */
public class AplicacionesController implements Initializable {

    @FXML
    private TabPane PanelApp;
    @FXML
    private Tab tabPanelApp;
    @FXML
    private AnchorPane PanelTabPanel;
    @FXML
    private GridPane GridPaneTabPanelApp;
    @FXML
    private Pane paneGridPane;
    @FXML
    private Button btnGridPaneTab1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        obtenerLosComponentesDeLaBaseDeDatos();
        anadirListaBtnACategorias();
    }

    //Inicializacion Auxiliar                                
    static int index = 0;
    int indice = 0;
    ArrayList<Button> listaBtnCategorias = new ArrayList<>();
    Boton boton = new Boton();
    Logica claseLogica = new Logica();
    ArrayList<String> nombresBD = claseLogica.obtenerTablas();
    //Fin

    //Crear los botontes en el panel categorias
    //Se crean los botones y se anade a una lista
    private void crearBotonesBoxCategorias(String nombreBtnConIcono)
    {
        String nombreBtn = nombreBtnConIcono;
        String icono = emparajarBtnIcono(nombreBtnConIcono);
        Button btnCategoria = boton.crearBtn(nombreBtn, icono);
        listaBtnCategorias.add(index, btnCategoria);
        index++;
    }

    //Se anade la lista al panel de categorias
    private void anadirListaBtnACategorias()
    {
        PanelApp.getTabs().clear();
        for (Button btn : listaBtnCategorias)
        {
            Tab tab = new Tab();
            tab = anadirTab(btn.getText());

            PanelApp.getTabs().add(tab);
        }
    }

    //Se crean los btn de Acceso directo
    private Tab anadirTab(String tabla)
    {
        Tab tabNuevo = new Tab();   //Tab para guardar los Accesos directos
        VBox contenido = new VBox(); // Contenedor para los elementos del Tab
        tabNuevo.setText(tabla);
        Boton btn = new Boton();
        ArrayList<Button> botones = new ArrayList<>();

        try
        {
            for (int i = 0; i < claseLogica.leerAccesosDirecto(tabla).size(); i++)
            {
                botones.add(btn.inicializarBotonDeLasPestañas(i, tabla));
            }
            botones.add(obtenerBtnCrearAccesoDirecto());
            for (int i = 0; i < botones.size(); i++)
            {
                contenido.getChildren().add(botones.get(i)); // Agregar el botón al contenido del Tab
            }
            tabNuevo.setContent(contenido); // Establecer el contenido del Tab
        } catch (Exception ex)
        {
            // Crea una pantalla emergente con el error
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Occured");
            alert.setHeaderText("Ooops, algo salió mal!");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
        return tabNuevo;
    }

    //Metodos Auxiliares
    private String emparajarBtnIcono(String nombre)
    {
        ArrayList<String> iconos = new ArrayList<>();
        iconos.add(0, "icons8-anonymous-mask-48.png");
        iconos.add(1, "icons8-game-controller-48.png");
        iconos.add(2, "icons8-folder-48.png");
        iconos.add(3, "icons8-video-48.png");
        String icon = null;

        icon = switch (nombre)
        {
            case "programas" ->
                iconos.get(0);
            case "juegos" ->
                iconos.get(1);
            case "redessociales" ->
                iconos.get(3);
            case "otros" ->
                iconos.get(2);
            default ->
                iconos.get(0);
        };

        return icon;
    }

    private void obtenerLosComponentesDeLaBaseDeDatos()
    {
        for (String a : nombresBD)
        {
            crearBotonesBoxCategorias(a);
        }
    }

    private Button obtenerBtnCrearAccesoDirecto()
    {
        Button btnCrear = new Button("Crear");

        btnCrear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                btnAnadirBd();
            }
        });

        return btnCrear;
    }

    //Fin
    
    
    //Agregar Accesos Directos
    //Inicializacion    
    FileChooser fileChooser = new FileChooser();
    Stage stage = new Stage();

    //btn para agregar a la bd
    private void btnAnadirBd()
    {
        String nombreBtn = "";
        ObservableList<String> opciones = FXCollections.observableArrayList(nombresBD);
        ChoiceDialog<String> dialogo = new ChoiceDialog<>(opciones.get(0), opciones);
        dialogo.setTitle("Título del cuadro de diálogo");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Selecciona una opción:");

        Optional<String> resultado = dialogo.showAndWait();
        if (resultado.isPresent())
        {
            nombreBtn = resultado.get();
            obtenerDirectorioArchivo(nombreBtn);
        }
    }

    //Metodo para configurar la forma de guardar el btn
    private void obtenerDirectorioArchivo(String titulo)
    {
        // Crea un FileChooser
        FileChooser fileChooser = new FileChooser();

        // Abre la ventana de selección de archivo
        File selectedFile = fileChooser.showOpenDialog(null);

        // Si se selecciona un archivo
        if (selectedFile != null)
        {
            String name = selectedFile.getName();
            String path = selectedFile.getAbsolutePath();

            AccesoDirecto ad = new AccesoDirecto();
            ad.setNombre(name);
            ad.setDireccion(path);

            // Obtener el icono del archivo seleccionado
            Image imagen = getFileIcon(path);
            if (imagen != null)
            {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imagen, null);
                ad.setIcon(bufferedImage);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Logrado");
                alert.showAndWait();

                boolean encontrado = false;
                for (String nombreBD : nombresBD)
                {
                    if (titulo.equals(nombreBD))
                    {
                        claseLogica.registrarAccesoDirecto(ad, titulo);
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado)
                {
                    claseLogica.registrarAccesoDirecto(ad, "otros");
                }
            } else
            {
                // Manejar el caso en que no se pudo obtener el icono del archivo
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No se pudo obtener el icono del archivo");
                alert.showAndWait();
            }
        }
    }

    // Método para conseguir el icono del botón capturado
    private Image getFileIcon(String filePath)
    {
        File file = new File(filePath);
        if (file.exists())
        {
            // Obtener el icono del archivo utilizando el FileView de FileSystemView
            FileSystemView fileView = FileSystemView.getFileSystemView();
            javax.swing.Icon icon = fileView.getSystemIcon(file);

            // Convertir el icono a JavaFX Image
            BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = bufferedImage.createGraphics();
            icon.paintIcon(null, graphics, 0, 0);
            graphics.dispose();

            return SwingFXUtils.toFXImage(bufferedImage, null);
        }

        return null;
    }
    //fin

}
