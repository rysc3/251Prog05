//5
package examples.intro;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.Duration;

public class TimerDisplay extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Time Display Demo");
        Label text = new Label();
        primaryStage.setScene(new Scene(text, 200, 100));
        primaryStage.show();

        AnimationTimer a = new AnimationTimer() {
            private long startTime = -1;

            @Override
            public void handle(long now) {
                if(startTime < 0) {
                    startTime = now;
                }

                Duration elapsed = Duration.ofNanos(now - startTime);
                long minutes = elapsed.toMinutes();
                long seconds = elapsed.toSeconds() - (minutes * 60);
                String str = String.format("elapsed time %2d:%02d",
                        minutes, seconds);
                text.setText(str);
            }
        };
        a.start();
    }
}
