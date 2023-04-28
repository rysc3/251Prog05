package examples.plus_sign;

import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class represents two rectangles that arranged into
 * a plus sign. When clicked they switch from one pane to
 * another. They also keep track of how many times they have
 * been clicked.
 */
public class PlusSign {
    // Group that holds the two rectangles
    private final Group shapes;
    // Label that displays the number of clicks on the screen
    private final Label numClicksLabel;
    // Pane on the left side of the screen
    private final Pane leftPane;
    // Pane on the right side of the screen
    private final Pane rightPane;

    // Keeps track of the number of times the plus has
    // been clicked
    private int numClicks = 0;
    // Keeps track of which pane the plus sign is in
    private boolean inLeft = true;

    public PlusSign(double width,
                    double height,
                    Label numClicksLabel,
                    Pane leftPane,
                    Pane rightPane) {
        shapes = makeRects(width, height);

        this.numClicksLabel = numClicksLabel;
        setNumClicks();

        this.leftPane = leftPane;
        this.rightPane = rightPane;

        this.leftPane.getChildren().add(shapes);
    }

    // Updates the number of clicks label and increments it
    private void setNumClicks() {
        numClicksLabel.setText(Integer.toString(numClicks));
        numClicks += 1;
    }

    /**
     * Calls setNumClicks then moves the plus sign group from the left
     * pane to the right pane
     * @param event event from the on click event
     */
    private void addToNumClicksAndMove(Event event) {
        setNumClicks();

        if (inLeft) {
//            leftPane.getChildren().clear();
            rightPane.getChildren().add(shapes);
        }
        else {
//            rightPane.getChildren().remove(shapes);
            leftPane.getChildren().add(shapes);
        }

        inLeft = !inLeft;
    }

    /**
     * Creates two rectangles that when juxtaposed
     * form a plus sign (+)
     * @param width width of the plus sign
     * @param height height of the plus sign
     */
    private Group makeRects(double width, double height) {
        Rectangle horizontal = new Rectangle(width, height, Color.RED);
        Rectangle vertical = new Rectangle(height, width, Color.BLUE);

        vertical.setX(horizontal.getX() + (width / 2) - (height / 2));
        vertical.setY(horizontal.getY() - (width / 2) + (height / 2));

        horizontal.setOnMouseClicked(event -> addToNumClicksAndMove(event));
        vertical.setOnMouseClicked(this::addToNumClicksAndMove);

        return new Group(horizontal, vertical);
    }
}
