//8
package examples.intro;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.time.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*
         * Setup
         */
        double width  = 800;
        double height = 800;
        primaryStage.setTitle("Hello JavaFX!");

        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        String versions = "Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".";

        /*
         * Setup a button to print your versions
         */
        Button btn = new Button();
        Alert a = new Alert(AlertType.INFORMATION);
        btn.setText("Print Versions");
        btn.setLayoutX(300);
        btn.setLayoutY(400);
        btn.setOnAction(event -> {
            a.setContentText(versions);
            a.showAndWait();
        });

        /*
         * Draw on the canvas
         * It's dangerous to go alone take this
         */
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill (Color.GOLD);
        gc.fillPolygon(
                new double[] {150, 300, 450},
                new double[] {150, 0, 150},
                3
        );
        gc.fillPolygon(
                new double[] {0, 150, 300},
                new double[] {300, 150, 300},
                3
        );
        gc.fillPolygon(
                new double[] {300, 450, 600},
                new double[] {300, 150, 300},
                3
        );
        /*
         * Setup mouse events
         */
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println("pressed " + event.getX() + " " + event.getY());
        });

        MouseState ms = new MouseState();
        canvas.setOnMouseMoved(ms.getMouseHandler());

        /*
         * Setup the root of the scene graph
         */
        Label text = new Label (" Initial String ");
        Pane root = new Pane(canvas, text);
        root.getChildren().add(btn);

        /*
         * Set the scene
         */
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

        /*
         * Animation timer setup
         */
        AnimationTimer at = new AnimationTimer() {
            private long startTime = - 1;
            private long nextTime = 0;
            @Override
            public void handle(long now) {
                if (startTime < 0) startTime = now;

                Duration elapsed = Duration.ofNanos(now - startTime);
                long minutes = elapsed.toMinutes();
                long seconds = elapsed.toSeconds() - (minutes * 60);
                String str = String.format("elapsed time %2d:%02d",
                        minutes, seconds);
                text.setText(str);

                if (now > nextTime) {
                    System.out.println ("mouse at " + ms.getX() + " " + ms.getY());
                    nextTime = now + Duration.ofSeconds(1).toNanos();
                }
            }
        };
        at.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
