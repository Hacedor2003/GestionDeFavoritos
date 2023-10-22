package Modelo;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boton {

    public Button inicializarBotonDeLasPestañas(int index, String tabla)
{
    //Declaracion de variables
    Logica claseLogica = new Logica();
    Button nuevoBoton = new Button("Nuevo Botón");
    String nombre;
    String direccion;

    //Creo un lista de la bd
    ArrayList<AccesoDirecto> leerAccesosDirectos = claseLogica.leerAccesosDirecto(tabla);

    //Configuro el boton
    nuevoBoton.setContentDisplay(ContentDisplay.TOP);
    nuevoBoton.setGraphicTextGap(5);
    nuevoBoton.setPrefSize(100, 64);

    nombre = leerAccesosDirectos.get(index).getNombre();
    direccion = leerAccesosDirectos.get(index).getDireccion();

    BufferedImage buffImg = null;
    buffImg = leerAccesosDirectos.get(index).getIcon();
    Image fxImage = SwingFXUtils.toFXImage(buffImg, null);
    nuevoBoton.setGraphic(new ImageView(fxImage));

    nuevoBoton.setText(nombre);
    nuevoBoton.setOnAction((ActionEvent e) ->
    {
        try
        {
            File file = new File(direccion);
            Desktop.getDesktop().open(file);
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            // Crea una pantalla emergente con el error
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Occured");
                alert.setHeaderText("Ooops, algo salió mal!");
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
        }
    });

    return nuevoBoton;
}

    public Button crearBtn(String nombrebtn, String icono)
    {
        Button boton = new Button();

        if (icono.equals(""))
        {
            icono = "icons8-anonymous-mask-48.png";
        }

        try
        {
            Image img = new Image(getClass().getResourceAsStream("D:\\Proyectos\\Proyectos_NetBeans\\GestionDeFavoritos\\src\\Archivos\\Images" + icono));
            boton.setGraphic(new javafx.scene.image.ImageView(img));
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        boton.setText(nombrebtn);
        boton.setAlignment(javafx.geometry.Pos.CENTER);
        boton.setCursor(javafx.scene.Cursor.HAND);
        //boton.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        boton.setMaxSize(150, 45);
        boton.setMinSize(150, 45);
        boton.setPrefSize(150, 45);

        return boton;
    }
}
