package Controlador;

import static Controlador.CarpetasController.btnAnadirCarpeta;
import Modelo.*;
import static Modelo.Auxiliares.*;
import static Modelo.Logica.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController implements Initializable, Hablable {

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
    private MenuItem menuVerIdioma;
    @FXML
    private Menu menuConfiguracion;
    @FXML
    private MenuItem menuConfigCargarIniciar;
    @FXML
    private MenuItem menuConfigIniMini;
    @FXML
    private Menu menuAyuda;
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

    private String catPerTipo1 = resourceBundle.getString("categoria_personalizada_tipo1");
    private String catPerTipo2 = resourceBundle.getString("categoria_personalizada_tipo2");
    private String catPerTipo3 = resourceBundle.getString("categoria_personalizada_tipo3");
    private String catPer = resourceBundle.getString("categoria_personalizada_tipo");
    private String catPerElegir = resourceBundle.getString("btn_flotante_categorias_elegir");

    private String textCategoriaHome = resourceBundle.getString("btn_CategoriasHome");
    private String textCategoriaApp = resourceBundle.getString("btn_CategoriasApp");
    private String textCategoriaWeb = resourceBundle.getString("btn_CategoriasWeb");
    private String textCategoriaCar = resourceBundle.getString("btn_CategoriasCar");
    private String textCategoriaFlo = resourceBundle.getString("btn_CategoriasFlo");

    private String textMenuArchivo = resourceBundle.getString("menu_Archivo");
    private String textMenuArchivoAgre = resourceBundle.getString("menu_Archivo_Agre");
    private String textMenuArchivoCerr = resourceBundle.getString("menu_Archivo_cerr");

    private String textMenuVer = resourceBundle.getString("menu_Ver");
    private String textMenuVerSiem = resourceBundle.getString("menu_Ver_siem");
    private String textMenuVerIdio = resourceBundle.getString("menu_Ver_idio");

    private String textMenuConf = resourceBundle.getString("menu_Configuracion");
    private String textMenuConfgCar = resourceBundle.getString("menu_Configuracion_car");
    private String textMenuConfgIni = resourceBundle.getString("menu_Configuracion_ini");

    private String textMenuAyuda = resourceBundle.getString("menu_Ayuda");
    private String textMenuAyudaSit = resourceBundle.getString("menu_Ayuda_sit");
    private String textMenuAyudaAce = resourceBundle.getString("menu_Ayuda_ace");

    private Stage stage;
    @FXML
    private Button btnCategoriaHome;
    @FXML
    private Button btnCategoriaApp;
    @FXML
    private Button btnCategoriaWeb;
    @FXML
    private Button btnCategoriaCar;
    @FXML
    private Button btnCategoriaFlo;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        obtenerLosComponentesDeLaBaseDeDatos(1, "");
        obtenerLosComponentesDeLaBaseDeDatos(2, "");
        obtenerLosComponentesDeLaBaseDeDatos(4, "");
        inicializar();
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

    private void MenuItemIdioma(ActionEvent event)
    {
        VentanaIdioma vi = new VentanaIdioma();
        vi.showAndWait();
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
        alert.setTitle(catPer);
        alert.setHeaderText(catPerElegir);

        // Crear los botones de opción
        ButtonType opcion1 = new ButtonType(catPerTipo1);
        ButtonType opcion2 = new ButtonType(catPerTipo2);
        ButtonType opcion3 = new ButtonType(catPerTipo3);

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

    @Override
    public void cambiarIdioma(String idioma)
    {
        ResourceBundle font = ResourceBundle.getBundle("labels", new Locale(idioma));

        // Recorrer todos los nodos de la escena y actualizar las cadenas de texto
        updateNodeLanguage(borderPanel, font);
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
                catPerTipo1 = resourceBundle.getString("categoria_personalizada_tipo1");
                catPerTipo2 = resourceBundle.getString("categoria_personalizada_tipo2");
                catPerTipo3 = resourceBundle.getString("categoria_personalizada_tipo3");
                catPer = resourceBundle.getString("categoria_personalizada_tipo");
                catPerElegir = resourceBundle.getString("btn_flotante_categorias_elegir");

                textCategoriaHome = resourceBundle.getString("btn_CategoriasHome");
                textCategoriaApp = resourceBundle.getString("btn_CategoriasApp");
                textCategoriaWeb = resourceBundle.getString("btn_CategoriasWeb");
                textCategoriaCar = resourceBundle.getString("btn_CategoriasCar");
                textCategoriaFlo = resourceBundle.getString("btn_CategoriasFlo");

                textMenuArchivo = resourceBundle.getString("menu_Archivo");
                textMenuArchivoAgre = resourceBundle.getString("menu_Archivo_Agre");
                textMenuArchivoCerr = resourceBundle.getString("menu_Archivo_cerr");

                textMenuVer = resourceBundle.getString("menu_Ver");
                textMenuVerSiem = resourceBundle.getString("menu_Ver_siem");
                textMenuVerIdio = resourceBundle.getString("menu_Ver_idio");

                textMenuConf = resourceBundle.getString("menu_Configuracion");
                textMenuConfgCar = resourceBundle.getString("menu_Configuracion_car");
                textMenuConfgIni = resourceBundle.getString("menu_Configuracion_ini");

                textMenuAyuda = resourceBundle.getString("menu_Ayuda");
                textMenuAyudaSit = resourceBundle.getString("menu_Ayuda_sit");
                textMenuAyudaAce = resourceBundle.getString("menu_Ayuda_ace");
            }
        }
    }

    private void inicializar()
    {
        btnCategoriaHome.setText(textCategoriaHome);
        btnCategoriaApp.setText(textCategoriaApp);
        btnCategoriaWeb.setText(textCategoriaWeb);
        btnCategoriaCar.setText(textCategoriaCar);
        btnCategoriaFlo.setText(textCategoriaFlo);

        menuArchivo.setText(textMenuArchivo);
        menuArchivoAgrega.setText(textMenuArchivoAgre);
        menuArchivoCerrar.setText(textMenuArchivoCerr);

        menuAyuda.setText(textMenuAyuda);
        menuAyudaAcerca.setText(textMenuAyudaAce);
        menuAyudaSitioWeb.setText(textMenuAyudaSit);

        menuConfiguracion.setText(textMenuConf);
        menuConfigCargarIniciar.setText(textMenuConfgCar);
        menuConfigIniMini.setText(textMenuConfgIni);

        menuVer.setText(textMenuVer);
        menuVerIdioma.setText(textMenuVerIdio);
        menuVerSiempreEncima.setText(textMenuVerSiem);
    }

}
