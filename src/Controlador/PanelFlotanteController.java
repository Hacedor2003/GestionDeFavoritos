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
import Modelo.Hablable;
import java.util.Locale;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Labeled;

/**
 * FXML Controller class
 *
 * @author Hacedor
 */
public class PanelFlotanteController implements Initializable, Hablable {

    private static String categoriaApp = resourceBundle.getString("btn_flotante_categoria_app");
    private static String categoriaWeb = resourceBundle.getString("btn_flotante_categoria_web");
    private static String categoriaPer = resourceBundle.getString("btn_flotante_categorias_personalizadas");
    private static String categoriaCar = resourceBundle.getString("btn_flotante_categorias_carpetas");
    private static String categoriaAll = resourceBundle.getString("btn_flotante_categorias_all");
    private static String categoriElegir = resourceBundle.getString("btn_flotante_categorias_elegir");

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
        listaTablas.add(categoriaApp);
        listaTablas.add(categoriaWeb);
        listaTablas.add(categoriaPer);
        listaTablas.add(categoriaCar);
        listaTablas.add(categoriaAll);
        opcion.getItems().addAll(listaTablas);
        opcion.setValue(categoriElegir);
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

    @Override
    public void cambiarIdioma(String idioma)
    {
        ResourceBundle font = ResourceBundle.getBundle("labels", new Locale(idioma));

        // Recorrer todos los nodos de la escena y actualizar las cadenas de texto
        updateNodeLanguage(panelBase, font);
    }

    @Override
    public void updateNodeLanguage(Node node, ResourceBundle bundle)
    {
        if (node instanceof Parent)
        {
            for (Node child : ((Parent) node).getChildrenUnmodifiable())
            {
                updateNodeLanguage(child, bundle);
            }
        }

        if (node instanceof Labeled)
        {
            Labeled labeled = (Labeled) node;
            String key = labeled.getText();
            if (key != null && !key.isEmpty())
            {
                categoriaApp = resourceBundle.getString("btn_flotante_categoria_app");
                categoriaWeb = resourceBundle.getString("btn_flotante_categoria_web");
                categoriaPer = resourceBundle.getString("btn_flotante_categorias_personalizadas");
                categoriaCar = resourceBundle.getString("btn_flotante_categorias_carpetas");
                categoriaAll = resourceBundle.getString("btn_flotante_categorias_all");
                categoriElegir = resourceBundle.getString("btn_flotante_categorias_elegir");
            }

        }
    }

}
