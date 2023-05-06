
/*
 * @author Ryan Scherbarth
 * cs251L
 * 4/17/23
 */
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.Duration;
import javafx.scene.layout.Pane;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Setups up all the JavaFX GUI controls and creates instances of
   * all the helper classes.
   *
   * @param primaryStage the primary stage for this application, onto which
   *                     the application scene can be set.
   *                     Applications may create other stages, if needed, but they
   *                     will not be
   *                     primary stages.
   * @throws Exception
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    // Always make sure to set the title of the window
    primaryStage.setTitle("Key Shooter");
    // Width/height variables so that we can mess with the size of the window
    double width = 800;
    double height = 1000;
    // BorderPane
    // (https://openjfx.io/javadoc/18/javafx.graphics/javafx/scene/layout/BorderPane.html)
    // Provides the basis which we basis the rest of the GUI on
    BorderPane window = new BorderPane();
    // VBox for the top part of the GUI
    VBox topVBox = new VBox(5);
    topVBox.setAlignment(Pos.CENTER);
    // Label which displays the score
    Label scoreLabel = new Label("0");
    scoreLabel.setFont(new Font(40));
    // Label which displays the currently typed letters
    Label typedLabel = new Label();
    typedLabel.setFont(new Font(40));
    // Add them all to the VBox
    topVBox.getChildren().addAll(scoreLabel, typedLabel);
    // Put them in the top of the BorderPane
    window.setTop(topVBox);
    // Create an instance of our helper Words class

    // Changed path to correct place.
    Pane root = new Pane();
    Words words = new Words("../docs/words.txt", width, (height * 3 / 4),
        scoreLabel, typedLabel, root);
    window.setCenter(root);

    // Put it in the middle of the BorderPane
    window.setCenter(words.getWordsPane());
    // words.getWordsPane().toFront();
    window.setStyle("-fx-background-color: #FFFFFF;");

    // Create a VBox for the keyboard and slider
    VBox keyBoardWindow = new VBox(10);
    // Create an instance of our helper class Keyboard
    Keyboard keyboard = new Keyboard((width - 150), (height / 6), 10);
    // Add a horizontal line above the keyboard to create clear separation
    keyBoardWindow.getChildren().addAll(new Separator(Orientation.HORIZONTAL), keyboard.getKeyboard());

    // Create a slider for adjusting the rate at which words generate
    Slider slider = new Slider(0, 5, 1); // minimum value = 0, maximum value = 5, default value = 1
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(1);
    slider.setMinorTickCount(0);
    slider.setSnapToTicks(true);

    // Add the slider to the VBox
    keyBoardWindow.getChildren().add(slider);

    // Put it in the bottom right of the BorderPane
    window.setBottom(keyBoardWindow);
    BorderPane.setAlignment(keyBoardWindow, Pos.BOTTOM_RIGHT);

    Scene scene = new Scene(window, width, height);
    // The scene is the best place to capture keyboard input
    // First get the KeyCode of the event
    // Then start the fill transition, which blinks the key
    // Then add it to the typed letters
    scene.setOnKeyPressed(event -> {
      KeyCode keyCode = event.getCode();
      // Well that's dumb, have to specifically say you want javafx.util seconds
      // rather
      // than just Duration.seconds, which will give you javafx.scene......
      keyboard.startFillTransition(keyCode, javafx.util.Duration.seconds(0.5));
      words.addTypedLetter(keyCode);
    });
    // Set the scene
    primaryStage.setScene(scene);
    // Showtime!
    primaryStage.show();
    // We also need an AnimationTimer to create words on the
    // screen every 3 seconds. This is done by call createWord
    // from the Words class. // We also need an AnimationTimer to create words on
    // the screen
    // every X seconds, where X is the value of the slider.
    AnimationTimer timer = new AnimationTimer() {
      private long lastTime = 0;
      private double delay = 3; // Default delay

      @Override
      public void handle(long currentTime) {
        // Check if X seconds have passed since the last time a word was created,
        // where X is the current value of the slider.
        if (Duration.ofNanos(currentTime - lastTime).compareTo(Duration.ofSeconds((long) delay)) >= 0) {
          words.createWord();
          lastTime = currentTime;
        }
      }
    };
    // Add a listener to the slider to update the delay time when the slider value
    // changes.
    slider.valueProperty().addListener((observable, oldValue, newValue) -> {
      timer.stop();
      //  delay = newValue.doubleValue();
      timer.start();
      // timer.delayProperty
    });
    timer.start();

    // Button to stop animations and display score
Label stopLabel = new Label("X");
stopLabel.setFont(new Font(20));
stopLabel.setOnMouseClicked(e -> {
  timer.stop(); // Stop the AnimationTimer
  double score = Double.parseDouble(scoreLabel.getText());
  double timeInMinutes = 4;
  double wpm = score / timeInMinutes;
  // Create a VBox to hold the score, WPM, and buttons
  VBox scoreVBox = new VBox(20);
  scoreVBox.setAlignment(Pos.CENTER);
  Label scoreText = new Label("Score: " + score);
  scoreText.setFont(new Font(30));
  Label wpmText = new Label("Words per minute: " + wpm);
  wpmText.setFont(new Font(30));
  // Create buttons to quit and play again
  Button quitButton = new Button("Quit");
  quitButton.setOnAction(event -> {
    primaryStage.close(); // Close the window
  });
  Button playAgainButton = new Button("Play Again");
  playAgainButton.setOnAction(event -> {
    // Restart the game
    scoreLabel.setText("0");
    typedLabel.setText("");
    // words.reset();
    timer.start();
    primaryStage.setScene(scene);
  });
  scoreVBox.getChildren().addAll(scoreText, wpmText, quitButton, playAgainButton);
  Scene scoreScene = new Scene(scoreVBox, width, height);
  primaryStage.setScene(scoreScene);
});
topVBox.getChildren().add(stopLabel);

  }
}
