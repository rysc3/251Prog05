package examples.two_pages;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CirclePage {
    private final Group CIRCLES;

    public CirclePage() {
        CIRCLES = new Group();

        Color red = new Color(1, 0, 0, 1);
        Color green = new Color(0, 1, 0, 1);
        Color blue = new Color(0, 0, 1, 1);

        Circle circle1 = new Circle(100, 100, 50);
        Circle circle2 = new Circle(200, 100, 50, red);
        Circle circle3 = new Circle(100, 200, 50, green);
        Circle circle4 = new Circle(200, 200, 50, blue);

        CIRCLES.getChildren().addAll(circle1, circle2, circle3, circle4);
    }

    public void show(Pane root) {
        root.getChildren().add(CIRCLES);
    }
}
