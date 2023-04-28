package examples.calculator;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CalculatorButton {
    private final Button button;

    public CalculatorButton(Text display, double width, double height, Object data) {
        button = new Button(data.toString());
        button.setMinSize(width, height);
        button.setOnAction(event -> display.setText(display.getText() + data));
    }

    public Button getJavaFXButton() {
        return button;
    }
}
