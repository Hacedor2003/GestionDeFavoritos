package Modelo;

import static Controlador.CarpetasController.btnAnadirCarpeta;
import static Modelo.Auxiliares.resourceBundle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CrearTabla extends Application {

    //Inicializo
    private RadioButton appRadioButton;
    private RadioButton webRadioButton;
    private RadioButton carpetaRadioButton;
    private AccesoDirecto ad;
    private String tabla;

    public void start(Stage primaryStage)
    {
        // Crear la ventana principal
        primaryStage.setTitle(resourceBundle.getString("categoria_personalizada_titulo_ventana"));
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);

        // Agregar los elementos a la ventana
        Label tipoLabel = new Label(resourceBundle.getString("categoria_personalizada_tipo"));
        grid.add(tipoLabel, 0, 1);
        ToggleGroup tipoToggleGroup = new ToggleGroup();
        appRadioButton = new RadioButton(resourceBundle.getString("categoria_personalizada_tipo1"));
        appRadioButton.setToggleGroup(tipoToggleGroup);
        webRadioButton = new RadioButton(resourceBundle.getString("categoria_personalizada_tipo2"));
        webRadioButton.setToggleGroup(tipoToggleGroup);
        carpetaRadioButton = new RadioButton(resourceBundle.getString("categoria_personalizada_tipo3"));
        carpetaRadioButton.setToggleGroup(tipoToggleGroup);
        grid.add(appRadioButton, 1, 1);
        grid.add(webRadioButton, 2, 1);
        grid.add(carpetaRadioButton, 3, 1);

        Button crearTablaButton = new Button(resourceBundle.getString("categoria_personalizada_btn_crear"));
        grid.add(crearTablaButton, 1, 2);

        // Agregar el evento al botón de crear tabla
        crearTablaButton.setOnAction(event ->
        {
            if (appRadioButton.isSelected())
            {
                Auxiliares.btnAnadirApp();
            } else if (webRadioButton.isSelected())
            {
                Auxiliares.btnAnadirWeb();
            } else if (carpetaRadioButton.isSelected())
            {
                btnAnadirCarpeta();
            } else
            {
                Auxiliares.alerta(resourceBundle.getString("categoria_personalizada_btn_crear_error"), "", "");
            }
        });

        primaryStage.show();
    }

    public AccesoDirecto getAd()
    {
        return ad;
    }

    public void setAd(AccesoDirecto ad)
    {
        this.ad = ad;
    }

    public String getTabla()
    {
        return tabla;
    }

    public void setTabla(String tabla)
    {
        this.tabla = tabla;
    }

}
