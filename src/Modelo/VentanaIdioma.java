package Modelo;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VentanaIdioma extends Stage implements Hablable {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("Archivos/Idioma/Home", new Locale("es"));
    private static String textTituloVentana = resourceBundle.getString("venta_acercaDe_tituloVentana");
    private static String textVersion = resourceBundle.getString("venta_acercaDe_version");
    private static String textAutor = resourceBundle.getString("venta_acercaDe_Autor");
    private static String textDescripcion = resourceBundle.getString("venta_acercaDe_Descripcion");

    private Label titulo;
    private Label version;
    private Label autor;
    private Label descripcion;

    public VentanaIdioma()
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
        titulo = new Label(textTituloVentana);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        version = new Label(textVersion);
        autor = new Label(textAutor);
        descripcion = new Label(textDescripcion);

        // Agrega los elementos al layout
        layout.getChildren().addAll(titulo, version, autor, descripcion);

        // Crea una barra de menú con un menú desplegable para cambiar el idioma
        MenuBar menuBar = new MenuBar();
        Menu menuIdioma = new Menu("Idioma");
        MenuItem menuItemEspanol = new MenuItem("Español");
        MenuItem menuItemIngles = new MenuItem("Inglés");
        menuIdioma.getItems().addAll(menuItemEspanol, menuItemIngles);
        menuBar.getMenus().add(menuIdioma);

        // Agrega la barra de menú y el layout a la escena
        Scene scene = new Scene(new VBox(menuBar, layout));

        // Asigna la escena a la ventana
        setScene(scene);

        // Agrega los manejadores de eventos para cambiar el idioma
        menuItemEspanol.setOnAction(e -> cambiarIdioma("es"));
        menuItemIngles.setOnAction(e -> cambiarIdioma("en"));
    }

    @Override
    public void cambiarIdioma(String idioma)
    {
        resourceBundle = ResourceBundle.getBundle("Archivos/Idioma/Home", new Locale(idioma));

        // Actualiza los textos de la ventana
        textVersion = resourceBundle.getString("venta_acercaDe_version");
        textAutor = resourceBundle.getString("venta_acercaDe_Autor");
        textDescripcion = resourceBundle.getString("venta_acercaDe_Descripcion");

        // Actualiza el título de la ventana
        setTitle(textTituloVentana);

        // Actualiza los textos de los elementos de la ventana
        titulo.setText(textTituloVentana);
        version.setText(textVersion);
        autor.setText(textAutor);
        descripcion.setText(textDescripcion);
    }

    @Override
    public void updateNodeLanguage(Node node, ResourceBundle bundle)
    {
        // Este método se puede implementar para actualizar el idioma de otros elementos de la ventana que no sean de texto, como imágenes o iconos.
    }
}
