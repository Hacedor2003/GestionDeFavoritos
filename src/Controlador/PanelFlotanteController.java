/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.botonFlotante;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static Modelo.Auxiliares.resourceBundle;

/**
 * FXML Controller class
 *
 * @author Hacedor
 */
public class PanelFlotanteController implements Initializable {

    @FXML
    private AnchorPane panelBase;
    @FXML
    private Button idBtnEnviar;

    private int indicador;
    private Stage stage;
    @FXML
    private ChoiceBox<String> opcion;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        stage = new Stage();

        ArrayList<String> listaTablas = new ArrayList<>();
        listaTablas.add(resourceBundle.getString("btn_flotante_categoria_app"));
        listaTablas.add(resourceBundle.getString("btn_flotante_categoria_web"));
        listaTablas.add(resourceBundle.getString("btn_flotante_categorias_personalizadas"));
        listaTablas.add(resourceBundle.getString("btn_flotante_categorias_carpetas"));
        listaTablas.add(resourceBundle.getString("btn_flotante_categorias_all"));
        opcion.getItems().addAll(listaTablas);
        opcion.setValue(resourceBundle.getString("btn_flotante_categorias_elegir"));  
    }

    @FXML
    private void btnEnviar(ActionEvent event)
    {
        botonFlotante bf = new botonFlotante();
        bf.setIndicador(compararOpciones());
        bf.start(stage);
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    private int compararOpciones()
    {
        String selection = opcion.getValue();
        int numero = 6;

        switch (selection)
        {
            case "Aplicaciones" ->
            {
                numero = 1;
            }
            case "Web" ->
            {
                numero = 2;
            }
            case "Personalizadas" ->
            {
                numero = 3;
            }
            case "Carpetas" ->
            {
                numero = 4;
            }
            case "Todo" ->
            {
                numero = 5;
            }
        }

        return numero;
    }

}
