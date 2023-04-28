package examples.calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.*;

public class Calculator extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Calculator");

        BorderPane calculator = new BorderPane();
        Text display = new Text();
        display.setFont(new Font(40));
        GridPane buttons = new GridPane();

        double buttonWidth = 50;
        double buttonHeight = 50;

        int row = 0;
        int col = 0;
        for (int i = 1; i <= 9; i++) {
            buttons.add(new CalculatorButton(display, buttonWidth, buttonHeight, i).getJavaFXButton(), col, row);
            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }

        row++;

        buttons.add(new CalculatorButton(display, buttonWidth, buttonHeight, 0).getJavaFXButton(),
                1, row);

        final int minCol = 0;
        final int maxCol = 2;
        final int midCol = (minCol + maxCol) / 2;

        Map<String, Pair<Integer, Integer>> operatorsColumns = new HashMap<>();
        operatorsColumns.put("+", new Pair<>(minCol, row+1));
        operatorsColumns.put("-", new Pair<>(maxCol, row+1));
        operatorsColumns.put("*", new Pair<>(minCol, row+2));
        operatorsColumns.put("/", new Pair<>(maxCol, row+2));

        for (Map.Entry<String, Pair<Integer, Integer>> opColRow : operatorsColumns.entrySet()) {
            CalculatorButton calcButton = new CalculatorButton(display, buttonWidth, buttonHeight, opColRow.getKey());
            buttons.add(calcButton.getJavaFXButton(),
                    opColRow.getValue().getKey(), opColRow.getValue().getValue());
        }

        Button equal = new Button("=");
        equal.setMinSize(buttonWidth, buttonHeight);

        buttons.add(equal, midCol, row+3);

        calculator.setTop(display);
        calculator.setCenter(buttons);

        Scene scene = new Scene(calculator);
        primaryStage.setScene(scene);
        primaryStage.show();

        equal.setOnAction(event -> {
            double answer = Parser.interpretInfix(display.getText());
            display.setText(Double.toString(answer));
        });
    }
}
