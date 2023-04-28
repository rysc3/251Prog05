//2
package examples.intro;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CanvasDemo extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Canvas Drawing Demo");

        Canvas canvas = new Canvas(600,600);

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

        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
    }
}