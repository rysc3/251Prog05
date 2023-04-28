package examples.todo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TODO");

        BorderPane root = new BorderPane();

        VBox todos = new VBox(10);

        HBox newTodoHbox = new HBox(10);
        Label newTodoLabel = new Label("Add New Todo:");
        TextField newTodoText = new TextField();
        Button submit = new Button("Submit");
        newTodoHbox.getChildren().addAll(newTodoLabel, newTodoText, submit);

        root.setCenter(todos);
        root.setTop(newTodoHbox);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        submit.setOnAction(event -> {
            Todo newTodo = new Todo(todos, newTodoText.getText());
            newTodoText.setText("");
            todos.getChildren().add(newTodo.getRoot());
        });
    }
}
