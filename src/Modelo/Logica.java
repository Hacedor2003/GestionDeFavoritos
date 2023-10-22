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

    public boolean registrarAccesoDirecto(AccesoDirecto ac, String tabla)
    {
        String sql = "INSERT INTO " + tabla + " (nombre,direccion,icono,id)VALUES(?,?,?,?)";
        //String sql = "INSERT INTO otros (nombre,direccion,icono,id)VALUES(?,?,?,?)";
        try
        {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, ac.getNombre());
            ps.setString(2, ac.getDireccion());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(ac.getIcon(), "png", baos);
            byte[] imageBytes = baos.toByteArray();
            ps.setBytes(3, imageBytes);
            ps.setInt(4, ac.getId());
            ps.execute();

        }
        catch (SQLException | IOException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
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

    public ArrayList<AccesoDirecto> leerAccesosDirecto(String tabla)
    {
        ArrayList<AccesoDirecto> listaAd = new ArrayList<AccesoDirecto>();
        String sql = "SELECT * FROM " + tabla;
        //String sql = "SELECT * FROM otros";

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
                byte[] imageBytes = rs.getBytes("icono");
                // Crear un objeto BufferedImage a partir de los bytes
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                cd.setIcon(image);
                listaAd.add(cd);
            }
            rs.close();
        } catch (SQLException | IOException e)
        {
            System.out.println(e.toString());
        }
        return listaAd;
    }

    public boolean eliminarAccesoDirecto(int id)
    {
        String sql = "DELETE FROM redessociales WHERE id = ?";

        try
        {
            ps = con.prepareStatement(sql);
            ps.setInt(3, id);
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

    }

    public boolean modificarAccesoDirecto(AccesoDirecto ad)
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
    }

    public ArrayList<String> obtenerTablas()
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
                nombresDeBD.add(tables.getString("TABLE_NAME"));
            }
        } catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        return nombresDeBD;
    }
}
