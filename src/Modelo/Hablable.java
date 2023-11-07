package Modelo;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Node;

/**
 * Especialista en los string de las clases
 *
 * @author Hacedor
 */
public interface Hablable {
    
    static ResourceBundle resourceBundle = ResourceBundle.getBundle("Archivos/Idioma/Home", new Locale("es"));    

    void cambiarIdioma(String idioma);
    
    void updateNodeLanguage(Node node, ResourceBundle bundle);

}
