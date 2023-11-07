package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearBaseDeDatos {

    public static void main(String[] args)
    {
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:D:/Proyectos/Proyectos_NetBeans/GestionDeFavoritos/src/Archivos/BaseDatos/gestiondeAccesosDirectos.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists Carpeta");
            statement.executeUpdate("create table Carpeta (nombre varchar(300), direccion varchar(300), icono blob,id integer primary key autoincrement)");
            statement.executeUpdate("drop table if exists Juegos");
            statement.executeUpdate("create table Juegos (nombre varchar(300), direccion varchar(300), icono blob,id integer primary key autoincrement)");
            statement.executeUpdate("drop table if exists Otros");
            statement.executeUpdate("create table Otros (nombre varchar(300), direccion varchar(300), icono blob,id integer primary key autoincrement)");
            statement.executeUpdate("drop table if exists Programas");
            statement.executeUpdate("create table Programas (nombre varchar(300), direccion varchar(300), icono blob,id integer primary key autoincrement)");
            statement.executeUpdate("drop table if exists Web");
            statement.executeUpdate("create table Web (nombre varchar(300), direccion varchar(300),id integer primary key autoincrement)");

          /*  statement.executeUpdate("drop table if exists Todo");
            statement.executeUpdate("create table Todo (nombre varchar(300))");

            statement.executeUpdate("drop table if exists Configuracion");
            statement.executeUpdate("create table Configuracion (nombre varchar(300))"); */
            
            statement.executeUpdate("INSERT INTO todo (nombre) VALUES ('Carpeta')");
            statement.executeUpdate("INSERT INTO todo (nombre) VALUES ('Juegos')");
            statement.executeUpdate("INSERT INTO todo (nombre) VALUES ('Otros')");
            statement.executeUpdate("INSERT INTO todo (nombre) VALUES ('Programas')");
            statement.executeUpdate("INSERT INTO todo (nombre) VALUES ('Web')");
            

        } catch (SQLException e)
        {
            Auxiliares.alerta(e.getMessage(), "", "");
        } finally
        {
            try
            {
                if (connection != null)
                {
                    connection.close();
                }
            } catch (SQLException e)
            {
                Auxiliares.alerta(e.getMessage(), "", "");
            }
        }
    }
}
