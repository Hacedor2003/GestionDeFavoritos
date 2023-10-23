/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Hacedor
 */
public class PanelParaBtnController implements Initializable {

    @FXML
    private Pane PanelParaColocar;
    @FXML
    private Button btnParaCancelar;
    @FXML
    private Label nombreTabla;
    @FXML
    private AnchorPane PanelDondeEstaTodo;
    @FXML
    private Button btnObjetivo;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
     
    }

    @FXML
    private void BtnParaBorrar(ActionEvent event)
    {
        //Logica claseLogica = new Logica();
        //claseLogica.eliminarAccesoDirecto(Integer.parseInt(btn.getId()), nombreTabla.getText());
    }

    public void setContent(Button b)
    {
        btnObjetivo.setText(b.getText());
        btnObjetivo.setStyle(b.getStyle());
        btnObjetivo.setPrefSize(b.getPrefWidth(), b.getPrefHeight());
        btnObjetivo.setOnAction(b.getOnAction());
    }

    public Node getRoot()
    {
        return PanelDondeEstaTodo; // Reemplaza "root" con el nombre de la variable que contiene el nodo raíz del panel
    }

}
