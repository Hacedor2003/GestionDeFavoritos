/*
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CircleAnimation extends Application {

    private Circle circle;
    private double dx = 5; // velocidad horizontal
    private double dy = 3; // velocidad vertical

    @Override
    public void start(Stage primaryStage)
    {
        // Crear un círculo
        circle = new Circle(50, Color.BLUE);
        circle.setTranslateX(100);
        circle.setTranslateY(100);

        Group root = new Group(circle);
        Scene scene = new Scene(root, 400, 300, Color.WHITE);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Crear un AnimationTimer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now)
            {
                // Actualizar la posición del círculo
                circle.setTranslateX(circle.getTranslateX() + dx);
                circle.setTranslateY(circle.getTranslateY() + dy);

                // Cambiar la dirección si el círculo llega a los bordes de la pantalla
                if (circle.getTranslateX() < circle.getRadius() || circle.getTranslateX() > scene.getWidth() - circle.getRadius())
                {
                    dx = -dx;
                }
                if (circle.getTranslateY() < circle.getRadius() || circle.getTranslateY() > scene.getHeight() - circle.getRadius())
                {
                    dy = -dy;
                }
            }
        };

        // Iniciar la animación
        timer.start();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RectangleAnimation extends Application {

    private Rectangle rectangle;
    private Button stopButton;
    private double width = 50;
    private double height = 50;
    private double dw = 2; // velocidad de cambio de ancho
    private double dh = 2; // velocidad de cambio de altura

    @Override
    public void start(Stage primaryStage)
    {
        // Crear un rectángulo
        rectangle = new Rectangle(100, 100, width, height);
        rectangle.setFill(Color.RED);
        stopButton = new Button("Stop");        

        Group root = new Group(rectangle,stopButton);
        Scene scene = new Scene(root, 400, 300, Color.WHITE);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Crear un AnimationTimer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now)
            {
                // Actualizar el tamaño del rectángulo
                width += dw;
                height += dh;
                rectangle.setWidth(width);
                //rectangle.setHeight(height);

                // Cambiar la dirección si el rectángulo llega a un tamaño máximo o mínimo 
                if (width > 50 || width < 0)
                {
                    dw = -dw;

                }

                if (height > 100 || height < 50)
                {
                    dh = -dh;
                }
            }
        };

        // Iniciar la animación
        timer.start();

        // Agregar evento de acción al botón de parada
        stopButton.setOnAction(event ->
        {
            timer.stop();
            width = 50;
            rectangle.rotateProperty().setValue(40);
        });
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
