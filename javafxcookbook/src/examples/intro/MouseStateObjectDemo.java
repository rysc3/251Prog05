//7
package examples.intro;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.Duration;

public class MouseStateObjectDemo extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Mouse Object Demo");
        Canvas canvas = new Canvas(700,500);

        MouseState info = new MouseState();
        canvas.setOnMouseMoved(info.getMouseHandler());
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();

        // Print out mouse location every second
        AnimationTimer a = new AnimationTimer() {
            private long nextTime = 0;

            @Override
            public void handle(long now) {
                if(now > nextTime) {
                    System.out.println("mouse at " + info.getX() + " " + info.getY());
                    nextTime = now + Duration.ofSeconds(1).toNanos();
                }
            }
        };
        a.start();
    }
}
