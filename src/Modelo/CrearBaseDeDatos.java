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
            connection = DriverManager.getConnection("jdbc:sqlite:D:/Proyectos/Proyectos_NetBeans/GestionDeFavoritos/src/Archivos/gestiondeAccesosDirectos.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists carpeta");
            statement.executeUpdate("create table carpeta (nombre varchar(300), direccion varchar(300), icono blob,id integer primary key autoincrement)");
            statement.executeUpdate("drop table if exists juegos");
            statement.executeUpdate("create table juegos (nombre varchar(300), direccion varchar(300), icono blob,id integer primary key autoincrement)");
            statement.executeUpdate("drop table if exists otros");
            statement.executeUpdate("create table otros (nombre varchar(300), direccion varchar(300), icono blob,id integer primary key autoincrement)");
            statement.executeUpdate("drop table if exists programas");
            statement.executeUpdate("create table programas (nombre varchar(300), direccion varchar(300), icono blob,id integer primary key autoincrement)");
            statement.executeUpdate("drop table if exists web");
            statement.executeUpdate("create table web (nombre varchar(300), direccion varchar(300),id integer primary key autoincrement)");

            statement.executeUpdate("drop table if exists todo");
            statement.executeUpdate("create table todo (nombre varchar(300))");

            statement.executeUpdate("drop table if exists configuracion");
            statement.executeUpdate("create table configuracion (nombre varchar(300))");
            
            statement.executeUpdate("INSERT INTO todo (nombre) VALUES ('carpeta')");
            statement.executeUpdate("INSERT INTO todo (nombre) VALUES ('juegos')");
            statement.executeUpdate("INSERT INTO todo (nombre) VALUES ('otros')");
            statement.executeUpdate("INSERT INTO todo (nombre) VALUES ('programas')");
            statement.executeUpdate("INSERT INTO todo (nombre) VALUES ('web')");
            

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
