
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageApp extends Application {

    private ResourceBundle bundle;
    private ResourceBundle bundle2;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Cargar el archivo de recursos predeterminado (en inglés)
        bundle = ResourceBundle.getBundle("labels", Locale.FRANCE);
        bundle2 = ResourceBundle.getBundle("labels", Locale.ENGLISH);

        Button button = new Button();
        button.setText(bundle.getString("button_greeting"));
        Button button2 = new Button();
        button2.setText(bundle2.getString("button_greeting"));

        // Acción de cambio de idioma para el primer botón (ejemplo: de francés a inglés)
        button.setOnAction(e ->
        {
            // Cambiar el bundle a la configuración en inglés
            bundle = bundle2;
            // Actualizar el texto del botón
            button.setText(bundle.getString("button_greeting"));            
        });

        // Acción de cambio de idioma para el segundo botón (ejemplo: de inglés a francés)
        button2.setOnAction(e ->
        {
            // Cambiar el bundle a la configuración en francés
            bundle = ResourceBundle.getBundle("labels", Locale.FRANCE);
            // Actualizar el texto del botón
            button2.setText(bundle.getString("button_greeting"));
        });

        root.getChildren().addAll(button, button2);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Aplicación de cambio de idioma");
        primaryStage.show();
    }

}
