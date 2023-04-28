package examples.bouncing_box;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OfficeDvdBouncingBoxMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Office DVD Bouncing Box");

        double size = 800;

        Pane drawing = new Pane();
        drawing.setStyle("-fx-background-color: Black");
        drawing.setMinWidth(size);
        drawing.setMinHeight(size);

        OfficeDvdBouncingBox box = new OfficeDvdBouncingBox(drawing);

        Scene scene = new Scene(drawing, size, size);
        primaryStage.setScene(scene);
        primaryStage.show();

        box.start();
    }
}
