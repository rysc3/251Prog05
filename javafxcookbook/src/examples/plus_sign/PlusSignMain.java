package examples.plus_sign;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This JavaFX project shows how you can move a
 * group of shapes from one pane to another.
 * It also showcases how you can pass a reference
 * to the control on the screen and update it from
 * a button click event.
 */
public class PlusSignMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Moving Plus Sign");

        // Label to track the number of clicks
        Label numClicks = new Label();
        numClicks.setFont(new Font(24));

        // HBox to center the label on the screen
        HBox numClicksHB = new HBox(numClicks);
        numClicksHB.setAlignment(Pos.CENTER);

        // Pane on the left side of the border pane
        Pane leftPane = new Pane();
        // Pane on the right side of the border pane
        Pane rightPane = new Pane();

        PlusSign ps = new PlusSign(200, 100, numClicks, leftPane, rightPane);

        // Border pane to organize controls on the screen
        BorderPane root = new BorderPane();
        root.setTop(numClicksHB);
        root.setLeft(leftPane);
        root.setRight(rightPane);

        // Standard boilerplate
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
