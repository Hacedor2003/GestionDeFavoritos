package Modelo;

import static Modelo.Auxiliares.resourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AcercaDeStage extends Stage implements Hablable {

    private static String textTituloVentana = resourceBundle.getString("venta_acercaDe_tituloVentana");
    private static String textTitulo = resourceBundle.getString("venta_acercaDe_titulo");
    private static String textVersion = resourceBundle.getString("venta_acercaDe_version");
    private static String textAutor = resourceBundle.getString("venta_acercaDe_Autor");
    private static String textDescripcion = resourceBundle.getString("venta_acercaDe_Descripcion");

    public AcercaDeStage()
    {
        // Configura la ventana de "Acerca de"
        setTitle(textTituloVentana);
        setWidth(400);
        setHeight(300);

        // Crea un layout para la ventana
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);

        // Agrega los elementos a mostrar en la ventana
        Label titulo = new Label(textTitulo);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label version = new Label(textVersion);
        Label autor = new Label(textAutor);
        Label descripcion = new Label(textDescripcion);

        // Agrega los elementos al layout
        layout.getChildren().addAll(titulo, version, autor, descripcion);

        // Crea una escena y agrega el layout
        Scene scene = new Scene(layout);

        // Asigna la escena a la ventana
        setScene(scene);
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
                textTituloVentana = resourceBundle.getString("venta_acercaDe_tituloVentana");
                textTitulo = resourceBundle.getString("venta_acercaDe_titulo");
                textVersion = resourceBundle.getString("venta_acercaDe_version");
                textAutor = resourceBundle.getString("venta_acercaDe_Autor");
                textDescripcion = resourceBundle.getString("venta_acercaDe_Descripcion");
            }
        }

    }
}
