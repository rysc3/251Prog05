package examples.hardcoded_ca;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Rule 30 Elementary CA");
        primaryStage.setWidth(880);
        primaryStage.setHeight(880);
        // Create grid to hold visual representation of CA
        final GridPane grid = new GridPane();
        // Set the scene
        final Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
        // Create initial generation
        final List<Generation.Cell> initGenList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            if (i != 5) {
                initGenList.add(Generation.Cell.DEAD);
            }
            else {
                initGenList.add(Generation.Cell.ALIVE);
            }
        }
        final Generation initGen = new Generation(initGenList);
        // Create animation timer to run the animation
        final AnimationTimer timer = new AnimationTimer() {
            private int curGenIndex = 0;
            private Generation curGen = initGen;
            private final int genSize = curGen.size();
            private long prevUpdate = 0;
            @Override
            public void handle(final long now) {
                if (now - prevUpdate >= TimeUnit.SECONDS.toNanos(1)) {
                    // Create visual representation of current generation
                    int curCol = 0;
                    for (final Neighborhood neighborhood : curGen) {
                        final Rectangle rect = new Rectangle();
                        // Set fill based on state of middle cell
                        if (neighborhood.middle() == Generation.Cell.ALIVE) {
                            rect.setFill(Color.BLACK);
                        } else {
                            rect.setFill(Color.WHITE);
                        }
                        // Bind the rects size according to the size of the window
                        rect.widthProperty().bind(primaryStage.widthProperty().divide(genSize));
                        rect.heightProperty().bind(rect.widthProperty());
                        grid.add(rect, curCol, curGenIndex);
                        // Increment the column
                        curCol++;
                    }
                    // Evolve next gen
                    curGen = curGen.evolve();
                    // Increment the row
                    curGenIndex++;
                    // Update previous update time
                    prevUpdate = now;
                }
            }
        };
        timer.start();
    }
}
