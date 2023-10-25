package Controlador;

import Modelo.AccesoDirecto;
import Modelo.Boton;
import Modelo.Logica;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;

public class HomeController implements Initializable {

    @FXML
    private BorderPane borderPanel;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuArchivo;
    @FXML
    private MenuItem menuArchivoConfig;
    @FXML
    private MenuItem menuArchivoAgrega;
    @FXML
    private MenuItem menuArchivoCerrar;
    @FXML
    private MenuItem menuEditarActualizar;
    @FXML
    private MenuItem menuEditarEliminartodo;
    @FXML
    private Menu menuVer;
    @FXML
    private MenuItem menuVerSiempreEncima;
    @FXML
    private MenuItem menuVerSoloNombres;
    @FXML
    private MenuItem menuVerIconos;
    @FXML
    private MenuItem menuVerIdioma;
    @FXML
    private Menu menuConfiguracion;
    @FXML
    private MenuItem menuConfigCargarIniciar;
    @FXML
    private MenuItem menuConfigIniMini;
    @FXML
    private Menu menuAyuda;
    @FXML
    private MenuItem menuAyudaSitioWeb;
    @FXML
    private MenuItem menuAyudaAcerca;
    @FXML
    private VBox PanelCategorias;
    @FXML
    private Button btnBoxCategorias;
    @FXML
    private TabPane PanelApp;
    private AnchorPane PanelTabPanel;
    @FXML
    private Tab tabPanelAppBuscar;
    @FXML
    private AnchorPane PanelTabPanel1;
    @FXML
    private Pane PaneBtn;
    @FXML
    private TextField TextField;
    @FXML
    private Button btnBuscar;
    @FXML
    private Pane PaneAccesosDirectos;

