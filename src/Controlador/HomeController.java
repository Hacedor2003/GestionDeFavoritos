package Controlador;

import Modelo.AccesoDirecto;
import Modelo.Boton;
import Modelo.CrearTabla;
import Modelo.Logica;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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

    private Stage stage;
    Stage nuevoStage = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        obtenerLosComponentesDeLaBaseDeDatos(1, "");
        obtenerLosComponentesDeLaBaseDeDatos(2, "");
        actualizarVBOX();
    }

    @FXML
    private void btnhome(MouseEvent event)
    {
        borderPanel.setCenter(PanelApp);
        PanelApp.getTabs().clear();
        obtenerLosComponentesDeLaBaseDeDatos(1, "");
        obtenerLosComponentesDeLaBaseDeDatos(2, "");
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
    private void obtenerLosComponentesDeLaBaseDeDatos(int indicador, String nombre)
    {
        Tab tab1 = new Tab();
        Tab tab2 = new Tab();
        try
        {
            if (indicador == 1)
            {
                tab1 = anadirTab(indicador, nombre);
            } else
            {
                tab2 = anadirTab(indicador, nombre);
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        switch (indicador)
        {
            case 1:
                PanelApp.getTabs().add(tab1);
                tab1.setOnSelectionChanged(event ->
                {
                    this.indicador = 1;
                });
                break;
            case 2:
                PanelApp.getTabs().add(tab2);
                tab2.setOnSelectionChanged(event ->
                {
                    this.indicador = 2;
                });
                break;
            default:
                PanelApp.getTabs().add(tab2);
                tab2.setOnSelectionChanged(event ->
                {
                    this.indicador = 3;
                });
                break;
        }

    }

    //Se crean los tab para guardar los accesos directos
    private Tab anadirTab(int indicador, String nombre)
    {
        String nombreTabla = "";
        ArrayList<String> tabla;
        if (indicador == 3)
        {
            ArrayList<String> tablasBuscar = claseLogica.obtenerTablas(indicador);
            for (String s : tablasBuscar)
            {
                if (s.equals(nombre))
                {
                    nombreTabla = s;
                }
            }
            tabla = new ArrayList<>();
            tabla.add(nombreTabla);
        } else
        {
            tabla = claseLogica.obtenerTablas(indicador);
        }

        Tab tabNuevo = new Tab();   //Tab para guardar los Accesos directos
        VBox contenido = new VBox(); // Contenedor para los elementos del Tab
        String nombreTab = null;
        if (!nombre.equals(""))
        {
            nombreTab = nombre;
        } else
        {
            nombreTab = (indicador == 1) ? "Aplicaciones" : "Web";
        }
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
                    boolean encontre = compararBtn(boton, indicador);
                    boolean encontrado = false;
                    if (!encontre)
                    {
                        panelConBotones = loadPage();
                        panelConBotones.setContent(boton);
                        panelConBotones.setTabla(s);
                        panelConBotones.setId(leerAccesosDirecto.get(i).getId());
                        contenido.getChildren().add(panelConBotones.getRoot());
                        AnchorPane TabPanel = new AnchorPane();
                        TabPanel.getChildren().clear();
                        TabPanel.getChildren().add(contenido);
                        tabNuevo.setContent(PanelTabPanel); // Establecer el contenido del Tab 
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
                            claseLogica.eliminarAccesoDirecto(Integer.parseInt(boton.getId()), s);
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
        String tabla = claseLogica.buscarAccesoDirecto(TextField.getText(), indicador);
        for (int i = 0; i < claseLogica.leerAccesosDirecto(tabla, indicador).size(); i++)
        {
            PaneAccesosDirectos.getChildren().add(btn.inicializarBotonDeLasPestañas(i, tabla, indicador));

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
        // Crea una pantalla emergente con el error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Occured");
        alert.setHeaderText("En el metodo MenuItemSoloNombreArchivos de la clase " + getClass());
        alert.setContentText("Implementacion");
        alert.showAndWait();
    }

    private void MenuItemIconos(ActionEvent event)
    {
        // Crea una pantalla emergente con el error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Occured");
        alert.setHeaderText("En el metodo MenuItemIconos de la clase " + getClass());
        alert.setContentText("Implementacion");
        alert.showAndWait();
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
                // Manejo de errores en caso de que no se pueda abrir el archivo
                e.printStackTrace();
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
        // Crea una pantalla emergente con el error
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error Occured");
        alert.setHeaderText("En el metodo MenuItemIconos de la clase " + getClass());
        alert.setContentText("Implementacion");
        alert.showAndWait();
    }

    @FXML
    private void MenuItemAcercaDe(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText("Versión 1.0");
        alert.setContentText("Autor: Bryan");
        alert.showAndWait();
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
            ArrayList<AccesoDirecto> a = claseLogica.leerAccesosDirecto(s, 1);
            for (AccesoDirecto b : a)
            {
                int id = b.getId();
                claseLogica.eliminarAccesoDirecto(id, s);
            }

        }
        for (String s : nombresWeb)
        {
            ArrayList<AccesoDirecto> a = claseLogica.leerAccesosDirecto(s, 2);
            for (AccesoDirecto b : a)
            {
                int id = b.getId();
                claseLogica.eliminarAccesoDirecto(id, s);
            }
        }

    }

    double width = 130.4;
    double height = 42.4;

    @FXML

    private void crearBtnBox(ActionEvent event)
    {
        TextInputDialog dialogoNombre = new TextInputDialog();
        dialogoNombre.setTitle("Crear botón");
        dialogoNombre.setHeaderText(null);
        dialogoNombre.setContentText("Ingresa un nombre para el botón:");

        Optional<String> resultadoNombre = dialogoNombre.showAndWait();
        if (resultadoNombre.isPresent())
        {
            String nombreBtn = resultadoNombre.get();

            Button nuevoBtn = new Button(nombreBtn);
            nuevoBtn.setMinHeight(width);
            nuevoBtn.setMinWidth(height);
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
            claseLogica.guardarVBox(nuevoBtn.getText(), nombreBtn);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Botón creado y añadido al VBox");
            alert.showAndWait();

            //Instancio la clase
            CrearTabla ct = new CrearTabla();
            ct.setTabla(nombreBtn);
            ct.start(nuevoStage);
        }
    }

    private void actualizarVBOX()
    {
        ArrayList<String> categoriasPersonalizadas = claseLogica.leerVBOX();

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

    @FXML
    private void menuArchivoEliminarCategoria(ActionEvent event)
    {
        ArrayList<String> listaTablas = claseLogica.obtenerTablas(4);
        String nombreBtn = "";
        ObservableList<String> opciones = FXCollections.observableArrayList(listaTablas);
        ChoiceDialog<String> dialogo = new ChoiceDialog<>(opciones.get(0), opciones);
        dialogo.setTitle("Eliminar Categoria");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Selecciona una opción:");

        Optional<String> resultado = dialogo.showAndWait();
        if (resultado.isPresent())
        {
            claseLogica.eliminarCategoria(resultado.get());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Categoria " + resultado.get() + " Eliminada");
            alert.showAndWait();
        }
    }

}
