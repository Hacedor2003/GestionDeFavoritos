package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Hacedor
 */
public class Conexion {
     Connection con;
     public Connection getConnection(){
         try{
             String myBD = "jdbc:sqlite:D:/Proyectos/Proyectos_NetBeans/GestionDeFavoritos/src/Archivos/gestiondeAccesosDirectos.db";
             con = DriverManager.getConnection(myBD,"root","");
             return con;
         }catch(SQLException e){
             System.out.println(e.toString());                       
         }
         return null;
     }
     
}