    private int indicador = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        obtenerLosComponentesDeLaBaseDeDatos(1);
        obtenerLosComponentesDeLaBaseDeDatos(2);
    }

    @FXML
    private void btnhome(MouseEvent event)
    {
        borderPanel.setCenter(PanelApp);
    }

    @FXML
    private void btnaplicaciones(MouseEvent event)
    {
        loadPage("Aplicaciones");
    }

    @FXML
    private void btnPaginasWeb(MouseEvent event)
    {
        loadPage("PaginasWeb");
    }

    @FXML
    private void btnAnadir(MouseEvent event)
    {
        System.out.println("Implementacion");
    }

    private void loadPage(String page)
    {
        Parent root = null;

        try
        {
            root = FXMLLoader.load(getClass().getResource("/Vista/" + page + ".fxml")); ///Vista/Home.fxml
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        borderPanel.setCenter(root);
    }

    //Inicializacion Auxiliar                                
    static int index = 0;
    int indice = 0;
    ArrayList<Button> listaBtnCategorias = new ArrayList<>();
    Boton boton = new Boton();
    Logica claseLogica = new Logica();
    ArrayList<String> nombresApp = claseLogica.obtenerTablas(1);
    ArrayList<String> nombresWeb = claseLogica.obtenerTablas(2);
    //Fin

    //Crear los botontes en el panelTab 
    private void obtenerLosComponentesDeLaBaseDeDatos(int indicador)
    {
        Tab tab1 = new Tab();
        Tab tab2 = new Tab();
        try
        {
            if (indicador == 1)
            {
                tab1 = anadirTab(indicador);
            } else
            {
                tab2 = anadirTab(indicador);
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        if (indicador == 1)
        {
            PanelApp.getTabs().add(tab1);
            tab1.setOnSelectionChanged(event ->
            {
                this.indicador = 1;
            });
        } else
        {
            PanelApp.getTabs().add(tab2);
            tab2.setOnSelectionChanged(event ->
            {
                this.indicador = 2;
            });
        }

    }

    //Se crean los tab para guardar los accesos directos
    private Tab anadirTab(int indicador)
    {
        ArrayList<String> tabla = claseLogica.obtenerTablas(indicador);
        Tab tabNuevo = new Tab();   //Tab para guardar los Accesos directos
        VBox contenido = new VBox(); // Contenedor para los elementos del Tab
        String nombreTab = (indicador == 1) ? "Aplicaciones" : "Web";
        tabNuevo.setText(nombreTab);
        for (String s : tabla)
        {
            Boton btn = new Boton();
            PanelParaBtnController panelConBotones;
            ArrayList<AccesoDirecto> leerAccesosDirecto = claseLogica.leerAccesosDirecto(s, indicador);
            try
            {
                for (int i = 0; i < leerAccesosDirecto.size(); i++)
                {
                    Button boton = btn.inicializarBotonDeLasPestañas(i, s, indicador);
                    panelConBotones = loadPage();
                    panelConBotones.setContent(boton);
                    panelConBotones.setTabla(s);
                    panelConBotones.setId(leerAccesosDirecto.get(i).getId());
                    contenido.getChildren().add(panelConBotones.getRoot());
                    AnchorPane TabPanel = new AnchorPane();
                    TabPanel.getChildren().clear();
                    TabPanel.getChildren().add(contenido);
                    tabNuevo.setContent(PanelTabPanel); // Establecer el contenido del Tab        

                }
            } catch (Exception ex)
            {
                // Crea una pantalla emergente con el error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Occured");
                alert.setHeaderText("Ooops, algo salió mal!");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

        }
        contenido.getChildren().add(obtenerBtnCrearAccesoDirecto()); // Agregar el botón al contenido del Tab
        tabNuevo.setContent(contenido); // Establecer el contenido del Tab            
        return tabNuevo;
    }

    //Metodos Auxiliares
    //Se empareja el nombre del icono correspondiente
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

    //Se crea el btn para anadir accesos directos
    private Button obtenerBtnCrearAccesoDirecto()
    {
        Button btnCrear = new Button("Crear");
        try
        {
            btnCrear.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event)
                {
                    if (indicador == 1)
                    {
                        btnAnadirApp();
                    } else
                    {
                        btnAnadirWeb();
                    }
                }
            });
        } catch (Exception ex)
        {
            // Crea una pantalla emergente con el error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Occured");
            alert.setHeaderText("Ooops, algo salió mal!");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }

        return btnCrear;
    }

    //Se crea el panel para colocar el boton
    private PanelParaBtnController loadPage()
    {
        PanelParaBtnController panelContolador = null;

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/panelParaBtn.fxml"));
            Parent root = loader.load();
            panelContolador = loader.getController();
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Occured");
            alert.setHeaderText("Ooops, algo salió mal!");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

        return panelContolador;
    }

    //Fin
    //Agregar Accesos Directos
    //Inicializacion    
    FileChooser fileChooser = new FileChooser();
    Stage stage = new Stage();

    //btn para agregar a la bd de aplicaciones
    private void btnAnadirApp()
    {
        String nombreBtn = "";
        ObservableList<String> opciones = FXCollections.observableArrayList(nombresApp);
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

    //btn para agregar a la bd de web
    private void btnAnadirWeb()
    {
        Stage ventana = new Stage();
        Label direccionLabel = new Label("Dirección de la pagina Web:");
        Label nombreLabel = new Label("Nombre del Acceso directo:");
        TextField urlFieldDireccion = new TextField();
        TextField urlFieldNombre = new TextField();
        Button saveButton = new Button("Guardar");
        Button cancelButton = new Button("Cancelar");

        saveButton.setOnAction(e ->
        {
            String direccion = urlFieldDireccion.getText();
            String nombre = urlFieldNombre.getText();

            guardarWeb("web", direccion, nombre);

            ventana.close();
        });

        cancelButton.setOnAction(e ->
        {
            ventana.close();
        });

        VBox root = new VBox(10, direccionLabel, urlFieldDireccion, nombreLabel, urlFieldNombre, saveButton, cancelButton);
        Scene scene = new Scene(root, 300, 200);
        ventana.setScene(scene);
        ventana.showAndWait();
    }

    //Metodo para configurar la forma de guardar la web
    private void guardarWeb(String titulo, String direccion, String nombre)
    {
        // Si se selecciona un archivo
        if (nombre != null)
        {
            String name = nombre;
            String path = direccion;

            AccesoDirecto ad = new AccesoDirecto();
            ad.setNombre(name);
            ad.setDireccion(path);

            try
            {
                claseLogica.registrarAccesoDirecto(ad, titulo, 2);

            } catch (Exception ex)
            {
                // Crea una pantalla emergente con el error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Occured");
                alert.setHeaderText("En el metodo obtenerDirectoArchivo en " + getClass());
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
            }
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
                for (String nombreBD : nombresApp)
                {
                    if (titulo.equals(nombreBD))
                    {
                        try
                        {
                            claseLogica.registrarAccesoDirecto(ad, titulo, 1);
                            encontrado = true;
                            break;
                        } catch (Exception ex)
                        {
                            // Crea una pantalla emergente con el error
                            alert.setTitle("Error Occured");
                            alert.setHeaderText("Ooops, algo salió mal!");
                            alert.setContentText(ex.getMessage());

                            alert.showAndWait();
                        }
                    }
                }
                if (!encontrado)
                {
                    claseLogica.registrarAccesoDirecto(ad, "otros", 1);
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

    @FXML
    private void btnBuscar(ActionEvent event)
    {
        Logica claseLogica = new Logica();
        Boton btn = new Boton();
        String tabla = claseLogica.buscarAccesoDirecto(TextField.getText(),indicador);
        for (int i = 0; i < claseLogica.leerAccesosDirecto(tabla, indicador).size(); i++)
        {
            PaneAccesosDirectos.getChildren().add(btn.inicializarBotonDeLasPestañas(i, tabla, indicador));

        }
    }

    @FXML
    private void MenuItemCerrarApp(ActionEvent event)
    {
    }

    @FXML
    private void MenuItemEliminarTodo(ActionEvent event)
    {
    }

    @FXML
    private void MenuItemSiempreEncima(ActionEvent event)
    {
    }

    @FXML
    private void MenuItemSoloNombreArchivos(ActionEvent event)
    {
    }

    @FXML
    private void MenuItemIconos(ActionEvent event)
    {
    }

    @FXML
    private void MenuItemIdioma(ActionEvent event)
    {
    }

    @FXML
    private void MenuItemCargarIniciarSistema(ActionEvent event)
    {
    }

    @FXML
    private void MenuItemIniciarMinimizado(ActionEvent event)
    {
    }

    @FXML
    private void MenuItemSitioWeb(ActionEvent event)
    {
    }

    @FXML
    private void MenuItemAcercaDe(ActionEvent event)
    {
    }
}
