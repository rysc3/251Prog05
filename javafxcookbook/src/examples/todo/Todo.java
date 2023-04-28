package examples.todo;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class Todo {
    private final HBox root;
    private final CheckBox done;
    private final Label text;

    public Todo(Pane holder, String str) {
        root = new HBox(10);
        done = new CheckBox();
        text = new Label(str);

        root.getChildren().addAll(done, text);

        done.setOnAction(event -> {
            if (done.isSelected()) {
                holder.getChildren().remove(getRoot());
            }
        });
    }

    public Node getRoot() {
        return root;
    }
}
