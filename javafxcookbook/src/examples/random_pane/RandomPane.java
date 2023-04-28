package examples.random_pane;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.concurrent.ThreadLocalRandom;

public class RandomPane extends Pane {
    private boolean randomize = true;

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        if (randomize) {
            for (Node node : getManagedChildren()) {
                node.setLayoutX(ThreadLocalRandom.current().nextDouble(getWidth()));
                node.setLayoutY(ThreadLocalRandom.current().nextDouble(getHeight()));
            }
            randomize = false;
        }
    }
}
