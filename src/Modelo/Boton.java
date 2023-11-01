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
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

public class Boton {

    public static Button inicializarBotonDeLasPestañas(int index, String tabla, int indicacion)
    {
        //Declaracion de variables
        Button nuevoBoton = new Button("Nuevo Botón");
        String nombre;
        String direccion;
        int id;

        //Creo un lista de la bd
        ArrayList<AccesoDirecto> leerAccesosDirectos = Logica.leerAccesosDirecto(tabla);

        //Configuro el boton
        nuevoBoton.setContentDisplay(ContentDisplay.TOP);
        nuevoBoton.setGraphicTextGap(5);
        nuevoBoton.setPrefWidth(Region.USE_COMPUTED_SIZE);
        nuevoBoton.setTextAlignment(TextAlignment.CENTER);

        nombre = leerAccesosDirectos.get(index).getNombre();
        direccion = leerAccesosDirectos.get(index).getDireccion();
        id = leerAccesosDirectos.get(index).getId();

        if (indicacion != 2)
        {
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
                if (indicacion != 2)
                {
                    Desktop.getDesktop().open(file);
                } else
                {
                    Desktop.getDesktop().browse(new URI(direccion));
                }
            } catch (IOException | URISyntaxException ex)
            {
                Auxiliares.alerta(ex.getMessage(), "Boton", "inicilizarBtndelasPestanas");
            }
        });

        return nuevoBoton;
    }

}
