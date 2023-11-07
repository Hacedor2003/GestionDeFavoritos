package Modelo;

import Controlador.PanelParaBtnController;
import static Modelo.Auxiliares.resourceBundle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.VBox;

/**
 *
 * @author Hacedor
 */
public class botonFlotante extends Application implements Hablable {

    //Inicializacion
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage NuevoStage;
    private VBox root;
    private int indicador;
    private Accordion acordeon;
    private TitledPane pestana;

    private String textoAlerta = resourceBundle.getString("error_nuevos_btn");
    private String btnCerrar = resourceBundle.getString("btn_flotante_pestana_cerrar");
    private String pestanaTexto = resourceBundle.getString("btn_flotante_pestana");

    @Override
    public void start(Stage stage)
    {
        root = new VBox();
        acordeon = new Accordion();
        pestana = new TitledPane();

        pestana.setText(pestanaTexto);

        //Panel Base        
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        ScrollPane scrollPane = crearPanelFlotante();
        scrollPane.setFitToWidth(true);  // Ajustar el ancho del ScrollPane al ancho del contenedor padre
        pestana.setContent(scrollPane);
        acordeon.getPanes().add(pestana);
        root.getChildren().clear();
        root.getChildren().add(acordeon); // Añadimos el ScrollPane en lugar del contenido directamente  
        actualizarRoot();

        pestana.setOnMouseDragged((MouseEvent event) ->
        {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        ContextMenu contextMenu = new ContextMenu();
        MenuItem closeMenuItem = new MenuItem(btnCerrar);
        closeMenuItem.setOnAction(event ->
        {
            stage.close();
        });
        contextMenu.getItems().add(closeMenuItem);
        pestana.setOnContextMenuRequested(event ->
        {
            contextMenu.show(root, event.getScreenX(), event.getScreenY());
        });
        pestana.setOnMouseClicked(event ->
        {
            contextMenu.hide();
        });

        acordeon.expandedPaneProperty().addListener((observable, oldPane, newPane) ->
        {
            if (newPane != null)
            {
                // Muestra un Alert
                Platform.runLater(() ->
                {
                    stage.setHeight((oldPane != null) ? oldPane.getHeight() : 100);
                });
            } else
            {
                stage.setHeight(37);
            }
        });

        Scene scene = new Scene(root);
        //Stilos
        scene.getStylesheets().add("Archivos/Css/flotante.css");

        stage.initStyle(StageStyle.UNDECORATED);

        setNuevoStage(stage);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setWidth(150);
        stage.setHeight(37);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public Stage getNuevoStage()
    {
        return NuevoStage;
    }

    public void setNuevoStage(Stage NuevoStage)
    {
        this.NuevoStage = NuevoStage;
    }

    public void actualizarRoot()
    {
        root.setPrefSize(VBox.USE_COMPUTED_SIZE, VBox.USE_COMPUTED_SIZE);
    }

    private VBox anadirCategoria()
    {
        //Inicializar
        VBox contenido = new VBox(); // Contenedor para los elementos de la pestana
        contenido.setFillWidth(true);  // Permitir que el VBox se expanda para llenar el espacio disponible

        //Busca el nombre de todas las Categorias
        ArrayList<String> tablasBuscar = Logica.obtenerTablas(indicador);
        for (String s : tablasBuscar)
        {
            TitledPane pestana = new TitledPane();

            pestana.setText(s);

            PanelParaBtnController panelConBotones;
            ArrayList<AccesoDirecto> leerAccesosDirecto = Logica.leerAccesosDirecto(s, indicador);
            try
            {
                for (int i = 0; i < leerAccesosDirecto.size(); i++)
                {
                    Button boton = Boton.inicializarBotonDeLasPestañas(i, s, indicador);
                    boolean encontre = compararBtn(boton, 3);
                    boolean encontrado = false;
                    if (!encontre)
                    {
                        //Configurar los accesos directos
                        panelConBotones = loadPage();
                        panelConBotones.setIndicacion(1);
                        panelConBotones.setContent(boton);
                        panelConBotones.setTabla(s);
                        panelConBotones.setNombre(leerAccesosDirecto.get(i).getNombre());
                        contenido.getChildren().add(panelConBotones.getRoot());
                        AnchorPane panel = new AnchorPane();
                        panel.getChildren().clear();
                        panel.getChildren().add(contenido);
                        pestana.setContent(panel); // Establecer el contenido del Tab 
                    } else
                    {
                        Auxiliares.alerta(textoAlerta, "", "");
                        encontrado = true;
                        if (encontrado)
                        {
                            Logica.eliminarAccesoDirecto(boton.getText(), s);
                        }
                    }
                }
            } catch (Exception ex)
            {
                Auxiliares.alerta(ex.getMessage(), "botonFlotante", "anadirCategoria");
            }
        }
        contenido.getChildren().add(Auxiliares.obtenerBtnCrearAccesoDirecto("Auxiliares", indicador)); // Agregar el botón al contenido de la pestana    
        return contenido;
    }

    private boolean compararBtn(Button boton, int indicador)
    {
        String comparar = boton.getText();
        int contador = 0;

        ArrayList<String> tabla = Logica.obtenerTablas(indicador);
        for (String s : tabla)
        {
            ArrayList<AccesoDirecto> leerAccesosDirecto = Logica.leerAccesosDirecto(s, indicador);
            Boton btn = new Boton();
            for (int index = 0; index < leerAccesosDirecto.size(); index++)
            {
                Button boton2 = Boton.inicializarBotonDeLasPestañas(index, s, indicador);
                if (comparar.equals(boton2.getText()))
                {
                    contador++;
                }
            }
        }
        return (contador > 1);
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
            Auxiliares.alerta(ex.getMessage(), "botonFlotante", "loadPage");
        }

        return panelContolador;
    }

    public ScrollPane crearPanelFlotante()
    {
        VBox contenido = anadirCategoria();
        ScrollPane scrollPane = new ScrollPane(contenido); // Creamos un ScrollPane con el contenido
        scrollPane.setFitToWidth(true); // Ajustamos el ancho del ScrollPane al ancho del contenedor padre
        return scrollPane;
    }

    public void setIndicador(int indicador)
    {
        this.indicador = indicador;
    }

    @Override
    public void cambiarIdioma(String idioma)
    {
        ResourceBundle font = ResourceBundle.getBundle("labels", new Locale(idioma));

        // Recorrer todos los nodos de la escena y actualizar las cadenas de texto
        //updateNodeLanguage(scene, font);
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
                pestanaTexto = resourceBundle.getString("btn_flotante_pestana");
                btnCerrar = resourceBundle.getString("btn_flotante_pestana_cerrar");
                textoAlerta = resourceBundle.getString("error_nuevos_btn");

            }
        }
    }

}
