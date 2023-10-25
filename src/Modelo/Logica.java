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
import javafx.scene.control.Alert;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Hacedor
 */
public class Logica {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrarAccesoDirecto(AccesoDirecto ac, String tabla, int condicion)
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
            } else {
                ps.setInt(3, ac.getId());
            }
            
            ps.execute();

        } catch (SQLException | IOException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
            return false; // Agregar un return false en caso de error
        } finally
        {
            try
            {
                con.close();
            } catch (SQLException e)
            {
                System.out.println(e.toString());
            }
        }
        return true;
    }

    public ArrayList<AccesoDirecto> leerAccesosDirecto(String tabla , int condicion)
    {
        ArrayList<AccesoDirecto> listaAd = new ArrayList<AccesoDirecto>();
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
                if (condicion == 1){
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
            System.out.println(e.toString());
        }
        return listaAd;
    }

    public String buscarAccesoDirecto(String nombre , int condicion)
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
                System.out.println(e.toString());
            } finally
            {
                try
                {
                    con.close();
                } catch (SQLException ex)
                {
                    System.out.println(ex.toString());
                }
            }
        }
        return null;
    }

    public boolean eliminarAccesoDirecto(int id, String tabla)
    {
        String sql = "DELETE FROM " + tabla + " WHERE id = ?";

        try
        {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (Exception e)
        {
            System.out.println(e.toString());
            // Crea una pantalla emergente con el error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Occured");
            alert.setHeaderText("Ooops, algo salió mal!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return false;
        } finally
        {
            try
            {
                con.close();
            } catch (SQLException ex)
            {
                System.out.println(ex.toString());
            }
        }
    }

    /*public boolean modificarAccesoDirecto(AccesoDirecto ad)
    {
        String sql = "UPDATE redessociales SET telegram=?, id=? WHERE id=?";
        try
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, ad.getNombre());
            ps.setString(2, ad.getDireccion());
            ps.setInt(3, ad.getId());
            ps.execute();
            return true;
        } catch (SQLException e)
        {
            System.out.println(e.toString());
            return false;
        } finally
        {
            try
            {
                con.close();
            } catch (SQLException ex)
            {
                System.out.println(ex.toString());
            }
        }
    }*/
    public ArrayList<String> obtenerTablas(int condicion)
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
                if (condicion == 1)
                {
                    if (!tableName.equalsIgnoreCase("web"))
                    {
                        nombresDeBD.add(tableName);
                    }
                } else
                {
                    if (tableName.equalsIgnoreCase("web"))
                    {
                        nombresDeBD.add(tableName);
                    }
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        return nombresDeBD;
    }

}
