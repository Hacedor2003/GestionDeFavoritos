package Controlador;

import Modelo.Logica;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Hacedor
 */
public class PanelParaBtnController implements Initializable {

    @FXML
    private Button btnParaCancelar;
    @FXML
    private Pane PanelDondeEstaTodo;
    @FXML
    private Button btnObjetivo;
    @FXML
    private Button btnBase;
    @FXML
    private Pane panel;

    private String nombre;
    private String tabla;
    private int indicacion;

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getTabla()
    {
        return tabla;
    }

    public void setTabla(String tabla)
    {
        this.tabla = tabla;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    @FXML
    private void BtnParaBorrar(ActionEvent event)
    {
        Platform.runLater(() ->
        {
            Logica.eliminarAccesoDirecto(nombre, tabla);
            PanelDondeEstaTodo.getChildren().clear();
        });
    }

    public void setIndicacion(int indicacion)
    {
        this.indicacion = indicacion;
    }
    
    public void setContent(Button b)
    {
        btnObjetivo.setText(b.getText());
        btnObjetivo.setStyle(b.getStyle());
        btnObjetivo.setPrefSize(b.getPrefWidth(), b.getPrefHeight());
        btnObjetivo.setOnAction(b.getOnAction());
        btnObjetivo.setGraphic(b.getGraphic());

        if (indicacion == 2){
        ImageView imageView = new ImageView();
        imageView.setImage(new Image("/archivos/icons8-cancel-24.png"));        
        btnParaCancelar.setGraphic(imageView);

        btnParaCancelar.setMinHeight(btnObjetivo.getHeight());           
        } else {
            PanelDondeEstaTodo.getChildren().remove(btnParaCancelar);
            btnObjetivo.setStyle("-fx-background-radius: 8;");   
            PanelDondeEstaTodo.setStyle("-fx-border-color:black;");
        }
    }

    public Node getRoot()
    {
        return btnBase; // Reemplaza "root" con el nombre de la variable que contiene el nodo raíz del panel
    }
}
