package Modelo;

import Controlador.PanelParaBtnController;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.filechooser.FileSystemView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Hacedor
 */
public class botonFlotante extends Application {

    //Inicializacion
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage NuevoStage;
    private VBox root;
    private Logica claseLogica;
    private int indicador;
    private Accordion acordeon;
    private TitledPane pestana;
    ArrayList<String> nombresApp;

    @Override
    public void start(Stage stage)
    {
        root = new VBox();
        claseLogica = new Logica();
        nombresApp = Logica.obtenerTablas(indicador);
        acordeon = new Accordion();
        pestana = new TitledPane();
        pestana.setText("Abreme");

        //Panel Base        
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        ScrollPane scrollPane = crearPanelFlotante();
        scrollPane.setFitToWidth(true);  // Ajustar el ancho del ScrollPane al ancho del contenedor padre
        pestana.setContent(scrollPane);
        acordeon.getPanes().add(pestana);
        root.getChildren().clear();
        root.getChildren().add(acordeon); // Añadimos el ScrollPane en lugar del contenido directamente  
        actualizarRoot();

        root.setOnMouseDragged((MouseEvent event) ->
        {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        ContextMenu contextMenu = new ContextMenu();
        MenuItem closeMenuItem = new MenuItem("Cerrar aplicación");
        closeMenuItem.setOnAction(event ->
        {
            stage.close();
        });
        contextMenu.getItems().add(closeMenuItem);
        root.setOnContextMenuRequested(event ->
        {
            contextMenu.show(root, event.getScreenX(), event.getScreenY());
        });
        root.setOnMouseClicked(event ->
        {
            contextMenu.hide();
        });

        acordeon.expandedPaneProperty().addListener((observable, oldPane, newPane) ->
        {
            if (newPane != null)
            {
                // Se ha abierto una nueva pestaña, ajusta el VBox al contenido
                root.requestLayout();

                // Muestra un Alert
                Platform.runLater(() ->
                {
                    stage.setHeight(200);
                });
            } else
            {
                stage.setHeight(50);
            }
        });

        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.DECORATED.UNDECORATED);

        setNuevoStage(stage);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
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
        // Agregar estilo al VBox
        root.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");

        // Agregar estilo al Accordion
        acordeon.setStyle("-fx-background-color: #ffffff;");

        // Agregar estilo al TitledPane
        pestana.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");

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

            Boton btn = new Boton();
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
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Occured");
                        alert.setHeaderText("Existe una coincidencia");
                        alert.setContentText("Por Favor use otro nombre que no sea: " + boton.getText());
                        alert.showAndWait();
                        encontrado = true;
                        if (encontrado)
                        {
                            claseLogica.eliminarAccesoDirecto(boton.getText(), s);
                        }
                    }
                }
            } catch (Exception ex)
            {
                Auxiliares.alerta(ex.getMessage(), "botonFlotante", "anadirCategoria");
            }
        }
        contenido.getChildren().add(obtenerBtnCrearAccesoDirecto()); // Agregar el botón al contenido de la pestana    
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

    private Button obtenerBtnCrearAccesoDirecto()
    {
        Button btnCrear = new Button("Crear");
        try
        {
            btnCrear.setOnAction((ActionEvent event) ->
            {
                if (indicador == 1)
                {
                    btnAnadirApp();
                } else
                {
                    btnAnadirWeb();
                }
            });
        } catch (Exception ex)
        {
            Auxiliares.alerta(ex.getMessage(), "botonFlotante", "obtenerBtnCrearAccesoDirecto");
        }

        return btnCrear;
    }

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
                Logica.registrarAccesoDirecto(ad, titulo, 2);

            } catch (Exception ex)
            {
                Auxiliares.alerta(ex.getMessage(), "botonFlotante", "guardarWeb");
            }
        }
    }

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
                            Logica.registrarAccesoDirecto(ad, titulo, indicador);
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
                    Logica.registrarAccesoDirecto(ad, "otros", indicador);
                }
            } else
            {
                Auxiliares.alerta("No se pudo obtener el icono del archivo", "botonFlotante", "getFileIcon");
            }
        }
    }

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

    public ScrollPane crearPanelFlotante()
    {
        VBox contenido = anadirCategoria();
        contenido.setOnMouseDragged((MouseEvent event) ->
        {
            double newHeight = event.getY();
            contenido.setPrefHeight(newHeight);
        });
        ScrollPane scrollPane = new ScrollPane(contenido); // Creamos un ScrollPane con el contenido
        scrollPane.setFitToWidth(true); // Ajustamos el ancho del ScrollPane al ancho del contenedor padre
        return scrollPane;
    }

    public void setIndicador(int indicador)
    {
        this.indicador = indicador;
    }

}
