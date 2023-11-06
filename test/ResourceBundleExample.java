
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleExample {

    public static void main(String[] args)
    {
        // Cargar el ResourceBundle inicial
        ResourceBundle bundle = ResourceBundle.getBundle("labels", Locale.getDefault());

        // Obtener el valor de una clave específica
        String mensaje = bundle.getString("button_greeting");

        System.out.println("Mensaje actual: " + mensaje);

        // Cambiar el ResourceBundle a otro idioma
        Locale nuevoLocale = new Locale("en");
        bundle = ResourceBundle.getBundle("labels", nuevoLocale);

        // Obtener el nuevo valor de la clave
        mensaje = bundle.getString("button_greeting");

        System.out.println("Nuevo mensaje: " + mensaje);
    }
}
