/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.awt.image.BufferedImage;

/**
 *
 * @author Hacedor
 */
public class AccesoDirecto {

    private String nombre;
    private String direccion;
    private BufferedImage icon;
    private int id;

    public AccesoDirecto()
    {
    }

    public AccesoDirecto(String nombre, String direccion)
    {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        if (nombre.contains("."))
        {
            int index = nombre.lastIndexOf(".");
            String newStr = nombre.substring(0, index);
            this.nombre = newStr;
        } else
        {
            this.nombre = nombre;
        }
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public BufferedImage getIcon()
    {
        return icon;
    }

    public void setIcon(BufferedImage icon)
    {
        this.icon = icon;
    }

}
