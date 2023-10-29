
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PanelSeleccion extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        Label pregunta = new Label("Que desea ver?");
        Button btn = new Button("Aceptar");
        btn.setOnAction((Action) ->
        {
            System.out.println("Prueba");
        });

        // Crear los controles de selección
        RadioButton appRadioButton = new RadioButton("Aplicación");
        RadioButton webRadioButton = new RadioButton("Página web");

        // Agrupar los controles de selección para que solo uno pueda estar seleccionado a la vez
        ToggleGroup toggleGroup = new ToggleGroup();
        appRadioButton.setToggleGroup(toggleGroup);
        webRadioButton.setToggleGroup(toggleGroup);

        // Crear un contenedor para los controles de selección
        VBox selectionBox = new VBox(10, pregunta, appRadioButton, webRadioButton);
        selectionBox.setPadding(new Insets(10));

        // Crear un contenedor para el botón y el panel flotante
        VBox floatingBox = new VBox(10, selectionBox, btn);
        floatingBox.setPadding(new Insets(10));

        // Crear la escena y mostrarla
        Scene scene = new Scene(floatingBox, floatingBox.USE_COMPUTED_SIZE, floatingBox.USE_COMPUTED_SIZE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
