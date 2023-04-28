
/*
 * @author Ryan Scherbarth
 * cs251L
 * 4/17/23
 */
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.*;

public class Keyboard {
  // 2 Dimensional list representing the rows of keys on the keyboard
  // Letter keys only
  private final List<List<KeyCode>> keyCodes;
  // Map that is used to access the keys JavaFX representation
  private final Map<KeyCode, WordBox> keyCodeToWordBox;
  // JavaFX control that represents the keyboard on the screen
  private final VBox keyboard;
  // Color that the keys are by default
  private static final Color from = Color.color(0.9, 0.9, 0.9);
  // Color that the keys become when pressed
  private static final Color to = Color.color(0.3, 0.3, 0.8);

  public Keyboard(double width, double height, double spacing) {
    keyCodes = initializeKeys();
    keyCodeToWordBox = new HashMap<>();

    keyboard = initializeKeyboard(width, height, keyCodes, spacing);
  }

  public VBox getKeyboard() {
    return keyboard;
  }

  /**
   * First checks if the given keyCode exists in the keyCodeToWordBox.
   * If it does then it starts a FillTransition
   * (https://openjfx.io/javadoc/18/javafx.graphics/javafx/animation/FillTransition.html)
   * to go from the from color to the to color.
   * If the keyCode does not exist then it does nothing.
   *
   * @param keyCode KeyCode to lookup in the map and flash
   */
  public void startFillTransition(KeyCode keyCode) {

    WordBox wordBox = keyCodeToWordBox.get(keyCode);
    if (wordBox != null) {
      FillTransition fillTransition = new FillTransition(Duration.seconds(0.2), wordBox.getRect(), from, to);
      fillTransition.setAutoReverse(true);
      fillTransition.play();
    }

  }

  /**
   * Simply creates the 2D list that represents the keyboard.
   * Each row is an element of the outer list and each inner list
   * contains all the letter keys in that row. Only contains
   * 3 rows. All letters are uppercase.
   *
   * @return 2D list representing the letters on the keyboard
   */
  private List<List<KeyCode>> initializeKeys() {

    List<List<KeyCode>> keys = new ArrayList<>();

    keys.add(List.of(KeyCode.Q, KeyCode.W, KeyCode.E, KeyCode.R, KeyCode.T, KeyCode.Y, KeyCode.U, KeyCode.I, KeyCode.O,
        KeyCode.P));
    keys.add(
        List.of(KeyCode.A, KeyCode.S, KeyCode.D, KeyCode.F, KeyCode.G, KeyCode.H, KeyCode.J, KeyCode.K, KeyCode.L));
    keys.add(List.of(KeyCode.Z, KeyCode.X, KeyCode.C, KeyCode.V, KeyCode.B, KeyCode.N, KeyCode.M));

    return keys;

  }

  /**
   * Creates the JavaFX control that visualized the keyboard on the screen
   * Also initializes the keyCodeToWordBox map as it goes.
   * It deduces the size of each key using the 2D list and the
   * width parameter. Then creates a VBox and sets its width/height
   * and centers it. Then loops over the 2D list and creates JavaFX
   * controls, WordBox, to represent each key and adds them to HBoxes.
   * The adds the row HBox to the VBox. It also adds the WordBox to the
   * map. Then it moves on to the next row.
   *
   * @param width    Width of the screen
   * @param height   Height of the screen
   * @param keyCodes 2D list that holds all the letters on the keyboard
   * @param spacing  Space between each key
   * @return JavaFX control that visualizes the keyboard on the screen
   */
  private VBox initializeKeyboard(double width, double height, List<List<KeyCode>> keyCodes, double spacing) {
    // Calculate the size of each key
    double keyWidth = (width - (2 * spacing)) / 10.0;
    double keyHeight = (height - (4 * spacing)) / 3.0;

    // Create the VBox that will contain all the rows of keys
    VBox keyboard = new VBox(spacing);
    keyboard.setAlignment(Pos.CENTER);

    // Loop over each row of keys
    for (List<KeyCode> row : keyCodes) {
      // Create an HBox to hold all the keys in this row
      HBox rowBox = new HBox(spacing);
      rowBox.setAlignment(Pos.CENTER);

      // Loop over each key in this row
      for (KeyCode keyCode : row) {
        // Create a new WordBox for this key
        // WordBox wordBox = new WordBox(keyCode.getName(), keyWidth, keyHeight, from,
        // to);
        WordBox wordBox = new WordBox(width, height, keyCode.getName(), from);
        // Add the WordBox to the map
        keyCodeToWordBox.put(keyCode, wordBox);
        // Add the WordBox to the row HBox

        // wrapping wordBox in a pane to be able to add to rowBox
        // rowBox.add() only takes Nodes as input, can't pass a custom class
        Pane wordPane = new Pane(wordBox);
        rowBox.getChildren().add(wordPane);
      }

      // Add the row HBox to the keyboard VBox
      keyboard.getChildren().add(rowBox);
    }

    // Set the width and height of the keyboard VBox
    keyboard.setPrefSize(width, height);

    return keyboard;
  }
}
