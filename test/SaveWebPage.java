import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
//import org.apache.commons.io.FileUtils;

public class SaveWebPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField urlField = new TextField();
        Button saveButton = new Button("Guardar");
        Button cancelButton = new Button("Cancelar");

        saveButton.setOnAction(e -> {
            String url = urlField.getText();
            try {
                URL website = new URL(url);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar página web");
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    //FileUtils.copyURLToFile(website, file);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        cancelButton.setOnAction(e -> {
            primaryStage.close();
        });

        VBox root = new VBox(10, urlField, saveButton, cancelButton);
        Scene scene = new Scene(root, 300, 150);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}