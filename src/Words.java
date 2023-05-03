
/*
 * @author Ryan Scherbarth
 * cs251L
 * 4/17/23
 */
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.naming.TimeLimitExceededException;

public class Words {
  // Pane
  // (https://openjfx.io/javadoc/18/javafx.graphics/javafx/scene/layout/Pane.html)
  // which represents the floating words part of the game
  private final Pane wordsPane;
  // List of all available words
  private final List<String> words;
  // List of all JavaFX floating words currently on the screen
  private final List<WordBox> activeWords;
  // List of all keys that have been pressed since the last correct word
  private final List<KeyCode> typed;
  // JavaFX Label which shows the score on the screen
  private final Label scoreLabel;
  // Keeps track of the number of correct words
  private int score = 0;
  // JavaFX Label which shows what the user has typed since the last correct word
  private final Label typedLabel;
  // Width/height of the screen
  private final double width;
  private final double height;
  // Pane where the words will be added
  private final Pane root;

  public Words(String path, double width, double height,
      Label scoreLabel, Label typedLabel, Pane root) throws FileNotFoundException {
    wordsPane = new Pane();
    wordsPane.setPrefWidth(width);
    wordsPane.setPrefHeight(height * 0.75);

    this.words = Utils.readWords(path);

    activeWords = new ArrayList<>();
    typed = new ArrayList<>();

    this.scoreLabel = scoreLabel;
    this.typedLabel = typedLabel;

    this.width = width;
    this.height = height;

    this.root = root;
  }

  public Pane getWordsPane() {
    return wordsPane;
  }

  /**
   * Removes the wordBox from the wordsPane as well as
   * removing it from activeWords.
   *
   * @param wordBox WordBox to remove
   */
  private void removeWord(WordBox wordBox) {
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.25), wordBox.getWordBox());
    fadeTransition.setFromValue(1);
    fadeTransition.setToValue(0);
    fadeTransition.setOnFinished((ActionEvent event) -> {
        wordsPane.getChildren().remove(wordBox.getWordBox());
        activeWords.remove(wordBox);
    });
    fadeTransition.play();
}


  /**
   * Creates a random floating word.
   * Choses a random word from the list of words.
   * Then chooses a starting point on any edge of the screen.
   * Then creates a Timeline
   * (https://openjfx.io/javadoc/18/javafx.graphics/javafx/animation/Timeline.html)
   * that moves the WordBox from its starting point to a random ending
   * point over 10 seconds.
   */
  public void createWord() {
    Random random = new Random();
    String word = words.get(random.nextInt(words.size()));
    // public WordBox(double sizeL, double sizeW, String word, Color color, double
    // scalingFactor)
    WordBox activeWord = new WordBox((height / 20), (width / 2), word, Color.TRANSPARENT, 1);

    // Keep words within the boundaries of the screen
    double rightEnd = width - (activeWord.getRect().getWidth() * 4);
    double bottomEnd = height - (height / 4) - activeWord.getRect().getHeight();
    double xCoord = random.nextInt(2) * rightEnd;
    double yCoord = random.nextDouble(2) * bottomEnd; // bottomEnd
    activeWord.getWordBox().relocate(xCoord, yCoord);
    wordsPane.getChildren().add(activeWord.getWordBox());
    activeWords.add(activeWord);

    // generate random starting point for the word
    final Timeline timeline = new Timeline();
    timeline.setCycleCount(2);
    timeline.setAutoReverse(true);

    // beginning coordinates
    KeyValue xStart = new KeyValue(activeWord.getWordBox().translateXProperty(), 0);
    KeyValue yStart = new KeyValue(activeWord.getWordBox().translateYProperty(), 0);
    Duration startTime = Duration.ZERO;
    KeyFrame beginKeyFrame = new KeyFrame(startTime, xStart, yStart);

    // randomly end each coordinate
    KeyValue xEnd = new KeyValue(activeWord.getWordBox().translateXProperty(),
        random.nextDouble(-xCoord, rightEnd - xCoord));
    // KeyValue yEnd = new KeyValue(activeWord.getWordBox().translateYProperty(),
    // random.nextDouble(-yCoord, bottomEnd - yCoord));
    // KeyValue yEnd = new KeyValue(activeWord.getWordBox().translateYProperty(),
    // random.nextDouble(yCoord));
    KeyValue yEnd = new KeyValue(activeWord.getWordBox().translateYProperty(), 7);
    Duration endTime = Duration.seconds(5);

    KeyFrame keyFrameEnd = new KeyFrame(endTime, xEnd, yEnd);

    // add keyframes
    timeline.getKeyFrames().addAll(beginKeyFrame, keyFrameEnd);
    timeline.setOnFinished(event -> removeWord(activeWord));
    timeline.play();
  }

  /**
   * Adds the keyCode to typed if it is a letter key.
   * Removes the first element of typed if it is the backspace key.
   * Either way it checks for a correct word and updates the typedLabel.
   *
   * @param keyCode KeyCode to add to the state
   */
  public void addTypedLetter(KeyCode keyCode) {
    if (keyCode.isLetterKey()) {
      typed.add(keyCode);
      String typedString = getTypedString();
      if (checkForCorrectWord(typedString)) {
        typed.clear();
        typedLabel.setText("");
      }
    } else if (keyCode == KeyCode.BACK_SPACE && !typed.isEmpty()) {
      typed.remove(typed.size() - 1); // remove current element
      typedLabel.setText(typed.stream().map(KeyCode::getName).collect(Collectors.joining("")));
    }
  }

  /*
   * helper method that works with addTypedLetter to check if the word
   * is complete
   */
  private String getTypedString() {
    StringBuilder sb = new StringBuilder();
    for (KeyCode keyCode : typed) {
      if (keyCode.isLetterKey()) {
        sb.append(keyCode.getChar());
      }
    }
    return sb.toString();
  }

  /**
   * Checks if the given String is equal to any of the currently
   * active words. If it is then it updates the score and scoreLabel.
   * It also removes the wordBox and clears the typed list.
   *
   * @param s Word to check
   */
  private boolean checkForCorrectWord(String s) {
    for (WordBox wordBox : activeWords) {
      if (wordBox.getWord().equals(s)) {
        score++;
        scoreLabel.setText("Score: " + score);
        typedLabel.setText("");
        removeWord(wordBox); // add this line to remove the word from the screen
        return true;
      }
    }
    typedLabel.setText(s);
    return false;
  }
}
