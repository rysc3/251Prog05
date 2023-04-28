//3
package examples.intro;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MouseEventDemo extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Mouse Object Demo");
        Canvas canvas = new Canvas(700,500);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println("pressed "
                    + event.getX() + " " + event.getY());
        });

        canvas.setOnMouseMoved(event -> {
            System.out.println("moved "
                    + event.getX() + " " + event.getY());
        });

        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
    }
}
