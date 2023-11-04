package prueba;


import java.util.ResourceBundle;
import java.util.Scanner;

public class Prueba {
    
    public static void main(String[] args){
        ResourceBundle rb = ResourceBundle.getBundle("prueba/bundle");
        
        Scanner lector = new Scanner(System.in);
        
        System.out.printf("%s " , rb.getString("console.get_name"));
        String nombre = lector.nextLine();
        
        System.out.printf("%s " , rb.getString("console.get_email"));
        String email = lector.nextLine();
        
        
    }
}
