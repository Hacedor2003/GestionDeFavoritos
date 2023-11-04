package Modelo;


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
        setTitle("Acerca de");
        setWidth(400);
        setHeight(300);

        // Crea un layout para la ventana
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);

        // Agrega los elementos a mostrar en la ventana
        Label titulo = new Label("Mi Aplicación");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label version = new Label("Versión 1.0");
        Label autor = new Label("Autor: Hacedor");
        Label descripcion = new Label("Programa de Gestion de Accesos Directos");

        // Agrega los elementos al layout
        layout.getChildren().addAll(titulo, version, autor, descripcion);

        // Crea una escena y agrega el layout
        Scene scene = new Scene(layout);

        // Asigna la escena a la ventana
        setScene(scene);
    }
}
