package examples.random_pane;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Text;

public class SubjectText {
    // Start with concrete TextField
//    private final TextField text;
    private final TextInputControl text;

    public SubjectText(TextInputControl text) {
        this.text = text;
    }

    public void bindToText(TextInputControl text) {
        text.textProperty().bind(this.text.textProperty());
    }
}
