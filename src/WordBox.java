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

  public WordBox(double sizeL, double sizeW, String word, Color color) {
    wordBox = new StackPane();
    rect = new Rectangle(sizeL, sizeW, color);
    this.word = word.toUpperCase();
    Label text = new Label(this.word);
    text.setFont(new Font(sizeL - 2));
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
}
