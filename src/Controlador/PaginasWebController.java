/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Hacedor
 */
public class PaginasWebController implements Initializable {

    @FXML
    private TabPane PanelApp;
    @FXML
    private Tab tabPanelApp;
    @FXML
    private AnchorPane PanelTabPanel;
    @FXML
    private GridPane GridPaneTabPanelApp;
    @FXML
    private Pane paneGridPane;
    @FXML
    private Button btnGridPaneTab1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
}
