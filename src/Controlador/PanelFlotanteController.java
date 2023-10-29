/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.botonFlotante;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hacedor
 */
public class PanelFlotanteController implements Initializable {

    @FXML
    private AnchorPane panelBase;
    @FXML
    private RadioButton radioApp;
    @FXML
    private RadioButton radioWeb;
    @FXML
    private Button idBtnEnviar;

    private int indicador;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        stage = new Stage();
        
        ToggleGroup toggleGroup = new ToggleGroup();
        radioApp.setToggleGroup(toggleGroup);
        radioWeb.setToggleGroup(toggleGroup);
    }

    @FXML
    private void onAplicaciones(ActionEvent event)
    {
        indicador = 1;
    }

    @FXML
    private void onWeb(ActionEvent event)
    {
        indicador = 2;
    }

    @FXML
    private void btnEnviar(ActionEvent event)
    {
        botonFlotante bf = new botonFlotante();
        bf.setIndicador(indicador);
        bf.start(stage);
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

}
