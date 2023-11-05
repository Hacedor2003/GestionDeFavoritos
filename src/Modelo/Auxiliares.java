package Modelo;

import Controlador.PanelParaBtnController;
import static Modelo.Logica.obtenerTablas;
import static Controlador.CarpetasController.btnAnadirCarpeta;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;

public class Auxiliares {

    //Inicializacion    
    static private ArrayList<String> listaTodasTablas;
    static ArrayList<String> nombresApp = Logica.obtenerTablas(1);
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("Archivos/bundle");

    /**
     * Metodo para mostrar un error 
     * mensaje:Mensaje a mostrar
     * tituloVentana:Titulo de la ventana emergente Evento: evento donde se
     * realizo el error
     */
    public static void alerta(String mensaje, String tituloVentana, String evento)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(resourceBundle.getString("alerta_error"));
        alert.setHeaderText(tituloVentana);
        alert.setContentText(evento + resourceBundle.getString("alerta_error") + "\n" + mensaje);
        alert.showAndWait();
    }

    /**
     * Se empareja el nombre del icono correspondiente 
     * nombre: aplicaciones ,
     * web o carpeta nombre:la categoria del acceso directo
     */
    public static String emparajarBtnIcono(String nombre)
    {
        ArrayList<String> iconos = new ArrayList<>();
        iconos.add(0, "icons8-windows-client-24.png");
        iconos.add(1, "icons8-web-24.png");
        iconos.add(2, "icons8-folder-24.png");
        String icon = null;

        icon = switch (nombre)
        {
            case "aplicaciones" ->
                iconos.get(0);
            case "web" ->
                iconos.get(1);
            case "carpeta" ->
                iconos.get(2);
            default ->
                iconos.get(0);
        };

        return icon;
    }

    /**
     * Se crea el btn para anadir accesos directos 
     * tituloClase:Para mostrar en
     * caso de error indicador: 1 = anadir App , 4 = Anadir Carpeta , otro =
     * anadir Web
     */
    public static Button obtenerBtnCrearAccesoDirecto(String tituloClase, int indicador)
    {
        Button btnCrear = new Button(resourceBundle.getString("btn_Anadir"));
        btnCrear.setId("btnCrear");
        btnCrear.getStylesheets().add("/Archivos/btncrear.css");
        btnCrear.getStyleClass().add("btnCrear");
        try
        {
            btnCrear.setOnAction((ActionEvent event) ->
            {
                switch (indicador)
                {
                    case 1 ->
                        btnAnadirApp();
                    case 4 ->
                        btnAnadirCarpeta();
                    default ->
                        btnAnadirWeb();
                }
            });
        } catch (Exception ex)
        {
            alerta(ex.getMessage(), tituloClase, "obtenerBtnCrearAccesoDirecto");
        }

        return btnCrear;
    }

    /**
     * Metodo para agregar a la base de datos de aplicaciones
     */
    public static void btnAnadirApp()
    {
        String nombreBtn = "";
        ObservableList<String> opciones = FXCollections.observableArrayList(nombresApp);
        ChoiceDialog<String> dialogo = new ChoiceDialog<>(opciones.get(0), opciones);
        dialogo.setTitle(resourceBundle.getString("alerta_titulo_add_app"));
        dialogo.setHeaderText(null);
        dialogo.setContentText(resourceBundle.getString("alerta_opcion_add_app"));

        Optional<String> resultado = dialogo.showAndWait();
        if (resultado.isPresent())
        {
            nombreBtn = resultado.get();
            obtenerDirectorioArchivo(nombreBtn, "Auxiliares");
        }
    }

    /**
     * Metodo para agregar a la base de datos de web
     */
    public static void btnAnadirWeb()
    {
        Stage ventana = new Stage();
        Label direccionLabel = new Label(resourceBundle.getString("alerta_direccion_add_web"));
        Label nombreLabel = new Label(resourceBundle.getString("alerta_nombre_add_web"));
        TextField urlFieldDireccion = new TextField();
        TextField urlFieldNombre = new TextField();
        Button saveButton = new Button(resourceBundle.getString("alerta_btn_guardar_web"));
        Button cancelButton = new Button(resourceBundle.getString("alerta_btn_cancelar_web"));

        saveButton.setOnAction(e ->
        {
            String direccion = urlFieldDireccion.getText();
            String nombre = urlFieldNombre.getText();

            guardarWeb("web", direccion, nombre, "Auxiliares");

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

    /**
     * Metodo para configurar la forma de guardar la web 
     * titulo:tabla donde
     * guardarla direccion:direccion de la web nombre: nombre del acceso directo
     * tituloClase: Titulo de la clase en caso de error
     */
    public static void guardarWeb(String titulo, String direccion, String nombre, String tituloClase)
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
                boolean registrarAccesoDirecto = Logica.registrarAccesoDirecto(ad, titulo, 2);

                if (!registrarAccesoDirecto)
                {
                    alerta(resourceBundle.getString("alerta_cancelar_add_web"), "", "");
                }

            } catch (Exception ex)
            {
                alerta(ex.getMessage(), tituloClase, "guardarWeb");
            }
        }
    }

    /**
     * Metodo para configurar la forma de guardar el btn 
     * titulo:Titulo de la
     * tabla donde se va a guardar tituloClase: Titulo de la clase en caso de
     * error
     */
    public static void obtenerDirectorioArchivo(String titulo, String tituloClase)
    {
        // Crea un FileChooser
        FileChooser fileChooser = new FileChooser();
        // Agregar filtro para archivos .exe
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos ejecutables (*.exe)", "*.exe");
        fileChooser.getExtensionFilters().add(extFilter);

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
                            boolean registrarAccesoDirecto = Logica.registrarAccesoDirecto(ad, titulo, 1);
                            if (!registrarAccesoDirecto)
                            {
                                alerta(resourceBundle.getString("alerta_cancelar_add_web"), "", "");
                            }
                            encontrado = true;
                            break;
                        } catch (Exception ex)
                        {
                            alerta(ex.getMessage(), tituloClase, "obtenerDirectorioArchivo");
                        }
                    }
                }
                if (!encontrado)
                {
                    boolean registrarAccesoDirecto = Logica.registrarAccesoDirecto(ad, "otros", 1);
                    if (!registrarAccesoDirecto)
                    {
                        alerta(resourceBundle.getString("alerta_cancelar_add_web"), "", "");
                    }
                }
            } else
            {
                alerta(resourceBundle.getString("alerta_cancelar_add_web"), tituloClase, "obtenerDirectorioArchivo");
            }
        }
    }

    /**
     * Método para conseguir el icono del botón capturado 
     * filePath:direccion de la aplicacion
     */
    public static Image getFileIcon(String filePath)
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

    /**
     * Metodo para comprobar btn duplicados
     * boton: boton con el que se va a comparar
     * indicador: 1 = app , 2 = web , 4 = carpeta , 5 = todos
     */
    public static boolean compararBtn(Button boton, int indicador)
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

    /**
     * Metodo para eliminar una categoria de la tabla para tablas personalizadas
     * tabla = nombre de la tabla personalizada
     */
    public static void eliminarCategoriasPersonalizadas(String tabla)
    {
        for (String s : listaTodasTablas)
        {
            if (s.equals(tabla))
            {
                Logica.eliminarAccesoDirecto(tabla, "configuracion");
            }
        }
    }

    /**
     * Se crean los tab para guardar los accesos directos
     * tabla: tabla = "" obtendra el nombre de las tablas segun el indicador
     * indicador: 1 = app , 2 = web , 4 carpetas
     */
    static public Tab anadirTab(String tabla, int indicador)
    {
        AnchorPane PanelTabPanel = new AnchorPane();
        Tab tabNuevo = new Tab();   //Tab para guardar los Accesos directos
        VBox contenido = new VBox(); // Contenedor para los elementos del Tab
        ArrayList<String> nombresApp;
        if (tabla.equals(""))
        {
            nombresApp = obtenerTablas(indicador);
        } else
        {
            nombresApp = new ArrayList<>();
            nombresApp.add(tabla);
        }

        for (String s : nombresApp)
        {
            tabNuevo.setText(s);
            PanelParaBtnController panelConBotones;
            ArrayList<AccesoDirecto> leerAccesosDirecto = Logica.leerAccesosDirecto(s, indicador);
            try
            {
                for (int i = 0; i < leerAccesosDirecto.size(); i++)
                {
                    Button boton = Boton.inicializarBotonDeLasPestañas(i, s, indicador);
                    panelConBotones = getPlantillaAccesoDirecto();
                    panelConBotones.setIndicacion(2);
                    panelConBotones.setContent(boton);
                    panelConBotones.setTabla(s);
                    panelConBotones.setNombre(leerAccesosDirecto.get(i).getNombre());
                    contenido.getChildren().add(panelConBotones.getRoot());
                    AnchorPane TabPanel = new AnchorPane();
                    TabPanel.getChildren().clear();
                    TabPanel.getChildren().add(contenido);
                    tabNuevo.setContent(PanelTabPanel); // Establecer el contenido del Tab        

                }
            } catch (Exception ex)
            {
                alerta(ex.getMessage(), "AplicacionesController", "anadirTab");
            }
        }
        contenido.getChildren().add(obtenerBtnCrearAccesoDirecto("Auxiliar", indicador)); // Agregar el botón al contenido del Tab
        tabNuevo.setContent(contenido); // Establecer el contenido del Tab            

        return tabNuevo;
    }

    /**Se crea el panel para colocar el acceso directo*/
    static public PanelParaBtnController getPlantillaAccesoDirecto()
    {
        PanelParaBtnController panelContolador = null;

        try
        {
            FXMLLoader loader = new FXMLLoader(PanelParaBtnController.class.getResource("/Vista/panelParaBtn.fxml"));
            Parent root = loader.load();
            panelContolador = loader.getController();
        } catch (IOException ex)
        {
            alerta(ex.getMessage(), "AplicacionesController", "loadPage");
        }

        return panelContolador;
    }

}
