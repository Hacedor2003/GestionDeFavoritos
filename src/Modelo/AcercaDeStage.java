package Modelo;


import static Modelo.Auxiliares.resourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AcercaDeStage extends Stage {

    public AcercaDeStage()
    {
        // Configura la ventana de "Acerca de"
        setTitle(resourceBundle.getString("venta_acercaDe_tituloVentana"));
        setWidth(400);
        setHeight(300);

        // Crea un layout para la ventana
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);

        // Agrega los elementos a mostrar en la ventana
        Label titulo = new Label(resourceBundle.getString("venta_acercaDe_titulo"));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label version = new Label(resourceBundle.getString("venta_acercaDe_version"));
        Label autor = new Label(resourceBundle.getString("venta_acercaDe_Autor"));
        Label descripcion = new Label(resourceBundle.getString("venta_acercaDe_Descripcion"));

        // Agrega los elementos al layout
        layout.getChildren().addAll(titulo, version, autor, descripcion);

        // Crea una escena y agrega el layout
        Scene scene = new Scene(layout);

        // Asigna la escena a la ventana
        setScene(scene);
    }
}
