//4
package examples.intro;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Scanner;

public class ScannerInput extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Scanner Input Demo");

        VBox vbox = new VBox(10);
        Label text = new Label("Initial String");
        Button startBtn = new Button("Start");
        vbox.getChildren().addAll(text, startBtn);
        primaryStage.setScene(new Scene(vbox, 200, 200));
        primaryStage.show();

        Scanner sc = new Scanner(System.in);
        AnimationTimer a = new AnimationTimer() {
            @Override
            public void handle(long now) {
                String line = sc.nextLine();
                text.setText(line);
            }
        };
//        a.start();
        startBtn.setOnAction(event -> a.start());
    }
}
