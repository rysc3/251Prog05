
/*
 * @author Ryan Scherbarth
 * cs251L
 * 4/17/23
 */
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class WordBox {
  private final StackPane wordBox;
  private final Rectangle rect;
  private final String word;

  public WordBox(double sizeL, double sizeW, String word, Color color, double scalingFactor) {

    wordBox = new StackPane();
    rect = new Rectangle(sizeL * scalingFactor, sizeW * scalingFactor, color);
    this.word = word.toUpperCase();
    Label text = new Label(this.word);
    text.setFont(new Font(sizeL)); // * scalingFactor / 2
    wordBox.getChildren().addAll(rect, text);
  }

  public StackPane getWordBox() {
    return wordBox;
  }

  public Rectangle getRect() {
    return rect;
  }

  public String getWord() {
    return word;
  }

  /*
   * Creates a new wordBox on top of the existing one the function is called on,
   * and mimics everything beside the font color. Essentially copying a second
   * wordBox on top to change the color of the letters as you get them correct.
   */
  public void showTyped(String input) {
    // Label text = (Label) wordBox.getChildren().get(1); // Get the Label node from
    // the StackPane
    // StringBuilder typedText = new StringBuilder();
    // for (int i = 0; i < input.length(); i++) {
    // if (i < text.getText().length() && input.charAt(i) ==
    // text.getText().charAt(i)) {
    // // typedText.append("<span style='color:
    // purple;'>").append(input.charAt(i)).append("</span>");
    // // Color.color(0.3, 0.3, 0.8); <- the color we want letters to change to when
    // typed
    // int r = (int) (0.3 * 255);
    // int g = (int) (0.3 * 255);
    // int b = (int) (0.8 * 255);
    // // typedText.append("\033[38;2;" + r + ";" + "g" + ";" + "b" + "m"); //
    // should hopefully make the purple we want
    // // System.out.println("I wonder: " + typedText.toString());
    // } else {
    // typedText.append(text.getText().charAt(i));
    // }
    // }
    // if (input.length() < text.getText().length()) {
    // typedText.append(text.getText().substring(input.length()));
    // }
    // text.setText(typedText.toString());
  }
}
