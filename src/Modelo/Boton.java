package Modelo;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

public class Boton {

    public Button inicializarBotonDeLasPestañas(int index, String tabla, int contenido)
    {
        //Declaracion de variables
        Logica claseLogica = new Logica();
        Button nuevoBoton = new Button("Nuevo Botón");
        String nombre;
        String direccion;
        int id;

        //Creo un lista de la bd
        ArrayList<AccesoDirecto> leerAccesosDirectos = claseLogica.leerAccesosDirecto(tabla,contenido);

        //Configuro el boton
        nuevoBoton.setContentDisplay(ContentDisplay.TOP);
        nuevoBoton.setGraphicTextGap(5);
        nuevoBoton.setPrefWidth(Region.USE_COMPUTED_SIZE);
        nuevoBoton.setTextAlignment(TextAlignment.CENTER);

        nombre = leerAccesosDirectos.get(index).getNombre();
        direccion = leerAccesosDirectos.get(index).getDireccion();
        id = leerAccesosDirectos.get(index).getId();

        if(contenido == 1){
        BufferedImage buffImg = null;
        buffImg = leerAccesosDirectos.get(index).getIcon();
        Image fxImage = SwingFXUtils.toFXImage(buffImg, null);
        nuevoBoton.setGraphic(new ImageView(fxImage));
        }

        nuevoBoton.setText(nombre);
        nuevoBoton.setId(id + "");
        nuevoBoton.setOnAction((ActionEvent e) ->
        {
            try
            {
                File file = new File(direccion);
                if (contenido == 1)
                {
                    Desktop.getDesktop().open(file);
                } else
                {
                    Desktop.getDesktop().browse(new URI(direccion));
                }
            } catch (IOException ex)
            {
                System.out.println(ex.getMessage());
                // Crea una pantalla emergente con el error
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Occured");
                alert.setHeaderText("Ooops, algo salió mal!");
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
            } catch (URISyntaxException ex)
            {
                System.out.println(ex.getMessage());
                // Crea una pantalla emergente con el error
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Occured");
                alert.setHeaderText("Ooops, algo salió mal!");
                alert.setContentText(ex.getMessage());
            }
        });

        return nuevoBoton;
    }

}
