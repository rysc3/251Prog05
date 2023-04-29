
/*
 * @author Ryan Scherbarth
 * cs251L
 * 4/17/23
 */
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
    wordsPane.getChildren().remove(wordBox);
    activeWords.remove(wordBox);
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
    if (words.isEmpty()) {
      return; // empty list
    }

    int randomIndex = ThreadLocalRandom.current().nextInt(words.size());
    String randomWord = words.get(randomIndex);
    randomWord = randomWord.toUpperCase();
    // Printing out the word to the terminal each time for debugging
    System.out.println(randomWord);
    System.out.println("[active]" + activeWords);

    char startingChar = randomWord.charAt(0);
    double startX, startY, endX, endY;
    if (ThreadLocalRandom.current().nextBoolean()) {
      startX = ThreadLocalRandom.current().nextDouble(width);
      startY = ThreadLocalRandom.current().nextBoolean() ? 0 : height- 600;
      endX = ThreadLocalRandom.current().nextDouble(width);
      endY = startY == 0 ? height : 0;
    } else {
      startX = ThreadLocalRandom.current().nextBoolean() ? 0 : width;
      startY = ThreadLocalRandom.current().nextDouble(height - 600);
      endX = startX == 0 ? width : 0;
      endY = ThreadLocalRandom.current().nextDouble(height - 600);
    }

    WordBox wordBox = new WordBox(60, 60, randomWord, Color.TRANSPARENT, 1);

    activeWords.add(wordBox);

    wordBox.getRect().setVisible(true);
    wordBox.getWordBox().setTranslateX(startX);
    wordBox.getWordBox().setTranslateY(startY);
    wordsPane.getChildren().add(wordBox.getWordBox());

    Timeline timeline = new Timeline();
    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(10),
        new KeyValue(wordBox.getWordBox().translateXProperty(), endX),
        new KeyValue(wordBox.getWordBox().translateYProperty(), endY)));
    timeline.setOnFinished(e -> {
      removeWord(wordBox);
    });
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
    // for (WordBox wordBox : activeWords) {
    // if (wordBox.getWord().equals(s)) {
    // score++;
    // scoreLabel.setText("Score: " + score);
    // removeWord(wordBox);
    // typed.clear();
    // typedLabel.setText("");
    // return true;
    // }
    // }
    // typedLabel.setText(s);
    // return false;
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
