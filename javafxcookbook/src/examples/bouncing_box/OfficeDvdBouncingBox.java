package examples.bouncing_box;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class OfficeDvdBouncingBox {
    private final Pane drawing;
    private final Rectangle rect;
    private final StackPane box;
//    private int dx = ThreadLocalRandom.current().nextInt(-6, 6);
//    private int dy = ThreadLocalRandom.current().nextInt(-6, 6);
    private int dx = 6;
    private int dy = 6;

    Color[] colors = new Color[] { Color.RED, Color.GREEN, Color.BLUE };
    private int curColor = 0;

    public OfficeDvdBouncingBox(Pane drawing) {
        this.drawing = drawing;

        rect = new Rectangle();
        rect.widthProperty().bind(drawing.widthProperty().divide(10));
        rect.heightProperty().bind(drawing.heightProperty().divide(10));
        rect.setFill(colors[curColor]);

        Label label = new Label("DVD");
        label.setFont(new Font(25));

        box = new StackPane(rect, label);
        box.setLayoutX(drawing.getMinWidth()/2);
        box.setLayoutY(drawing.getMinHeight()/2);

        this.drawing.getChildren().add(box);
    }

    private void moveRect() {
        int sidesHit = 0;
        // Top side
        if (box.getLayoutY() <= 0) {
            dy *= -1;
            sidesHit++;
        }
        // Bottom side
        else if (box.getLayoutY() >= drawing.getHeight() - box.getHeight()) {
            dy *= -1;
            sidesHit++;
        }

        // Left side
        if (box.getLayoutX() <= 0) {
            dx *= -1;
            sidesHit++;
        }
        // Right side
        else if (box.getLayoutX() >= drawing.getWidth() - box.getWidth()) {
            dx *= -1;
            sidesHit++;
        }

        if (sidesHit > 0) {
            curColor++;
            rect.setFill(colors[curColor%3]);
        }

        if (sidesHit == 2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "HIT TWO SIDES!");
            alert.show();
        }

        box.setLayoutX(box.getLayoutX() + dx);
        box.setLayoutY(box.getLayoutY() + dy);
    }

    public void start() {
        AnimationTimer timer = new AnimationTimer() {
            private Duration lastUpdate = Duration.of(0, ChronoUnit.NANOS);
            @Override
            public void handle(long now) {
                Duration nowDur = Duration.of(now, ChronoUnit.NANOS);
                if (nowDur.minus(lastUpdate).toMillis() > 25) {
                    lastUpdate = nowDur;
                    moveRect();
                }
            }
        };
        timer.start();
    }
}
