package Controlador;

import Modelo.*;
import static Modelo.Auxiliares.*;
import static Modelo.Logica.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    @FXML
    private BorderPane borderPanel;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuArchivo;
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

    protected Stage stage;
    Stage nuevoStage = new Stage();
    private ArrayList<String> listaTodasTablas;
    @FXML
    private Button btnCategoriaHome;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //listaTodasTablas = obtenerTablas(5);
        obtenerLosComponentesDeLaBaseDeDatos(1, "");
        obtenerLosComponentesDeLaBaseDeDatos(2, "");
        obtenerLosComponentesDeLaBaseDeDatos(4, "");
        actualizarVBOX();
    }

    @FXML
    private void btnhome(MouseEvent event)
    {
        borderPanel.setCenter(PanelApp);
        PanelApp.getTabs().clear();
        PanelApp.getTabs().add(tabPanelAppBuscar);
        obtenerLosComponentesDeLaBaseDeDatos(1, "");
        obtenerLosComponentesDeLaBaseDeDatos(2, "");
        obtenerLosComponentesDeLaBaseDeDatos(4, "");
    }

    void obtenerLosComponentesDeLaBaseDeDatos(int indicador, String nombre)
    {
        Tab tab = anadirTab(nombre, indicador);

        switch (indicador)
        {
            case 1 ->
            {
                tab.setText("");
                ImageView iv = new ImageView("/Archivos/icons8-windows-client-24.png");
                tab.setGraphic(iv);
                PanelApp.getTabs().add(tab);
                tab.setOnSelectionChanged(event ->
                {
                    this.indicador = 1;
                });
            }
            case 2 ->
            {
                tab.setText("");
                ImageView iv2 = new ImageView("/Archivos/icons8-web-24.png");
                tab.setGraphic(iv2);
                PanelApp.getTabs().add(tab);
                tab.setOnSelectionChanged(event ->
                {
                    this.indicador = 2;
                });
            }
            case 4 ->
            {
                tab.setText("");
                ImageView iv2 = new ImageView("/Archivos/icons8-folder-24.png");
                tab.setGraphic(iv2);
                PanelApp.getTabs().add(tab);
                tab.setOnSelectionChanged(event ->
                {
                    this.indicador = 4;
                });
            }
            default ->
            {
                PanelApp.getTabs().add(tab);
                tab.setOnSelectionChanged(event ->
                {
                    this.indicador = 1;
                });
            }
        }

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

    private void loadPage(String page)
    {
        Parent root = null;

        try
        {
            root = FXMLLoader.load(getClass().getResource("/Vista/" + page + ".fxml")); ///Vista/Home.fxml
        } catch (IOException ex)
        {
            alerta(ex.getMessage(), "HomeController", "loadPage");
        }
        borderPanel.setCenter(root);
    }

    //Inicializacion Auxiliar 
    ArrayList<String> nombresApp = obtenerTablas(1);
    ArrayList<String> nombresWeb = obtenerTablas(2);

    //Fin
    //Crear los botontes en el panelTab 
    @FXML
    private void btnBuscar(ActionEvent event)
    {
        for(int j = 0 ; j < 5 ; j++){
        String tabla = buscarAccesoDirecto(TextField.getText(), j);
        for (int i = 0; i < leerAccesosDirecto(tabla, j).size(); i++)
        {
            PaneAccesosDirectos.getChildren().clear();
            PaneAccesosDirectos.getChildren().add(Boton.inicializarBotonDeLasPestañas(i, tabla, j));
        }
        }
    }

    private int idSiempreEncima = 1;

    @FXML
    private void MenuItemSiempreEncima(ActionEvent event)
    {
        if (idSiempreEncima == 1)
        {
            stage.setAlwaysOnTop(!stage.isAlwaysOnTop());
            idSiempreEncima--;
        } else
        {
            stage.setAlwaysOnTop(stage.isAlwaysOnTop());
            idSiempreEncima++;
        }
    }

    private void MenuItemSoloNombreArchivos(ActionEvent event)
    {
        alerta("Implementacion", "HomeController", "MenuItemSoloNombreArchivos");
    }

    private void MenuItemIconos(ActionEvent event)
    {
        alerta("Implementacion", "HomeController", "MenuItemIconos");
    }

    @FXML
    private void MenuItemIdioma(ActionEvent event)
    {/*
        // Obtener el idioma seleccionado por el usuario
        String idioma = "es"; // Por ejemplo, "es" para español o "en" para inglés

        // Cargar el archivo de propiedades correspondiente al idioma seleccionado
        ResourceBundle bundle = ResourceBundle.getBundle("strings", new Locale(idioma));

        // Recorrer todos los nodos de la escena y actualizar las cadenas de texto
        updateNodeLanguage(root, bundle);*/
        alerta("Implementacion", "HomeController", "MenuItemIdioma");
    }

    private void updateNodeLanguage(Node node, ResourceBundle bundle)
    {
        /*
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
                labeled.setText(bundle.getString(key));
            }
        } 
         */
        alerta("Implementacion", "HomeController", "updateNodeLanguage");
    }

    @FXML
    private void MenuItemCargarIniciarSistema(ActionEvent event)
    {
        Platform.runLater(() ->
        {
            try
            {
                // Ruta al archivo ejecutable de tu aplicación
                String rutaArchivo = "ruta_a_tu_archivo_ejecutable";

                // Abre el archivo ejecutable con el programa predeterminado del sistema operativo
                Desktop.getDesktop().open(new File(rutaArchivo));

                // Aquí puedes agregar más código que deseas que se ejecute al iniciar la aplicación
                // ...
            } catch (IOException e)
            {
                alerta(e.getMessage(), "HomeController", "MenuItemCargarIniciarSistema");
            }
        });
    }

    private int idIniciarMinimizado = 1;

    @FXML
    private void MenuItemIniciarMinimizado(ActionEvent event)
    {
        if (idIniciarMinimizado == 1)
        {
            stage.setIconified(true);
            idIniciarMinimizado--;
        } else
        {
            stage.setIconified(false);
            idIniciarMinimizado++;
        }
    }

    @FXML
    private void MenuItemSitioWeb(ActionEvent event)
    {
        alerta("Implementacion", "HomeController", "MenuItemSitioWeb");
    }

    @FXML
    private void MenuItemAcercaDe(ActionEvent event)
    {
        alerta("Implementacion", "HomeController", "MenuItemAcercaDe");
    }

    @FXML
    private void menuArchivoAgregar(ActionEvent event)
    {
        // Crear un nuevo diálogo de alerta
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Seleccionar opción");
        alert.setHeaderText("Por favor, seleccione que desea agregar:");

        // Crear los botones de opción
        ButtonType opcion1 = new ButtonType("Una aplicacion");
        ButtonType opcion2 = new ButtonType("Una Web");

        // Agregar los botones de opción al diálogo
        alert.getButtonTypes().setAll(opcion1, opcion2);

        // Mostrar el diálogo y esperar a que el usuario seleccione una opción
        Optional<ButtonType> result = alert.showAndWait();

        // Verificar qué opción seleccionó el usuario y realizar la acción correspondiente
        if (result.get() == opcion1)
        {
            btnAnadirApp();
        } else if (result.get() == opcion2)
        {
            btnAnadirWeb();
        }
    }

    @FXML
    private void MenuItemCerrarApp(ActionEvent event)
    {
        // Close the stage
        stage.close();
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void menuEditarActualizar(ActionEvent event)
    {
        stage.show();
    }

    @FXML
    private void MenuEditarEliminarTodo(ActionEvent event)
    {

        for (String s : nombresApp)
        {
            ArrayList<AccesoDirecto> a = leerAccesosDirecto(s, 1);
            for (AccesoDirecto b : a)
            {
                String nombre = b.getNombre();
                eliminarAccesoDirecto(nombre, s);
            }

        }
    }

    private void actualizarVBOX()
    {
        ArrayList<String> categoriasPersonalizadas = leerVBOX();

        for (String cp : categoriasPersonalizadas)
        {
            Button nuevoBtn = new Button(cp);
            nuevoBtn.setMinHeight(height);
            nuevoBtn.setMinWidth(width);
            nuevoBtn.setStyle(btnBoxCategorias.getStyle() + "-fx-background-color: blue; -fx-text-fill: white;");
            nuevoBtn.setOnAction((ActionEvent event) ->
            {
                PanelApp.getTabs().clear();
                borderPanel.setCenter(PanelApp);
                obtenerLosComponentesDeLaBaseDeDatos(3, nuevoBtn.getText());
            });

            // Agrega el botón al VBox
            PanelCategorias.getChildren().add(nuevoBtn);
        }
    }

    @FXML
    private void menuArchivoEliminarCategoria(ActionEvent event)
    {
        ObservableList<String> opciones = FXCollections.observableArrayList(listaTodasTablas);
        ChoiceDialog<String> dialogo = new ChoiceDialog<>(opciones.get(0), opciones);
        dialogo.setTitle("Eliminar Categoria");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Selecciona una opción:");

        Optional<String> resultado = dialogo.showAndWait();
        if (resultado.isPresent())
        {
            eliminarCategoria(resultado.get());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Categoria " + resultado.get() + " Eliminada");
            alert.showAndWait();
            eliminarCategoriasPersonalizadas(resultado.get());
        }
    }

    double width = 130.4;
    double height = 42.4;

    @FXML
    private void anadirCategoria(ActionEvent event)
    {
        TextInputDialog dialogoNombre = new TextInputDialog();
        dialogoNombre.setTitle("Crear botón");
        dialogoNombre.setHeaderText(null);
        dialogoNombre.setContentText("Ingresa un nombre para la nueva Categoria:");

        Optional<String> resultadoNombre = dialogoNombre.showAndWait();
        if (resultadoNombre.isPresent())
        {
            String nombreBtn = resultadoNombre.get();

            Button nuevoBtn = new Button(nombreBtn);
            nuevoBtn.setMinHeight(height);
            nuevoBtn.setMinWidth(width);
            // Configura el estilo de tu botón si lo deseas
            nuevoBtn.setStyle(btnBoxCategorias.getStyle() + "-fx-background-color: blue; -fx-text-fill: white;");
            nuevoBtn.setOnAction((ActionEvent event1) ->
            {
                PanelApp.getTabs().clear();
                borderPanel.setCenter(PanelApp);
                obtenerLosComponentesDeLaBaseDeDatos(3, nuevoBtn.getText());
            });

            // Agrega el botón al VBox
            PanelCategorias.getChildren().add(nuevoBtn);

            // Guarda el VBox actualizado en tu lógica o donde sea necesario
            guardarVBox(nuevoBtn.getText(), nombreBtn);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Botón creado y añadido al VBox");
            alert.showAndWait();

            //Instancio la clase
            CrearTabla ct = new CrearTabla();
            ct.setTabla(nombreBtn);
            ct.start(nuevoStage);
        }
    }

    @FXML
    private void btnFlotante(MouseEvent event)
    {
        loadPage("panelFlotante");
    }

    @FXML
    private void btnCarpetas(MouseEvent event)
    {
        loadPage("Carpetas");
    }

}
