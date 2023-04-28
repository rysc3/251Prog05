package examples.recursiveH;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class RecursiveLinesMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Recursive Lines");

        Pane root = new Pane();
        root.setMinWidth(1000);
        root.setMinHeight(1000);

        double spacing = 10;

        VBox controls = new VBox(spacing);
        root.getChildren().add(controls);

        HBox lengthHBox = new HBox(spacing);
        Label lengthLabel = new Label("Length:");
        TextField lengthTF = new TextField();
        lengthHBox.getChildren().addAll(lengthLabel, lengthTF);

        HBox depthHBox = new HBox(spacing);
        Label depthLabel = new Label("Depth");
        TextField depthTF = new TextField();
        depthHBox.getChildren().addAll(depthLabel, depthTF);

        HBox multiThreadHBox = new HBox(spacing);
        Label multiThreadLbl = new Label("Multi Thread?");
        CheckBox multiThread = new CheckBox();
        multiThreadHBox.getChildren().addAll(multiThreadLbl, multiThread);

        HBox randomColorHBox = new HBox(spacing);
        Label randomColorLbl = new Label("Random Color?");
        CheckBox randomColor = new CheckBox();
        randomColorHBox.getChildren().addAll(randomColorLbl, randomColor);

        Button submit = new Button("Submit");

        controls.getChildren().addAll(lengthHBox, depthHBox, multiThreadHBox, randomColorHBox, submit);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Known Bug: If the user hits submit again while the multi thread is still running
        // it will continue to draw to the screen.
        submit.setOnAction(event -> {
            try {
                double length = Double.parseDouble(lengthTF.getText());
                int depth = Integer.parseInt(depthTF.getText());

                root.getChildren().removeIf(node -> node instanceof Line);

                if (multiThread.isSelected()) {
                    BlockingQueue<DrawLinesLanguage> lines = new LinkedBlockingDeque<>();

                    Thread thread = new Thread(() -> {
                        RecursiveH.draw(
                                line -> {
                                    try {
                                        lines.put(new DrawLine(line));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }, randomColor.isSelected(),
                                length, depth,
                                root.getMinWidth() / 2, root.getMinHeight() / 2
                        );
                        try {
                            lines.put(new Stop());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    thread.start();

                    AnimationTimer timer = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            DrawLinesLanguage dll = null;
                            try {
                                dll = Objects.requireNonNull(lines.take());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (dll instanceof DrawLine drawLine) {
                                root.getChildren().add(drawLine.line());
                            }
                            else {
                                System.out.print(Thread.currentThread().getName());
                                System.out.println(": Stopping...");
                                this.stop();
                            }
                        }
                    };
                    timer.start();
                }
                else {
                    RecursiveH.draw(
                            line -> root.getChildren().add(line), randomColor.isSelected(),
                            length, depth,
                            root.getMinWidth() / 2, root.getMinHeight() / 2
                    );
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Either length: " + lengthTF.getText() +
                        ", depth: " + depthTF.getText() + " is not valid");
            }
        });
    }
}
