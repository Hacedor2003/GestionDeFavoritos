package Controlador;

import Modelo.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Hacedor
 */
public class botonFlotante extends Application {

    //Inicializacion
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage NuevoStage;

    private TabPane PanelApp;
    private HBox root;
    private Logica claseLogica;
    private AnchorPane PanelReservado;
    private int indicador;
    ArrayList<String> nombresApp = claseLogica.obtenerTablas(1);

    @Override
    public void start(Stage stage)
    {
        root = new HBox();
        claseLogica = new Logica();

        //Boton Flotante
        Button btn = new Button();
        btn.setText("Gestion De Accesos Directos");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event)
            {
                System.out.println("Hello World!");
            }
        });

        //Boron para Arrastrar
        Button btnArrast = new Button("Arrastrar");
        btnArrast.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event)
            {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        //Boton para mostrar los accesos directos
        Button btnMostrar = new Button("Mostrar");
        btnMostrar.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event)
            {
                Accordion acordeon = new Accordion();
                TitledPane categoria = new TitledPane("Prueba", acordeon);
                root.getChildren().clear();
                root.getChildren().addAll(acordeon, categoria);
                actualizarRoot();
            }
        });

        //Panel Base        
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        root.getChildren().add(btn);
        actualizarRoot();
        btnArrast.setMinWidth(btnArrast.USE_PREF_SIZE);
        btnMostrar.setMinWidth(btnMostrar.USE_PREF_SIZE);

        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.DECORATED.UNDECORATED);

        //Se configura mostrar los botones
        root.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event)
            {
                root.getChildren().clear();
                root.getChildren().add(btnArrast);
                root.getChildren().add(btnMostrar);
                actualizarRoot();
            }
        });

        root.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event)
            {
                root.getChildren().clear();
                root.getChildren().add(btn);
                actualizarRoot();
            }
        });

        setNuevoStage(stage);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
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
        root.setMinSize(root.USE_COMPUTED_SIZE, root.USE_COMPUTED_SIZE);
        root.setPrefSize(root.USE_COMPUTED_SIZE, root.USE_COMPUTED_SIZE);
        root.setMaxSize(root.USE_COMPUTED_SIZE, root.USE_COMPUTED_SIZE);
    }

    private TitledPane anadirCategoria()
    {
        //Inicializar
        String nombreCategoria = "";
        ArrayList<String> categorias;
        VBox contenido = new VBox(); // Contenedor para los elementos de la pestana        

        //Busca el nombre de todas las Categorias
        ArrayList<String> tablasBuscar = claseLogica.obtenerTablas(4);
        for (String s : tablasBuscar)
        {
            nombreCategoria = s;
        }
        categorias = new ArrayList<>();
        categorias.add(nombreCategoria);
        TitledPane pestana = new TitledPane();

        for (String s : categorias)
        {
            pestana.setText(s);

            Boton btn = new Boton();
            PanelParaBtnController panelConBotones;
            ArrayList<AccesoDirecto> leerAccesosDirecto = claseLogica.leerAccesosDirecto(s, 3);
            try
            {
                for (int i = 0; i < leerAccesosDirecto.size(); i++)
                {
                    Button boton = btn.inicializarBotonDeLasPestañas(i, s, 4);
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
                        pestana.setContent(PanelReservado); // Establecer el contenido del Tab 
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
                // Crea una pantalla emergente con el error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Occured");
                alert.setHeaderText("En el metodo AnadirTab de la clase " + getClass());
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

        }
        contenido.getChildren().add(obtenerBtnCrearAccesoDirecto()); // Agregar el botón al contenido de la pestana       
        pestana.setContent(contenido); // Establecer el contenido de la pestana    
        return pestana;
    }

    private boolean compararBtn(Button boton, int indicador)
    {
        String comparar = boton.getText();
        int contador = 0;

        ArrayList<String> tabla = claseLogica.obtenerTablas(indicador);
        for (String s : tabla)
        {
            ArrayList<AccesoDirecto> leerAccesosDirecto = claseLogica.leerAccesosDirecto(s, indicador);
            Boton btn = new Boton();
            for (int index = 0; index < leerAccesosDirecto.size(); index++)
            {
                Button boton2 = btn.inicializarBotonDeLasPestañas(index, s, indicador);
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
            System.out.println(ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Occured");
            alert.setHeaderText("Ooops, algo salió mal!");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

        return panelContolador;
    }

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

}
