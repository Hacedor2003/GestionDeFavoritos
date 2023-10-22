package Controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    @FXML
    private Tab tabPanelApp;
    @FXML
    private AnchorPane PanelTabPanel;
    @FXML
    private GridPane GridPaneTabPanelApp;
    @FXML
    private Button btnGridPaneTab1;
    @FXML
    private Pane paneGridPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
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

}
