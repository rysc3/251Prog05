package examples.two_pages;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquarePage {
    private final Group SQUARES;

    public SquarePage() {
        SQUARES = new Group();

        Color red = new Color(1, 0, 0, 1);
        Color green = new Color(0, 1, 0, 1);
        Color blue = new Color(0, 0, 1, 1);

        Rectangle rect1 = new Rectangle(100, 100, 50, 50);
        Rectangle rect2 = new Rectangle(200, 100, 50, 50);
        rect2.setFill(red);
        Rectangle rect3 = new Rectangle(100, 200, 50, 50);
        rect3.setFill(green);
        Rectangle rect4 = new Rectangle(200, 200, 50, 50);
        rect4.setFill(blue);

        SQUARES.getChildren().addAll(rect1, rect2, rect3, rect4);
    }

    public void show(Pane root) {
        root.getChildren().add(SQUARES);
    }
}
