/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.AccesoDirecto;
import Modelo.Boton;
import Modelo.Logica;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import javafx.scene.control.Label;

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
        obtenerLosComponentesDeLaBaseDeDatos();
    }

    //Inicializacion Auxiliar                                
    static int index = 0;
    int indice = 0;
    ArrayList<Button> listaBtnCategorias = new ArrayList<>();
    Boton boton = new Boton();
    Logica claseLogica = new Logica();
    ArrayList<String> nombresBD = claseLogica.obtenerTablas(2);
    //Fin

    //Crear los botontes en el panelTab 
    private void obtenerLosComponentesDeLaBaseDeDatos()
    {
        for (String nombresAccD : nombresBD)
        {
            String icono = emparajarBtnIcono(nombresAccD);
            Tab tab = new Tab();
            try
            {
                tab = anadirTab(nombresAccD);
                //Image img = new Image(getClass().getResourceAsStream("/Archivos/" + icono));
                //tab.setGraphic(new javafx.scene.image.ImageView(img));

            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            PanelApp.getTabs().add(tab);

        }
    }

    //Se crean los tab para guardar los accesos directos
    private Tab anadirTab(String tabla)
    {
        Tab tabNuevo = new Tab();   //Tab para guardar los Accesos directos
        VBox contenido = new VBox(); // Contenedor para los elementos del Tab
        tabNuevo.setText(tabla);
        Boton btn = new Boton();
        PanelParaBtnController panelConBotones;
        ArrayList<AccesoDirecto> leerAccesosDirecto = claseLogica.leerAccesosDirecto(tabla, 2);
        try
        {
            for (int i = 0; i < leerAccesosDirecto.size(); i++)
            {
                Button boton = btn.inicializarBotonDeLasPestañas(i, tabla, 2);
                panelConBotones = loadPage();
                panelConBotones.setContent(boton);
                panelConBotones.setTabla(tabla);
                panelConBotones.setNombre(leerAccesosDirecto.get(i).getNombre());
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
            alert.setHeaderText("En el Metodo AnadirTab en" + getClass());
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
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
        iconos.add(4, "icons8-web-16.png");
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
            case "web" ->
                iconos.get(4);
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
                    btnAnadirBd();
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

    //btn para agregar a la bd
    private void btnAnadirBd()
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

            obtenerDirectorioArchivo("web", direccion, nombre);

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

    //Metodo para configurar la forma de guardar el btn
    private void obtenerDirectorioArchivo(String titulo, String direccion, String nombre)
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

    @FXML
    private void btnBuscar(ActionEvent event)
    {
        Logica claseLogica = new Logica();
        Boton btn = new Boton();
        String tabla = claseLogica.buscarAccesoDirecto(TextField.getText(),2);
        for (int i = 0; i < claseLogica.leerAccesosDirecto(tabla, 2).size(); i++)
        {
            PaneAccesosDirectos.getChildren().add(btn.inicializarBotonDeLasPestañas(i, tabla, 2));

        }

    }
}
