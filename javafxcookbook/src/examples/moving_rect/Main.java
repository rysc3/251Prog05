package examples.moving_rect;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Moving Rectangle");

        MovingRect.AnimationType defaultAnimationType = MovingRect.AnimationType.L2R;

        ComboBox<MovingRect.AnimationType> animationTypeComboBox = new ComboBox<>();
        animationTypeComboBox.getItems().setAll(MovingRect.AnimationType.values());
        animationTypeComboBox.setValue(defaultAnimationType);

        double size = 800;

        Pane drawing = new Pane();
        drawing.setMinWidth(size);
        drawing.setMinHeight(size);

        drawing.getChildren().add(animationTypeComboBox);

        MovingRect movingRect = new MovingRect(drawing, defaultAnimationType);
        movingRect.playAnimation();

        Scene scene = new Scene(drawing);
        primaryStage.setScene(scene);
        primaryStage.show();

        animationTypeComboBox.setOnAction(event -> {
            movingRect.setAnimation(animationTypeComboBox.getValue());
            movingRect.playAnimation();
        });
    }
}
