package examples.random_pane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.concurrent.ThreadLocalRandom;

public class TooManyTextBoxes extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Too Many Text Boxes!");

        double size = 1000;

        BorderPane root = new BorderPane();
        root.setMinSize(size, size);

        // Start with normal Pane
//        Pane other = new Pane();
        RandomPane other = new RandomPane();
        other.setMinSize(size, size);
        root.setCenter(other);

        TextField mainTextBox = new TextField("replace me");
        root.setTop(mainTextBox);

        SubjectText textBox = new SubjectText(mainTextBox);

        for (int i = 0; i < 100; i++) {
            TextField otherTextBox = new TextField();
            textBox.bindToText(otherTextBox);
//            otherTextBox.setLayoutX(ThreadLocalRandom.current().nextDouble(size));
//            otherTextBox.setLayoutY(ThreadLocalRandom.current().nextDouble(size));
            other.getChildren().add(otherTextBox);
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
