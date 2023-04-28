package examples.two_pages;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TwoPages extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("A Tale of Two Scene Graphs");

        Canvas canvas = new Canvas(800, 800);
        Pane root = new Pane(canvas);

        CirclePage circlePage = new CirclePage();
        SquarePage squarePage = new SquarePage();

        circlePage.show(root);

        Button switchBtn = new Button("Switch");
        //switchBtn.setLayoutX(500);
        switchBtn.setOnAction(event -> {
            root.getChildren().clear();
            squarePage.show(root);
        });

        root.getChildren().add(switchBtn);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
