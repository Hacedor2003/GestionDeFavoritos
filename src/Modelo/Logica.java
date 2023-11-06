/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Logica {

    static Conexion cn = new Conexion();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;

    //Metodo para registrar el acceso directo en un tabla de la base de datos
    public static boolean registrarAccesoDirecto(AccesoDirecto ac, String tabla, int condicion)
    {
        String sql;
        if (condicion == 1)
        {
            sql = "INSERT INTO " + tabla + " (nombre,direccion,icono,id)VALUES(?,?,?,?)";
        } else
        {
            sql = "INSERT INTO " + tabla + " (nombre,direccion,id)VALUES(?,?,?)";
        }
        try
        {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, ac.getNombre());
            ps.setString(2, ac.getDireccion());
            if (condicion == 1)
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(ac.getIcon(), "png", baos);
                byte[] imageBytes = baos.toByteArray();
                ps.setBytes(3, imageBytes);
                ps.setInt(4, ac.getId());
            } else
            {
                ps.setInt(3, ac.getId());
            }

            ps.execute();

        } catch (SQLException | IOException e)
        {
            Auxiliares.alerta(e.getMessage(), "Clase Logica", "registrarAccesoDirecto");
            return false; // Agregar un return false en caso de error
        } finally
        {
            try
            {
                con.close();
            } catch (SQLException e)
            {
                Auxiliares.alerta(e.getMessage(), "Clase Logica", "registrarAccesoDirecto");
            }
        }
        return true;
    }

    //Metodo que devuelve una lista de los accesos directos de una tabla
    public static ArrayList<AccesoDirecto> leerAccesosDirecto(String tabla, int condicion)
    {
        ArrayList<AccesoDirecto> listaAd = new ArrayList<>();
        String sql = "SELECT * FROM " + tabla;

        try
        {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next())
            {
                AccesoDirecto cd = new AccesoDirecto();
                BufferedImage buffImg = null;
                cd.setNombre(rs.getString("nombre"));
                cd.setDireccion(rs.getString("direccion"));
                if (condicion != 2)
                {
                    byte[] imageBytes = rs.getBytes("icono");
                    // Crear un objeto BufferedImage a partir de los bytes
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    cd.setIcon(image);
                }
                cd.setId(rs.getInt("id"));
                listaAd.add(cd);
            }
            rs.close();
        } catch (SQLException | IOException e)
        {
            // Auxiliares.alerta(e.getMessage(), "Clase Logica", "leerAccesosDirecto");
        }
        return listaAd;
    }

    //metodo que busca un acceso directo en la base de datos
    public static String buscarAccesoDirecto(String nombre, int condicion)
    {
        ArrayList<String> tabla = obtenerTablas(condicion);
        for (String s : tabla)
        {
            String sql = "SELECT * FROM " + s + " WHERE nombre = ?";
            try
            {
                con = cn.getConnection();
                ps = con.prepareStatement(sql);
                ps.setString(1, nombre);
                rs = ps.executeQuery();
                if (rs.next())
                {
                    return s;
                }
                rs.close();
            } catch (SQLException e)
            {
                //Auxiliares.alerta(e.getMessage(), "Clase Logica", "buscarAccesoDirecto");
            } finally
            {
                try
                {
                    con.close();
                } catch (SQLException ex)
                {
                    //Auxiliares.alerta(ex.getMessage(), "Clase Logica", "buscarAccesoDirecto");
                }
            }
        }
        return null;
    }

    //Metodo que elimina el acceso directo de la tabla
    public static boolean eliminarAccesoDirecto(String nombre, String tabla)
    {
        String sql = "DELETE FROM " + tabla + " WHERE nombre = ?";

        try
        {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.execute();
            return true;
        } catch (SQLException e)
        {
            Auxiliares.alerta(e.getMessage(), "Clase Logica", "eliminarAccesoDirecto");
            return false;
        } finally
        {
            try
            {
                con.close();
            } catch (SQLException ex)
            {
                Auxiliares.alerta(ex.getMessage(), "Clase Logica", "eliminarAccesoDirecto");
            }
        }
    }

    //metodo que elimina una categoria
    public static boolean eliminarCategoria(String tabla)
    {
        String sql = "DROP TABLE `gestiondeaccesosdirectos`.`" + tabla + "`";

        try
        {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.execute();
            return true;
        } catch (SQLException e)
        {
            Auxiliares.alerta(e.getMessage(), "Clase Logica", "eliminarCategoria");
            return false;
        } finally
        {
            try
            {
                con.close();
            } catch (SQLException ex)
            {
                Auxiliares.alerta(ex.getMessage(), "Clase Logica", "eliminarCategoria");
            }
        }
    }

    //metodo para obtener tablas
    public static ArrayList<String> obtenerTablas(int condicion)
    {
        ArrayList<String> nombresDeBD = new ArrayList<>();
        try (Connection conn = cn.getConnection())
        {
            DatabaseMetaData metaData = conn.getMetaData();
            String[] types =
            {
                "TABLE"
            };
            ResultSet tables = metaData.getTables("gestiondeaccesosdirectos", null, "%", types);
            while (tables.next())
            {
                String tableName = tables.getString("TABLE_NAME");
                switch (condicion)
                {
                    case 1 ->
                    {
                        if (tableName.equalsIgnoreCase("Programas") || tableName.equalsIgnoreCase("Otros") || tableName.equalsIgnoreCase("Juegos"))
                        {
                            nombresDeBD.add(tableName);
                        }
                    }
                    case 2 ->
                    {
                        if (tableName.equalsIgnoreCase("Web"))
                        {
                            nombresDeBD.add(tableName);
                        }
                    }
                    case 3 ->
                    {
                        if (tableName.equalsIgnoreCase("configuracion"))
                        {
                            String sql = "SELECT * FROM " + tableName;
                            ps = con.prepareStatement(sql);
                            rs = ps.executeQuery();
                            while (rs.next())
                            {
                                nombresDeBD.add(rs.getString("nombre"));
                            }
                        }
                    }
                    case 4 ->
                    {
                        if (tableName.equalsIgnoreCase("Carpeta"))
                        {
                            nombresDeBD.add(tableName);
                        }
                    }
                    case 5 ->
                    {
                        if (tableName.equalsIgnoreCase("Todo"))
                        {
                            String sql = "SELECT * FROM " + tableName;
                            ps = con.prepareStatement(sql);
                            rs = ps.executeQuery();
                            while (rs.next())
                            {
                                nombresDeBD.add(rs.getString("nombre"));
                            }
                        }
                    }

                    default ->
                        nombresDeBD.add(tableName);
                }
            }
        } catch (SQLException e)
        {
            //Auxiliares.alerta(e.getMessage(), "Clase Logica", "obtenerTablas");
        }
        return nombresDeBD;
    }

}
