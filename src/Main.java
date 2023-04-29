
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
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.Duration;

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
    Words words = new Words("../docs/words.txt", width, (height * 3) / 4,
        scoreLabel, typedLabel);
    // Put it in the middle of the BorderPane
    window.setCenter(words.getWordsPane());
    // Create a VBox for the keyboard
    VBox keyBoardWindow = new VBox(10);
    // Create an instance of our helper class Keyboard
    Keyboard keyboard = new Keyboard((width - 150), (height / 5), 10);
    // Add a horizontal line above the keyboard to create clear seperation
    keyBoardWindow.getChildren().addAll(new Separator(Orientation.HORIZONTAL), keyboard.getKeyboard());
    // Put it in the bottom of the BorderPane
    window.setBottom(keyBoardWindow);
    // Create the scene
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
    // from the Words class.
    AnimationTimer timer = new AnimationTimer() {
      private long lastTime = 0;

      @Override
      public void handle(long currentTime) {
        // Check if 3 seconds have passed since the last time a word was created
        if (Duration.ofNanos(currentTime - lastTime).compareTo(Duration.ofSeconds(3)) >= 0) {
          words.createWord();
          lastTime = currentTime;
        }
      }
    };
    timer.start();
  }
}
