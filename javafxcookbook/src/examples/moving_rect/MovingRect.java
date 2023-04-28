package examples.moving_rect;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class MovingRect {
    private final Rectangle rect;

    private final double width;
    private final double height;

    private Animation animation;

    public MovingRect(Pane root, AnimationType defaultAnimationType) {
        width = root.getMinWidth();
        height = root.getMinHeight();

        rect = new Rectangle(width/2, height/2, 30, 30);
        rect.setFill(Color.color(0.3, 0.3, 0.8));
        root.getChildren().add(rect);

        setAnimation(defaultAnimationType);
    }

    public enum AnimationType { L2R, T2B }

    public void setAnimation(AnimationType animationType) {
        if (animationType == AnimationType.L2R) {
            animation = leftToRight();
        }
        else {
            animation = topToBottom();
        }
    }

    public void playAnimation() {
        animation.play();
    }

    private Animation leftToRight() {
        PathTransition pathTransition = new PathTransition();

        Path path = new Path();
        path.getElements().add(new MoveTo(0, height/2));
        path.getElements().add(new LineTo(width, height/2));

        pathTransition.setDuration(Duration.millis(5000));
        pathTransition.setNode(rect);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        pathTransition.setAutoReverse(true);

        return pathTransition;
    }

    private Animation topToBottom() {
        PathTransition pathTransition = new PathTransition();

        Path path = new Path();
        path.getElements().add(new MoveTo(width/2, 0));
        path.getElements().add(new LineTo(width/2, height));

        pathTransition.setDuration(Duration.millis(5000));
        pathTransition.setNode(rect);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        pathTransition.setAutoReverse(true);

        return pathTransition;
    }
}
