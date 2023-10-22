/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javafx.geometry.Insets;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Hacedor
 */
public class TablePane {

    public TabPane crearTablePane()
    {
        TabPane jTabbedPane;

        jTabbedPane = new TabPane();

        //jTabbedPane.setBackground(new javafx.scene.paint.Color(255, 204, 0, 1));
        jTabbedPane.setCursor(javafx.scene.Cursor.DEFAULT);
        jTabbedPane.setOpaqueInsets(Insets.EMPTY);

        return jTabbedPane;
    }

    public Pane crearPanel()
    {
        Pane jPanel;
        jPanel = new GridPane();

        //jPanel.setBackground(new javafx.scene.paint.Color(0, 0, 0, 1));
        jPanel.setBorder(new javafx.scene.layout.Border(new javafx.scene.layout.BorderStroke(javafx.scene.paint.Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        jPanel.setPrefSize(400, 400);
        jPanel.setPadding(new Insets(5));
        //jPanel.getChildren().addAll(/*Agregue aquí los nodos que desee*/);

        return jPanel;
    }

}
