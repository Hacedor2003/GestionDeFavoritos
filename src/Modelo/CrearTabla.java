package Modelo;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javax.swing.filechooser.FileSystemView;

public class CrearTabla extends Application {

    //Inicializo
    private RadioButton appRadioButton;
    private RadioButton webRadioButton;
    private TextField nombreTextField;
    private TextField direccionTextField;
    private AccesoDirecto ad;
    private Logica claseLogica = new Logica();
    private String tabla;

    public void start(Stage primaryStage)
    {
        // Crear la ventana principal
        primaryStage.setTitle("Crear tabla en MySQL");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);

        // Agregar los elementos a la ventana
        Label tipoLabel = new Label("Tipo de proyecto:");
        grid.add(tipoLabel, 0, 1);
        ToggleGroup tipoToggleGroup = new ToggleGroup();
        appRadioButton = new RadioButton("App");
        appRadioButton.setToggleGroup(tipoToggleGroup);
        webRadioButton = new RadioButton("Web");
        webRadioButton.setToggleGroup(tipoToggleGroup);
        grid.add(appRadioButton, 1, 1);
        grid.add(webRadioButton, 2, 1);

        Button crearTablaButton = new Button("Crear tabla");
        grid.add(crearTablaButton, 1, 2);

        // Agregar el evento al botón de crear tabla
        crearTablaButton.setOnAction(event ->
        {
            if (appRadioButton.isSelected())
            {
                int realizo = abrirBuscandor(tabla, 1);

                // Crear la ventana para solicitar el nombre y la dirección
                Stage appStage = new Stage();
                appStage.setTitle("Datos de la app");
                GridPane appGrid = new GridPane();
                appGrid.setAlignment(Pos.CENTER);
                appGrid.setHgap(10);
                appGrid.setVgap(10);
                appGrid.setPadding(new Insets(25, 25, 25, 25));
                Scene appScene = new Scene(appGrid, 400, 300);
                appStage.setScene(appScene);

                Label nombreLabel = new Label("Nombre:");
                appGrid.add(nombreLabel, 0, 0);
                nombreTextField = new TextField();
                if (realizo == 1)
                {
                    nombreTextField.setText(ad.getNombre());
                }
                appGrid.add(nombreTextField, 1, 0);

                Button btnAbrirBuscandor = new Button((realizo != 1) ? "Buscar ubicacion" : "Guardar");
                appGrid.add(btnAbrirBuscandor, 0, 1);
                btnAbrirBuscandor.setOnAction(e ->
                {
                    // Crear la tabla en MySQL                    
                    claseLogica.registrarAccesoDirecto(ad, tabla, 1);
                    appStage.close();
                    primaryStage.close();
                });

                appStage.showAndWait();
            } else if (webRadioButton.isSelected())
            {
                // Crear la ventana para solicitar el nombre
                Stage webStage = new Stage();
                webStage.setTitle("Datos de la web");
                GridPane webGrid = new GridPane();
                webGrid.setAlignment(Pos.CENTER);
                webGrid.setHgap(10);
                webGrid.setVgap(10);
                webGrid.setPadding(new Insets(25, 25, 25, 25));
                Scene webScene = new Scene(webGrid, 400, 300);
                webStage.setScene(webScene);

                Label nombreLabel = new Label("Nombre:");
                webGrid.add(nombreLabel, 0, 0);
                nombreTextField = new TextField();
                webGrid.add(nombreTextField, 1, 0);

                Label direccionLabel = new Label("Direccion URL:");
                webGrid.add(direccionLabel, 0, 1);
                direccionTextField = new TextField();
                webGrid.add(direccionTextField, 1, 1);

                Button guardarButton = new Button("Guardar");
                webGrid.add(guardarButton, 0, 2);

                // Agregar el evento al botón de guardar
                guardarButton.setOnAction(event1 ->
                {
                    String nombre = nombreTextField.getText();
                    if (nombre.isEmpty())
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "El campo no puede estar vacío.");
                        alert.showAndWait();
                        return;
                    }

                    // Crear la tabla en MySQL                    
                    claseLogica.registrarAccesoDirecto(ad, tabla, 2);

                    webStage.close();
                    primaryStage.close();
                });

                webStage.showAndWait();
            } else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Debe seleccionar un tipo de proyecto.");
                alert.showAndWait();
            }
        });

        primaryStage.show();
    }

    private int abrirBuscandor(String titulo, int condicion)
    {
        // Crea un FileChooser
        FileChooser fileChooser = new FileChooser();

        // Abre la ventana de selección de archivo
        File selectedFile = fileChooser.showOpenDialog(null);

        // Si se selecciona un archivo
        if (selectedFile != null)
        {
            String nombre = selectedFile.getName();
            String path = selectedFile.getAbsolutePath();

            ad = new AccesoDirecto();
            ad.setNombre(nombre);
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

                //claseLogica.registrarAccesoDirecto(ad, titulo, condicion);
            } else
            {
                // Manejar el caso en que no se pudo obtener el icono del archivo
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No se pudo obtener el icono del archivo");
                alert.showAndWait();
            }
            return 1;
        }
        return 0;
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

    public AccesoDirecto getAd()
    {
        return ad;
    }

    public void setAd(AccesoDirecto ad)
    {
        this.ad = ad;
    }

    public String getTabla()
    {
        return tabla;
    }

    public void setTabla(String tabla)
    {
        this.tabla = tabla;
    }

}
