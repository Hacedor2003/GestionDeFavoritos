
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Labeled;

public class LanguageApp extends Application {

    private ResourceBundle bundle;
    private ResourceBundle bundle2;
    private VBox root;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        root = new VBox(10);
        root.setPadding(new Insets(10));

        // Cargar el archivo de recursos predeterminado (en inglés)
        bundle = ResourceBundle.getBundle("labels", Locale.FRANCE);

        Button button = new Button();
        button.setText(bundle.getString("button_greeting"));

        // Acción de cambio de idioma para el primer botón (ejemplo: de francés a inglés)
        button.setOnAction(e ->
        {
            // Obtener el idioma seleccionado por el usuario
            String idioma = "en"; // o "en" para inglés

            // Cargar el archivo de propiedades correspondiente al idioma seleccionado
            ResourceBundle font = ResourceBundle.getBundle("labels", Locale.ENGLISH);

            // Recorrer todos los nodos de la escena y actualizar las cadenas de texto
            updateNodeLanguage(root, font);
        });

        root.getChildren().addAll(button);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Aplicación de cambio de idioma");
        primaryStage.show();
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
                labeled.setText(bundle.getString("button_greeting"));
            }
        }
    }

}
