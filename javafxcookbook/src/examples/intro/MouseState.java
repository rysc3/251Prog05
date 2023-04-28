//6
package examples.intro;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseState {
    private double x, y;

    public EventHandler<MouseEvent> getMouseHandler() {
        return event -> {
            x = event.getX();
            y = event.getY();
        };
    }

    public double getX() { return x; }
    public double getY() { return y; }
}
