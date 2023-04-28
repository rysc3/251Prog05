package examples.recursiveH;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class RecursiveH {
    private static ThreadLocalRandom rand = ThreadLocalRandom.current();
    public static void draw(Consumer<Line> addLine, boolean randomColor,
                            double length, int depth,
                            double centerX, double centerY) {
        createHCenteredAt(addLine, randomColor, length, depth, centerX, centerY);
    }

    private enum Orientation { HORIZONTAL, VERTICAL }

    private static Line lineCenteredAt(Orientation orientation, boolean randomColor,
                                       double halfLen, double centerX, double centerY) {
        double offsetX = orientation == Orientation.HORIZONTAL ? halfLen : 0;
        double offsetY = orientation == Orientation.VERTICAL ? halfLen : 0;

        double newX1 = centerX - offsetX;
        double newY1 = centerY - offsetY;

        double newX2 = centerX + offsetX;
        double newY2 = centerY + offsetY;

        Line line = new Line(newX1, newY1, newX2, newY2);
        if (randomColor) {
            Color color = new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1);
            line.setStroke(color);
        }
        else {
            line.setStroke(Color.BLACK);
        }

        return line;
    }

    private static void createHCenteredAt(Consumer<Line> addLine, boolean randomColor,
                                          double length, int depth,
                                          double centerX, double centerY) {
        if (depth > 0) {
            double halfLen = length / 2;

            Line leftLine   = lineCenteredAt(
                    Orientation.VERTICAL, randomColor,
                    halfLen, centerX - halfLen, centerY);
            Line middleLine = lineCenteredAt(
                    Orientation.HORIZONTAL, randomColor,
                    halfLen, centerX, centerY);
            Line rightLine  = lineCenteredAt(
                    Orientation.VERTICAL, randomColor,
                    halfLen, centerX + halfLen, centerY);


            addLine.accept(leftLine);
            addLine.accept(middleLine);
            addLine.accept(rightLine);

            createHCenteredAt(
                    addLine, randomColor,
                    halfLen, depth - 1,
                    centerX - halfLen, centerY - halfLen);
            createHCenteredAt(
                    addLine, randomColor,
                    halfLen, depth - 1,
                    centerX - halfLen, centerY + halfLen);
            createHCenteredAt(
                    addLine, randomColor,
                    halfLen, depth - 1,
                    centerX + halfLen, centerY - halfLen);
            createHCenteredAt(
                    addLine, randomColor,
                    halfLen, depth - 1,
                    centerX + halfLen, centerY + halfLen);
        }
    }
}
