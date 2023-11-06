package Controlador;

import static Controlador.CarpetasController.btnAnadirCarpeta;
import Modelo.*;
import static Modelo.Auxiliares.*;
import static Modelo.Logica.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
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
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
        listaTodasTablas = obtenerTablas(3);
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
                tab.setId("tabPanelApilacion");
                PanelApp.getTabs().add(tab);
                tab.setOnSelectionChanged(event ->
                {
                    this.indicador = 1;
                });
            }
            case 2 ->
            {
                tab.setText("");
                tab.setId("tabPanelWeb");
                PanelApp.getTabs().add(tab);
                tab.setOnSelectionChanged(event ->
                {
                    this.indicador = 2;
                });
            }
            case 3 ->
            {
                tab.setText("");
                tab.setId("tabPanelPersonalizado");
                PanelApp.getTabs().add(tab);
                tab.setOnSelectionChanged(event ->
                {
                    this.indicador = 3;
                });
            }
            case 4 ->
            {
                tab.setText("");
                tab.setId("tabPanelCarpeta");
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

    //Fin
    //Crear los botontes en el panelTab 
    @FXML
    private void btnBuscar(ActionEvent event)
    {
        for (int j = 0; j < 5; j++)
        {
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

    @FXML
    private void MenuItemIdioma(ActionEvent event)
    {
        // Obtener el idioma seleccionado por el usuario
        //String idioma = "es_ES"; // Por ejemplo, "es" para español o "en" para inglés

        // Cargar el archivo de propiedades correspondiente al idioma seleccionado
        ResourceBundle bundle = ResourceBundle.getBundle("Home");

        // Recorrer todos los nodos de la escena y actualizar las cadenas de texto
        updateNodeLanguage(borderPanel, bundle);
    }

    private void updateNodeLanguage(Node node, ResourceBundle bundle)
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
                labeled.setText(bundle.getString(key));
            }
        }
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
        AcercaDeStage acercaDeStage = new AcercaDeStage();
        acercaDeStage.show();
    }

    @FXML
    private void menuArchivoAgregar(ActionEvent event)
    {
        // Crear un nuevo diálogo de alerta
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resourceBundle.getString("categoria_personalizada_tipo"));
        alert.setHeaderText(resourceBundle.getString("btn_flotante_categorias_elegir"));

        // Crear los botones de opción
        ButtonType opcion1 = new ButtonType(resourceBundle.getString("categoria_personalizada_tipo1"));
        ButtonType opcion2 = new ButtonType(resourceBundle.getString("categoria_personalizada_tipo2"));
        ButtonType opcion3 = new ButtonType(resourceBundle.getString("categoria_personalizada_tipo3"));

        // Agregar los botones de opción al diálogo
        alert.getButtonTypes().addAll(opcion1, opcion2, opcion3);

        // Mostrar el diálogo y esperar a que el usuario seleccione una opción
        Optional<ButtonType> result = alert.showAndWait();

        // Verificar qué opción seleccionó el usuario y realizar la acción correspondiente
        if (result.get() == opcion1)
        {
            btnAnadirApp();
        } else if (result.get() == opcion2)
        {
            btnAnadirWeb();
        } else if (result.get() == opcion3)
        {
            btnAnadirCarpeta();
        } else
        {
            alert.close();
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
